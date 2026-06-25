package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChefCabinetDTO extends UtilisateurDTO {
    @ArraySchema(schema = @Schema(description = "Identifiant d'un cabinet géré par le chef de cabinet", example = "1"))
    private List<Integer> cabinetIds;
}