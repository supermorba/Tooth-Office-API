package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité d'association représentant les services qu'un dentiste est habilité à pratiquer.
 * Correspond à la table SERVICE_DENTISTE dans le schéma SQL.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "SERVICE_DENTISTE")
public class DentisteService {

    @EmbeddedId
    private DentisteServiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idService")
    @JoinColumn(name = "id_service")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDentiste")
    @JoinColumn(name = "id_dentiste")
    private Dentiste dentiste;
}
