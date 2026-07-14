package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité représentant un dentiste.
 * Chaque dentiste appartient à un cabinet et peut avoir une spécialité.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Dentiste")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class Dentiste extends Utilisateur {

    @Column(length = 100)
    private String specialite;

    /** Cabinet auquel appartient ce dentiste */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabinet", nullable = false)
    private Cabinet cabinet;
}