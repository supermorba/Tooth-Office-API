package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    @Query("SELECT r FROM RendezVous r WHERE r.dentiste.id_utilisateur = :dentisteId")
    List<Creneau> findByDentisteIdAndDisponibleTrue(Long dentisteId);
    List<Creneau> findByDateAndDisponibleTrue(LocalDate date);
}
