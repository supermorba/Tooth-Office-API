package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter @Setter
public class TraitementDTO implements Serializable {

    @Schema(description = "Type du traitement", example = "Orthodontie")
    @NotBlank
    private String type;
    @Schema(description = "Description détaillée du traitement", example = "Pose d'un appareil dentaire")
    @NotBlank
    private String description;
    @Schema(description = "Durée estimée du traitement en minutes", example = "45")
    private int duree;

}
