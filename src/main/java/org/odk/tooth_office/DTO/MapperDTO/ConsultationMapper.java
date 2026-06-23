package org.odk.tooth_office.DTO.MapperDTO;



import org.mapstruct.Mapper;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationDTO;
import org.odk.tooth_office.Entity.Consultation;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation toConsultation(ConsultationCreateDTO createDTO);
    @Mapping(
            target = "patient",
            expression = "java(consultation.getDossierMedical() != null && consultation.getDossierMedical().getPatient() != null ? consultation.getDossierMedical().getPatient().getPrenom() : null)"
    )
    @Mapping(source= "consultation.dossierMedical.patient.prenom", target = "patient")
    @Mapping(source= "consultation.dentiste.prenom", target = "dentiste")
    ConsultationDTO toConsultationDTO(Consultation consultation);
}
