package org.odk.tooth_office.security;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cherche d'abord par email, puis par téléphone si non trouvé
        Utilisateur utilisateur = utilisateurRepository.findByEmail(username)
                .or(() -> utilisateurRepository.findByTelephone(username))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        return new CustomUserPrincipal(utilisateur);
    }
}