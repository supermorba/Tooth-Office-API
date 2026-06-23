package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.Services.Implementations.AvisImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class AvisController {
    private final AvisImplementation avisImplementation;

    public AvisController(AvisImplementation avisImplementation) {
        this.avisImplementation = avisImplementation;
    }

    @PostMapping
    public ResponseEntity<AvisDetailDTO> ajouterAvis(@RequestBody AvisRequestDTO dto) {
        AvisDetailDTO created = avisImplementation.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvisDetailDTO>> listerAvis() {
        return ResponseEntity.ok(avisImplementation.getAll());
    }
}
