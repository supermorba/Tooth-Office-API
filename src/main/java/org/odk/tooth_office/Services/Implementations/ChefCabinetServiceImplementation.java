package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ChefCabinetDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.ChefCabinetRepository;
import org.odk.tooth_office.Services.Interfaces.ChefCabinetService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChefCabinetServiceImplementation implements ChefCabinetService {

    private final ChefCabinetRepository chefCabinetRepository;
    private final CabinetRepository cabinetRepository;
    private final PasswordService passwordService;

    @Override
    public List<ChefCabinetDTO> getAll() {
        return chefCabinetRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ChefCabinetDTO> getById(Long id) {
        return chefCabinetRepository.findById(id).map(this::toDto);
    }

    @Override
    public ChefCabinetDTO create(ChefCabinetDTO dto) {
        ChefCabinet saved = chefCabinetRepository.save(toEntity(dto, new ChefCabinet()));
        return toDto(saved);
    }

    @Override
    public Optional<ChefCabinetDTO> update(Long id, ChefCabinetDTO dto) {
        return chefCabinetRepository.findById(id).map(existing -> {
            ChefCabinet updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            return toDto(chefCabinetRepository.save(updated));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!chefCabinetRepository.existsById(id)) {
            return false;
        }
        chefCabinetRepository.deleteById(id);
        return true;
    }

    private ChefCabinetDTO toDto(ChefCabinet chefCabinet) {
        ChefCabinetDTO dto = new ChefCabinetDTO();
        dto.setId_utilisateur(chefCabinet.getId_utilisateur());
        dto.setNom(chefCabinet.getNom());
        dto.setPrenom(chefCabinet.getPrenom());
        dto.setEmail(chefCabinet.getEmail());
        dto.setAdresse(chefCabinet.getAdresse());
        dto.setRole(chefCabinet.getRole());
        dto.setTelephone(chefCabinet.getTelephone());
        dto.setStatutCompte(chefCabinet.getStatutCompte());
        dto.setCreatedAt(chefCabinet.getCreatedAt());
        dto.setUpdatedAt(chefCabinet.getUpdatedAt());
        dto.setCreatedBy(chefCabinet.getCreatedBy());
        dto.setUpdatedBy(chefCabinet.getUpdatedBy());

        List<Integer> cabinetIds = chefCabinet.getCabinets() == null
                ? Collections.emptyList()
                : chefCabinet.getCabinets().stream().map(Cabinet::getIdCabinet).collect(Collectors.toList());
        dto.setCabinetIds(cabinetIds);
        return dto;
    }

    private ChefCabinet toEntity(ChefCabinetDTO dto, ChefCabinet chefCabinet) {
        chefCabinet.setNom(dto.getNom());
        chefCabinet.setPrenom(dto.getPrenom());
        chefCabinet.setEmail(dto.getEmail());
        if (dto.getMpd() != null && !dto.getMpd().isBlank()) {
            chefCabinet.setMpd(passwordService.encodeIfNeeded(dto.getMpd()));
        }
        chefCabinet.setAdresse(dto.getAdresse());
        chefCabinet.setRole(dto.getRole());
        chefCabinet.setTelephone(dto.getTelephone());
        chefCabinet.setStatutCompte(dto.getStatutCompte());
        chefCabinet.setCreatedAt(dto.getCreatedAt());
        chefCabinet.setUpdatedAt(dto.getUpdatedAt());
        chefCabinet.setCreatedBy(dto.getCreatedBy());
        chefCabinet.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getCabinetIds() != null) {
            List<Cabinet> cabinets = dto.getCabinetIds().stream()
                    .map(cabinetRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            chefCabinet.setCabinets(cabinets);
        }
        return chefCabinet;
    }
}
