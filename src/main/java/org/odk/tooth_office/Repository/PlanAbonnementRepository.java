package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.PlanAbonnement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanAbonnementRepository extends JpaRepository<PlanAbonnement,Long> {
    List<PlanAbonnement> findByNom(String nom);

    List<PlanAbonnement> findByPrix(Double prix);

    List<PlanAbonnement> findByNomAndPrix(String nom, Double prix);

    Optional<PlanAbonnement> findById(Long id); // déjà fourni par JpaRepository
}
