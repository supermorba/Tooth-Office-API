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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecretaireServiceImplementation implements SecretaireService {

    private final SecretaireRepository secretaireRepository;
    private final CabinetRepository cabinetRepository;
    private final ChefCabinetRepository chefCabinetRepository;
    private final SecretaireMapper secretaireMapper;

    @Override
    public List<SecretaireResponseDTO> recupererTous() {
        return secretaireRepository.findAll().stream()
                .map(secretaireMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<SecretaireResponseDTO> getById(Long id) {
        return secretaireRepository.findById(id)
                .map(secretaireMapper::toResponseDTO);
    }

    @Override
    public SecretaireResponseDTO create(SecretaireDTO dto) {
        Secretaire secretaire = toEntity(dto, new Secretaire());
        Secretaire saved = secretaireRepository.save(secretaire);
        return secretaireMapper.toResponseDTO(saved);
    }

    @Override
    public Optional<SecretaireResponseDTO> update(Long id, SecretaireDTO dto) {
        return secretaireRepository.findById(id).map(existing -> {
            Secretaire updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            Secretaire saved = secretaireRepository.save(updated);
            return secretaireMapper.toResponseDTO(saved);
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

    // Cette méthode gère la transformation des requêtes (DTO entrante) vers l'entité de la BDD
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
