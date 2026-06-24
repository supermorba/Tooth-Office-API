package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousResponseDTO {
    @Schema(description = "Identifiant unique du rendez-vous", example = "12")
    private Long id;
    @Schema(description = "Date et heure du rendez-vous", example = "2026-06-25T10:00:00")
    private LocalDateTime dateRdv;
    @Schema(description = "Notes liées au rendez-vous", example = "Arriver 10 minutes à l'avance")
    private String notes;
    @Schema(description = "État courant du rendez-vous", example = "PLANIFIE")
    private String etatRdv;
    @Schema(description = "Type du rendez-vous", example = "CONSULTATION")
    private String typeRdv;
    @Schema(description = "Identifiant du patient concerné", example = "1")
    private Long patientId;
    @Schema(description = "Nom complet du patient", example = "Fatou Diallo")
    private String patientNom;
    @Schema(description = "Identifiant du dentiste concerné", example = "2")
    private Long dentisteId;
    @Schema(description = "Nom complet du dentiste", example = "Dr Ousmane Kane")
    private String dentisteNom;
    @Schema(description = "Identifiant du créneau réservé", example = "8")
    private Long creneauId;
}
