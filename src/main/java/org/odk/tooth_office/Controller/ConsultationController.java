package org.odk.tooth_office.Controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Services.Interfaces.IConsultation;
import org.odk.tooth_office.utils.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Consultations", description = "Gestion des consultations médicales")
public class ConsultationController {

    private final IConsultation consultationServcice;

    @GetMapping("/consultation/{id}/patient")
    public Response getConsultationByPatient(@PathVariable Long id){
        try {
            return consultationServcice.getConsultationByPatient(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @GetMapping("/consultation/{id}/dentiste")
    public Response getConsultationByDentiste(@PathVariable Long id){
        try {
            return consultationServcice.getConsultationByDentist(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @PostMapping("/consultation")
    public Response saveConsultation(@RequestBody Consultation consultation){
        try {
            return consultationServcice.save(consultation);

    } catch (Exception e) {
        e.printStackTrace(System.out);
        return Response.error("Erreur au niveau du serveur");
    }
    }

    @GetMapping("/consultations")
    public Response getAllConsultations(){
        try {
            return consultationServcice.getAll();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @PutMapping("/consultation")
    public Response updateConsultation(@RequestBody Consultation consultation){
        try {
            return consultationServcice.save(consultation);

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @DeleteMapping("/consultation/{id}")
    public Response deleteConsultation(@PathVariable Long id){
        try {
            return consultationServcice.delete(id);

    } catch (Exception e) {
        e.printStackTrace(System.out);
        return Response.error("Erreur au niveau du serveur");
    }
    }



}
