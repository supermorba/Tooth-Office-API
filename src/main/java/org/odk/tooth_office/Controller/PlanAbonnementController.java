package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.PlanAbonnementDTO;
import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Services.Implementations.PlanAbonnementService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan_abonnement")
@RequiredArgsConstructor
public class PlanAbonnementController {

    private final PlanAbonnementService iPlanAbonnementService;

    // CREATE
    @PostMapping("")
    public PlanAbonnementDTO create(@RequestBody PlanAbonnementDTO planAbonnement) {
        return iPlanAbonnementService.createPlanAbonnement(planAbonnement);
    }

    // UPDATE
    @PutMapping("/{id}")
    public PlanAbonnement update(@PathVariable Long id,
                                 @RequestBody PlanAbonnementDTO planAbonnement) {
        return iPlanAbonnementService.updatePlanAbonnement(id, planAbonnement);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iPlanAbonnementService.deletePlanAbonnement(id);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public PlanAbonnement getById(@PathVariable Long id) {
        return iPlanAbonnementService.getPlanAbonnementById(id)
                .orElseThrow(() -> new RuntimeException("Plan non trouvé avec id: " + id));
    }

    // GET ALL
    @GetMapping("")
    public List<PlanAbonnement> getAll() {
        return iPlanAbonnementService.getAllPlanAbonnements();
    }
}