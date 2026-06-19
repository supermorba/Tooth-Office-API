package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité représentant une secrétaire du cabinet.
 * La secrétaire est rattachée à un cabinet et supervisée par un chef de cabinet.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Secretaire")
@PrimaryKeyJoinColumn(name = "id_secretaire")
public class Secretaire extends Utilisateur {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabinet")
    private Cabinet cabinet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chef_cabinet")
    private ChefCabinet chefCabinet;
}
