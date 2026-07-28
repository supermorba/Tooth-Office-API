package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Secretaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretaireRepository extends JpaRepository<Secretaire, Long> {
    @Query("SELECT s.cabinet.idCabinet from Secretaire s WHERE s.id_utilisateur=:id")
    public Long getcabinetId(@Param("id") Long id);

    public Long countByCabinetIdCabinet(int idCabinet);
}