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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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

        // Modifier directement l'entité existante (conserve l'ID → JPA fait un UPDATE)
        avis.setNote(dto.note());
        avis.setDescription(dto.description());
        avis.setCabinet(cabinet);
        avis.setPatient(patient);

        Avis avisSave = avisRepository.save(avis);
        return mapper.toDetailDTO(avisSave);
    }

    @Override
    public void delete(Long id) {
        Avis avis = avisRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Avis non trouvé"));
        avisRepository.delete(avis);
    }

    @Override
    public AvisDetailDTO getById(Long id) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Avis non trouvé"));
        return mapper.toDetailDTO(avis);
    }

    @Override
    public List<AvisDetailDTO> findByCabinetId(int id) {
        List<Avis> listAvisCabinet = avisRepository.findByCabinetId(id);
        if (listAvisCabinet.isEmpty()){
            throw new IllegalArgumentException("Aucun avis trouvé pour le cabinet avec l'ID : " + id);
        }
        return avisRepository.findByCabinetId(id).stream()
                .map(mapper::toDetailDTO)
                .toList();
    }

    @Override
    public List<AvisDetailDTO> findByPatientId(int id) {
        List<Avis> listAvisPatient = avisRepository.findByPatientId(id);
        if (listAvisPatient.isEmpty()){
            throw new IllegalArgumentException("Aucun avis trouvé pour le patient avec l'ID : " + id);
        }
        return avisRepository.findByPatientId(id).stream()
                .map(mapper::toDetailDTO)
                .toList();
    }
}
