package org.odk.tooth_office.auth;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.RefreshToken;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Gère le cycle de vie des refresh tokens :
 * création, validation, rotation et révocation.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    /**
     * Crée et persiste un nouveau refresh token pour l'utilisateur.
     */
    @Transactional
    public RefreshToken createRefreshToken(Utilisateur utilisateur) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .utilisateur(utilisateur)
                .expiresAt(Instant.now().plusMillis(refreshExpirationMs))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Valide le refresh token : existence, non-révoqué, non-expiré.
     * Lance une BadCredentialsException si invalide.
     */
    public RefreshToken validateRefreshToken(String tokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new BadCredentialsException("Refresh token introuvable ou invalide"));

        if (refreshToken.isRevoked()) {
            throw new BadCredentialsException("Ce refresh token a été révoqué");
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            // Révoquer automatiquement le token expiré
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            throw new BadCredentialsException("Le refresh token a expiré, veuillez vous reconnecter");
        }

        return refreshToken;
    }

    /**
     * Révoque un refresh token spécifique (rotation de token).
     */
    @Transactional
    public void revokeToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * Révoque tous les refresh tokens de l'utilisateur (déconnexion totale).
     */
    @Transactional
    public void revokeAllUserTokens(Utilisateur utilisateur) {
        refreshTokenRepository.revokeAllByUtilisateur(utilisateur);
    }
}
