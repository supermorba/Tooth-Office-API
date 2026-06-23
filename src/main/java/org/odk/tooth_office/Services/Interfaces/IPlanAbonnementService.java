package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.PlanAbonnementDTO;
import org.odk.tooth_office.Entity.PlanAbonnement;


import java.util.List;
import java.util.Optional;
public interface IPlanAbonnementService {

    PlanAbonnementDTO createPlanAbonnement(PlanAbonnementDTO planAbonnement);

    PlanAbonnement updatePlanAbonnement(Long id, PlanAbonnementDTO planAbonnement);

    void deletePlanAbonnement(Long id);

    Optional<PlanAbonnement> getPlanAbonnementById(Long id);

    List<PlanAbonnement>getAllPlanAbonnements();

}