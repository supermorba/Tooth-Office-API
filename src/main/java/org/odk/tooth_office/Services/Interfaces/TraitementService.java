package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.Traitement;

import java.util.List;

public interface TraitementService {
    List<Traitement> getAll();
    void delete(Traitement traitement);
    void save(Traitement traitement);
    void update(Traitement traitement);
    void getById(int id);

}
