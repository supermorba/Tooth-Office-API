package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.PlanAbonnementDTO;
import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Repository.PlanAbonnementRepository;
import org.odk.tooth_office.Services.Interfaces.IPlanAbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanAbonnementService implements IPlanAbonnementService {

    @Autowired
    private PlanAbonnementRepository repository;

    @Override
    public PlanAbonnementDTO ajouterPlan(PlanAbonnementDTO dto) {

        PlanAbonnement plan = new PlanAbonnement();

        // Conversion DTO -> Entity
        plan.setNom(dto.getNom());
        plan.setPrixMensuel(dto.getPrixMensuel());
        plan.setPrixAnnuel(dto.getPrixAnnuel());
        plan.setMaxCabinet(dto.getMaxCabinet());
        plan.setMaxDentistes(dto.getMaxDentistes());
        plan.setMaxSecretaires(dto.getMaxSecretaires());
        plan.setDescription(dto.getDescription());

        repository.save(plan);

        return dto;
    }

    @Override
    public List<PlanAbonnementDTO> afficherTous() {
        return null;
    }

    @Override
    public PlanAbonnementDTO rechercherParId(Long id) {
        return null;
    }

    @Override
    public PlanAbonnementDTO modifier(Long id, PlanAbonnementDTO dto) {
        return null;
    }

    @Override
    public void supprimer(Long id) {

        repository.deleteById(id);

    }
}