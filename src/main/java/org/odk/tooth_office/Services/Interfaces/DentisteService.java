package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.Entity.Dentiste;

import java.util.List;
import java.util.Optional;

public interface DentisteService {

        Optional<DentisteResponseDTO> getById(Long id);
        List<DentisteResponseDTO> getAll();
        boolean delete(Long id);
        void save(Dentiste dentiste);
        void update(Dentiste dentiste);
        DentisteResponseDTO create(DentisteDTO dto);
        Optional<DentisteResponseDTO> update(Long id, DentisteDTO dto);
}
