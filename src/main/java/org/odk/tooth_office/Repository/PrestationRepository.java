package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation,Long> {
    boolean existsByNomPrestation(String nomPrestation);
}
