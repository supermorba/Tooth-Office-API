package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.SecretaireDTO;
import org.odk.tooth_office.Services.Interfaces.SecretaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secretaires")
@RequiredArgsConstructor
@Tag(name = "Secrétaires", description = "Gestion des secrétaires du cabinet")
public class SecretaireController {

    private final SecretaireService secretaireService;

    @GetMapping
    public ResponseEntity<List<SecretaireDTO>> getAll() {
        return ResponseEntity.ok(secretaireService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretaireDTO> getById(@PathVariable Long id) {
        return secretaireService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SecretaireDTO> create(@RequestBody SecretaireDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(secretaireService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecretaireDTO> update(@PathVariable Long id, @RequestBody SecretaireDTO dto) {
        return secretaireService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!secretaireService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}