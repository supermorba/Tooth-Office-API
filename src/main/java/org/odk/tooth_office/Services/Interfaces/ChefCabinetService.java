package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.ChefCabinetDTO;

import java.util.List;
import java.util.Optional;

public interface ChefCabinetService {
    List<ChefCabinetDTO> getAll();
    Optional<ChefCabinetDTO> getById(Long id);
    ChefCabinetDTO create(ChefCabinetDTO dto);
    Optional<ChefCabinetDTO> update(Long id, ChefCabinetDTO dto);
    boolean delete(Long id);
    List<CabinetResponseDTO>  getCabinetsChefCabinets(Long id);
}