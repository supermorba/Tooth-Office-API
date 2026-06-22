package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretaireDTO extends UtilisateurDTO {
    private Integer cabinetId;
    private Long chefCabinetId;
}