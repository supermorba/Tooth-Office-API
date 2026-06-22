package org.odk.tooth_office.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter @Setter
public class TraitementDTO implements Serializable {

    @NotBlank
    private String type;
    @NotBlank
    private String description;
    @NotBlank
    private int duree;

}
