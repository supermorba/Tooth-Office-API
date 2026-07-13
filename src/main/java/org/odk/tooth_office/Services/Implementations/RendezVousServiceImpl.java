package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;
import org.odk.tooth_office.Entity.Creneau;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Entity.RendezVous;
import org.odk.tooth_office.Enum.EtatRdv;
import org.odk.tooth_office.Enum.TypeRdv;
import org.odk.tooth_office.Services.Interfaces.RendezVousService;
import org.odk.tooth_office.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rdvRepository;
    private final CreneauRepository creneauRepository;
    private final PatientRepository patientRepository;
    private final DentisteRepository dentisteRepository;



    @Override
    @Transactional
    public RendezVousResponseDTO prendreRendezVous(RendezVousRequestDTO dto) {
        // 1. Vérification et récupération du Patient et du Dentiste
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient introuvable avec l'ID : " + dto.getPatientId()));

        Dentiste dentiste = dentisteRepository.findById(dto.getDentisteId())
                .orElseThrow(() -> new RuntimeException("Dentiste introuvable avec l'ID : " + dto.getDentisteId()));

        // 2. Vérification et réservation du créneau
        Creneau creneau = creneauRepository.findById(dto.getCreneauId())
                .orElseThrow(() -> new RuntimeException("Créneau introuvable avec l'ID : " + dto.getCreneauId()));

        if (!creneau.isDisponible()) {
            throw new IllegalStateException("Ce créneau horaire est déjà réservé.");
        }

        // Bloquer le créneau
        creneau.setDisponible(false);
        creneauRepository.save(creneau);

        // 3. Création et enregistrement du Rendez-vous
        RendezVous rdv = new RendezVous();
        rdv.setDateRdv(dto.getDateRdv());
        rdv.setNotes(dto.getNotes());
        rdv.setTypeRdv(TypeRdv.valueOf(dto.getTypeRdv())); // Assurez-vous d'avoir l'enum TypeRdv
        rdv.setEtatRdv(EtatRdv.EN_ATTENTE);                 // Assurez-vous d'avoir l'enum EtatRdv
        rdv.setPatient(patient);
        rdv.setDentiste(dentiste);


        RendezVous savedRdv = rdvRepository.save(rdv);
        return mapToResponseDTO(savedRdv);
    }

    @Override
    @Transactional
    public void annulerRendezVous(Long rdvId) {
        RendezVous rdv = rdvRepository.findById(rdvId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable avec l'ID : " + rdvId));

        rdv.setEtatRdv(EtatRdv.ANNULE);

        // Si un créneau était lié, on le libère pour d'autres patients
        if (rdv.getCreneau() != null) {
            rdv.getCreneau().setDisponible(true);
            creneauRepository.save(rdv.getCreneau());
        }

        rdvRepository.save(rdv);
    }

    @Override
    @Transactional
    public RendezVousResponseDTO modifierStatutRdv(Long rdvId, String nouvelEtat) {
        RendezVous rdv = rdvRepository.findById(rdvId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable avec l'ID : " + rdvId));

        EtatRdv etat = EtatRdv.valueOf(nouvelEtat.toUpperCase());
        rdv.setEtatRdv(etat);

        // Si le statut passe à ANNULE d'une autre manière, on libère aussi le créneau
        if (etat == EtatRdv.ANNULE && rdv.getCreneau() != null) {
            rdv.getCreneau().setDisponible(true);
            creneauRepository.save(rdv.getCreneau());
        }

        return mapToResponseDTO(rdvRepository.save(rdv));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponseDTO> obtenirRdvParPatient(Long patientId) {
        return rdvRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RendezVousResponseDTO> obtenirRdvParDentiste(Long dentisteId) {
        return rdvRepository.findByDentisteId(dentisteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Méthode d'aide (Mapper) pour transformer une entité RendezVous en DTO de réponse.
     */
    private RendezVousResponseDTO mapToResponseDTO(RendezVous rdv) {
        RendezVousResponseDTO dto = new RendezVousResponseDTO();
        dto.setId(rdv.getIdRendezVous());
        dto.setDateRdv(rdv.getDateRdv());
        dto.setNotes(rdv.getNotes());
        dto.setEtatRdv(rdv.getEtatRdv().name());
        dto.setTypeRdv(rdv.getTypeRdv().name());

        // Extraction des informations du Patient (héritées d'Utilisateur)
        dto.setPatientId(rdv.getPatient().getId_utilisateur()); // ou idUtilisateur selon votre choix final
        dto.setPatientNom(rdv.getPatient().getNom() + " " + rdv.getPatient().getPrenom());

        // Extraction des informations du Dentiste (héritées d'Utilisateur)
        dto.setDentisteId(rdv.getDentiste().getId_utilisateur());
        dto.setDentisteNom("Dr. " + rdv.getDentiste().getNom());

        if (rdv.getCreneau() != null) {
            dto.setCreneauId(rdv.getCreneau().getIdCreneau());
        }

        return dto;
    }
}