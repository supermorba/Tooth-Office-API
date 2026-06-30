package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Avis;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Long> {
    @Query("SELECT v FROM Avis v WHERE v.cabinet.idCabinet = :id")
    List<Avis> findByCabinetId(int id);
    @Query("SELECT v FROM Avis v WHERE v.patient.id_utilisateur = :id")
    List<Avis> findByPatientId(int id);
}
