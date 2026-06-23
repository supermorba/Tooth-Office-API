package org.odk.tooth_office.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DossierMedicalDTO;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers-medicaux")
@RequiredArgsConstructor
public class DossierMedicalController {

    private final IDossierMedical dossierMedicalService;

    @PostMapping
    public ResponseEntity<DossierMedicalDTO> createDossier(@Valid @RequestBody DossierMedicalDTO dto) {
        DossierMedicalDTO saved = dossierMedicalService.createDossierMedical(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierMedicalDTO> getDossierById(@PathVariable Long id) {
        return ResponseEntity.ok(dossierMedicalService.getDossierMedicalById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<DossierMedicalDTO> getDossierByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(dossierMedicalService.getDossierMedicalByPatientId(patientId));
    }

    @GetMapping
    public ResponseEntity<List<DossierMedicalDTO>> getAllDossiers() {
        return ResponseEntity.ok(dossierMedicalService.getAllDossiersMedicaux());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierMedicalDTO> updateDossier(
            @PathVariable Long id,
            @Valid @RequestBody DossierMedicalDTO dto) {

        return ResponseEntity.ok(dossierMedicalService.updateDossierMedical(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierMedicalService.deleteDossierMedical(id);
        return ResponseEntity.noContent().build();
    }
}