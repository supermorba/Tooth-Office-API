package org.odk.tooth_office.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record  ConsultationCreateDTO(

        @NotBlank(message = "Le diagnostic doit être renseigné")
        String diagnostic,

        @NotBlank(message = "Enregistrer une note pour la consultation")
        String notes,

        @NotNull(message = "Identifiez le dentiste qui effectue la consultation")
        Long idDentiste,

        @NotNull(message = "Identifiez le dossier medical")
        Long idDossierMedical,

        @NotNull(message = "Une consultation doit concerné un RDV")
        Long idRendezVous


) {
}
