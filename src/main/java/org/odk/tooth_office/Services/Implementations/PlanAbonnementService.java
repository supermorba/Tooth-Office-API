package org.odk.tooth_office.Services.Implementations;


import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.PlanAbonnementDTO;
import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Repository.PlanAbonnementRepository;
import org.odk.tooth_office.Services.Interfaces.IPlanAbonnementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanAbonnementService implements IPlanAbonnementService {

    private final PlanAbonnementRepository repository;



   @Override
    public PlanAbonnementDTO createPlanAbonnement(PlanAbonnementDTO planAbonnementdto) {
        PlanAbonnement p = new PlanAbonnement();
        p.setNom(planAbonnementdto.nom());
        p.setDescription(planAbonnementdto.description());
        p.setPrixAnnuel(planAbonnementdto.prixAnnuel());
        p.setPrixMensuel(planAbonnementdto.prixMensuel());
        p.setMaxCabinet(planAbonnementdto.maxCabinet());
        p.setMaxDentistes(planAbonnementdto.maxDentistes());
        PlanAbonnement plan = repository.save(p);
        PlanAbonnementDTO dto = PlanAbonnementDTO.builder().nom(plan.getNom()).build();


        return dto;
    }

    @Override
    public PlanAbonnement updatePlanAbonnement(Long id, PlanAbonnementDTO planAbonnement) {
        return null;
    }



    @Override
    public void deletePlanAbonnement(Long id) {

    }

    @Override
    public Optional<PlanAbonnement> getPlanAbonnementById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<PlanAbonnement> getAllPlanAbonnements() {
        return List.of();
    }
}