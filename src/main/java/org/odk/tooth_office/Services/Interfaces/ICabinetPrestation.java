package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.CabinetPrestationDTO;
import org.odk.tooth_office.utils.Response;

public interface ICabinetPrestation {
    Response getPrestationCabinet(Long cabinetId);
    Response saveCabinetPrestation(CabinetPrestationDTO dto);
    Response deleteCabinetPrestation(Long id);
}
