package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreneauDTO {
    @Schema(description = "Identifiant unique du créneau", example = "3")
    private Long idCreneau;
    @Schema(description = "Date du créneau", example = "2026-06-25")
    private LocalDate date;
    @Schema(description = "Heure de début du créneau", example = "09:00:00")
    private LocalTime heureDebut;
    @Schema(description = "Heure de fin du créneau", example = "09:30:00")
    private LocalTime heureFin;
    @Schema(description = "Indique si le créneau est encore disponible", example = "true")
    private boolean disponible;
    @Schema(description = "Identifiant du dentiste associé au créneau", example = "2")
    private Long dentisteId;
    @Schema(description = "Nom du dentiste associé au créneau", example = "Dr Awa Ba")
    private String dentisteNom;
}
