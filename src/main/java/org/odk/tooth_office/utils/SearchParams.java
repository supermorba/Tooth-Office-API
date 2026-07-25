package org.odk.tooth_office.utils;

import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.PatientDTO;


import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class SearchParams {
    private Long secretaireId;
    private Long patient;
    private Long dentiste;
    private String etatRdv;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
