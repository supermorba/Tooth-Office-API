package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.odk.tooth_office.DTO.AbonnementDTO;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Services.Implementations.AbonnementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
@Tag(name = "Abonnements", description = "Gestion des abonnements des cabinets dentaires")
public class AbonnementController {

    private final AbonnementService abonnementService;

    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }

    @PostMapping
    public ResponseEntity<AbonnementDTO> creerAbonnement(@RequestBody AbonnementDTO dto) {
        AbonnementDTO cree = abonnementService.creerAbonnement(dto);
        return new ResponseEntity<>(cree, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AbonnementDTO>> recupererTous() {
        return ResponseEntity.ok(abonnementService.recupererTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbonnementDTO> recupererParId(@PathVariable Integer id) {
        return abonnementService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cabinet/{idCabinet}")
    public ResponseEntity<List<AbonnementDTO>> recupererParCabinet(@PathVariable int idCabinet) {
        return ResponseEntity.ok(abonnementService.recupererParCabinet(idCabinet));
    }

    @GetMapping("/plan/{idPlan}")
    public ResponseEntity<List<AbonnementDTO>> recupererParPlan(@PathVariable Long idPlan) {
        return ResponseEntity.ok(abonnementService.recupererParPlan(idPlan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbonnementDTO> modifierAbonnement(@PathVariable Integer id, @RequestBody AbonnementDTO dto) {
        try {
            AbonnementDTO modifie = abonnementService.modifierAbonnement(id, dto);
            return ResponseEntity.ok(modifie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<AbonnementDTO> changerStatut(
            @PathVariable Integer id,
            @RequestParam EtatAbonnement nouveauStatut) {
        try {
            AbonnementDTO majStatut = abonnementService.changerStatut(id, nouveauStatut);
            return ResponseEntity.ok(majStatut);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAbonnement(@PathVariable Integer id) {
        abonnementService.supprimerAbonnement(id);
        return ResponseEntity.noContent().build();
    }
}