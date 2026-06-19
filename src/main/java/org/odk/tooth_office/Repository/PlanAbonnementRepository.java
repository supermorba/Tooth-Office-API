package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.PlanAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface PlanAbonnementRepository extends JpaRepository<PlanAbonnement, Long> {

    Optional<PlanAbonnement> findByNom(String nom);

}