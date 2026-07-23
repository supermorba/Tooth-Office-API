package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatientDTO extends UtilisateurDTO {
    @Schema(description = "Date de naissance du patient", example = "1995-08-14")
    private LocalDate dateNaissance;
    @ArraySchema(schema = @Schema(description = "Identifiant d'un cabinet rattaché au patient", example = "1"))
    private List<Integer> cabinetIds;
}
