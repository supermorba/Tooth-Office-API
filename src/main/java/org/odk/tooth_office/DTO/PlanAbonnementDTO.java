package org.odk.tooth_office.DTO;

import java.math.BigDecimal;

public record PlanAbonnementDTO(
        Long idPlan,
        String nom,
        BigDecimal prixMensuel,
        BigDecimal prixAnnuel,
        int maxCabinet,
        int maxDentistes,
        int maxSecretaires,
        String description
) {}