package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretaireDTO extends UtilisateurDTO {
    @Schema(description = "Identifiant du cabinet auquel la secrétaire est rattachée", example = "1")
    private Integer cabinetId;
    @Schema(description = "Identifiant du chef de cabinet superviseur", example = "2")
    private Long chefCabinetId;
}