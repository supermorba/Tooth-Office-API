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
    public void delete(Traitement traitement) {
        repository.delete(traitement);
    }

    @Override
    public void save(Traitement traitement) {
        repository.save(traitement);
    }

    @Override
    public void update(Traitement traitement) {
        repository.save(traitement);
    }

    @Override
    public void getById(int id) {
        repository.getById(id);

    }
}
