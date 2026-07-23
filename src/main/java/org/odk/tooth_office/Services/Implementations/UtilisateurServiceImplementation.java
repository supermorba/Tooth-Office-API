package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.UtilisateurDTO;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.Services.Interfaces.UtilisateurService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImplementation implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordService passwordService;

    @Override
    public List<UtilisateurDTO> getAll() {
        return utilisateurRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UtilisateurDTO> getById(Long id) {
        return utilisateurRepository.findById(id).map(this::toDto);
    }

    @Override
    public UtilisateurDTO create(UtilisateurDTO dto) {
        Utilisateur saved = utilisateurRepository.save(toEntity(dto, new Utilisateur()));
        return toDto(saved);
    }

    @Override
    public Optional<UtilisateurDTO> update(Long id, UtilisateurDTO dto) {
        return utilisateurRepository.findById(id).map(existing -> {
            Utilisateur updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            return toDto(utilisateurRepository.save(updated));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            return false;
        }
        utilisateurRepository.deleteById(id);
        return true;
    }

    private UtilisateurDTO toDto(Utilisateur utilisateur) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId_utilisateur(utilisateur.getId_utilisateur());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setEmail(utilisateur.getEmail());
        dto.setAdresse(utilisateur.getAdresse());
        dto.setRole(utilisateur.getRole());
        dto.setTelephone(utilisateur.getTelephone());
        dto.setStatutCompte(utilisateur.getStatutCompte());
        dto.setCreatedAt(utilisateur.getCreatedAt());
        dto.setUpdatedAt(utilisateur.getUpdatedAt());
        dto.setCreatedBy(utilisateur.getCreatedBy());
        dto.setUpdatedBy(utilisateur.getUpdatedBy());
        return dto;
    }

    private Utilisateur toEntity(UtilisateurDTO dto, Utilisateur utilisateur) {
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        if (dto.getMdp() != null && !dto.getMdp().isBlank()) {
            utilisateur.setMdp(passwordService.encodeIfNeeded(dto.getMdp()));
        }
        utilisateur.setAdresse(dto.getAdresse());
        utilisateur.setRole(dto.getRole());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setStatutCompte(dto.getStatutCompte());
        utilisateur.setCreatedAt(dto.getCreatedAt());
        utilisateur.setUpdatedAt(dto.getUpdatedAt());
        utilisateur.setCreatedBy(dto.getCreatedBy());
        utilisateur.setUpdatedBy(dto.getUpdatedBy());
        return utilisateur;
    }
}