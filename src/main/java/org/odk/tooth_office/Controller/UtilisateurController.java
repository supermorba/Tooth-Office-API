package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.DTO.UtilisateurDTO;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Services.Implementations.DentisteImplementation;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.odk.tooth_office.Services.Interfaces.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs de la plateforme")
@PreAuthorize("hasRole('ADMIN_SYSTEM')")
@CrossOrigin("*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final DentisteService dentisteService;


    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAll() {
        return ResponseEntity.ok(utilisateurService.getAll());
    }


    @GetMapping("/cabinet/{cabinetId}/dentistes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<DentisteResponseDTO>> getDentistesParCabinet(@PathVariable Integer cabinetId) {
        return ResponseEntity.ok(dentisteService.afficherDentistesParCabinet(cabinetId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getById(@PathVariable Long id) {
        return utilisateurService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UtilisateurDTO> create(@RequestBody UtilisateurDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> update(@PathVariable Long id, @RequestBody UtilisateurDTO dto) {
        return utilisateurService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!utilisateurService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}