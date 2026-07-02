package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationDTO;
import org.odk.tooth_office.DTO.ConsultationPatchDTO;
import org.odk.tooth_office.DTO.MapperDTO.ConsultationMapper;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.DossierMedical;
import org.odk.tooth_office.Entity.RendezVous;
import org.odk.tooth_office.Repository.*;
import org.odk.tooth_office.Services.Interfaces.IConsultation;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultationService implements IConsultation {
    private final ConsultationRepository repository;
    private final ConsultationMapper consultationMapper;
    private final DentisteRepository dentisteRepository;
    private final DossierMedicalRepository dossierMedicalRepository;
    private final RendezVousRepository rendezVousRepository;


    @Override
    public Response getConsultationByDentist(Long idDentiste) {
        try {
            if(!repository.dentisteHadConsultation(idDentiste)){
                return Response.error("Ce dentiste n'a pas de consultation enregistrée !!!");
            }
            List<Consultation> consultations = repository.getByDentiste(idDentiste);
            List<ConsultationDTO> consultationDTOS= new ArrayList<>();
            consultations.forEach(c -> {
                consultationDTOS.add(consultationMapper.toConsultationDTO(c));
            });
            return Response.succes("La liste des consultations du dentiste", consultationDTOS);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation des consultations du dentiste");
        }
    }

    @Override
    public Response getConsultationByPatient(Long idPatient) {
        try {
            if(!repository.patientHadConsultation(idPatient)){
                return Response.error("Ce patient n'a pas de consultation enregistrée !!!");
            }
            List<Consultation> consultations = repository.getByPatient(idPatient);
            List<ConsultationDTO> consultationDTOS= new ArrayList<>();
            consultations.forEach(c -> {
                System.out.println("dentiste"+ c.getDentiste().getNom());
                consultationDTOS.add(consultationMapper.toConsultationDTO(c));
            });
            return Response.succes("La liste des consultations du patient", consultationDTOS);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation des consultations du patient");
        }
    }

    @Override
    public Response completConsultation(ConsultationCreateDTO createDTO) {
        try {
            Consultation consultation = consultationMapper.toConsultation(createDTO);

            Optional<Dentiste> dentiste = dentisteRepository.findById(createDTO.idDentiste());
            Optional<DossierMedical> dossierMedical = dossierMedicalRepository.findById(createDTO.idDossierMedical());
            Optional<RendezVous> rendezVous = rendezVousRepository.findById(createDTO.idRendezVous());
            if(dentiste.isEmpty()) return Response.error("Dentiste introuvable !!");
            if(rendezVous.isEmpty()) return Response.error("Rendez-vous introuvable !!");
            if(dossierMedical.isEmpty()) return Response.error("Dossier medical introuvable !!");
            consultation.setDentiste(dentiste.get());
            consultation.setDossierMedical(dossierMedical.get());
            consultation.setRendezVous(rendezVous.get());
            return Response.succes("Consultation complète", consultation);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la completion de la consultation !!");
        }
    }

    @Override
    public Response save(ConsultationCreateDTO createDTO) {
        try {
            Response response = completConsultation(createDTO);
            if(!"ok".equalsIgnoreCase(response.getStatut())) return Response.error(response.getMessage());
            Consultation consultation = (Consultation) response.getData();
            repository.save(consultation);
            return Response.succes("Consultation enregistrée avec succès !!", consultationMapper.toConsultationDTO(consultation));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de l'enregistrement de la consultation");
        }

    }

    @Override
    public Response update(ConsultationCreateDTO createDTO, Long id) {
       try {
           System.out.println("id au niveau du service  "+ id);

           Optional<Consultation> consultationOpt = repository.getConsultationById(id);
           if(consultationOpt.isEmpty()){
               System.out.println("consultation avec cet id non trouvé  "+ id);
               return Response.error("Consultation introuvable !!!");
           }
           System.out.println("construction de consultation ");

           Response response = completConsultation(createDTO);
           if(!"ok".equalsIgnoreCase(response.getStatut())){
               System.out.println("construction echouée ");
               return Response.error(response.getMessage());
           }
           Consultation consultation = (Consultation) response.getData();
           System.out.println("consultation completerrrrrrr "+ consultation.getNotes());
           consultation.setUpdateAt(new Date());
           consultation.setId(consultationOpt.get().getId());
           System.out.println("l'id de la consultation "+ consultation.getId());
           repository.save(consultation);
           return Response.succes("Consultation modifiée avec succès", consultationMapper.toConsultationDTO(consultation));
       } catch (Exception e) {
           e.printStackTrace(System.out);
           return Response.error("Erreur lors de la modification de la consultation !!!");
       }
    }


    @Override
    public Response getById(Long id) {
        try {
            Optional<Consultation> consultation = repository.getConsultationById(id);
            return consultation.map(value -> Response.succes("Consultation recuperée", consultationMapper.toConsultationDTO(value))).orElseGet(() -> Response.error("Cette consultation n'existe pas"));
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation de la consultation");
        }

    }

    @Override
    public Response getAll() {
        try {
            List<Consultation> consultations = repository.getAll();
            List<ConsultationDTO> consultationDTOS = new ArrayList<>();
            consultations.forEach(c -> {
                consultationDTOS.add(consultationMapper.toConsultationDTO(c));
            });
            return Response.succes("La liste des consultations", consultationDTOS);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation des consultations");

        }
    }

    @Override
    public Response delete(Long id) {
        try {
            Optional<Consultation> consultationOpt = repository.getConsultationById(id);
            if(consultationOpt.isEmpty()) {
                return Response.error("Cette consultation n'existe pas");
            }
                Consultation consultation = consultationOpt.get();
                consultation.setUpdateAt(new Date());
                consultation.setEnabled(false);
                repository.save(consultation);
                return Response.succes("Consultation supprimée avec succès !!", consultationMapper.toConsultationDTO(consultation));

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la suppression de la consultation");

        }

    }


    @Override
    public Response patchUpdate(ConsultationPatchDTO patchDTO, Long id) {
       try {
           Optional<Consultation> consultationOpt = repository.getConsultationById(id);
           if(consultationOpt.isEmpty()){
               return Response.error("Cette consultation n'existe pas");
           }
           Consultation consultation= consultationOpt.get();
           consultationMapper.patchToConsultation(patchDTO, consultation);
           if(patchDTO.idDossierMedical() != null){
               Optional<DossierMedical> dossierMedicalOpt = dossierMedicalRepository.findById(patchDTO.idDossierMedical());
               if(dossierMedicalOpt.isEmpty()) return Response.error("Ce dossier n'a pas de consultation enregistrée");
               consultation.setDossierMedical(dossierMedicalOpt.get());
           }
           if(patchDTO.idDentiste() != null){
               Optional<Dentiste> dentiste = dentisteRepository.findById(patchDTO.idDentiste());
               if(dentiste.isEmpty()) return Response.error("Ce dentiste n'a pas de consultation enregistrée");
               consultation.setDentiste(dentiste.get());
           }
           if(patchDTO.idRendezVous() != null){
               Optional<RendezVous> rdv = rendezVousRepository.findById(patchDTO.idRendezVous());
               if(rdv.isEmpty()) return Response.error("Ce rdv n'a pas de consultation enregistrée");
               consultation.setRendezVous(rdv.get());
           }
           consultation.setUpdateAt(new Date());
            repository.save(consultation);
           return Response.succes("Consultion modifiée avec succès", consultationMapper.toConsultationDTO(consultation));
       } catch (Exception e) {
           e.printStackTrace(System.out);
           return Response.error("Erreur lors de la modification partielle de la consultation");
       }
    }


}
