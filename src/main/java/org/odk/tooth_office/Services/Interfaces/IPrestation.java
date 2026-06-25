package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.PrestationDTO;
import org.odk.tooth_office.Entity.Prestation;

import java.util.List;

public interface IPrestation {
    Prestation create(PrestationDTO dto);

    PrestationDTO update(Long id, PrestationDTO dto);

    PrestationDTO getById(Long id);

    List<PrestationDTO> getAll();

    void delete(Long id);

}
