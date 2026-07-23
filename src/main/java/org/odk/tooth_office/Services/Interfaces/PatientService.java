package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.PatientDTO;
import org.odk.tooth_office.Entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientDTO> getAll();
    Optional<PatientDTO> getById(Long id);
    PatientDTO create(PatientDTO dto);
    Optional<PatientDTO> update(Long id, PatientDTO dto);
    boolean delete(Long id);
    List<PatientDTO>  getPatientsByDentiste(Long idDentiste);
}
