package org.odk.tooth_office.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "medicament_en_cours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentEnCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medicament;

    @Column(nullable = false)
    private String posologie;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(nullable = false)
    private boolean actif = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_medical_id", nullable = false)
    private DossierMedical dossierMedical;
}
