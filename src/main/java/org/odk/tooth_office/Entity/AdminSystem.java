package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entité représentant l'administrateur système de la plateforme.
 * Il est le seul à pouvoir valider, rejeter, suspendre ou réactiver des cabinets
 * et gérer l'ensemble des utilisateurs.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "AdminSystem")
@PrimaryKeyJoinColumn(name = "id_admin")
public class AdminSystem extends Utilisateur {

    @Column(length = 100)
    private String niveauPrivilege;

    private LocalDateTime dateDerniereConnexion;
}