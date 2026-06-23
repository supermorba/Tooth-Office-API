package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.DossierMedical;

import java.util.List;

public interface IDossierMedical {

    DossierMedical createDossierMedical(DossierMedical dossierMedical);

    DossierMedical getDossierMedicalById(Long id);

    //DossierMedical getDossierMedicalByPatientId(Long patientId);

    List<DossierMedical> getAllDossiersMedicaux();

    DossierMedical updateDossierMedical(Long id, DossierMedical dossierMedical);

    void deleteDossierMedical(Long id);
}
