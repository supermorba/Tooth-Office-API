package org.odk.tooth_office.DTO;

import java.time.LocalDateTime;

public record AvisResponseDTO(
        Integer id,
        Integer note,
        String description,
        Integer idCabinet,
        Integer idPatient,
        LocalDateTime createAt
){}
