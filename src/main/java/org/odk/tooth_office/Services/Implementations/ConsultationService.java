package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationDTO;
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
            return Response.succes("Consultation enregistrée avec succès !!", consultation);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de l'enregistrement de la consultation");
        }

    }

    @Override
    public Response update(ConsultationCreateDTO createDTO, Long id) {
       try {
           Optional<Consultation> consultationOpt = repository.getConsultationById(id);
           if(consultationOpt.isEmpty()) return Response.error("Consultation introuvable !!!");
           Response response = completConsultation(createDTO);
           if(!"ok".equalsIgnoreCase(response.getStatut())) return Response.error(response.getMessage());
           Consultation consultation = (Consultation) response.getData();
           consultation.setUpdateAt(new Date());
           consultation.setId(id);
           repository.save(consultation);
           return Response.succes("Consultation modifiée avec succès", consultation);
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
            List<Consultation> consultations = repository.findAll();
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
            if(consultationOpt.isPresent()){
                Consultation consultation = consultationOpt.get();
                consultation.setUpdateAt(new Date());
                consultation.setEnabled(false);
                repository.save(consultation);
                return Response.succes("Consultation supprimée avec succès !!", consultation);
            } else return Response.error("Cette consultation n'existe pas");
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la suppression de la consultation");

        }

    }
}
