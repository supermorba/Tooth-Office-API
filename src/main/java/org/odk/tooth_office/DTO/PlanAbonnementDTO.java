package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record PlanAbonnementDTO(

        @Schema(description = "Nom du plan d'abonnement", example = "Premium")
        String nom,
        @Schema(description = "Prix mensuel du plan", example = "15000")
        BigDecimal prixMensuel,
        @Schema(description = "Prix annuel du plan", example = "160000")
        BigDecimal prixAnnuel,
        @Schema(description = "Nombre maximal de cabinets autorisés", example = "3")
        int maxCabinet,
        @Schema(description = "Nombre maximal de dentistes autorisés", example = "10")
        int maxDentistes,
        @Schema(description = "Nombre maximal de secrétaires autorisées", example = "5")
        int maxSecretaires,
        @Schema(description = "Description fonctionnelle du plan", example = "Plan premium avec gestion multi-cabinets")
        String description
) {}