package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UtilisateurDTO {
    private Long id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mpd;
    private String adresse;
    private RoleEnum role;
    private String telephone;
    private StatutCompte statutCompte;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}