package org.odk.tooth_office.Controller;

import org.odk.tooth_office.DTO.AvisDetailDTO;
import org.odk.tooth_office.DTO.AvisRequestDTO;
import org.odk.tooth_office.Services.Interfaces.AvisInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
public class AvisController {
    private final AvisInterface avisInterface;

    public AvisController(AvisInterface avisInterface) {
        this.avisInterface = avisInterface;
    }

    @PostMapping
    public ResponseEntity<AvisDetailDTO> ajouterAvis(@RequestBody AvisRequestDTO dto) {
        AvisDetailDTO created = avisInterface.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvisDetailDTO>> listerAvis() {
        return ResponseEntity.ok(avisInterface.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvisDetailDTO> getAvis(@PathVariable Long id)
    {
        return ResponseEntity.ok(avisInterface.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvisDetailDTO> modifierAvis(@PathVariable Long id, @RequestBody AvisRequestDTO dto) {
        AvisDetailDTO updated = avisInterface.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> supprimerAvis(@PathVariable Long id)
    {
        avisInterface.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cabinet/{id}")
    public ResponseEntity<List<AvisDetailDTO>> avisDuCabinet(@PathVariable int id)
    {
        return ResponseEntity.ok(avisInterface.findByCabinetId(id));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AvisDetailDTO>> avisDupatient(@PathVariable int id)
    {
        return ResponseEntity.ok(avisInterface.findByPatientId(id));
    }
}
