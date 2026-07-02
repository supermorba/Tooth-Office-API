package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinetDTO {
    @Schema(description = "Nom du cabinet dentaire", example = "Cabinet Smile Plus")
    private String nomCabinet;
    @Schema(description = "Téléphone principal du cabinet", example = "+221338001122")
    private String tel;
    @Schema(description = "Adresse physique du cabinet", example = "Avenue Cheikh Anta Diop, Dakar")
    private String adresse;
    @Schema(description = "URL ou chemin du logo du cabinet", example = "https://example.com/logo-smile-plus.png")
    private String logo;
    @Schema(description = "Description du cabinet", example = "Cabinet spécialisé en soins dentaires et orthodontie")
    private String description;
}

