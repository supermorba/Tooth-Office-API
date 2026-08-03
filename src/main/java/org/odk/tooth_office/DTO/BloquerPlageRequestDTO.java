package org.odk.tooth_office.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BloquerPlageRequestDTO {
    @NotNull
    private Long dentisteId;

    @NotNull
    private LocalDate date;

    // 'JOURNEE', 'MATIN', 'APRES_MIDI'
    @NotNull
    private String plage;

    private boolean bloquer = true; // true = bloquer, false = liberer
}
