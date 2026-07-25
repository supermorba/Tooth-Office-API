package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.Enum.GraviteAllergie;
import org.odk.tooth_office.Enum.TypeAllergie;

@Getter
@Setter
public class AllergieIntoleranceDTO {
    @Schema(description = "Identifiant de l'allergie ou intolérance")
    private Long id;

    @NotBlank
    @Schema(description = "Libellé de l'allergie ou intolérance", example = "Pénicilline")
    private String libelle;

    @NotNull
    @Schema(description = "Type : ALLERGIE ou INTOLERANCE", example = "ALLERGIE")
    private TypeAllergie type;

    @NotNull
    @Schema(description = "Gravité : LEGERE, MODEREE ou SEVERE (alerte rouge si SEVERE)", example = "SEVERE")
    private GraviteAllergie gravite;

    @Schema(description = "Description complémentaire")
    private String description;
}
