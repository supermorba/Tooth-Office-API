package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Représente un refresh token persisté en base.
 * Associé à un Utilisateur, il permet de renouveler l'access token JWT
 * sans que l'utilisateur ait à se reconnecter.
 */
@Entity
@Table(name = "RefreshToken")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valeur opaque du token (UUID).
     */
    @Column(nullable = false, unique = true, length = 36)
    private String token;

    /**
     * Date d'expiration absolue du refresh token.
     */
    @Column(nullable = false)
    private Instant expiresAt;

    /**
     * Indique si le token a été révoqué manuellement (logout).
     */
    @Column(nullable = false)
    private boolean revoked;

    /**
     * Utilisateur propriétaire du refresh token.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}
