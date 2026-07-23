package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant le dossier médical d'un patient.
 * Regroupe les antécédents, allergies et l'historique global des soins.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "DossierMedicale")
public class DossierMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dossier")
    private Integer idDossier;

    @Column(length = 100)
    private String antecedents;

    @Column(length = 100)
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String historique;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    /** Patient propriétaire de ce dossier (relation 1-1) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient", nullable = false, unique = true)
    private Patient patient;

    @OneToMany(mappedBy = "dossierMedicale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();
}
