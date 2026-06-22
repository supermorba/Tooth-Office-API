package org.odk.tooth_office.DTO;

import java.time.LocalDate;

public record PrestationDTO(
        Long id_prestation,
        String nom_prestation,
        LocalDate dateCreation

) {
}
