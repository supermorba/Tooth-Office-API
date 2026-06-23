package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.PlanAbonnement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanAbonnementRepository extends JpaRepository<PlanAbonnement, Long> {

    // Rechercher tous les plans ayant un nom donné
    List<PlanAbonnement> findByNom(String nom);

    // Rechercher tous les plans ayant un prix donné
    List<PlanAbonnement> findByPrix(Double prix);

    // Rechercher un plan selon son idPlan et sa description
    List<PlanAbonnement> findByIdPlanAndDescription(Long idPlan, String description);

}
