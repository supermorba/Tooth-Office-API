package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.EtatRdv;
import org.odk.tooth_office.Enum.TypeRdv;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rendez_vous")
    private Long idRendezVous;

    @Column(name = "date_rdv", nullable = false)
    private LocalDateTime dateRdv;

    @Column(length = 50)
    private String motif;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_rdv")
    private EtatRdv etatRdv = EtatRdv.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_rdv")
    private TypeRdv typeRdv = TypeRdv.ENLIGNE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dentiste", nullable = false)
    private Dentiste dentiste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_secretaire")
    private Secretaire secretaire;
//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creneau")
    private Creneau creneau;


    /*@OneToOne(mappedBy = "rendezVous", cascade = CascadeType.ALL)
    private Consultation consultation;*/

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}