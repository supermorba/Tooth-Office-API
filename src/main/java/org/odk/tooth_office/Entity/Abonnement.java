package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Enum.TypePaiement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant l'abonnement d'un chef de cabinet à un plan.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Abonnement")
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abonnement")
    private Integer idAbonnement;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_abonnement")
    private EtatAbonnement etatAbonnement;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement", nullable = false)
    private TypePaiement typePaiement;

    @Column(name = "montant_total", nullable = false)
    private Integer montantTotal;

    /** Chef de cabinet ayant souscrit à cet abonnement */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chef_cabinet", nullable = false)
    private ChefCabinet chefCabinet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanAbonnement planAbonnement;

    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaiementAbonnement> paiements = new ArrayList<>();
}
