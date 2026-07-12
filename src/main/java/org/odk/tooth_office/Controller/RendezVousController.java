package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;
import org.odk.tooth_office.Services.Interfaces.RendezVousService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendez-vous")
public class RendezVousController {

    private final RendezVousService rdvService;

    // Injection du service par constructeur
    public RendezVousController(RendezVousService rdvService) {
        this.rdvService = rdvService;
    }

    /**
     * Prendre un nouveau rendez-vous.
     * Accessible par : Patient et Secrétaire
     * POST http://localhost:8080/api/rendez-vous/prendre
     */
    @PostMapping("/prendre")
    public ResponseEntity<RendezVousResponseDTO> prendreRdv(@RequestBody RendezVousRequestDTO dto) {
        RendezVousResponseDTO response = rdvService.prendreRendezVous(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Annuler un rendez-vous (passe le statut à ANNULE et libère le créneau).
     * Accessible par : Patient, Secrétaire et Dentiste
     * PUT http://localhost:8080/api/rendez-vous/1/annuler
     */
    @PutMapping("/{rdvId}/annuler")
    public ResponseEntity<Void> annulerRdv(@PathVariable Long rdvId) {
        rdvService.annulerRendezVous(rdvId);
        return ResponseEntity.noContent().build(); // Retourne un code 204 No Content
    }

    /**
     * Modifier manuellement le statut d'un rendez-vous (ex: HONORE, NON_HONORE).
     * Accessible par : Dentiste et Secrétaire
     * PATCH http://localhost:8080/api/rendez-vous/1/statut?nouvelEtat=HONORE
     */
    @PatchMapping("/{rdvId}/statut")
    public ResponseEntity<RendezVousResponseDTO> modifierStatut(
            @PathVariable Long rdvId,
            @RequestParam String nouvelEtat) {
        RendezVousResponseDTO response = rdvService.modifierStatutRdv(rdvId, nouvelEtat);
        return ResponseEntity.ok(response);
    }

    /**
     * Consulter l'historique de tous les rendez-vous d'un patient spécifique.
     * Accessible par : Patient (le sien) et Secrétaire
     * GET http://localhost:8080/api/rendez-vous/patient/5
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVousResponseDTO>> getRdvParPatient(@PathVariable Long patientId) {
        List<RendezVousResponseDTO> liste = rdvService.obtenirRdvParPatient(patientId);
        return ResponseEntity.ok(liste);
    }

    /**
     * Consulter le planning complet ou du jour d'un dentiste spécifique.
     * Accessible par : Dentiste (son planning) et Secrétaire
     * GET http://localhost:8080/api/rendez-vous/dentiste/3
     */
    @GetMapping("/dentiste/{dentisteId}")
    public ResponseEntity<List<RendezVousResponseDTO>> getRdvParDentiste(@PathVariable Long dentisteId) {
        List<RendezVousResponseDTO> liste = rdvService.obtenirRdvParDentiste(dentisteId);
        return ResponseEntity.ok(liste);
    }
}