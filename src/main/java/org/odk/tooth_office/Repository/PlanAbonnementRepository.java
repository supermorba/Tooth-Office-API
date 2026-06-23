package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.PlanAbonnement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanAbonnementRepository extends JpaRepository<PlanAbonnement,Long> {



    Optional<PlanAbonnement> findById(Long id); // déjà fourni par JpaRepository
}
