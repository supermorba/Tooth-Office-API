package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminSystemDTO extends UtilisateurDTO {
    @Schema(description = "Niveau de privilège de l'administrateur système", example = "SUPER_ADMIN")
    private String niveauPrivilege;
    @Schema(description = "Date et heure de la dernière connexion", example = "2026-06-24T08:45:00")
    private LocalDateTime dateDerniereConnexion;
}