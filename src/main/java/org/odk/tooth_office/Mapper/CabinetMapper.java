package org.odk.tooth_office.Mapper;

import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Avis;
import org.springframework.stereotype.Component;

@Component
public class CabinetMapper {

    public CabinetResponseDTO toCabinet(Cabinet cabinet){
        CabinetResponseDTO cabinetResponseDTO = new CabinetResponseDTO();
        cabinetResponseDTO.setNomCabinet(cabinet.getNomCabinet());
        cabinetResponseDTO.setIdCabinet(cabinet.getIdCabinet());
        cabinetResponseDTO.setLogo(cabinet.getLogo());
        cabinetResponseDTO.setTel(cabinet.getTel());
        cabinetResponseDTO.setAdresse(cabinet.getAdresse());
        cabinetResponseDTO.setDescription(cabinet.getDescription());
        var avis = cabinet.getAvis();
        long nombreAvis = avis == null ? 0 : avis.size();
        double noteMoyenne = avis == null ? 0 : avis.stream()
                .mapToInt(Avis::getNote)
                .average()
                .orElse(0);
        cabinetResponseDTO.setNombreAvis(nombreAvis);
        cabinetResponseDTO.setNoteMoyenne(noteMoyenne);
        return  cabinetResponseDTO;
    }

    public Cabinet fromCabinetDTO(CabinetDTO cabinetDTO){
        Cabinet cabinet = new Cabinet();
        cabinet.setNomCabinet(cabinetDTO.getNomCabinet());
        cabinet.setLogo(cabinetDTO.getLogo());
        cabinet.setTel(cabinetDTO.getTel());
        cabinet.setAdresse(cabinetDTO.getAdresse());
        cabinet.setDescription(cabinetDTO.getDescription());
        return  cabinet;
    }
}
