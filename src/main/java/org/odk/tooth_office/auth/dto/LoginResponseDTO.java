package org.odk.tooth_office.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
    private String token;
    private String type;
    private String refreshToken;
    private Long id;
    private String email;
    private String nomComplet;
    private String role;
    private Boolean mustChangePassword;
}