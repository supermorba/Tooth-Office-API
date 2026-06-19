package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entité représentant un avis laissé par un patient sur un cabinet.
 * La note est comprise entre 0 et 5.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Avis")
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private Integer idAvis;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    /** Note de 0 à 5 */
    private Double note;

    @Column(name = "date_avis")
    private LocalDate dateAvis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabinet", nullable = false)
    private Cabinet cabinet;
}
