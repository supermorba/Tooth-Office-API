package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.Consultation;

import java.util.List;

public interface IConsultation extends IService<Consultation>{
    List<Consultation> getConsultationByDentist(Long idDentiste);
    List<Consultation> getConsultationByPatient(Long idPatient);

}
