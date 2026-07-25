package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.*;

import java.util.List;

public interface IDossierMedical {

    DossierMedicalDTO createDossierMedical(DossierMedicalDTO dto);

    DossierMedicalDTO getDossierMedicalById(Long id);

    DossierMedicalDTO getDossierMedicalByPatientId(Long patientId);

    List<DossierMedicalDTO> getAllDossiersMedicaux();

    DossierMedicalDTO updateDossierMedical(Long id, DossierMedicalDTO dto);

    void deleteDossierMedical(Long id);

    boolean existsByPatientId(Long patientId);

    // Operational sub-resource methods
    AllergieIntoleranceDTO addAllergie(Long dossierId, AllergieIntoleranceDTO dto);

    void removeAllergie(Long dossierId, Long allergieId);

    AntecedentDTO addAntecedent(Long dossierId, AntecedentDTO dto);

    void removeAntecedent(Long dossierId, Long antecedentId);

    PathologieChroniqueDTO addPathologie(Long dossierId, PathologieChroniqueDTO dto);

    void removePathologie(Long dossierId, Long pathologieId);

    MedicamentEnCoursDTO addMedicament(Long dossierId, MedicamentEnCoursDTO dto);

    void removeMedicament(Long dossierId, Long medicamentId);
}