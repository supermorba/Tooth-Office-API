package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.*;
import org.odk.tooth_office.Entity.*;
import org.odk.tooth_office.Enum.GraviteAllergie;
import org.odk.tooth_office.Repository.*;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DossierMedicalServiceImpl implements IDossierMedical {

    private final DossierMedicalRepository dossierMedicalRepository;
    private final AllergieIntoleranceRepository allergieRepository;
    private final AntecedentRepository antecedentRepository;
    private final PathologieChroniqueRepository pathologieRepository;
    private final MedicamentEnCoursRepository medicamentRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public DossierMedicalDTO createDossierMedical(DossierMedicalDTO dto) {
        if (dto.getPatientId() != null && dossierMedicalRepository.existsByPatientId(dto.getPatientId())) {
            throw new IllegalArgumentException("Un dossier médical existe déjà pour le patient ID : " + dto.getPatientId());
        }

        DossierMedical entity = convertToEntity(dto);

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient introuvable avec l'ID : " + dto.getPatientId()));
            entity.setPatient(patient);
        }

        DossierMedical saved = dossierMedicalRepository.save(entity);
        return convertToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DossierMedicalDTO getDossierMedicalById(Long id) {
        DossierMedical entity = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + id));
        return convertToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DossierMedicalDTO getDossierMedicalByPatientId(Long patientId) {
        DossierMedical entity = dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable pour le patient : " + patientId));
        return convertToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DossierMedicalDTO> getAllDossiersMedicaux() {
        return dossierMedicalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPatientId(Long patientId) {
        return dossierMedicalRepository.existsByPatientId(patientId);
    }

    @Override
    @Transactional
    public DossierMedicalDTO updateDossierMedical(Long id, DossierMedicalDTO dto) {
        DossierMedical existing = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + id));

        existing.setHistoriques(dto.getHistoriques());

        if (dto.getPatientId() != null && (existing.getPatient() == null || !existing.getPatient().getId_utilisateur().equals(dto.getPatientId()))) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient introuvable avec l'ID : " + dto.getPatientId()));
            existing.setPatient(patient);
        }

        updateAllergies(existing, dto.getAllergiesIntolerances());
        updateAntecedents(existing, dto.getAntecedents());
        updatePathologies(existing, dto.getPathologiesChroniques());
        updateMedicaments(existing, dto.getMedicamentsEnCours());

        DossierMedical saved = dossierMedicalRepository.save(existing);
        return convertToDto(saved);
    }

    @Override
    @Transactional
    public void deleteDossierMedical(Long id) {
        DossierMedical dossier = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + id));
        dossierMedicalRepository.delete(dossier);
    }

    // Granular Sub-Resource Methods

    @Override
    @Transactional
    public AllergieIntoleranceDTO addAllergie(Long dossierId, AllergieIntoleranceDTO dto) {
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + dossierId));
        AllergieIntolerance entity = toAllergieEntity(dto, dossier);
        AllergieIntolerance saved = allergieRepository.save(entity);
        return toAllergieDto(saved);
    }

    @Override
    @Transactional
    public void removeAllergie(Long dossierId, Long allergieId) {
        AllergieIntolerance allergie = allergieRepository.findById(allergieId)
                .orElseThrow(() -> new IllegalArgumentException("Allergie introuvable avec l'ID : " + allergieId));
        if (!allergie.getDossierMedical().getId().equals(dossierId)) {
            throw new IllegalArgumentException("L'allergie spécifiée n'appartient pas à ce dossier médical.");
        }
        allergieRepository.delete(allergie);
    }

    @Override
    @Transactional
    public AntecedentDTO addAntecedent(Long dossierId, AntecedentDTO dto) {
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + dossierId));
        Antecedent entity = toAntecedentEntity(dto, dossier);
        Antecedent saved = antecedentRepository.save(entity);
        return toAntecedentDto(saved);
    }

    @Override
    @Transactional
    public void removeAntecedent(Long dossierId, Long antecedentId) {
        Antecedent antecedent = antecedentRepository.findById(antecedentId)
                .orElseThrow(() -> new IllegalArgumentException("Antécédent introuvable avec l'ID : " + antecedentId));
        if (!antecedent.getDossierMedical().getId().equals(dossierId)) {
            throw new IllegalArgumentException("L'antécédent spécifié n'appartient pas à ce dossier médical.");
        }
        antecedentRepository.delete(antecedent);
    }

    @Override
    @Transactional
    public PathologieChroniqueDTO addPathologie(Long dossierId, PathologieChroniqueDTO dto) {
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + dossierId));
        PathologieChronique entity = toPathologieEntity(dto, dossier);
        PathologieChronique saved = pathologieRepository.save(entity);
        return toPathologieDto(saved);
    }

    @Override
    @Transactional
    public void removePathologie(Long dossierId, Long pathologieId) {
        PathologieChronique pathologie = pathologieRepository.findById(pathologieId)
                .orElseThrow(() -> new IllegalArgumentException("Pathologie introuvable avec l'ID : " + pathologieId));
        if (!pathologie.getDossierMedical().getId().equals(dossierId)) {
            throw new IllegalArgumentException("La pathologie spécifiée n'appartient pas à ce dossier médical.");
        }
        pathologieRepository.delete(pathologie);
    }

    @Override
    @Transactional
    public MedicamentEnCoursDTO addMedicament(Long dossierId, MedicamentEnCoursDTO dto) {
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier médical introuvable avec l'ID : " + dossierId));
        MedicamentEnCours entity = toMedicamentEntity(dto, dossier);
        MedicamentEnCours saved = medicamentRepository.save(entity);
        return toMedicamentDto(saved);
    }

    @Override
    @Transactional
    public void removeMedicament(Long dossierId, Long medicamentId) {
        MedicamentEnCours medicament = medicamentRepository.findById(medicamentId)
                .orElseThrow(() -> new IllegalArgumentException("Médicament introuvable avec l'ID : " + medicamentId));
        if (!medicament.getDossierMedical().getId().equals(dossierId)) {
            throw new IllegalArgumentException("Le médicament spécifié n'appartient pas à ce dossier médical.");
        }
        medicamentRepository.delete(medicament);
    }

    // Helper conversion methods

    private void updateAllergies(DossierMedical dossier, List<AllergieIntoleranceDTO> dtos) {
        dossier.getAllergiesIntolerances().clear();
        if (dtos != null) {
            dtos.forEach(dto -> dossier.getAllergiesIntolerances().add(toAllergieEntity(dto, dossier)));
        }
    }

    private void updateAntecedents(DossierMedical dossier, List<AntecedentDTO> dtos) {
        dossier.getAntecedents().clear();
        if (dtos != null) {
            dtos.forEach(dto -> dossier.getAntecedents().add(toAntecedentEntity(dto, dossier)));
        }
    }

    private void updatePathologies(DossierMedical dossier, List<PathologieChroniqueDTO> dtos) {
        dossier.getPathologiesChroniques().clear();
        if (dtos != null) {
            dtos.forEach(dto -> dossier.getPathologiesChroniques().add(toPathologieEntity(dto, dossier)));
        }
    }

    private void updateMedicaments(DossierMedical dossier, List<MedicamentEnCoursDTO> dtos) {
        dossier.getMedicamentsEnCours().clear();
        if (dtos != null) {
            dtos.forEach(dto -> dossier.getMedicamentsEnCours().add(toMedicamentEntity(dto, dossier)));
        }
    }

    private DossierMedical convertToEntity(DossierMedicalDTO dto) {
        DossierMedical entity = new DossierMedical();
        entity.setId(dto.getId());
        entity.setHistoriques(dto.getHistoriques());

        if (dto.getAllergiesIntolerances() != null) {
            dto.getAllergiesIntolerances().forEach(a ->
                    entity.getAllergiesIntolerances().add(toAllergieEntity(a, entity)));
        }
        if (dto.getAntecedents() != null) {
            dto.getAntecedents().forEach(a ->
                    entity.getAntecedents().add(toAntecedentEntity(a, entity)));
        }
        if (dto.getPathologiesChroniques() != null) {
            dto.getPathologiesChroniques().forEach(p ->
                    entity.getPathologiesChroniques().add(toPathologieEntity(p, entity)));
        }
        if (dto.getMedicamentsEnCours() != null) {
            dto.getMedicamentsEnCours().forEach(m ->
                    entity.getMedicamentsEnCours().add(toMedicamentEntity(m, entity)));
        }

        return entity;
    }

    private AllergieIntolerance toAllergieEntity(AllergieIntoleranceDTO dto, DossierMedical dossier) {
        AllergieIntolerance entity = new AllergieIntolerance();
        entity.setId(dto.getId());
        entity.setLibelle(dto.getLibelle());
        entity.setType(dto.getType());
        entity.setGravite(dto.getGravite());
        entity.setDescription(dto.getDescription());
        entity.setDossierMedical(dossier);
        return entity;
    }

    private Antecedent toAntecedentEntity(AntecedentDTO dto, DossierMedical dossier) {
        Antecedent entity = new Antecedent();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setLibelle(dto.getLibelle());
        entity.setDescription(dto.getDescription());
        entity.setDateSurvenue(dto.getDateSurvenue());
        entity.setDossierMedical(dossier);
        return entity;
    }

    private PathologieChronique toPathologieEntity(PathologieChroniqueDTO dto, DossierMedical dossier) {
        PathologieChronique entity = new PathologieChronique();
        entity.setId(dto.getId());
        entity.setLibelle(dto.getLibelle());
        entity.setEstAld(dto.isEstAld());
        entity.setDescription(dto.getDescription());
        entity.setDateDiagnostic(dto.getDateDiagnostic());
        entity.setDossierMedical(dossier);
        return entity;
    }

    private MedicamentEnCours toMedicamentEntity(MedicamentEnCoursDTO dto, DossierMedical dossier) {
        MedicamentEnCours entity = new MedicamentEnCours();
        entity.setId(dto.getId());
        entity.setMedicament(dto.getMedicament());
        entity.setPosologie(dto.getPosologie());
        entity.setNotes(dto.getNotes());
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setActif(dto.isActif());
        entity.setDossierMedical(dossier);
        return entity;
    }

    private DossierMedicalDTO convertToDto(DossierMedical entity) {
        DossierMedicalDTO dto = new DossierMedicalDTO();
        dto.setId(entity.getId());
        dto.setHistoriques(entity.getHistoriques());

        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId_utilisateur());
        }

        if (entity.getAllergiesIntolerances() != null) {
            dto.setAllergiesIntolerances(entity.getAllergiesIntolerances().stream()
                    .map(this::toAllergieDto)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        if (entity.getAntecedents() != null) {
            dto.setAntecedents(entity.getAntecedents().stream()
                    .map(this::toAntecedentDto)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        if (entity.getPathologiesChroniques() != null) {
            dto.setPathologiesChroniques(entity.getPathologiesChroniques().stream()
                    .map(this::toPathologieDto)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        if (entity.getMedicamentsEnCours() != null) {
            dto.setMedicamentsEnCours(entity.getMedicamentsEnCours().stream()
                    .map(this::toMedicamentDto)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        if (entity.getAllergiesIntolerances() != null) {
            dto.setAlerteAllergie(entity.getAllergiesIntolerances().stream()
                    .anyMatch(a -> a.getGravite() == GraviteAllergie.SEVERE));
        }

        return dto;
    }

    private AllergieIntoleranceDTO toAllergieDto(AllergieIntolerance entity) {
        AllergieIntoleranceDTO dto = new AllergieIntoleranceDTO();
        dto.setId(entity.getId());
        dto.setLibelle(entity.getLibelle());
        dto.setType(entity.getType());
        dto.setGravite(entity.getGravite());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    private AntecedentDTO toAntecedentDto(Antecedent entity) {
        AntecedentDTO dto = new AntecedentDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setLibelle(entity.getLibelle());
        dto.setDescription(entity.getDescription());
        dto.setDateSurvenue(entity.getDateSurvenue());
        return dto;
    }

    private PathologieChroniqueDTO toPathologieDto(PathologieChronique entity) {
        PathologieChroniqueDTO dto = new PathologieChroniqueDTO();
        dto.setId(entity.getId());
        dto.setLibelle(entity.getLibelle());
        dto.setEstAld(entity.isEstAld());
        dto.setDescription(entity.getDescription());
        dto.setDateDiagnostic(entity.getDateDiagnostic());
        return dto;
    }

    private MedicamentEnCoursDTO toMedicamentDto(MedicamentEnCours entity) {
        MedicamentEnCoursDTO dto = new MedicamentEnCoursDTO();
        dto.setId(entity.getId());
        dto.setMedicament(entity.getMedicament());
        dto.setPosologie(entity.getPosologie());
        dto.setNotes(entity.getNotes());
        dto.setDateDebut(entity.getDateDebut());
        dto.setDateFin(entity.getDateFin());
        dto.setActif(entity.isActif());
        return dto;
    }
}
