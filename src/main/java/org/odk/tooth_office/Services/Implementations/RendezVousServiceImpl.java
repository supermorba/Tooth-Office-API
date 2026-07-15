package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odk.tooth_office.DTO.CreneauDtoSurplace;
import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;
import org.odk.tooth_office.Entity.*;
import org.odk.tooth_office.Enum.EtatRdv;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.TypeRdv;
import org.odk.tooth_office.Repository.*;
import org.odk.tooth_office.Services.Interfaces.RendezVousService;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final DentisteRepository dentisteRepository;
    private final CreneauRepository creneauRepository;
    private CreneauServiceImpl creneauService;
    private final SecretaireRepository secretaireRepository;
    private final ConsultationRepository consultationRepository;
    private final UtilisateurRepository utilisateurRepository;

    /**
     * Prendre un rendez-vous
     */
    @Override
    @Transactional
    public RendezVousResponseDTO prendreRendezVous(RendezVousRequestDTO dto) {


        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient introuvable avec l'ID : " + dto.getPatientId()));

        Dentiste dentiste = dentisteRepository.findById(dto.getDentisteId())
                .orElseThrow(() -> new RuntimeException("Dentiste introuvable avec l'ID : " + dto.getDentisteId()));

        Creneau creneau;
        TypeRdv typeRdv = TypeRdv.valueOf(dto.getTypeRdv());

        // Cas d'un rendez-vous SUR PLACE
        if (typeRdv == TypeRdv.SURPLACE && dto.getCreneauId() == null) {

            CreneauDtoSurplace creneauDto = new CreneauDtoSurplace(
                    dto.getDateRdv().toLocalDate(),
                    dto.getDateRdv().toLocalTime(),
                    dto.getDateRdv().toLocalTime().plusMinutes(40),
                    false,
                    dto.getDentisteId()
            );
            if(creneauRepository.existsCreneauAtDateTimeForDentiste(creneauDto.dentisteId(), creneauDto.date(), creneauDto.heureDebut())){
                throw new RuntimeException("Ce creneau est deja reservé !!!!");
            }

            creneau = creneauService.creerCreneauSurplace(creneauDto);

        } else {

            // Cas classique : réservation d'un créneau existant
            creneau = creneauRepository.findById(dto.getCreneauId())
                    .orElseThrow(() -> new RuntimeException("Créneau introuvable avec l'ID : " + dto.getCreneauId()));

            if (!creneau.isDisponible()) {
                throw new IllegalStateException("Ce créneau horaire est déjà réservé.");
            }

            creneau.setDisponible(false);
            creneauRepository.save(creneau);
        }

        // Création du rendez-vous
        RendezVous rdv = new RendezVous();
        rdv.setDateRdv(dto.getDateRdv());
        rdv.setNotes(dto.getNotes());
        rdv.setTypeRdv(TypeRdv.valueOf(dto.getTypeRdv()));
        rdv.setEtatRdv(EtatRdv.EN_ATTENTE);
        rdv.setPatient(patient);
        rdv.setDentiste(dentiste);

        // Association du créneau
        rdv.setCreneau(creneau);

        RendezVous savedRdv = rendezVousRepository.save(rdv);

        return mapToResponseDTO(savedRdv);
    }

    /**
     * Annuler un rendez-vous
     */
    @Override
    public void annulerRendezVous(Long rdvId) {
        log.info("Annulation du rendez-vous {}", rdvId);

        RendezVous rendezVous = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID: " + rdvId));

        if (rendezVous.getEtatRdv() == EtatRdv.ANNULE) {
            log.warn("Le rendez-vous {} est déjà annulé", rdvId);
            return;
        }

        rendezVous.setEtatRdv(EtatRdv.ANNULE);
        rendezVous.setUpdatedAt(LocalDateTime.now());
        rendezVousRepository.save(rendezVous);

        if (rendezVous.getCreneau() != null) {
            Creneau creneau = rendezVous.getCreneau();
            creneau.setDisponible(true);
            creneauRepository.save(creneau);
            log.info("Créneau {} libéré suite à l'annulation du RDV", creneau.getIdCreneau());
        }

        log.info("Rendez-vous {} annulé avec succès", rdvId);
    }

    /**
     * Modifier le statut d'un rendez-vous
     */
    @Override
    public RendezVousResponseDTO modifierStatutRdv(Long rdvId, String nouvelEtat) {
        log.info("Modification du statut du rendez-vous {} en {}", rdvId, nouvelEtat);

        RendezVous rendezVous = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID: " + rdvId));

        try {
            EtatRdv newStatus = EtatRdv.valueOf(nouvelEtat.toUpperCase());
            rendezVous.setEtatRdv(newStatus);
            rendezVous.setUpdatedAt(LocalDateTime.now());
            RendezVous rdvMisAJour = rendezVousRepository.save(rendezVous);
            log.info("Statut du rendez-vous {} mis à jour en {}", rdvId, newStatus);
            return mapToResponseDTO(rdvMisAJour);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("État invalide: " + nouvelEtat);
        }
    }

    /**
     * Obtenir l'historique des rendez-vous d'un patient
     */
    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponseDTO> obtenirRdvParPatient(Long patientId) {
        log.info("Récupération des RDV du patient {}", patientId);

        patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID: " + patientId));

        List<RendezVous> rendezVous = rendezVousRepository.findByPatientId(patientId);
        return rendezVous.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtenir le planning d'un dentiste
     */
    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponseDTO> obtenirRdvParDentiste(Long dentisteId) {
        log.info("Récupération des RDV du dentiste {}", dentisteId);

        dentisteRepository.findById(dentisteId)
                .orElseThrow(() -> new RuntimeException("Dentiste non trouvé avec l'ID: " + dentisteId));

        LocalDateTime debut = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = debut.plusDays(1);

        List<RendezVous> rendezVous = rendezVousRepository.findRdvParDentisteEtPeriode(dentisteId, debut, fin);
        return rendezVous.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RendezVousResponseDTO> findAllRdvOfCabinet(Long id) {
        Optional<Utilisateur> utilisateurOpt= utilisateurRepository.findById(id);
        if(utilisateurOpt.isEmpty()){
            throw new RuntimeException("Cet utilisateur n'existe pas !!!!");
        }
        Utilisateur utilisateur = utilisateurOpt.get();

        if(utilisateur.getRole() != RoleEnum.SECRETAIRE){
            throw new RuntimeException("Vous n'êtes pas autorisé à voir ces infos !!!!");
        }
        Optional<Secretaire> secretaireOpt = secretaireRepository.findById(utilisateur.getId_utilisateur());
        if(secretaireOpt.isEmpty()){
            throw new RuntimeException("Cet secretaire n'existe pas !!!!");
        }
        Secretaire secretaire = secretaireOpt.get();

        List<RendezVous> rendezVous= rendezVousRepository.findRdvByCabinet((long) secretaire.getCabinet().getIdCabinet());

        return rendezVous.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Response deleteRdv(Long idRdv) {
        try {

            if (!rendezVousRepository.existsById(idRdv)) {
                return Response.error("Ce rendez-vous n'existe pas.");
            }
            if(consultationRepository.isConsultationExistByRdv(idRdv)){
                return Response.error("Impossible!!!! Ce rendez-vous possède deja une consultation .");
            }

            rendezVousRepository.deleteById(idRdv);

            return Response.succes("Rendez-vous supprimé avec succès.", true);

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la suppression du rendez-vous.");
        }
    }

    /**
     * Valide les données du DTO
     */
    private void validateRendezVousRequest(RendezVousRequestDTO dto) {
        if (dto.getDateRdv() == null) {
            throw new RuntimeException("La date du rendez-vous est obligatoire");
        }

        if (dto.getPatientId() == null) {
            throw new RuntimeException("L'ID du patient est obligatoire");
        }

        if (dto.getDentisteId() == null) {
            throw new RuntimeException("L'ID du dentiste est obligatoire");
        }

        if (dto.getDateRdv().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La date du rendez-vous ne peut pas être dans le passé");
        }
    }

    /**
     * Mappe une entité RendezVous vers un DTO de réponse
     */
    private RendezVousResponseDTO mapToResponseDTO(RendezVous rendezVous) {
        RendezVousResponseDTO dto = new RendezVousResponseDTO();
        dto.setId(rendezVous.getIdRendezVous());
        dto.setDateRdv(rendezVous.getDateRdv());
        dto.setNotes(rendezVous.getNotes());
        dto.setEtatRdv(rendezVous.getEtatRdv().toString());
        dto.setTypeRdv(rendezVous.getTypeRdv().toString());

        dto.setPatientId(rendezVous.getPatient().getId_utilisateur());
        dto.setPatientNom(rendezVous.getPatient().getNom() + " " + rendezVous.getPatient().getPrenom());

        dto.setDentisteId(rendezVous.getDentiste().getId_utilisateur());
        dto.setDentisteNom(rendezVous.getDentiste().getNom() + " " + rendezVous.getDentiste().getPrenom());

        if (rendezVous.getCreneau() != null) {
            dto.setCreneauId(rendezVous.getCreneau().getIdCreneau());
        }

        return dto;
    }
}