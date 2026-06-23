package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationDTO;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.utils.Response;

import java.util.List;

public interface IConsultation extends IService<ConsultationCreateDTO, Long>{
    Response completConsultation(ConsultationCreateDTO createDTO);
    Response getConsultationByDentist(Long idDentiste);
    Response getConsultationByPatient(Long idPatient);


}
