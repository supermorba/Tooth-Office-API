package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un plan d'abonnement proposé par la plateforme.
 * Définit les limites et le tarif applicable aux cabinets souscripteurs.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Plan_Abonnement")
public class PlanAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;

    @Column(nullable = false, length = 50)
    private String nom;

    /** Prix mensuel en FCFA (ou monnaie locale) */
    @Column(name = "prix_mensuel", nullable = false)
    private Integer prixMensuel;

    /** Prix annuel en FCFA */
    @Column(name = "prix_annuel", nullable = false)
    private Integer prixAnnuel;

    /** Nombre maximum de cabinets autorisés */
    @Column(name = "max_cabinet", nullable = false)
    private Integer maxCabinet;

    /** Nombre maximum de dentistes autorisés */
    @Column(name = "max_dentistes", nullable = false)
    private Integer maxDentistes;

    /** Nombre maximum de secrétaires autorisées */
    @Column(name = "max_secretaires", nullable = false)
    private Integer maxSecretaires;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "planAbonnement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abonnement> abonnements = new ArrayList<>();
}
