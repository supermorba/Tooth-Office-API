package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.Entity.Avis;

import java.util.List;

public interface AvisInterface {
    AvisDetailDTO create(AvisRequestDTO dto);
    List<AvisDetailDTO> getAll();
    AvisDetailDTO update(Long id, AvisRequestDTO dto);
    void delete(Long id);
    AvisDetailDTO getById(Long id);
    List<AvisDetailDTO> findByCabinetId(int id);
    List<AvisDetailDTO> findByPatientId(int id);
}
