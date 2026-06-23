package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DossierMedicalDTO;
import org.odk.tooth_office.Entity.DossierMedical;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Repository.DossierMedicalRepository;
import org.odk.tooth_office.Services.Interfaces.IDossierMedical;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class DossierMedicalServiceImpl implements IDossierMedical {

    private final DossierMedicalRepository dossierMedicalRepository;

    @Override
    public DossierMedicalDTO createDossierMedical(DossierMedicalDTO dto) {
        DossierMedical entity = convertToEntity(dto);
        DossierMedical saved = dossierMedicalRepository.save(entity);
        return convertToDto(saved);
    }

    @Override
    public DossierMedicalDTO getDossierMedicalById(Long id) {
        DossierMedical entity = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable avec l'ID : " + id));
        return convertToDto(entity);
    }

    @Override
    public DossierMedicalDTO getDossierMedicalByPatientId(Long patientId) {
        DossierMedical entity = dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable pour le patient : " + patientId));
        return convertToDto(entity);
    }

    @Override
    public List<DossierMedicalDTO> getAllDossiersMedicaux() {
        return dossierMedicalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DossierMedicalDTO updateDossierMedical(Long id, DossierMedicalDTO dto) {
        DossierMedical existing = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable avec l'ID : " + id));

        existing.setAntecedents(dto.getAntecedents());
        existing.setAllergies(dto.getAllergies());
        existing.setHistoriques(dto.getHistoriques());

        if (dto.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setId(dto.getPatientId());
            existing.setPatient(patient);
        }

        DossierMedical saved = dossierMedicalRepository.save(existing);
        return convertToDto(saved);
    }

    @Override
    public void deleteDossierMedical(Long id) {
        DossierMedical dossier = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable"));
        dossierMedicalRepository.delete(dossier);
    }

    // Conversions
    private DossierMedical convertToEntity(DossierMedicalDTO dto) {
        DossierMedical entity = new DossierMedical();
        entity.setId(dto.getId());
        entity.setAntecedents(dto.getAntecedents());
        entity.setAllergies(dto.getAllergies());
        entity.setHistoriques(dto.getHistoriques());

        if (dto.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setId(dto.getPatientId());
            entity.setPatient(patient);
        }
        return entity;
    }

    private DossierMedicalDTO convertToDto(DossierMedical entity) {
        DossierMedicalDTO dto = new DossierMedicalDTO();
        dto.setId(entity.getId());
        dto.setAntecedents(entity.getAntecedents());
        dto.setAllergies(entity.getAllergies());
        dto.setHistoriques(entity.getHistoriques());

        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
        }
        return dto;
    }
}