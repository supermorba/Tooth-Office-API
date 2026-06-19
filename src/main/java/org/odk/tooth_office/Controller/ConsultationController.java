package org.odk.tooth_office.Controller;


import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final IConsultation consultation;

    @GetMapping("/{id}/consultation")
    public Response getConsultationByPatient(@PathVariable Long id){
        try {
            return consultation.getConsultationByPatient(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

}
