package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatientDTO extends UtilisateurDTO {
    private LocalDate dateNaissance;
    private List<Integer> cabinetIds;
}
