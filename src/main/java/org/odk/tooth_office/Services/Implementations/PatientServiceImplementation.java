package org.odk.tooth_office.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.PatientDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Services.Interfaces.PatientService;
import org.odk.tooth_office.security.PasswordService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImplementation implements PatientService {

    private final PatientRepository patientRepository;
    private final CabinetRepository cabinetRepository;
    private final PasswordService passwordService;

    @Override
    public List<PatientDTO> getAll() {
        return patientRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PatientDTO> getById(Long id) {
        return patientRepository.findById(id).map(this::toDto);
    }

    @Override
    public PatientDTO create(PatientDTO dto) {
        Patient saved = patientRepository.save(toEntity(dto, new Patient()));
        return toDto(saved);
    }

    @Override
    public Optional<PatientDTO> update(Long id, PatientDTO dto) {
        return patientRepository.findById(id).map(existing -> {
            Patient updated = toEntity(dto, existing);
            updated.setId_utilisateur(id);
            return toDto(patientRepository.save(updated));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!patientRepository.existsById(id)) {
            return false;
        }
        patientRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PatientDTO> getPatientsParDentiste(Long dentisteId) {
        return patientRepository.findPatientsByDentisteId(dentisteId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PatientDTO toDto(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId_utilisateur(patient.getId_utilisateur());
        dto.setNom(patient.getNom());
        dto.setPrenom(patient.getPrenom());
        dto.setEmail(patient.getEmail());
        dto.setAdresse(patient.getAdresse());
        dto.setRole(patient.getRole());
        dto.setTelephone(patient.getTelephone());
        dto.setStatutCompte(patient.getStatutCompte());
        dto.setCreatedAt(patient.getCreatedAt());
        dto.setUpdatedAt(patient.getUpdatedAt());
        dto.setCreatedBy(patient.getCreatedBy());
        dto.setUpdatedBy(patient.getUpdatedBy());
        dto.setDateNaissance(patient.getDateNaissance());
        dto.setCabinetIds(patient.getCabinets() == null
                ? Collections.emptyList()
                : patient.getCabinets().stream().map(Cabinet::getIdCabinet).collect(Collectors.toList()));
        return dto;
    }

    private Patient toEntity(PatientDTO dto, Patient patient) {
        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setEmail(dto.getEmail());
        if (dto.getMdp() != null && !dto.getMdp().isBlank()) {
            patient.setMdp(passwordService.encodeIfNeeded(dto.getMdp()));
        }
        patient.setAdresse(dto.getAdresse());
        patient.setRole(dto.getRole());
        patient.setTelephone(dto.getTelephone());
        patient.setStatutCompte(dto.getStatutCompte());
        patient.setCreatedAt(dto.getCreatedAt());
        patient.setUpdatedAt(dto.getUpdatedAt());
        patient.setCreatedBy(dto.getCreatedBy());
        patient.setUpdatedBy(dto.getUpdatedBy());
        patient.setDateNaissance(dto.getDateNaissance());

        List<Cabinet> cabinets = dto.getCabinetIds() == null
                ? Collections.emptyList()
                : cabinetRepository.findAllById(dto.getCabinetIds());
        patient.setCabinets(cabinets);

        return patient;
    }
}
