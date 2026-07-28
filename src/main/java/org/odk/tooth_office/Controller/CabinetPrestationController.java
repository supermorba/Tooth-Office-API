package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.CabinetPrestationDTO;
import org.odk.tooth_office.Services.Interfaces.ICabinetPrestation;
import org.odk.tooth_office.utils.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cabinetprestation")
@RequiredArgsConstructor
public class CabinetPrestationController {
    public final ICabinetPrestation iCabinetPrestation;

    @GetMapping("/cabinet/{cabinetId}")
    public Response getPrestationCabinet(@PathVariable Long cabinetId){
        try {
            return iCabinetPrestation.getPrestationCabinet(cabinetId);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @PostMapping
    public Response savePrestationCabinet(@RequestBody CabinetPrestationDTO dto) {
        return iCabinetPrestation.saveCabinetPrestation(dto);
    }

    @DeleteMapping("/{id}")
    public Response deletePrestationCabinet(@PathVariable Long id) {
        return iCabinetPrestation.deleteCabinetPrestation(id);
    }
}
