package org.odk.tooth_office.DTO.MapperDTO;



import org.mapstruct.*;
import org.odk.tooth_office.DTO.ConsultationCreateDTO;
import org.odk.tooth_office.DTO.ConsultationDTO;
import org.odk.tooth_office.DTO.ConsultationPatchDTO;
import org.odk.tooth_office.Entity.Consultation;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Patient;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    @Mapping(target = "dossierMedical", ignore = true)
    @Mapping(target = "dentiste", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    Consultation toConsultation(ConsultationCreateDTO createDTO);

    @Mapping(source= "consultation.dossierMedical.patient" , target = "patient")
    @Mapping(source= "consultation.dentiste", target = "dentiste")
    @Mapping(source= "consultation.dossierMedical.patient.telephone", target = "telPatient")
    ConsultationDTO toConsultationDTO(Consultation consultation);
    default String map(Patient patient){
        if(patient == null){
            return null;
        }
        return patient.getPrenom() + " " + patient.getNom();
    }
    default String map(Dentiste dentiste){
        if(dentiste == null){
            return null;
        }
        return dentiste.getPrenom() + " " + dentiste.getNom();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchToConsultation(ConsultationPatchDTO patchDTO, @MappingTarget Consultation consultation);

}
