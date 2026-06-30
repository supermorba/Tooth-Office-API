package org.odk.tooth_office.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationPatchDTO;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Services.Interfaces.IConsultation;
import org.odk.tooth_office.utils.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultationController {

    private final IConsultation consultationServcice;

    @GetMapping("/consultation/patient/{id}")
    public Response getConsultationByPatient(@PathVariable Long id){
        try {
            System.out.println("id patient: "+ id);
            return consultationServcice.getConsultationByPatient(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @GetMapping("/consultation/dentiste/{id}")
    public Response getConsultationByDentiste(@PathVariable Long id){
        try {

            return consultationServcice.getConsultationByDentist(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }
    }

    @PostMapping("/consultation")
    public Response saveConsultation(@Valid @RequestBody ConsultationCreateDTO consultation){
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

    @PutMapping("/consultation/{id}")
    public Response updateConsultation(@Valid @RequestBody ConsultationCreateDTO consultation, @PathVariable Long id){
        try {
            System.out.println("id a modifieer "+ id);
            return consultationServcice.update(consultation, id);

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

    @PatchMapping("/consultation/{id}")
    public Response updatePatch(@RequestBody ConsultationPatchDTO patchDTO, @PathVariable Long id){
        try {
            return consultationServcice.patchUpdate(patchDTO, id);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
    }

}}
