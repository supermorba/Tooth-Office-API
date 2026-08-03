package org.odk.tooth_office.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ModeleCreneauxRequestDTO {
    @NotNull
    private Long dentisteId;

    @NotNull
    @Min(15)
    @Max(120)
    private Integer dureeMinutes = 30; // 30, 45, or 60 min

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    private LocalTime heureDebutMatin = LocalTime.of(8, 0);
    private LocalTime heureFinMatin = LocalTime.of(12, 0);

    private LocalTime heureDebutApresMidi = LocalTime.of(14, 0);
    private LocalTime heureFinApresMidi = LocalTime.of(18, 0);

    // Day of week: 1 (Mon) to 7 (Sun)
    private List<Integer> joursTravailles = List.of(1, 2, 3, 4, 5);

    private Boolean remplacerExistants = false;
}
