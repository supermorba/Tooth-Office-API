package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DossierMedicalDTO;
import org.odk.tooth_office.Entity.DossierMedical;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dossiers-medicaux")
@RequiredArgsConstructor
public class DossierMedicalController {

    private final IDossierMedical dossierMedicalService;

    // 1. Créer un dossier médical
    @PostMapping
    public ResponseEntity<DossierMedicalDTO> createDossier(@RequestBody DossierMedicalDTO dto) {
        DossierMedical dossier = convertToEntity(dto);
        DossierMedical cree = dossierMedicalService.createDossierMedical(dossier);
        return new ResponseEntity<>(convertToDto(cree), HttpStatus.CREATED);
    }

    // 2. Récupérer un dossier par son ID
    @GetMapping("/{id}")
    public ResponseEntity<DossierMedicalDTO> getDossierById(@PathVariable Long id) {
        DossierMedical dossier = dossierMedicalService.getDossierMedicalById(id);
        return ResponseEntity.ok(convertToDto(dossier));
    }

    // 3. Récupérer le dossier d'un patient via son ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<DossierMedicalDTO> getDossierByPatientId(@PathVariable Long patientId) {
        DossierMedical dossier = dossierMedicalService.getDossierMedicalByPatientId(patientId);
        return ResponseEntity.ok(convertToDto(dossier));
    }

    // 4. Récupérer tous les dossiers médicaux
    @GetMapping
    public ResponseEntity<List<DossierMedicalDTO>> getAllDossiers() {
        List<DossierMedicalDTO> list = dossierMedicalService.getAllDossiersMedicaux()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // 5. Modifier un dossier médical
    @PutMapping("/{id}")
    public ResponseEntity<DossierMedicalDTO> updateDossier(@PathVariable Long id, @RequestBody DossierMedicalDTO dto) {
        DossierMedical dossierData = convertToEntity(dto);
        DossierMedical modifie = dossierMedicalService.updateDossierMedical(id, dossierData);
        return ResponseEntity.ok(convertToDto(modifie));
    }

    // 6. Supprimer un dossier médical
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierMedicalService.deleteDossierMedical(id);
        return ResponseEntity.noContent().build();
    }
    // MÉTHODES DE CONVERSION
    private DossierMedical convertToEntity(DossierMedicalDTO dto) {
        DossierMedical dossier = new DossierMedical();
        dossier.setId(dto.getId()); // Optionnel (utile pour l'update)
        dossier.setAntecedents(dto.getAntecedents());
        dossier.setAllergies(dto.getAllergies());
        dossier.setHistoriques(dto.getHistoriques());

        if (dto.getPatientId() != null) {
            Patient patient = new Patient();
            ((Utilisateur) patient).setId_utilisateur(dto.getPatientId()); // Accès sécurisé à l'ID hérité
            dossier.setPatient(patient);
        }
        return dossier;
    }

    private DossierMedicalDTO convertToDto(DossierMedical entity) {
        DossierMedicalDTO dto = new DossierMedicalDTO();
        dto.setId(entity.getId());
        dto.setAntecedents(entity.getAntecedents());
        dto.setAllergies(entity.getAllergies());
        dto.setHistoriques(entity.getHistoriques());

        if (entity.getPatient() != null) {
            Utilisateur u = entity.getPatient();
            dto.setPatientId(u.getId_utilisateur());
        }
        return dto;
    }
}