package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.DTO.MapperDTO.AvisMapper;
import org.odk.tooth_office.Entity.Avis;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Patient;
import org.odk.tooth_office.Repository.AvisRepository;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Services.Interfaces.AvisInterface;

import java.time.LocalDateTime;
import java.util.List;

public class AvisImplementation implements AvisInterface {
    private final AvisRepository avisRepository;
    private final CabinetRepository cabinetRepository;
    private final PatientRepository patientRepository;
    private final AvisMapper mapper;

    public AvisImplementation(AvisRepository avisRepository, CabinetRepository cabinetRepository, PatientRepository patientRepository, AvisMapper mapper) {
        this.avisRepository = avisRepository;
        this.cabinetRepository = cabinetRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    public AvisDetailDTO create(AvisRequestDTO dto) {
        Cabinet cabinet = cabinetRepository.findById(dto.cabinetId()).orElseThrow(() -> new IllegalArgumentException("Cabinet introuvable"));
        Patient patient = patientRepository.findById(Long.valueOf(dto.patientId())).orElseThrow(() -> new IllegalArgumentException("Patient Introuvable"));

        //dto en entité
        Avis avis = mapper.toEntity(dto);
        avis.setCabinet(cabinet);
        avis.setPatient(patient);
        avis.setCreateAt(LocalDateTime.now());

        Avis avisSave = avisRepository.save(avis);

        return mapper.toDetailDTO(avisSave);
    }

    @Override
    public List<AvisDetailDTO> getAll() {

        return avisRepository.findAll().stream()
                .map(mapper::toDetailDTO)
                .toList();
    }

    @Override
    public AvisDetailDTO update(Long id, AvisRequestDTO dto) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cet avis est introuvable"));
        Cabinet cabinet = cabinetRepository.findById(dto.cabinetId())
                .orElseThrow(() -> new IllegalArgumentException("Cabinet introuvable"));
        Patient patient = patientRepository.findById(Long.valueOf(dto.patientId()))
                .orElseThrow(() -> new IllegalArgumentException("Patient Introuvable"));

        avis.setCabinet(cabinet);
        avis.setPatient(patient);
        avis.setCreateAt(LocalDateTime.now());

        Avis avisSave = avisRepository.save(avis);
        return mapper.toDetailDTO(avisSave);
    }

    @Override
    public void delete(Avis avis) {
        avisRepository.delete(avis);
    }

    @Override
    public Avis getById(Long id) {
        return avisRepository.getById(id);
    }

    @Override
    public List<Avis> findByCabinetId(int id) {
        return avisRepository.findByCabinetId(id);
    }

    @Override
    public List<Avis> findByPatientId(int id) {
        return avisRepository.findByPatientId(id);
    }
}
