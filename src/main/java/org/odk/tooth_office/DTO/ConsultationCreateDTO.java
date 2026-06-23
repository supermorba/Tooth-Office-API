package org.odk.tooth_office.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultationCreateDTO(

        @NotBlank(message = "Le diagnostic doit être renseigné")
        String diagnostic,

        String notes,
        @NotNull(message = "Identifiez le dentiste qui effectue la consultation")
        Long idDentiste,

        @NotNull(message = "Identifiez le patient qui se fait consulter")
        Long idPatient



) {
}
