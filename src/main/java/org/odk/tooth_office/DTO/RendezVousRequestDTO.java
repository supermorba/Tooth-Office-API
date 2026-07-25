package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequestDTO {
    @Schema(description = "Date et heure prévues du rendez-vous", example = "2026-06-25T10:00:00")
    private LocalDateTime dateRdv;
    @Schema(description = "Motif court du rendez-vous", example = "Contrôle annuel")
    private String motif;
    @Schema(description = "Notes additionnelles pour le rendez-vous", example = "Première consultation")
    private String notes;
    @Schema(description = "Type de rendez-vous demandé", example = "CONSULTATION")
    private String typeRdv;
    @Schema(description = "Identifiant unique du patient", example = "1")
    private Long patientId;
    @Schema(description = "Identifiant unique du dentiste", example = "2")
    private Long dentisteId;
    @Schema(description = "Identifiant du créneau réservé", example = "8")
    private Long creneauId;
}
