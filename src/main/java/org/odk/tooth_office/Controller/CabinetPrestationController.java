package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Services.Interfaces.ICabinetPrestation;
import org.odk.tooth_office.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
