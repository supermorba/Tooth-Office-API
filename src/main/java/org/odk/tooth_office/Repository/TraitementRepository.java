package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Traitement;

import java.util.List;

public interface TraitementRepository {

    List<Traitement> getAllTraitements();
    void addTraitement(Traitement traitement);
    void updateTraitement(Traitement traitement);
    void deleteTraitement(Traitement traitement);
    Traitement getTraitementById(int id);
}
