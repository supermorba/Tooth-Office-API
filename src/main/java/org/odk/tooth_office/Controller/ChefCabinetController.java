package org.odk.tooth_office.Controller;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.ChefCabinetDTO;
import org.odk.tooth_office.Services.Interfaces.ChefCabinetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chefs-cabinet")
@RequiredArgsConstructor
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
}