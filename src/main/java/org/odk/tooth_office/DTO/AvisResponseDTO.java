package org.odk.tooth_office.DTO;

import java.time.LocalDateTime;

public record AvisResponseDTO(
        Integer id,
        Integer note,
        String description,
        Integer cabinetId,
        Integer patientId,
        LocalDateTime createAt
){}
