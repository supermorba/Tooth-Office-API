package org.odk.tooth_office.auth;

import org.odk.tooth_office.auth.dto.ChangePasswordDTO;
import org.odk.tooth_office.auth.dto.LoginRequestDTO;
import org.odk.tooth_office.auth.dto.LoginResponseDTO;
import org.odk.tooth_office.auth.dto.MeResponseDTO;
import org.odk.tooth_office.auth.dto.RegisterRequestDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);

    LoginResponseDTO register(RegisterRequestDTO request);

    MeResponseDTO me(Authentication authentication);

    void changePassword(Authentication authentication, ChangePasswordDTO request);
}