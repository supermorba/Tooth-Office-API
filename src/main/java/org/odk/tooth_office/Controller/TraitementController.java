package org.odk.tooth_office.Controller;

import org.odk.tooth_office.Entity.Traitement;
import org.odk.tooth_office.Services.Interfaces.TraitementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traitements")

public class TraitementController {
    private final TraitementService service;
    public TraitementController(TraitementService service) {
        this.service = service;
    }
    @GetMapping
    public List<Traitement> getAll() {
        return service.getAll();
    }
//    @GetMapping
//    public List<Traitement> getAllById(@RequestParam Integer id) {
//        return service.getAll();
//    }
    @PostMapping
    public Traitement save(@RequestBody Traitement traitement) {
        service.save(traitement);
        return traitement;
    }

    @PutMapping
    public Traitement update(@RequestBody Traitement traitement) {
        service.save(traitement);
        return traitement;
    }

    @DeleteMapping
    public void delete(@RequestBody Traitement traitement) {
        service.save(traitement);
    }
}
