package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.Entity.DossierMedical;
import org.odk.tooth_office.Repository.DossierMedicalRepository;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DossierMedicalServiceImpl implements IDossierMedical {

    private final DossierMedicalRepository dossierMedicalRepository;

    @Override
    public DossierMedical createDossierMedical(DossierMedical dossierMedical) {
        return dossierMedicalRepository.save(dossierMedical);
    }

    @Override
    public DossierMedical getDossierMedicalById(Long id) {
        return dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable avec l'ID : " + id));
    }

    @Override
    public DossierMedical getDossierMedicalByPatientId(Long patientId) {
        return dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable pour le patient : " + patientId));
    }

    @Override
    public List<DossierMedical> getAllDossiersMedicaux() {
        return dossierMedicalRepository.findAll();
    }

    @Override
    public DossierMedical updateDossierMedical(Long id, DossierMedical dossierMedical) {
        DossierMedical dossierExistant = getDossierMedicalById(id);

        dossierExistant.setAntecedents(dossierMedical.getAntecedents());
        dossierExistant.setAllergies(dossierMedical.getAllergies());
        dossierExistant.setHistoriques(dossierMedical.getHistoriques()); // La coquille est corrigée ici

        return dossierMedicalRepository.save(dossierExistant);
    }
    @Override
    public void deleteDossierMedical(Long id) {
        DossierMedical dossier = getDossierMedicalById(id);
        dossierMedicalRepository.delete(dossier);
    }
}
