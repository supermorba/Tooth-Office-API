package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.CabinetPrestationDTO;
import org.odk.tooth_office.DTO.MapperDTO.CabinetPrestationMapper;
import org.odk.tooth_office.Entity.CabinetPrestation;
import org.odk.tooth_office.Repository.CabinetPrestationRepository;
import org.odk.tooth_office.Services.Interfaces.ICabinetPrestation;
import org.odk.tooth_office.Services.Interfaces.SecretaireService;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinetPrestationServiceImpl implements ICabinetPrestation {
    public final CabinetPrestationRepository repository;
    public final CabinetPrestationMapper mapper;
    public final SecretaireService secretaireService;
    @Override
    public Response getPrestationCabinet(Long secretaireId) {
        try {
            Long idCabinet= secretaireService.getCabinetIdBySecretaireId(secretaireId);
            List<CabinetPrestation> cabinetPrestation= repository.getCabinetPrestations(idCabinet);
            List<CabinetPrestationDTO> cabinetPrestationDTOS= mapper.toDto(cabinetPrestation);
            return Response.succes("La liste des services de ce cabinet !!!", cabinetPrestationDTOS);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation de la liste des services du cabinet");
        }

    }
}
