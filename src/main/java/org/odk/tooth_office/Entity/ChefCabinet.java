package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un Chef de Cabinet.
 * Il peut gérer un ou plusieurs cabinets dentaires.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Chef_Cabinet")
@PrimaryKeyJoinColumn(name = "id_chef_cabinet")
public class ChefCabinet extends Utilisateur {

    /**
     * Liste des cabinets gérés par ce chef de cabinet.
     * Relation Many-to-Many car un chef peut gérer plusieurs cabinets
     * et un cabinet peut être co-géré.
     */
    @ManyToMany
    @JoinTable(
            name = "CHEFCABINET_CABINET",
            joinColumns = @JoinColumn(name = "id_chef_cabinet"),
            inverseJoinColumns = @JoinColumn(name = "id_cabinet")
    )
    private List<Cabinet> cabinets = new ArrayList<>();
}