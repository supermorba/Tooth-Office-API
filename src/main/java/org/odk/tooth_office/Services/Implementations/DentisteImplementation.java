package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class DentisteImplementation implements DentisteService {
    @Override
    public List<Dentiste> getAll() {
        return List.of();
    }

    @Override
    public void delete(Dentiste dentiste) {

    }

    @Override
    public void save(Dentiste dentiste) {

    }

    @Override
    public void update(Dentiste dentiste) {

    }

    @Override
    public void getById(int id) {

    }
}
