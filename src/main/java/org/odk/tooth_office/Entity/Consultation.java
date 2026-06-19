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
 * Entité représentant une consultation médicale réalisée par un dentiste.
 * Elle est rattachée à un dossier médical et optionnellement à un rendez-vous.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultation")
    private Integer idConsultation;

    @Column(name = "date_consultation", nullable = false)
    private LocalDate dateConsultation;

    @Column(length = 50)
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dossier", nullable = false)
    private DossierMedicale dossierMedicale;

    /** Rendez-vous ayant donné lieu à cette consultation (optionnel) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rendez_vous")
    private RendezVous rendezVous;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Traitement> traitements = new ArrayList<>();
}
