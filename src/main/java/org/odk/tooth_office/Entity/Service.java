package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entité représentant un type de service proposé par la plateforme (ex: détartrage, blanchiment).
 * Un service peut être assigné à plusieurs cabinets via CabinetService.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "SERVICES")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Integer idService;

    @Column(name = "nom_service", nullable = false, length = 50)
    private String nomService;

    @Column(name = "date_creation")
    private LocalDate dateCreation;
}
