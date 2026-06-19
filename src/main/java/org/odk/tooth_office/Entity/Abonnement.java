package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Enum.TypePaiement;

import java.time.LocalDate;



@Entity
@Table(name = "abonnement")
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abonnement")
    private Integer idAbonnement;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_abonnement")
    private EtatAbonnement etatAbonnement;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement")
    private TypePaiement typePaiement;

    @Column(name = "montant_total")
    private Integer montantTotal;

    // Clés étrangères gérées par de simples IDs pour l'indépendance du code
    @Column(name = "id_plan", nullable = false)
    private Integer idPlan;

    @Column(name = "id_cabinet", nullable = false)
    private Integer idCabinet;

    public Abonnement() {}

    public Abonnement(LocalDate dateDebut, LocalDate dateFin, EtatAbonnement etatAbonnement, TypePaiement typePaiement, Integer montantTotal, Integer idPlan, Integer idCabinet) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etatAbonnement = etatAbonnement;
        this.typePaiement = typePaiement;
        this.montantTotal = montantTotal;
        this.idPlan = idPlan;
        this.idCabinet = idCabinet;
    }
    public Integer getIdAbonnement() { return idAbonnement; }
    public void setIdAbonnement(Integer idAbonnement) { this.idAbonnement = idAbonnement; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public EtatAbonnement getEtatAbonnement() { return etatAbonnement; }
    public void setEtatAbonnement(EtatAbonnement etatAbonnement) { this.etatAbonnement = etatAbonnement; }

    public TypePaiement getTypePaiement() { return typePaiement; }
    public void setTypePaiement(TypePaiement typePaiement) { this.typePaiement = typePaiement; }

    public Integer getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Integer montantTotal) { this.montantTotal = montantTotal; }

    public Integer getIdPlan() { return idPlan; }
    public void setIdPlan(Integer idPlan) { this.idPlan = idPlan; }

    public Integer getIdCabinet() { return idCabinet; }
    public void setIdCabinet(Integer idCabinet) { this.idCabinet = idCabinet; }
}
