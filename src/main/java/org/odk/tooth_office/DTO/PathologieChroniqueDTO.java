package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PathologieChroniqueDTO {
    @Schema(description = "Identifiant de la pathologie chronique")
    private Long id;

    @NotBlank
    @Schema(description = "Libellé de la pathologie", example = "Diabète type 2")
    private String libelle;

    @Schema(description = "Indique si la pathologie est une ALD (Affection Longue Durée)", example = "true")
    private boolean estAld;

    @Schema(description = "Description complémentaire")
    private String description;

    @Schema(description = "Date de diagnostic")
    private LocalDate dateDiagnostic;
}
