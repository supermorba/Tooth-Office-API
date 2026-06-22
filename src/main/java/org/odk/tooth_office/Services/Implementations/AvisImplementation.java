package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Entity.Avis;
import org.odk.tooth_office.Repository.AvisRepository;
import org.odk.tooth_office.Services.Interfaces.AvisInterface;

import java.util.List;

public class AvisImplementation implements AvisInterface {
    private final AvisRepository avisRepository;

    public AvisImplementation(AvisRepository avisRepository) {
        this.avisRepository = avisRepository;
    }

    @Override
    public void create(Avis avis) {
        avisRepository.save(avis);
    }

    @Override
    public List<Avis> getAll() {
        return avisRepository.findAll();
    }

    @Override
    public void update(Avis avis) {
        avisRepository.save(avis);
    }

    @Override
    public void delete(Avis avis) {
        avisRepository.delete(avis);
    }

    @Override
    public Avis getById(int id) {
        return avisRepository.getById(id);
    }

    @Override
    public List<Avis> findByIdCabinet(int id) {
        return avisRepository.findByIdCabinet(id);
    }

    @Override
    public List<Avis> findByIdClient(int id) {
        return avisRepository.findByIdClient(id);
    }
}
