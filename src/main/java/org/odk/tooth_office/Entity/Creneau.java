package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un créneau horaire disponible pour un dentiste.
 * Un créneau est défini par une date, une heure de début et de fin,
 * et un statut de disponibilité.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Creneau")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_creneau")
    private Integer idCreneau;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(nullable = false)
    private Boolean disponible = true;

    /** Dentiste associé à ce créneau */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dentiste")
    private Dentiste dentiste;

    @OneToMany(mappedBy = "creneau", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVousList = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
