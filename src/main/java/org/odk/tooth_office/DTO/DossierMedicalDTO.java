package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DossierMedicalDTO {
    @Schema(description = "Identifiant unique du dossier médical", example = "7")
    private Long id;
    @Schema(description = "Antécédents médicaux du patient", example = "Antécédents d'hypertension")
    private String antecedents;
    @Schema(description = "Allergies connues du patient", example = "Allergie à la pénicilline")
    private String allergies;
    @Schema(description = "Historique médical ou dentaire du patient", example = "Extraction d'une molaire en 2024")
    private String historiques;
    @Schema(description = "Identifiant du patient associé au dossier", example = "5")
    private Long patientId;
}