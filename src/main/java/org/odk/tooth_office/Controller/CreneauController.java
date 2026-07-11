package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.CreneauDTO;
import org.odk.tooth_office.Services.Interfaces.CreneauService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
public class CreneauController {

    private final CreneauService creneauService;

    // Injection du service par constructeur
    public CreneauController(CreneauService creneauService) {
        this.creneauService = creneauService;
    }

    /**
     * Générer automatiquement les tranches horaires d'un dentiste pour une journée donnée.
     * Accessible par : Secrétaire et Dentiste
     * POST http://localhost:8080/api/creneaux/generer?date=2026-07-15&dentisteId=3
     */
    @PostMapping("/generer")
    public ResponseEntity<List<CreneauDTO>> genererCreneaux(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long dentisteId) {
        List<CreneauDTO> creneaux = creneauService.genererCreneauxPourJournee(date, dentisteId);
        return new ResponseEntity<>(creneaux, HttpStatus.CREATED);
    }

    /**
     * Obtenir la liste de tous les créneaux libres (disponibles) d'un dentiste spécifique.
     * Accessible par : Patient (pour prendre RDV) et Secrétaire
     * GET http://localhost:8080/api/creneaux/disponibles/dentiste/3
     */
    @GetMapping("/disponibles/dentiste/{dentisteId}")
    public ResponseEntity<List<CreneauDTO>> getCreneauxDisponibles(@PathVariable Long dentisteId) {
        List<CreneauDTO> disponibles = creneauService.obtenirCreneauxDisponiblesParDentiste(dentisteId);
        return ResponseEntity.ok(disponibles);
    }

    /**
     * Bloquer manuellement un créneau (ex: pause déjeuner du dentiste, urgence imprévue).
     * Accessible par : Secrétaire et Dentiste
     * PUT http://localhost:8080/api/creneaux/12/bloquer
     */
    @PutMapping("/{idCreneau}/bloquer")
    public ResponseEntity<Void> bloquerCreneauManuel(@PathVariable Long idCreneau) {
        creneauService.bloquerCreneau(idCreneau);
        return ResponseEntity.noContent().build(); // Code HTTP 204
    }

    /**
     * Libérer manuellement un créneau qui était bloqué ou annulé.
     * Accessible par : Secrétaire et Dentiste
     * PUT http://localhost:8080/api/creneaux/12/liberer
     */
    @PutMapping("/{idCreneau}/liberer")
    public ResponseEntity<Void> libererCreneauManuel(@PathVariable Long idCreneau) {
        creneauService.libererCreneau(idCreneau);
        return ResponseEntity.noContent().build(); // Code HTTP 204
    }
}