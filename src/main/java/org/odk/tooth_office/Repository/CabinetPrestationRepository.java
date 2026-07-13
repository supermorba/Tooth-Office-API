package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.CabinetPrestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinetPrestationRepository extends JpaRepository<CabinetPrestation, Long> {
    @Query("SELECT cp from CabinetPrestation cp WHERE cp.cabinet.idCabinet=:idCabinet")
    List<CabinetPrestation> getCabinetPrestations(@Param("idCabinet") Long idCabinet);
}
