package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefCabinetRepository extends JpaRepository<ChefCabinet, Long> {
    @Query("SELECT c.cabinets FROM ChefCabinet c WHERE c.id_utilisateur = :id")
    List<Cabinet> getCabinetsChefCabinets(@Param("id") Long id);
}