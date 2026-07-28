package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentisteImplementation implements DentisteService {

    private final DentisteRepository dentisteRepository;
    private final DentisteMapper dentisteMapper;
    private final CabinetRepository cabinetRepository;
    private final PasswordService passwordService;

    @Override
    public List<DentisteResponseDTO> getAll() {
        return dentisteRepository.findAll().stream()
                .map(dentisteMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<DentisteResponseDTO> getById(Long id) {
        return dentisteRepository.findById(id)
                .map(dentisteMapper::toResponseDTO);
    }

    @Override
    public boolean delete(Long id) {
        if (!dentisteRepository.existsById(id)) {
            return false;
        }
        dentisteRepository.deleteById(id);
        return true;
    }

    @Override
    public void save(Dentiste dentiste) {
        dentisteRepository.save(dentiste);
    }

    @Override
    public void update(Dentiste dentiste) {
        dentisteRepository.save(dentiste);
    }

    @Override
    public DentisteResponseDTO create(DentisteDTO dto) {
        Dentiste dentiste = toEntity(dto, new Dentiste());
        Dentiste saved = dentisteRepository.save(dentiste);
        return dentisteMapper.toResponseDTO(saved);
    }

    @Override
    public Optional<DentisteResponseDTO> update(Long id, DentisteDTO dto) {
        return dentisteRepository.findById(id).map(existing -> {
            Dentiste updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            Dentiste saved = dentisteRepository.save(updated);
            return dentisteMapper.toResponseDTO(saved);
        });
    }

    private Dentiste toEntity(DentisteDTO dto, Dentiste dentiste) {
        dentiste.setNom(dto.getNom());
        dentiste.setPrenom(dto.getPrenom());
        dentiste.setEmail(dto.getEmail());
        if (dto.getMdp() != null && !dto.getMdp().isBlank()) {
            dentiste.setMdp(passwordService.encodeIfNeeded(dto.getMdp()));
        }
        dentiste.setAdresse(dto.getAdresse());
        dentiste.setRole(dto.getRole());
        dentiste.setTelephone(dto.getTelephone());
        dentiste.setStatutCompte(dto.getStatutCompte());
        dentiste.setCreatedAt(dto.getCreatedAt());
        dentiste.setUpdatedAt(dto.getUpdatedAt());
        dentiste.setCreatedBy(dto.getCreatedBy());
        dentiste.setUpdatedBy(dto.getUpdatedBy());
        dentiste.setSpecialite(dto.getSpecialite());

        Cabinet cabinet = dto.getCabinetId() == null ? null : cabinetRepository.findById(dto.getCabinetId()).orElse(null);
        dentiste.setCabinet(cabinet);

        return dentiste;
    }
}
