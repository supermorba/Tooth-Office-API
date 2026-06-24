package org.odk.tooth_office.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

public record ConsultationDTO(
        @Schema(description = "Identifiant unique de la consultation", example = "10")
        Long id,
        @Schema(description = "Diagnostic de la consultation", example = "Gingivite légère")
        String diagnostic,
        @Schema(description = "Notes associées à la consultation", example = "Prescription d'un bain de bouche")
        String notes,
        @Schema(description = "Date de la consultation", example = "2026-06-24T09:00:00.000+00:00")
        Date date_consultation,
        @Schema(description = "Nom complet du patient", example = "Aminata Ndiaye")
        String patient,
        @Schema(description = "Téléphone du patient", example = "+221771112233")
        String telPatient,
        @Schema(description = "Nom complet du dentiste", example = "Dr Mamadou Fall")
        String dentiste
        ) {
}
