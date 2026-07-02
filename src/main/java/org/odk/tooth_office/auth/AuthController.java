package org.odk.tooth_office.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.auth.dto.ChangePasswordDTO;
import org.odk.tooth_office.auth.dto.LoginRequestDTO;
import org.odk.tooth_office.auth.dto.LoginResponseDTO;
import org.odk.tooth_office.auth.dto.MeResponseDTO;
import org.odk.tooth_office.auth.dto.RegisterRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Connexion et opérations liées à l'utilisateur authentifié")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Se connecter", description = "Authentifie un utilisateur avec son email et son mot de passe puis retourne un JWT")
    @SecurityRequirements()
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "S'inscrire", description = "Inscrit un nouvel utilisateur (Patient ou Chef de cabinet) et retourne son JWT")
    @SecurityRequirements()
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Récupérer l'utilisateur connecté", description = "Retourne les informations du compte actuellement authentifié")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MeResponseDTO> me(Authentication authentication) {
        return ResponseEntity.ok(authService.me(authentication));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Changer son mot de passe", description = "Modifie le mot de passe du compte actuellement authentifié")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO request,
                                               Authentication authentication) {
        authService.changePassword(authentication, request);
        return ResponseEntity.noContent().build();
    }
}