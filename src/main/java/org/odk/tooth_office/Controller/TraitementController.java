package org.odk.tooth_office.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.odk.tooth_office.Entity.Traitement;
import org.odk.tooth_office.Services.Interfaces.TraitementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traitements")
@Tag(name = "Traitements", description = "Gestion des traitements dentaires")

public class TraitementController {
    private final TraitementService service;
    public TraitementController(TraitementService service) {
        this.service = service;
    }


    @GetMapping
    public List<Traitement> getAll() {

        return service.getAll();
    }


    @GetMapping("/{id}")
    public Traitement getById(@PathVariable int id) {
        return service.getById(id);
    }


    @PostMapping
    public Traitement save(@RequestBody Traitement traitement) {
        service.save(traitement);
        return traitement;
    }

    @PutMapping("/{id}")
    public Traitement update(@RequestBody Traitement traitement) {
        service.save(traitement);
        return traitement;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
         service.deleteById(id);
    }

}
