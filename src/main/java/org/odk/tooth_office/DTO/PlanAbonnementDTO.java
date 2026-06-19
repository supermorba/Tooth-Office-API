package org.odk.tooth_office.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanAbonnementDTO {

    private String nom;

    private BigDecimal prixMensuel;

    private BigDecimal prixAnnuel;

    private int maxCabinet;

    private int maxDentistes;

    private int maxSecretaires;

    private String description;
}