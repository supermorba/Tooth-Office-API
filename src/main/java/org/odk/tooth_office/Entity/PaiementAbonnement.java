package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.ModePaiement;

import java.time.LocalDateTime;

/**
 * Entité représentant un paiement effectué pour un abonnement.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Paiement_Abonnement")
public class PaiementAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement")
    private Integer idPaiement;

    @Column(nullable = false)
    private Integer montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement")
    private ModePaiement modePaiement;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abonnement", nullable = false)
    private Abonnement abonnement;
}
