package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ConsultationDTO;
import org.odk.tooth_office.DTO.MapperDTO.ConsultationMapper;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Repository.ConsultationRepository;
import org.odk.tooth_office.Services.Interfaces.IConsultation;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService implements IConsultation {
    private final ConsultationRepository repository;
    private final ConsultationMapper consultationMapper;

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
           return Response.error("Erreur lors de la recuperation des consultations");
        }
    }

    @Override
    public Response getConsultationByPatient(Long idPatient) {
        try {
            List<Consultation> consultations = repository.getByPatient(idPatient);
            List<ConsultationDTO> consultationDTOS= new ArrayList<>();
            consultations.forEach(c -> {
                consultationDTOS.add(consultationMapper.toConsultationDTO(c));
            });
            return Response.succes("La liste des consultations du patient", consultationDTOS);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de la recuperation des consultations");
        }
    }

    @Override
    public Consultation save(Consultation entity) {
        return null;
    }

    @Override
    public Consultation update(Consultation entity) {
        return null;
    }

    @Override
    public Consultation getById(Long id) {
        return null;
    }

    @Override
    public List<Consultation> getAll() {
        return List.of();
    }
}
