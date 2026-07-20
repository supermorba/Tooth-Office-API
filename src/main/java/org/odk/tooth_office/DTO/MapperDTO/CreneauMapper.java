package org.odk.tooth_office.DTO.MapperDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.odk.tooth_office.DTO.CreneauDtoSurplace;
import org.odk.tooth_office.Entity.Creneau;

@Mapper(componentModel = "spring")
public interface CreneauMapper {

    @Mapping(target = "idCreneau", ignore = true)
    @Mapping(target = "dentiste", ignore = true)
    Creneau toEntity(CreneauDtoSurplace dto);

    @Mapping(target = "dentisteId", source = "dentiste.id_utilisateur")
    CreneauDtoSurplace toDto(Creneau entity);
}
