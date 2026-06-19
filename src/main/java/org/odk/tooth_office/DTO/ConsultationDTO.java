package org.odk.tooth_office.DTO;


import java.util.Date;

public record ConsultationDTO(
        Long id,
        String diagnostic,
        String notes,
        Date date_consultation,
        String patient,
        String telPatient,
        String dentiste
        ) {
}
