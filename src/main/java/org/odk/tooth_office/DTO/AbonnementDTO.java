package org.odk.tooth_office.DTO;

import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Enum.TypePaiement;
import java.time.LocalDate;

public class AbonnementDTO {
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private EtatAbonnement etatAbonnement;
        private TypePaiement typePaiement;
        private Integer montantTotal;
        private Integer idPlan;
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