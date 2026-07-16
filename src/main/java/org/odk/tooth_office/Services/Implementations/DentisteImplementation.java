package org.odk.tooth_office.Services.Implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DentisteImplementation implements DentisteService {

    private final DentisteRepository dentisteRepository;
    private final DentisteMapper dentisteMapper;
    private final CabinetRepository cabinetRepository;

    @Override
    public List<DentisteResponseDTO> getAll() {
        return dentisteRepository.findAll().stream()
                .map(dentisteMapper::toResponseDTO)
                .toList();
    }


    @Override
    public List<DentisteResponseDTO> afficherDentistesParCabinet(Integer idCabinet) {
        return cabinetRepository.findById(idCabinet)
                .map(c -> c.getDentistes().stream()
                        .map(dentisteMapper::toResponseDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));
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

