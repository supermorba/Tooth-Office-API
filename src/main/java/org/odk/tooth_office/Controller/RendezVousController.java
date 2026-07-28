package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.RendezVousRequestDTO;
import org.odk.tooth_office.DTO.RendezVousResponseDTO;
import org.odk.tooth_office.Services.Interfaces.RendezVousService;
import org.odk.tooth_office.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rendez-vous")
@RequiredArgsConstructor
@Tag(name = "Rendez-vous", description = "API de gestion des rendez-vous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    /**
     * Prendre un rendez-vous
     */
    @PostMapping("/prendre")
    @Operation(summary = "Prendre un rendez-vous", description = "Permet au patient ou à la secrétaire de prendre un rendez-vous")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rendez-vous créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Patient, Dentiste ou Créneau non trouvé")
    })
    public ResponseEntity<RendezVousResponseDTO> prendreRendezVous(
            Authentication authentication,
            @RequestBody RendezVousRequestDTO dto) {
        validateTypeForRole(authentication, dto);
        RendezVousResponseDTO response = rendezVousService.prendreRendezVous(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private void validateTypeForRole(Authentication authentication, RendezVousRequestDTO dto) {
        if (authentication == null || dto.getTypeRdv() == null) {
            return;
        }

        boolean isPatient = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_PATIENT".equals(authority.getAuthority()));
        boolean isDentiste = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_DENTISTE".equals(authority.getAuthority()));
        boolean isSecretaire = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_SECRETAIRE".equals(authority.getAuthority()));

        if (isPatient && !"ENLIGNE".equalsIgnoreCase(dto.getTypeRdv())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un patient peut uniquement prendre un rendez-vous en ligne.");
        }

        if ((isDentiste || isSecretaire) && !"SURPLACE".equalsIgnoreCase(dto.getTypeRdv())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un dentiste ou une secrétaire peut uniquement enregistrer un rendez-vous sur place.");
        }
    }

    /**
     * Annuler un rendez-vous
     */
    @DeleteMapping("/{rdvId}")
    @Operation(summary = "Annuler un rendez-vous", description = "Permet d'annuler un rendez-vous existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rendez-vous annulé avec succès"),
            @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé"),
            @ApiResponse(responseCode = "400", description = "Le rendez-vous ne peut pas être annulé")
    })
    public ResponseEntity<Void> annulerRendezVous(
            @Parameter(description = "ID du rendez-vous à annuler")
            @PathVariable Long rdvId) {
        rendezVousService.annulerRendezVous(rdvId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Modifier le statut d'un rendez-vous
     */
    @PatchMapping("/{rdvId}/statut")
    @Operation(summary = "Modifier le statut d'un rendez-vous",
            description = "Permet au dentiste ou à la secrétaire de modifier l'état du rendez-vous")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé"),
            @ApiResponse(responseCode = "400", description = "État invalide")
    })
    public ResponseEntity<RendezVousResponseDTO> modifierStatutRdv(
            @Parameter(description = "ID du rendez-vous") @PathVariable Long rdvId,
            @Parameter(description = "Nouvel état du rendez-vous (EN_ATTENTE, PLANIFIE, HONORE, NON_HONORE, ANNULE)")
            @RequestParam String nouvelEtat) {
        RendezVousResponseDTO response = rendezVousService.modifierStatutRdv(rdvId, nouvelEtat);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtenir l'historique des rendez-vous d'un patient
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Consulter l'historique d'un patient",
            description = "Récupère tous les rendez-vous d'un patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des rendez-vous"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    public ResponseEntity<List<RendezVousResponseDTO>> obtenirRdvParPatient(
            @Parameter(description = "ID du patient") @PathVariable Long patientId) {
        List<RendezVousResponseDTO> response = rendezVousService.obtenirRdvParPatient(patientId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtenir le planning du jour d'un dentiste
     */
    @GetMapping({"/dentiste/{dentisteId}", "/{dentisteId}/dentiste", "/get-Rdv-by-dentiste/{dentisteId}"})
    @Operation(summary = "Consulter le planning d'un dentiste",
            description = "Récupère tous les rendez-vous d'un dentiste pour la journée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planning du dentiste"),
            @ApiResponse(responseCode = "404", description = "Dentiste non trouvé")
    })
    public ResponseEntity<List<RendezVousResponseDTO>> obtenirRdvParDentiste(
            @Parameter(description = "ID du dentiste") @PathVariable Long dentisteId) {
        List<RendezVousResponseDTO> response = rendezVousService.obtenirRdvParDentiste(dentisteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cabinet/{id}")
    public Response getAllRdvCabinet(@PathVariable Long id){
        try{
            List<RendezVousResponseDTO> liste = rendezVousService.findAllRdvOfCabinet(id);
            return Response.succes("La liste des rdv du cabinet", liste);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur au niveau du serveur");
        }


    }
}
