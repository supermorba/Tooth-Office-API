package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.DentisteDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.Services.Implementations.DentisteImplementation;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dentistes")
@PreAuthorize("hasAnyRole('ADMIN_SYSTEM','CHEF_CABINET')")
public class DentisteController {
    private final DentisteService dentisteService;

    public DentisteController(DentisteImplementation dentisteService) {
        this.dentisteService = dentisteService;
    }

    @GetMapping
    public ResponseEntity<List<DentisteResponseDTO>> getAll() {
        return ResponseEntity.ok(dentisteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentisteResponseDTO> getById(@PathVariable Long id) {
        return dentisteService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DentisteResponseDTO> create(@RequestBody DentisteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dentisteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentisteResponseDTO> update(@PathVariable Long id, @RequestBody DentisteDTO dto) {
        return dentisteService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!dentisteService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
