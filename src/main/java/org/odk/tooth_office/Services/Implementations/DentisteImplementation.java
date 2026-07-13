package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentisteImplementation implements DentisteService {

    private final DentisteRepository dentisteRepository;
    private final DentisteMapper dentisteMapper;

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
}
