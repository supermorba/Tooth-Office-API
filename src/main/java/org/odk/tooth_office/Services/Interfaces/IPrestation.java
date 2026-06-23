package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.PrestationDTO;

import java.util.List;

public interface IPrestation {
    //PrestationDTO create(PrestationDTO dto);

    PrestationDTO update(Long id, PrestationDTO dto);

    PrestationDTO getById(Long id);

    List<PrestationDTO> getAll();

    void delete(Long id);

}
