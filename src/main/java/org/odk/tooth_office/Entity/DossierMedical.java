package org.odk.tooth_office.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity @Table @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String antecedents;

    @Column(length = 100)
    private String allergies;
        @Column(columnDefinition = "TEXT")
    private String historiques;
    
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    /** Patient propriétaire de ce dossier (relation 1-1) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient", nullable = false, unique = true)
    private Patient patient;

    @OneToMany(mappedBy = "dossierMedical", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();
}