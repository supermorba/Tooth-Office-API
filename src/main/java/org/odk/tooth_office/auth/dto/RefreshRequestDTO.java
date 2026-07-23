package org.odk.tooth_office.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequestDTO {

    @NotBlank(message = "Le refresh token est obligatoire")
    private String refreshToken;
}
