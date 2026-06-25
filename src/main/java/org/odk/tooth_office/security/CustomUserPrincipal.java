package org.odk.tooth_office.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    private final Utilisateur utilisateur;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleEnum role = utilisateur.getRole();
        if (role == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return utilisateur.getMpd();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return utilisateur.getStatutCompte() != StatutCompte.SUSPENDU;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getStatutCompte() == StatutCompte.VALIDE;
    }

    public Long getUserId() {
        return utilisateur.getId_utilisateur();
    }

    public String getNomComplet() {
        return utilisateur.getPrenom() + " " + utilisateur.getNom();
    }
}