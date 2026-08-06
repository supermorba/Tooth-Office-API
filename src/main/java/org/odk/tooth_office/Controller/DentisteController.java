package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Services.Implementations.DentisteImplementation;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dentistes")
@PreAuthorize("hasAnyRole('ADMIN_SYSTEM','CHEF_CABINET', 'PATIENT')")
public class DentisteController {
    private final DentisteService dentisteService;

    public DentisteController(DentisteImplementation dentisteService) {
        this.dentisteService = dentisteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DentisteResponseDTO>> afficherDentistesParCabinet(@PathVariable Integer id) {
            return ResponseEntity.ok(dentisteService.afficherDentistesParCabinet(id));

    }
}
