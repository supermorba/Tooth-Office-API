package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.ChefCabinetDTO;
import org.odk.tooth_office.Services.Interfaces.ChefCabinetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chefs-cabinet")
@RequiredArgsConstructor
@Tag(name = "Chefs de cabinet", description = "Gestion des chefs de cabinet")
@PreAuthorize("hasAnyRole('ADMIN_SYSTEM','CHEF_CABINET')")
public class ChefCabinetController {

    private final ChefCabinetService chefCabinetService;

    @GetMapping
    public ResponseEntity<List<ChefCabinetDTO>> getAll() {
        return ResponseEntity.ok(chefCabinetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefCabinetDTO> getById(@PathVariable Long id) {
        return chefCabinetService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChefCabinetDTO> create(@RequestBody ChefCabinetDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chefCabinetService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChefCabinetDTO> update(@PathVariable Long id, @RequestBody ChefCabinetDTO dto) {
        return chefCabinetService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!chefCabinetService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-cabinets-of-chef/{id}")
    public ResponseEntity<List<CabinetResponseDTO>> getCabinetsOfChef(@PathVariable Long id) {
        return ResponseEntity.ok(chefCabinetService.getCabinetsChefCabinets(id));
    }
}