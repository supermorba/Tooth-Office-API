package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.AdminSystemDTO;
import org.odk.tooth_office.Entity.AdminSystem;
import org.odk.tooth_office.Repository.AdminSystemRepository;
import org.odk.tooth_office.Services.Interfaces.AdminSystemService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSystemServiceImplementation implements AdminSystemService {

    private final AdminSystemRepository adminSystemRepository;
    private final PasswordService passwordService;

    @Override
    public List<AdminSystemDTO> getAll() {
        return adminSystemRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdminSystemDTO> getById(Long id) {
        return adminSystemRepository.findById(id).map(this::toDto);
    }

    @Override
    public AdminSystemDTO create(AdminSystemDTO dto) {
        AdminSystem saved = adminSystemRepository.save(toEntity(dto, new AdminSystem()));
        return toDto(saved);
    }

    @Override
    public Optional<AdminSystemDTO> update(Long id, AdminSystemDTO dto) {
        return adminSystemRepository.findById(id).map(existing -> {
            AdminSystem updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            return toDto(adminSystemRepository.save(updated));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!adminSystemRepository.existsById(id)) {
            return false;
        }
        adminSystemRepository.deleteById(id);
        return true;
    }

    private AdminSystemDTO toDto(AdminSystem adminSystem) {
        AdminSystemDTO dto = new AdminSystemDTO();
        dto.setId_utilisateur(adminSystem.getId_utilisateur());
        dto.setNom(adminSystem.getNom());
        dto.setPrenom(adminSystem.getPrenom());
        dto.setEmail(adminSystem.getEmail());
        dto.setAdresse(adminSystem.getAdresse());
        dto.setRole(adminSystem.getRole());
        dto.setTelephone(adminSystem.getTelephone());
        dto.setStatutCompte(adminSystem.getStatutCompte());
        dto.setCreatedAt(adminSystem.getCreatedAt());
        dto.setUpdatedAt(adminSystem.getUpdatedAt());
        dto.setCreatedBy(adminSystem.getCreatedBy());
        dto.setUpdatedBy(adminSystem.getUpdatedBy());
        dto.setNiveauPrivilege(adminSystem.getNiveauPrivilege());
        dto.setDateDerniereConnexion(adminSystem.getDateDerniereConnexion());
        return dto;
    }

    private AdminSystem toEntity(AdminSystemDTO dto, AdminSystem adminSystem) {
        adminSystem.setNom(dto.getNom());
        adminSystem.setPrenom(dto.getPrenom());
        adminSystem.setEmail(dto.getEmail());
        if (dto.getMpd() != null && !dto.getMpd().isBlank()) {
            adminSystem.setMpd(passwordService.encodeIfNeeded(dto.getMpd()));
        }
        adminSystem.setAdresse(dto.getAdresse());
        adminSystem.setRole(dto.getRole());
        adminSystem.setTelephone(dto.getTelephone());
        adminSystem.setStatutCompte(dto.getStatutCompte());
        adminSystem.setCreatedAt(dto.getCreatedAt());
        adminSystem.setUpdatedAt(dto.getUpdatedAt());
        adminSystem.setCreatedBy(dto.getCreatedBy());
        adminSystem.setUpdatedBy(dto.getUpdatedBy());
        adminSystem.setNiveauPrivilege(dto.getNiveauPrivilege());
        adminSystem.setDateDerniereConnexion(dto.getDateDerniereConnexion());
        return adminSystem;
    }
}
