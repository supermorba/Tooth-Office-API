package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;
import org.odk.tooth_office.Entity.Creneau;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Entity.RendezVous;
import org.odk.tooth_office.Enum.EtatRdv;
import org.odk.tooth_office.Enum.TypeRdv;
import org.odk.tooth_office.Repository.CreneauRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Repository.RendezVousRepository;
import org.odk.tooth_office.Repository.SecretaireRepository;
import org.odk.tooth_office.Services.Interfaces.RendezVousService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final SecretaireRepository secretaireRepository;

    /**
     * Prendre un rendez-vous
     */
    @Override
    public RendezVousResponseDTO prendreRendezVous(RendezVousRequestDTO dto) {
        log.info("Prise de rendez-vous pour le patient {} avec le dentiste {}",
                dto.getPatientId(), dto.getDentisteId());

        validateRendezVousRequest(dto);

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID: " + dto.getPatientId()));

        Dentiste dentiste = dentisteRepository.findById(dto.getDentisteId())
                .orElseThrow(() -> new RuntimeException("Dentiste non trouvé avec l'ID: " + dto.getDentisteId()));

        Creneau creneau = null;
        if (dto.getCreneauId() != null) {
            creneau = creneauRepository.findById(dto.getCreneauId())
                    .orElseThrow(() -> new RuntimeException("Créneau non trouvé avec l'ID: " + dto.getCreneauId()));

            if (!creneau.getDentiste().getId_utilisateur().equals(dentiste.getId_utilisateur())) {
                throw new RuntimeException("Le créneau n'appartient pas au dentiste spécifié");
            }

            if (!creneau.isDisponible()) {
                throw new RuntimeException("Le créneau n'est pas disponible");
            }
        }

        if (rendezVousRepository.existsRendezVousAMemeHeure(dto.getPatientId(), dto.getDateRdv())) {
            throw new RuntimeException("Le patient a déjà un rendez-vous à cette heure");
        }

        RendezVous rendezVous = new RendezVous();
        rendezVous.setDateRdv(dto.getDateRdv());
        rendezVous.setNotes(dto.getNotes());
        rendezVous.setMotif(dto.getNotes());
        rendezVous.setPatient(patient);
        rendezVous.setDentiste(dentiste);
        rendezVous.setCreneau(creneau);
        rendezVous.setEtatRdv(EtatRdv.EN_ATTENTE);

        try {
            rendezVous.setTypeRdv(TypeRdv.valueOf(dto.getTypeRdv().toUpperCase()));
        } catch (IllegalArgumentException e) {
            rendezVous.setTypeRdv(TypeRdv.ENLIGNE);
        }

        rendezVous.setCreatedAt(LocalDateTime.now());
        rendezVous.setUpdatedAt(LocalDateTime.now());

        RendezVous rdvSauvegarde = rendezVousRepository.save(rendezVous);

        if (creneau != null) {
            creneau.setDisponible(false);
            creneauRepository.save(creneau);
        }

        log.info("Rendez-vous créé avec l'ID: {}", rdvSauvegarde.getIdRendezVous());
        return mapToResponseDTO(rdvSauvegarde);
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