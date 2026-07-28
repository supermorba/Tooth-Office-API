package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Secretaire;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Services.Interfaces.CabinetService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.odk.tooth_office.DTO.AvisResponseDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;
    private final DentisteMapper dentisteMapper;

    @PostMapping
    public ResponseEntity<CabinetResponseDTO> creerCabinet(@RequestBody CabinetDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cabinetService.creerCabinet(dto));
    }

    @GetMapping
    public ResponseEntity<List<CabinetResponseDTO>> recupererTous() {
        return ResponseEntity.ok(cabinetService.recupererTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinetResponseDTO> recupererParId(@PathVariable Integer id) {
        return cabinetService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/recherche")
    public ResponseEntity<CabinetResponseDTO> recupererParNom(@RequestParam String nom) {
        return cabinetService.recupererParNom(nom)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinetResponseDTO> modifierCabinet(@PathVariable Integer id, @RequestBody CabinetDTO dto) {
        return ResponseEntity.ok(cabinetService.modifierCabinet(id, dto));
    }

    @PostMapping("/{id}/logo")
    public ResponseEntity<CabinetResponseDTO> uploadLogo(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(cabinetService.uploadLogo(id, file));
    }

    @GetMapping("/{id}/logo")
    public ResponseEntity<Resource> recupererLogo(@PathVariable Integer id) {
        Resource resource = cabinetService.getLogoResource(id);
        String contentType = cabinetService.getLogoContentType(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}/logo")
    public ResponseEntity<CabinetResponseDTO> supprimerLogo(@PathVariable Integer id) {
        return ResponseEntity.ok(cabinetService.supprimerLogo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCabinet(@PathVariable Integer id) {
        cabinetService.supprimerCabinet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/dentistes")
    public ResponseEntity<List<DentisteResponseDTO>> afficherDentistesParCabinet(@PathVariable Integer id) {
        return ResponseEntity.ok(cabinetService.afficherDentistesParCabinet(id));
    }

    @GetMapping("/dentistes/{id}")
    public ResponseEntity<List<DentisteResponseDTO>> getDentistesParCabinet(@PathVariable Long id) {
        return ResponseEntity.ok(cabinetService.getDentistesCabinetSecretaire(id));
    }

    @GetMapping("/{id}/secretaires")
    public ResponseEntity<List<SecretaireResponseDTO>> afficherSecretairesParCabinet(@PathVariable Integer id) {
        return ResponseEntity.ok(cabinetService.afficherSecretairesParCabinet(id));
    }

    @GetMapping("/{idCabinet}/secretaires/{idSecretaire}")
    public ResponseEntity<SecretaireResponseDTO> afficherUnSecretaireParCabinet(
            @PathVariable Integer idCabinet,
            @PathVariable Integer idSecretaire) {
        return cabinetService.afficherUnSecretaireParCabinet(idCabinet, idSecretaire)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idCabinet}/dentistes/{idDentiste}")
    public ResponseEntity<DentisteResponseDTO> afficherUnDentisteParCabinet(
            @PathVariable Integer idCabinet,
            @PathVariable Long idDentiste) {
        return cabinetService.afficherUnDentisteParCabinet(idCabinet, idDentiste)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idCabinet}/avis/{idAvis}")
    public ResponseEntity<AvisResponseDTO> afficherUnAvisParCabinet(
            @PathVariable Integer idCabinet,
            @PathVariable Long idAvis
    ){

        return cabinetService
                .afficherUnAvisParCabinet(idCabinet,idAvis)

                .map(ResponseEntity::ok)

                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/{id}/avis")
    public ResponseEntity<List<AvisResponseDTO>> afficherAvisParCabinet(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                cabinetService.afficherLesAvisParCabinet(id)
        );
    }
}
