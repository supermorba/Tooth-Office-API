package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.AdminSystemDTO;
import org.odk.tooth_office.Services.Interfaces.AdminSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "Administrateurs système", description = "Gestion des administrateurs système")
@PreAuthorize("hasRole('ADMIN_SYSTEM')")
public class AdminSystemController {

    private final AdminSystemService adminSystemService;

    @GetMapping
    public ResponseEntity<List<AdminSystemDTO>> getAll() {
        return ResponseEntity.ok(adminSystemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminSystemDTO> getById(@PathVariable Long id) {
        return adminSystemService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdminSystemDTO> create(@RequestBody AdminSystemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminSystemService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminSystemDTO> update(@PathVariable Long id, @RequestBody AdminSystemDTO dto) {
        return adminSystemService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!adminSystemService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}