package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Services.Interfaces.Traitement;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TraitementImplementation implements Traitement {
    @Override
    public List<Traitement> getTraitements() {
        List<Traitement> traitements = new ArrayList<>();
        String query ="select * from traitement";
     //  try {
           //Connection conn = connectBD.getConnection();

     //  }
        return List.of();
    }

    @Override
    public void addTraitement(Traitement traitement) {

    }

    @Override
    public void updateTraitement(Traitement traitement) {

    }

    @Override
    public void deleteTraitement(Traitement traitement) {

    }

    @Override
    public void deleteTraitementById(int id) {

    }
}
