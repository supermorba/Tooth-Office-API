package org.odk.tooth_office.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public record AvisDetailDTO(
        Integer id,
        Integer note,
        String description,
        LocalDateTime createAt,
        String nomCabinet,
        String nomPatient
) {
}
