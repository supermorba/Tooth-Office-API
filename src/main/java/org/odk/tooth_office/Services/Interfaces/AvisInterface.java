package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.Entity.Avis;

import java.util.List;

public interface AvisInterface {
    AvisDetailDTO create(AvisRequestDTO dto);
    List<AvisDetailDTO> getAll();
    void update(Avis avis);
    void delete(Avis avis);
    Avis getById(int id);
    List<Avis> findByIdCabinet(int id);
    List<Avis> findByIdClient(int id);
}
