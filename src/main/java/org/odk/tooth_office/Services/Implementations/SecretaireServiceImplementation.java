package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.SecretaireDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.odk.tooth_office.Entity.Secretaire;
import org.odk.tooth_office.Mapper.SecretaireMapper;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.ChefCabinetRepository;
import org.odk.tooth_office.Repository.SecretaireRepository;
import org.odk.tooth_office.Services.Interfaces.SecretaireService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SecretaireServiceImplementation implements SecretaireService {

    private final SecretaireRepository secretaireRepository;
    private final CabinetRepository cabinetRepository;
    private final ChefCabinetRepository chefCabinetRepository;
    private final PasswordService passwordService;

    @Override
    public List<SecretaireResponseDTO> getAll() {
        return secretaireRepository.findAll().stream()
                .map(SecretaireMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<SecretaireResponseDTO> getById(Long id) {
        return secretaireRepository.findById(id)
                .map(SecretaireMapper::toResponseDTO);
    }

    @Override
    public SecretaireResponseDTO create(SecretaireDTO dto) {
        Secretaire secretaire = toEntity(dto, new Secretaire());
        Secretaire saved = secretaireRepository.save(secretaire);
        return SecretaireMapper.toResponseDTO(saved);
    }

    @Override
    public Optional<SecretaireResponseDTO> update(Long id, SecretaireDTO dto) {
        return secretaireRepository.findById(id).map(existing -> {
            Secretaire updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            Secretaire saved = secretaireRepository.save(updated);
            return SecretaireMapper.toResponseDTO(saved);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!secretaireRepository.existsById(id)) {
            return false;
        }
        secretaireRepository.deleteById(id);
        return true;
    }

    private SecretaireDTO toDto(Secretaire secretaire) {
        SecretaireDTO dto = new SecretaireDTO();
        dto.setId_utilisateur(secretaire.getId_utilisateur());
        dto.setNom(secretaire.getNom());
        dto.setPrenom(secretaire.getPrenom());
        dto.setEmail(secretaire.getEmail());
        dto.setAdresse(secretaire.getAdresse());
        dto.setRole(secretaire.getRole());
        dto.setTelephone(secretaire.getTelephone());
        dto.setStatutCompte(secretaire.getStatutCompte());
        dto.setCreatedAt(secretaire.getCreatedAt());
        dto.setUpdatedAt(secretaire.getUpdatedAt());
        dto.setCreatedBy(secretaire.getCreatedBy());
        dto.setUpdatedBy(secretaire.getUpdatedBy());

        dto.setCabinetId(secretaire.getCabinet() != null ? secretaire.getCabinet().getIdCabinet() : null);
        dto.setChefCabinetId(secretaire.getChefCabinet() != null ? secretaire.getChefCabinet().getId_utilisateur() : null);
        return dto;
    }

    private Secretaire toEntity(SecretaireDTO dto, Secretaire secretaire) {
        secretaire.setNom(dto.getNom());
        secretaire.setPrenom(dto.getPrenom());
        secretaire.setEmail(dto.getEmail());
        if (dto.getMpd() != null && !dto.getMpd().isBlank()) {
            secretaire.setMpd(passwordService.encodeIfNeeded(dto.getMpd()));
        }
        secretaire.setAdresse(dto.getAdresse());
        secretaire.setRole(dto.getRole());
        secretaire.setTelephone(dto.getTelephone());
        secretaire.setStatutCompte(dto.getStatutCompte());
        secretaire.setCreatedAt(dto.getCreatedAt());
        secretaire.setUpdatedAt(dto.getUpdatedAt());
        secretaire.setCreatedBy(dto.getCreatedBy());
        secretaire.setUpdatedBy(dto.getUpdatedBy());

        Cabinet cabinet = dto.getCabinetId() == null ? null : cabinetRepository.findById(dto.getCabinetId()).orElse(null);
        ChefCabinet chefCabinet = dto.getChefCabinetId() == null
                ? null
                : chefCabinetRepository.findById(dto.getChefCabinetId()).orElse(null);
        secretaire.setCabinet(cabinet);
        secretaire.setChefCabinet(chefCabinet);

        return secretaire;
    }
}
