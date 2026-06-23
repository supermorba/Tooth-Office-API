package org.odk.tooth_office.Controller;

import org.odk.tooth_office.Entity.Traitement;
import org.odk.tooth_office.Services.Interfaces.TraitementService;
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


    @GetMapping("/{id}")
    public List<Traitement> getById(@RequestParam Integer id) {
        return service.getAll();
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

//    @DeleteMapping
//    public void delete(@PathVariable Integer id) {
//         service.save(id);
//    }
}
