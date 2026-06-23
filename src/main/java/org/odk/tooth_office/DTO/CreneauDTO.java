package org.odk.tooth_office.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreneauDTO {
    private Long idCreneau;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private boolean disponible;
    private Long dentisteId;
    private String dentisteNom;
}
