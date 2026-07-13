package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.CreneauDTO;
import org.odk.tooth_office.Services.Interfaces.CreneauService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/creneaux")
@RequiredArgsConstructor
@Tag(name = "Créneaux", description = "API de gestion des créneaux de consultation")
public class CreneauController {

    private final CreneauService creneauService;

    /**
     * Générer les créneaux pour une journée
     */
    @PostMapping("/generer")
    @Operation(summary = "Générer les créneaux d'une journée",
            description = "Génère les créneaux de consultation pour un dentiste et une date donnée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Créneaux générés avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Dentiste non trouvé")
    })
    public ResponseEntity<List<CreneauDTO>> genererCreneauxPourJournee(
            @Parameter(description = "Date pour laquelle générer les créneaux", example = "2026-06-25")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "ID du dentiste") @RequestParam Long dentisteId) {
        List<CreneauDTO> response = creneauService.genererCreneauxPourJournee(date, dentisteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtenir les créneaux disponibles d'un dentiste
     */
    @GetMapping("/disponibles/{dentisteId}")
    @Operation(summary = "Consulter les créneaux disponibles",
            description = "Récupère tous les créneaux disponibles d'un dentiste")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des créneaux disponibles"),
            @ApiResponse(responseCode = "404", description = "Dentiste non trouvé")
    })
    public ResponseEntity<List<CreneauDTO>> obtenirCreneauxDisponiblesParDentiste(
            @Parameter(description = "ID du dentiste") @PathVariable Long dentisteId) {
        List<CreneauDTO> response = creneauService.obtenirCreneauxDisponiblesParDentiste(dentisteId);
        return ResponseEntity.ok(response);
    }

    /**
     * Bloquer un créneau
     */
    @PatchMapping("/{creneauId}/bloquer")
    @Operation(summary = "Bloquer un créneau",
            description = "Bloque un créneau (urgence, pause, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Créneau bloqué avec succès"),
            @ApiResponse(responseCode = "404", description = "Créneau non trouvé"),
            @ApiResponse(responseCode = "400", description = "Le créneau est déjà bloqué")
    })
    public ResponseEntity<Void> bloquerCreneau(
            @Parameter(description = "ID du créneau à bloquer") @PathVariable Long creneauId) {
        creneauService.bloquerCreneau(creneauId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Libérer un créneau
     */
    @PatchMapping("/{creneauId}/liberer")
    @Operation(summary = "Libérer un créneau",
            description = "Libère un créneau précédemment bloqué")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Créneau libéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Créneau non trouvé"),
            @ApiResponse(responseCode = "400", description = "Le créneau est déjà disponible")
    })
    public ResponseEntity<Void> libererCreneau(
            @Parameter(description = "ID du créneau à libérer") @PathVariable Long creneauId) {
        creneauService.libererCreneau(creneauId);
        return ResponseEntity.noContent().build();
    }
}