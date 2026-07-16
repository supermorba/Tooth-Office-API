package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Dentiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DentisteRepository extends JpaRepository<Dentiste, Long> {
    @Query ("select d from Dentiste d where d.cabinet.idCabinet=:id")
    List<Dentiste> findById_cabinet(@Param(("id")) Long id);
}
