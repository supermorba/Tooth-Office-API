package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "plan_abonnement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

    @Column(name = "nom", nullable = false, unique = true, length = 100)
    private String nom;

    @Column(name = "prix_mensuel", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixMensuel;

    @Column(name = "prix_annuel", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAnnuel;

    @Column(name = "max_cabinet", nullable = false)
    private int maxCabinet;

    @Column(name = "max_dentistes", nullable = false)
    private int maxDentistes;

    @Column(name = "max_secretaires", nullable = false)
    private int maxSecretaires;

    @Column(name = "description", length = 1000)
    private String description;
}