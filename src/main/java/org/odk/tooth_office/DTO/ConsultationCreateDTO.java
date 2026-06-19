package org.odk.tooth_office.DTO;


import jakarta.validation.constraints.NotBlank;

public record ConsultationCreateDTO(

        @NotBlank(message = "Le diagnostic doit être renseigné")
        String diagnostic,
        String notes
) {
}
