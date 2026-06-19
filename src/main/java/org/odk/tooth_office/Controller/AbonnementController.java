package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.AbonnementDTO;
import org.odk.tooth_office.Entity.Abonnement;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Services.Implementations.AbonnementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final AbonnementService abonnementService;
    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }
    @PostMapping
    public ResponseEntity<Abonnement> create(@RequestBody AbonnementDTO dto) {
        return ResponseEntity.ok(abonnementService.creerAbonnement(dto));
    }
    @GetMapping
    public ResponseEntity<List<Abonnement>> getAll() {
        return ResponseEntity.ok(abonnementService.recupererTous());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Abonnement> getById(@PathVariable Integer id) {
        return abonnementService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Abonnement> update(@PathVariable Integer id, @RequestBody AbonnementDTO dto) {
        return ResponseEntity.ok(abonnementService.modifierAbonnement(id, dto));
    }
    @PatchMapping("/{id}/statut")
    public ResponseEntity<Abonnement> changeStatus(@PathVariable Integer id, @RequestParam EtatAbonnement statut) {
        return ResponseEntity.ok(abonnementService.changerStatut(id, statut));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        abonnementService.supprimerAbonnement(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/cabinet/{idCabinet}")
    public ResponseEntity<List<Abonnement>> getByCabinet(@PathVariable Integer idCabinet) {
        return ResponseEntity.ok(abonnementService.recupererParCabinet(idCabinet));
    }
    @GetMapping("/plan/{idPlan}")
    public ResponseEntity<List<Abonnement>> getByPlan(@PathVariable Integer idPlan) {
        return ResponseEntity.ok(abonnementService.recupererParPlan(idPlan));
    }
}