package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.RefreshToken;
import org.odk.tooth_office.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    /**
     * Révoque tous les refresh tokens actifs d'un utilisateur (utilisé au logout).
     */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.utilisateur = :utilisateur AND rt.revoked = false")
    void revokeAllByUtilisateur(Utilisateur utilisateur);

    /**
     * Supprime les tokens expirés ou révoqués (utile pour une tâche de nettoyage).
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiresAt < :now OR rt.revoked = true")
    void deleteExpiredAndRevoked(Instant now);
}
