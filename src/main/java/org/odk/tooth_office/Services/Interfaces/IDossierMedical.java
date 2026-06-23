package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.DossierMedicalDTO;

import java.util.List;

public interface IDossierMedical {

    DossierMedicalDTO createDossierMedical(DossierMedicalDTO dto);

    DossierMedicalDTO getDossierMedicalById(Long id);

    DossierMedicalDTO getDossierMedicalByPatientId(Long patientId);

    List<DossierMedicalDTO> getAllDossiersMedicaux();

    DossierMedicalDTO updateDossierMedical(Long id, DossierMedicalDTO dto);

    void deleteDossierMedical(Long id);
}