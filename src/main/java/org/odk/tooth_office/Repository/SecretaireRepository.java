package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Secretaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SecretaireRepository extends JpaRepository<Secretaire, Long> {
    @Query("SELECT COUNT(s) FROM Secretaire s WHERE s.cabinet.idCabinet = :idCabinet")
    long countByCabinetIdCabinet(@Param("idCabinet") int idCabinet);
}