package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.PrestationDTO;
import org.odk.tooth_office.Entity.Prestation;
import org.odk.tooth_office.Services.Interfaces.IPrestation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/prestation")
@AllArgsConstructor
@Tag(name = "Prestations", description = "Gestion des prestations proposées par le cabinet")
public class PrestationController {

    private  IPrestation prestationService;


    @PostMapping
    public ResponseEntity<Prestation> create(
            @RequestBody PrestationDTO prestation) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestationService.create(prestation));
    }

    @GetMapping
    public ResponseEntity<List<PrestationDTO>> getAll() {

        return ResponseEntity.ok(prestationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestationDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(prestationService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestationDTO> update(
            @PathVariable Long id,
            @RequestBody PrestationDTO dto) {

        return ResponseEntity.ok(prestationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        prestationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
