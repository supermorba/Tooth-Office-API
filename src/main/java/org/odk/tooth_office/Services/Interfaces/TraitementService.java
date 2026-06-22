package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.Traitement;

import java.util.List;

public interface TraitementService {
    List<Traitement> getAll();
    void deleteById(int id);
    Traitement save(Traitement traitement);
    Traitement getById(int id);

}
