package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.*;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers-medicaux")
@RequiredArgsConstructor
@Tag(name = "Dossiers médicaux", description = "Gestion des dossiers médicaux des patients")
@PreAuthorize("hasAnyRole('ADMIN_SYSTEM','CHEF_CABINET','DENTISTE','SECRETAIRE','PATIENT')")
public class DossierMedicalController {

    private final IDossierMedical dossierMedicalService;

    @PostMapping
    @Operation(summary = "Créer un nouveau dossier médical")
    public ResponseEntity<DossierMedicalDTO> createDossier(@Valid @RequestBody DossierMedicalDTO dto) {
        DossierMedicalDTO saved = dossierMedicalService.createDossierMedical(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un dossier médical par son ID")
    public ResponseEntity<DossierMedicalDTO> getDossierById(@PathVariable Long id) {
        return ResponseEntity.ok(dossierMedicalService.getDossierMedicalById(id));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Récupérer le dossier médical d'un patient")
    public ResponseEntity<DossierMedicalDTO> getDossierByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(dossierMedicalService.getDossierMedicalByPatientId(patientId));
    }

    @GetMapping("/patient/{patientId}/exists")
    @Operation(summary = "Vérifier si un patient a un dossier médical")
    public ResponseEntity<Boolean> existsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(dossierMedicalService.existsByPatientId(patientId));
    }

    @GetMapping
    @Operation(summary = "Lister tous les dossiers médicaux")
    public ResponseEntity<List<DossierMedicalDTO>> getAllDossiers() {
        return ResponseEntity.ok(dossierMedicalService.getAllDossiersMedicaux());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour l'intégralité d'un dossier médical")
    public ResponseEntity<DossierMedicalDTO> updateDossier(
            @PathVariable Long id,
            @Valid @RequestBody DossierMedicalDTO dto) {

        return ResponseEntity.ok(dossierMedicalService.updateDossierMedical(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un dossier médical")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierMedicalService.deleteDossierMedical(id);
        return ResponseEntity.noContent().build();
    }

    // Granular endpoints for sub-resources

    @PostMapping("/{id}/allergies")
    @Operation(summary = "Ajouter une allergie au dossier médical")
    public ResponseEntity<AllergieIntoleranceDTO> addAllergie(
            @PathVariable Long id,
            @Valid @RequestBody AllergieIntoleranceDTO dto) {
        AllergieIntoleranceDTO saved = dossierMedicalService.addAllergie(id, dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/allergies/{allergieId}")
    @Operation(summary = "Supprimer une allergie du dossier médical")
    public ResponseEntity<Void> removeAllergie(
            @PathVariable Long id,
            @PathVariable Long allergieId) {
        dossierMedicalService.removeAllergie(id, allergieId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/antecedents")
    @Operation(summary = "Ajouter un antécédent au dossier médical")
    public ResponseEntity<AntecedentDTO> addAntecedent(
            @PathVariable Long id,
            @Valid @RequestBody AntecedentDTO dto) {
        AntecedentDTO saved = dossierMedicalService.addAntecedent(id, dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/antecedents/{antecedentId}")
    @Operation(summary = "Supprimer un antécédent du dossier médical")
    public ResponseEntity<Void> removeAntecedent(
            @PathVariable Long id,
            @PathVariable Long antecedentId) {
        dossierMedicalService.removeAntecedent(id, antecedentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pathologies")
    @Operation(summary = "Ajouter une pathologie chronique au dossier médical")
    public ResponseEntity<PathologieChroniqueDTO> addPathologie(
            @PathVariable Long id,
            @Valid @RequestBody PathologieChroniqueDTO dto) {
        PathologieChroniqueDTO saved = dossierMedicalService.addPathologie(id, dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/pathologies/{pathologieId}")
    @Operation(summary = "Supprimer une pathologie chronique du dossier médical")
    public ResponseEntity<Void> removePathologie(
            @PathVariable Long id,
            @PathVariable Long pathologieId) {
        dossierMedicalService.removePathologie(id, pathologieId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/medicaments")
    @Operation(summary = "Ajouter un médicament en cours au dossier médical")
    public ResponseEntity<MedicamentEnCoursDTO> addMedicament(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentEnCoursDTO dto) {
        MedicamentEnCoursDTO saved = dossierMedicalService.addMedicament(id, dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/medicaments/{medicamentId}")
    @Operation(summary = "Supprimer un médicament en cours du dossier médical")
    public ResponseEntity<Void> removeMedicament(
            @PathVariable Long id,
            @PathVariable Long medicamentId) {
        dossierMedicalService.removeMedicament(id, medicamentId);
        return ResponseEntity.noContent().build();
    }
}