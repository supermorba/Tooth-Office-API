package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Entity.Traitement;
import org.odk.tooth_office.Repository.TraitementRepository;
import org.odk.tooth_office.Services.Interfaces.TraitementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraitementServiceImplementation implements TraitementService {

    private final TraitementRepository repository;
    public TraitementServiceImplementation(TraitementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Traitement> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public Traitement save(Traitement traitement) {
        return repository.save(traitement);
    }



    @Override
    public Traitement getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Traitement introuvable"));
    }
}
