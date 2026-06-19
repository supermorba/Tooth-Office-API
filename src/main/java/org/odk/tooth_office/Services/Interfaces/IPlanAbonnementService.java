package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.PlanAbonnementDTO;

import java.util.List;

public interface IPlanAbonnementService {

    PlanAbonnementDTO ajouterPlan(PlanAbonnementDTO dto);

    List<PlanAbonnementDTO> afficherTous();

    PlanAbonnementDTO rechercherParId(Long id);

    PlanAbonnementDTO modifier(Long id, PlanAbonnementDTO dto);

    void supprimer(Long id);
}