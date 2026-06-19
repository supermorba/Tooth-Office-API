package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe de base représentant tout utilisateur du système.
 * Stratégie d'héritage JOINED : chaque sous-classe aura sa propre table
 * reliée à la table Utilisateur par une clé étrangère.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long id_utilisateur;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    /** Mot de passe (à hacher en production) */
    @Column(nullable = false, length = 100)
    private String mpd;

    @Column(length = 255)
    private String adresse;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;

    private LocalDate createdAt;

    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String createdBy;

    @Column(length = 100)
    private String updatedBy;
}
