package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.Entity.PlanAbonnement;


import java.util.List;
import java.util.Optional;
public interface IPlanAbonnementService {

    PlanAbonnement createPlanAbonnement(PlanAbonnement planAbonnement);

    PlanAbonnement updatePlanAbonnement(Long id, PlanAbonnement planAbonnement);

    void deletePlanAbonnement(Long id);

    Optional<PlanAbonnement> getPlanAbonnementById(Long id);

    List<PlanAbonnement>getAllPlanAbonnements();

}