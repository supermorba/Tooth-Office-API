package org.odk.tooth_office.Services.Implementations;

import java.time.LocalDate;
import java.util.List;

import org.odk.tooth_office.DTO.CabinetPrestationDTO;
import org.odk.tooth_office.DTO.MapperDTO.CabinetPrestationMapper;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.CabinetPrestation;
import org.odk.tooth_office.Entity.Prestation;
import org.odk.tooth_office.Repository.CabinetPrestationRepository;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.PrestationRepository;
import org.odk.tooth_office.Services.Interfaces.ICabinetPrestation;
import org.odk.tooth_office.Services.Interfaces.SecretaireService;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CabinetPrestationServiceImpl implements ICabinetPrestation {
    public final CabinetPrestationRepository repository;
    public final CabinetPrestationMapper mapper;
    public final CabinetRepository cabinetRepository;
    public final PrestationRepository prestationRepository;

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

    @Override
    public Response saveCabinetPrestation(CabinetPrestationDTO dto) {
        try {
            Cabinet cabinet = cabinetRepository.findById(dto.cabinetId())
                    .orElseThrow(() -> new RuntimeException("Cabinet introuvable"));

            String nomPrestation = dto.nomPrestation();
            Prestation prestation = prestationRepository.findByNomPrestation(nomPrestation)
                    .orElseGet(() -> {
                        Prestation newPrestation = new Prestation();
                        newPrestation.setNomPrestation(nomPrestation);
                        newPrestation.setDateCreation(LocalDate.now());
                        return prestationRepository.save(newPrestation);
                    });

            List<CabinetPrestation> existing = repository.getCabinetPrestations((long) dto.cabinetId());
            CabinetPrestation cabinetPrestation = existing.stream()
                    .filter(cp -> cp.getPrestation().getId() == prestation.getId())
                    .findFirst()
                    .orElse(null);

            if (cabinetPrestation == null) {
                cabinetPrestation = new CabinetPrestation();
                cabinetPrestation.setCabinet(cabinet);
                cabinetPrestation.setPrestation(prestation);
            }

            cabinetPrestation.setPrix(dto.prix());
            cabinetPrestation.setDescription(dto.description());

            CabinetPrestation saved = repository.save(cabinetPrestation);
            return Response.succes("Prestation enregistrée pour le cabinet", mapper.toDto(saved));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de l'enregistrement de la prestation: " + e.getMessage());
        }
    }

    @Override
    public Response deleteCabinetPrestation(Long id) {
        try {
            if (!repository.existsById(id)) {
                return Response.error("Prestation de cabinet introuvable");
            }
            repository.deleteById(id);
            return Response.succes("Prestation de cabinet supprimée", null);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la suppression de la prestation: " + e.getMessage());
        }
    }
}
