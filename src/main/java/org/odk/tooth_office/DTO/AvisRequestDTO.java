package org.odk.tooth_office.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//Création d'un Avis
public record AvisRequestDTO(
        @NotNull @Min(1) @Max(5) Integer note,
        @NotNull String description,
        @NotNull Long idCabinet,
        @NotNull Long idPatient
){}
