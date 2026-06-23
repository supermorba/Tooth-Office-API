package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DossierMedicalDTO {
    private Long id;
    private String antecedents;
    private String allergies;
    private String historiques;
    private Long patientId;
}