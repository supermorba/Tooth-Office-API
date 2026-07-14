package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UtilisateurDTO {
    @Schema(description = "Identifiant unique de l'utilisateur", example = "1")
    private Long id_utilisateur;
    @Schema(description = "Nom de famille de l'utilisateur", example = "Dupont")
    private String nom;
    @Schema(description = "Prénom de l'utilisateur", example = "Jean")
    private String prenom;
    @Schema(description = "Adresse email de l'utilisateur", example = "jean.dupont@tooth-office.com")
    private String email;
    @Schema(description = "Mot de passe de l'utilisateur", example = "MotDePasse123!")
    private String mdp;
    @Schema(description = "Adresse postale de l'utilisateur", example = "12 rue des Lilas, Dakar")
    private String adresse;
    @Schema(description = "Rôle attribué à l'utilisateur", example = "PATIENT")
    private RoleEnum role;
    @Schema(description = "Numéro de téléphone de l'utilisateur", example = "+221771234567")
    private String telephone;
    @Schema(description = "Statut du compte utilisateur", example = "ACTIF")
    private StatutCompte statutCompte;
    @Schema(description = "Date de création du compte", example = "2026-06-24")
    private LocalDate createdAt;
    @Schema(description = "Date et heure de dernière mise à jour du compte", example = "2026-06-24T09:30:00")
    private LocalDateTime updatedAt;
    @Schema(description = "Auteur de la création du compte", example = "system")
    private String createdBy;
    @Schema(description = "Auteur de la dernière mise à jour du compte", example = "admin")
    private String updatedBy;
}