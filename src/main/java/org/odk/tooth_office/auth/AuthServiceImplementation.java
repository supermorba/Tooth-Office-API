package org.odk.tooth_office.auth;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;
import org.odk.tooth_office.Exception.EmailDejaUtiliseException;
import org.odk.tooth_office.Exception.TelephoneDejaUtiliseException;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.ChefCabinetRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.auth.dto.ChangePasswordDTO;
import org.odk.tooth_office.auth.dto.LoginRequestDTO;
import org.odk.tooth_office.auth.dto.LoginResponseDTO;
import org.odk.tooth_office.auth.dto.MeResponseDTO;
import org.odk.tooth_office.auth.dto.RefreshRequestDTO;
import org.odk.tooth_office.auth.dto.RegisterRequestDTO;
import org.odk.tooth_office.Entity.RefreshToken;
import org.odk.tooth_office.security.CustomUserPrincipal;
import org.odk.tooth_office.security.JwtService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImplementation implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PatientRepository patientRepository;
    private final ChefCabinetRepository chefCabinetRepository;
    private final CabinetRepository cabinetRepository;
    private final JwtService jwtService;
    private final PasswordService passwordService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        // Recherche par email ou par téléphone selon ce qui est fourni
        Utilisateur utilisateur;

        boolean hasEmail = request.getEmail() != null && !request.getEmail().isBlank();
        boolean hasTelephone = request.getTelephone() != null && !request.getTelephone().isBlank();

        if (!hasEmail && !hasTelephone) {
            throw new BadCredentialsException("Veuillez fournir un email ou un numéro de téléphone");
        }

        if (hasEmail) {
            utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe invalide"));
        } else {
            utilisateur = utilisateurRepository.findByTelephone(request.getTelephone())
                    .orElseThrow(() -> new BadCredentialsException("Téléphone ou mot de passe invalide"));
        }

        if (utilisateur.getStatutCompte() != StatutCompte.VALIDE) {
            throw new BadCredentialsException("Ce compte n'est pas actif");
        }

        if (!passwordService.matches(request.getMotDePasse(), utilisateur.getMpd())) {
            String identifiant = hasEmail ? "Email" : "Téléphone";
            throw new BadCredentialsException(identifiant + " ou mot de passe invalide");
        }

        if (passwordService.needsRehash(utilisateur.getMpd())) {
            utilisateur.setMpd(passwordService.encodeIfNeeded(request.getMotDePasse()));
            utilisateurRepository.save(utilisateur);
        }

        CustomUserPrincipal principal = new CustomUserPrincipal(utilisateur);
        String token = jwtService.generateToken(principal);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(utilisateur);

        return LoginResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .refreshToken(refreshToken.getToken())
                .id(utilisateur.getId_utilisateur())
                .email(utilisateur.getEmail())
                .nomComplet(principal.getNomComplet())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().name() : null)
                .build();
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO request) {
        // 1. Vérifier si les mots de passe correspondent
        if (!request.getMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new IllegalArgumentException("La confirmation du mot de passe ne correspond pas");
        }

        // 2. Vérifier si le rôle est autorisé pour l'inscription publique
        if (request.getRole() != RoleEnum.PATIENT && request.getRole() != RoleEnum.CHEF_CABINET) {
            throw new IllegalArgumentException("Rôle non autorisé pour l'inscription publique. Seuls les Patients et Chefs de cabinet peuvent s'inscrire.");
        }

        // 3. Vérifier l'unicité du numéro de téléphone (obligatoire)
        if (utilisateurRepository.existsByTelephone(request.getTelephone())) {
            throw new TelephoneDejaUtiliseException("Ce numéro de téléphone est déjà utilisé");
        }

        // 4. Vérifier l'unicité de l'email si fourni
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new EmailDejaUtiliseException("Cet email est déjà utilisé");
        }

        Utilisateur savedUtilisateur;

        if (request.getRole() == RoleEnum.PATIENT) {
            Patient patient = new Patient();
            hydrateUtilisateurFields(patient, request);
            patient.setDateNaissance(request.getDateNaissance());

            if (request.getCabinetIds() != null && !request.getCabinetIds().isEmpty()) {
                List<Cabinet> cabinets = cabinetRepository.findAllById(request.getCabinetIds());
                patient.setCabinets(cabinets);
            }
            savedUtilisateur = patientRepository.save(patient);
        } else {
            ChefCabinet chefCabinet = new ChefCabinet();
            hydrateUtilisateurFields(chefCabinet, request);

            if (request.getCabinetIds() != null && !request.getCabinetIds().isEmpty()) {
                List<Cabinet> cabinets = cabinetRepository.findAllById(request.getCabinetIds());
                chefCabinet.setCabinets(cabinets);
            }
            savedUtilisateur = chefCabinetRepository.save(chefCabinet);
        }

        // 5. Connecter automatiquement l'utilisateur et générer les tokens
        CustomUserPrincipal principal = new CustomUserPrincipal(savedUtilisateur);
        String token = jwtService.generateToken(principal);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUtilisateur);

        return LoginResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .refreshToken(refreshToken.getToken())
                .id(savedUtilisateur.getId_utilisateur())
                .email(savedUtilisateur.getEmail())
                .nomComplet(principal.getNomComplet())
                .role(savedUtilisateur.getRole() != null ? savedUtilisateur.getRole().name() : null)
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

    private void hydrateUtilisateurFields(Utilisateur utilisateur, RegisterRequestDTO request) {
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail() != null && !request.getEmail().isBlank() ? request.getEmail() : null);
        utilisateur.setMpd(passwordService.encodeIfNeeded(request.getMotDePasse()));
        utilisateur.setAdresse(request.getAdresse());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setRole(request.getRole());
        utilisateur.setStatutCompte(StatutCompte.VALIDE);

        utilisateur.setCreatedAt(LocalDate.now());
        utilisateur.setUpdatedAt(LocalDateTime.now());
        utilisateur.setCreatedBy("self_registration");
        utilisateur.setUpdatedBy("self_registration");
    }

    @Override
    public LoginResponseDTO refresh(RefreshRequestDTO request) {
        // 1. Valider le refresh token (existence, non-révoqué, non-expiré)
        RefreshToken oldRefreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        Utilisateur utilisateur = oldRefreshToken.getUtilisateur();

        if (utilisateur.getStatutCompte() != StatutCompte.VALIDE) {
            throw new BadCredentialsException("Ce compte n'est pas actif");
        }

        // 2. Rotation : révoquer l'ancien token et émettre un nouveau
        refreshTokenService.revokeToken(oldRefreshToken);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(utilisateur);

        // 3. Générer un nouvel access token JWT
        CustomUserPrincipal principal = new CustomUserPrincipal(utilisateur);
        String newAccessToken = jwtService.generateToken(principal);

        return LoginResponseDTO.builder()
                .token(newAccessToken)
                .type("Bearer")
                .refreshToken(newRefreshToken.getToken())
                .id(utilisateur.getId_utilisateur())
                .email(utilisateur.getEmail())
                .nomComplet(principal.getNomComplet())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().name() : null)
                .build();
    }

    @Override
    public void logout(Authentication authentication) {
        CustomUserPrincipal principal = extractPrincipal(authentication);
        // Révoquer tous les refresh tokens de l'utilisateur
        refreshTokenService.revokeAllUserTokens(principal.getUtilisateur());
    }
}