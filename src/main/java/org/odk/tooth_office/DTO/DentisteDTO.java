package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DentisteDTO extends UtilisateurDTO {
    private String specialite;
    private Integer cabinetId;
}
