package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record PrestationDTO(
        @Schema(description = "Identifiant unique de la prestation", example = "4")
        Long id_prestation,
        @Schema(description = "Nom de la prestation", example = "Détartrage")
        String nom_prestation,
        @Schema(description = "Date de création de la prestation", example = "2026-06-24")
        LocalDate dateCreation

) {
}
