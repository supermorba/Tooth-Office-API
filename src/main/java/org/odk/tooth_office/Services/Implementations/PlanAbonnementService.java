package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Repository.PlanAbonnementRepository;
import org.odk.tooth_office.Services.Interfaces.IPlanAbonnementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanAbonnementService implements IPlanAbonnementService {

    private final PlanAbonnementRepository repository;

    public PlanAbonnementService(PlanAbonnementRepository repository) {
        this.repository = repository;
    }

   @Override
    public PlanAbonnement createPlanAbonnement(PlanAbonnement planAbonnement) {
        return repository.save(planAbonnement);
    }

    @Override
    public PlanAbonnement updatePlanAbonnement(Long id, PlanAbonnement planAbonnement) {

        PlanAbonnement existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan non trouvé avec id: " + id));

        existing.setNom(planAbonnement.getNom());
        existing.setPrixMensuel(planAbonnement.getPrixMensuel());
        existing.setPrixAnnuel(planAbonnement.getPrixAnnuel());
        existing.setMaxCabinet(planAbonnement.getMaxCabinet());
        existing.setMaxDentistes(planAbonnement.getMaxDentistes());
        existing.setMaxSecretaires(planAbonnement.getMaxSecretaires());
        existing.setDescription(planAbonnement.getDescription());

        return repository.save(existing);
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