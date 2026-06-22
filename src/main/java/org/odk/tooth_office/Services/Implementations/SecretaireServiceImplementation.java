package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.SecretaireDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.odk.tooth_office.Entity.Secretaire;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.ChefCabinetRepository;
import org.odk.tooth_office.Repository.SecretaireRepository;
import org.odk.tooth_office.Services.Interfaces.SecretaireService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecretaireServiceImplementation implements SecretaireService {

    private final SecretaireRepository secretaireRepository;
    private final CabinetRepository cabinetRepository;
    private final ChefCabinetRepository chefCabinetRepository;

    @Override
    public List<SecretaireDTO> getAll() {
        return secretaireRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<SecretaireDTO> getById(Long id) {
        return secretaireRepository.findById(id).map(this::toDto);
    }

    @Override
    public SecretaireDTO create(SecretaireDTO dto) {
        Secretaire saved = secretaireRepository.save(toEntity(dto, new Secretaire()));
        return toDto(saved);
    }

    @Override
    public Optional<SecretaireDTO> update(Long id, SecretaireDTO dto) {
        return secretaireRepository.findById(id).map(existing -> {
            Secretaire updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            return toDto(secretaireRepository.save(updated));
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
        dto.setMpd(secretaire.getMpd());
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
        secretaire.setMpd(dto.getMpd());
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
