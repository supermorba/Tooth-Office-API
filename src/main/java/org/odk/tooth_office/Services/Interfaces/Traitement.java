package org.odk.tooth_office.Services.Interfaces;

import java.util.List;

public interface Traitement {
    List<Traitement> getTraitements();
    void addTraitement(Traitement traitement);
    void updateTraitement(Traitement traitement);
    void deleteTraitement(Traitement traitement);
    void deleteTraitementById(int id);
}
