package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminSystemDTO extends UtilisateurDTO {
    private String niveauPrivilege;
    private LocalDateTime dateDerniereConnexion;
}