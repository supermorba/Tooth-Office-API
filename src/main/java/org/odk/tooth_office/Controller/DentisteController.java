package org.odk.tooth_office.Controller;

import org.odk.tooth_office.Services.Implementations.AbonnementService;
import org.odk.tooth_office.Services.Implementations.DentisteImplementation;
import org.odk.tooth_office.Services.Interfaces.DentisteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dentistes")
public class DentisteController {
    private final DentisteService dentisteService;

    public DentisteController(DentisteImplementation dentisteService) {
        this.dentisteService = dentisteService;
    }
}
