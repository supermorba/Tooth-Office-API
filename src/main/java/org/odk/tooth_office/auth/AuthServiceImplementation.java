package org.odk.tooth_office.auth;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Enum.StatutCompte;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.auth.dto.ChangePasswordDTO;
import org.odk.tooth_office.auth.dto.LoginRequestDTO;
import org.odk.tooth_office.auth.dto.LoginResponseDTO;
import org.odk.tooth_office.auth.dto.MeResponseDTO;
import org.odk.tooth_office.security.CustomUserPrincipal;
import org.odk.tooth_office.security.JwtService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final PasswordService passwordService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe invalide"));

        if (utilisateur.getStatutCompte() != StatutCompte.VALIDE) {
            throw new BadCredentialsException("Ce compte n'est pas actif");
        }

        if (!passwordService.matches(request.getMotDePasse(), utilisateur.getMpd())) {
            throw new BadCredentialsException("Email ou mot de passe invalide");
        }

        if (passwordService.needsRehash(utilisateur.getMpd())) {
            utilisateur.setMpd(passwordService.encodeIfNeeded(request.getMotDePasse()));
            utilisateurRepository.save(utilisateur);
        }

        CustomUserPrincipal principal = new CustomUserPrincipal(utilisateur);
        String token = jwtService.generateToken(principal);

        return LoginResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .id(utilisateur.getId_utilisateur())
                .email(utilisateur.getEmail())
                .nomComplet(principal.getNomComplet())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().name() : null)
                .build();
    }

    @Override
    public MeResponseDTO me(Authentication authentication) {
        CustomUserPrincipal principal = extractPrincipal(authentication);
        Utilisateur utilisateur = principal.getUtilisateur();

        return MeResponseDTO.builder()
                .id(utilisateur.getId_utilisateur())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().name() : null)
                .statutCompte(utilisateur.getStatutCompte() != null ? utilisateur.getStatutCompte().name() : null)
                .build();
    }

    @Override
    public void changePassword(Authentication authentication, ChangePasswordDTO request) {
        CustomUserPrincipal principal = extractPrincipal(authentication);
        Utilisateur utilisateur = principal.getUtilisateur();

        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new IllegalArgumentException("La confirmation du mot de passe ne correspond pas");
        }

        if (!passwordService.matches(request.getAncienMotDePasse(), utilisateur.getMpd())) {
            throw new BadCredentialsException("L'ancien mot de passe est incorrect");
        }

        utilisateur.setMpd(passwordService.encodeIfNeeded(request.getNouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);
    }

    private CustomUserPrincipal extractPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new BadCredentialsException("Utilisateur non authentifié");
        }
        return principal;
    }
}