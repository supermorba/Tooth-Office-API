package org.odk.tooth_office.DTO;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record PlanAbonnementDTO(

        String nom,
        BigDecimal prixMensuel,
        BigDecimal prixAnnuel,
        int maxCabinet,
        int maxDentistes,
        int maxSecretaires,
        String description
) {}