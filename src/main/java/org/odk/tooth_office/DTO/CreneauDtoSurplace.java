package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreneauDtoSurplace(

        @Schema(description = "Date du créneau", example = "2026-06-25")
        LocalDate date,

        @Schema(description = "Heure de début du créneau", example = "09:00:00")
        LocalTime heureDebut,

        @Schema(description = "Heure de fin du créneau", example = "09:40:00")
        LocalTime heureFin,

        @Schema(description = "Indique si le créneau est disponible", example = "false")
        boolean disponible,

        @Schema(description = "Identifiant du dentiste", example = "2")
        Long dentisteId
) {
}
