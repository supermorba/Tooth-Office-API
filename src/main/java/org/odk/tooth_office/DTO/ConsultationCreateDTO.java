package org.odk.tooth_office.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultationCreateDTO(

        @Schema(description = "Diagnostic posé lors de la consultation", example = "Carie molaire à traiter")
        @NotBlank(message = "Le diagnostic doit être renseigné")
        String diagnostic,

        @Schema(description = "Notes complémentaires liées à la consultation", example = "Prévoir une radiographie de contrôle")
        String notes,
        @Schema(description = "Identifiant du dentiste qui effectue la consultation", example = "2")
        @NotNull(message = "Identifiez le dentiste qui effectue la consultation")
        Long idDentiste,

        @Schema(description = "Identifiant du patient consulté", example = "5")
        @NotNull(message = "Identifiez le patient qui se fait consulter")
        Long idPatient
) {
}
