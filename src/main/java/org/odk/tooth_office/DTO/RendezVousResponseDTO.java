package org.odk.tooth_office.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousResponseDTO {
    private Long id;
    private LocalDateTime dateRdv;
    private String notes;
    private String etatRdv;
    private String typeRdv;
    private Long patientId;
    private String patientNom;
    private Long dentisteId;
    private String dentisteNom;
    private Long creneauId;
}
