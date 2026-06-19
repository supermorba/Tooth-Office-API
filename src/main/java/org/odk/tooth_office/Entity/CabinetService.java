package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité d'association représentant l'offre d'un service dans un cabinet spécifique.
 * Porte les attributs prix et description propres à chaque affectation
 * (table ASSIGNATION_CAB_SER dans le SQL).
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ASSIGNATION_CAB_SER")
public class CabinetService {

    @EmbeddedId
    private CabinetServiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idService")
    @JoinColumn(name = "id_service")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("idCabinet")
    @JoinColumn(name = "id_cabinet")
    private Cabinet cabinet;

    /** Prix du service dans ce cabinet */
    @Column(nullable = false)
    private Integer prix;

    @Column(length = 200)
    private String description;
}
