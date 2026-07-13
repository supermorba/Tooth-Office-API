package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id_utilisateur = :dentisteId AND c.disponible = true")
    List<Creneau> findByDentisteIdAndDisponibleTrue(@Param("dentisteId") Long dentisteId);

    List<Creneau> findByDateAndDisponibleTrue(LocalDate date);

    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id_utilisateur = :dentisteId AND c.disponible = true AND c.date = :date")
    List<Creneau> findByDentisteIdAndDisponibleTrueAndDate(@Param("dentisteId") Long dentisteId, @Param("date") LocalDate date);
}
