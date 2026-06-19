package org.odk.tooth_office.Services.Implementations;


import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.PrestationDTO;
import org.odk.tooth_office.Entity.Prestation;
import org.odk.tooth_office.Repository.PrestationRepository;
import org.odk.tooth_office.Services.Interfaces.IPrestation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PrestationImplementation implements IPrestation {

    private PrestationRepository prestationRepository;

    @Override
    public PrestationDTO create(PrestationDTO dto) {

        if (prestationRepository.existsByNomPrestation(dto.nom_prestation())) {
            throw new RuntimeException("Cette prestation existe déjà.");
        }

        Prestation prestation = new Prestation();

        prestation.setNom_prestation(dto.nom_prestation());
        prestation.setDateCreation(LocalDate.now());

        prestationRepository.save(prestation);

        return new PrestationDTO(
                prestation.getId_prestation(),
                prestation.getNom_prestation(),
                prestation.getDateCreation()
        );
    }

    @Override
    public PrestationDTO update(Long id, PrestationDTO dto) {

        Prestation prestation = prestationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestation introuvable."));

        prestation.setNom_prestation(dto.nom_prestation());

        prestationRepository.save(prestation);

        return new PrestationDTO(
                prestation.getId_prestation(),
                prestation.getNom_prestation(),
                prestation.getDateCreation()
        );
    }

    @Override
    public PrestationDTO getById(Long id) {

        Prestation prestation = prestationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestation introuvable."));

        return new PrestationDTO(
                prestation.getId_prestation(),
                prestation.getNom_prestation(),
                prestation.getDateCreation()
        );
    }

    @Override
    public List<PrestationDTO> getAll() {

        return prestationRepository.findAll()
                .stream()
                .map(prestation -> new PrestationDTO(
                        prestation.getId_prestation(),
                        prestation.getNom_prestation(),
                        prestation.getDateCreation()
                ))
                .toList();
    }

    @Override
    public void delete(Long id) {

        if (!prestationRepository.existsById(id)) {
            throw new RuntimeException("Prestation introuvable.");
        }

        prestationRepository.deleteById(id);
    }
}
