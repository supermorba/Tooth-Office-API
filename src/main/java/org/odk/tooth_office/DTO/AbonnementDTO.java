package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Enum.TypePaiement;
import java.time.LocalDate;

public class AbonnementDTO {
        @Schema(description = "Date de début de l'abonnement", example = "2026-06-01")
        private LocalDate dateDebut;
        @Schema(description = "Date de fin de l'abonnement", example = "2027-05-31")
        private LocalDate dateFin;
        @Schema(description = "État actuel de l'abonnement", example = "ACTIF")
        private EtatAbonnement etatAbonnement;
        @Schema(description = "Type de paiement choisi", example = "MENSUEL")
        private TypePaiement typePaiement;
        @Schema(description = "Montant total payé pour l'abonnement", example = "25000")
        private Integer montantTotal;
        @Schema(description = "Identifiant du plan d'abonnement associé", example = "1")
        private Integer idPlan;
        @Schema(description = "Identifiant du cabinet associé", example = "1")
        private Integer idCabinet;

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