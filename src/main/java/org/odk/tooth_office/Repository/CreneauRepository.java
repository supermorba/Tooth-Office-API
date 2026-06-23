package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByDentisteId_utilisateurAndDisponibleTrue(Long dentisteId);
    List<Creneau> findByDateAndDisponibleTrue(LocalDate date);
}
