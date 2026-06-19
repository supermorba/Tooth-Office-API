package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Services.Interfaces.IConsultation;

import java.util.List;

public class ConsultationService implements IConsultation {

    @Override
    public List<Consultation> getConsultationByDentist(Long idDentiste) {
        return List.of();
    }

    @Override
    public List<Consultation> getConsultationByPatient(Long idPatient) {
        return List.of();
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
