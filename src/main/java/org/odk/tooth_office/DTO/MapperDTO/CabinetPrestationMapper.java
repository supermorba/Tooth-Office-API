package org.odk.tooth_office.DTO.MapperDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.odk.tooth_office.DTO.CabinetPrestationDTO;
import org.odk.tooth_office.Entity.CabinetPrestation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CabinetPrestationMapper {
    @Mapping(target = "cabinetId", source = "cabinet.idCabinet")
    @Mapping(target = "nomCabinet", source = "cabinet.nomCabinet")
    @Mapping(target = "prestationId", source = "prestation.id")
    @Mapping(target = "nomPrestation", source = "prestation.nomPrestation")
    CabinetPrestationDTO toDto(CabinetPrestation entity);

    List<CabinetPrestationDTO> toDto(List<CabinetPrestation> entities);
}
