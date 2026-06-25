package org.odk.tooth_office.DTO;

public record ConsultationPatchDTO(

        String diagnostic,
        String notes,
        Long idDentiste,
        Long idDossierMedical,
        Long idRendezVous

) {
}
