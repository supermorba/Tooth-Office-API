package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;

import java.util.List;

public interface RendezVousService {
    // Accessible par le Patient et la Secrétaire
    RendezVousResponseDTO prendreRendezVous(RendezVousRequestDTO dto);

    // Accessible par le Patient, la Secrétaire et le Dentiste
    void annulerRendezVous(Long rdvId);

    // Pour le Dentiste et la Secrétaire : Modifier le statut (ex: HONORE, NON_HONORE)
    RendezVousResponseDTO modifierStatutRdv(Long rdvId, String nouvelEtat);

    // Pour le Patient et la Secrétaire : Consulter l'historique d'un patient
    List<RendezVousResponseDTO> obtenirRdvParPatient(Long patientId);

    // Pour le Dentiste et la Secrétaire : Consulter le planning du jour d'un praticien
    List<RendezVousResponseDTO> obtenirRdvParDentiste(Long dentisteId);
}
