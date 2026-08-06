package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.Enum.TypeAntecedent;

import java.time.LocalDate;

@Getter
@Setter
public class AntecedentDTO {
    @Schema(description = "Identifiant de l'antécédent")
    private Long id;

    @NotNull
    @Schema(description = "Type : MEDICAL, CHIRURGICAL, FAMILIAL, TABAC, ALCOOL, SPORT", example = "MEDICAL")
    private TypeAntecedent type;

    @NotBlank
    @Schema(description = "Libellé de l'antécédent", example = "Appendicectomie")
    private String libelle;

    @Schema(description = "Description complémentaire")
    private String description;

    @Schema(description = "Date de survenue ou de diagnostic")
    private LocalDate dateSurvenue;
}
