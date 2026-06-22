package org.odk.tooth_office.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequestDTO {
    private LocalDateTime dateRdv;
    private String notes;
    private String typeRdv;
    private Long patientId;
    private Long dentisteId;
    private Long creneauId;
}
