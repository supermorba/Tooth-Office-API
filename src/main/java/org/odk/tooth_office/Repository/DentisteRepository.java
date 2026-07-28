package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Dentiste;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DentisteRepository extends JpaRepository<Dentiste, Long> {
    @Query("SELECT COUNT(d) FROM Dentiste d WHERE d.cabinet.idCabinet = :idCabinet")
    long countByCabinetIdCabinet(@Param("idCabinet") int idCabinet);
}
