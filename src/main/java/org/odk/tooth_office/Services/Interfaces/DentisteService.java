package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Traitement;

import java.util.List;

public interface DentisteService {
        List<Dentiste> getAll();
        void delete(Dentiste dentiste);
        void save(Dentiste dentiste);
        void update(Dentiste dentiste);
        void getById(int id);
}

