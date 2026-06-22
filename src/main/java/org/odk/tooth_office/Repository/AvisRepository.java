package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Avis;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
    List<Avis> findByIdCabinet(int id);
    List<Avis> findByIdClient(int id);
}
