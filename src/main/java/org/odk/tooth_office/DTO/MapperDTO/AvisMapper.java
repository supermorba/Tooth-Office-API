package org.odk.tooth_office.DTO.MapperDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.DTO.AvisResponseDTO;
import org.odk.tooth_office.Entity.Avis;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AvisMapper {
    AvisResponseDTO toResponseDTO(Avis avis);

    @Mapping(target = "nomCabinet", source = "cabinet.nom_cabinet")
    @Mapping(target = "nomPatient", source = "patient.nom_patient")
    AvisDetailDTO toDetailDTO(Avis avis);

    List<AvisResponseDTO> toResponseDTOList(List<Avis> avis);

    // --- Conversion DTO -> Entité ---

    // Pour la création, on ne mappe que les IDs.
    // Le Service devra charger les vrais objets Cabinet/Patient depuis la BDD.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "cabinet", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Avis toEntity(AvisRequestDTO dto);
}
