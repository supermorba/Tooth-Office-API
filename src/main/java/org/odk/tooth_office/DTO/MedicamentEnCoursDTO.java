package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicamentEnCoursDTO {
    @Schema(description = "Identifiant du médicament en cours")
    private Long id;

    @NotBlank
    @Schema(description = "Nom du médicament", example = "Metformine")
    private String medicament;

    @NotBlank
    @Schema(description = "Posologie", example = "500 mg, 2 fois par jour")
    private String posologie;

    @Schema(description = "Notes complémentaires")
    private String notes;

    @Schema(description = "Date de début du traitement")
    private LocalDate dateDebut;

    @Schema(description = "Date de fin du traitement (null si en cours)")
    private LocalDate dateFin;

    @Schema(description = "Indique si le traitement est actif", example = "true")
    private boolean actif = true;
}
