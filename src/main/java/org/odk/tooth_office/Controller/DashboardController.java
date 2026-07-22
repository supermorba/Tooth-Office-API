package org.odk.tooth_office.Controller;

import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.DashboardDTO;
import org.odk.tooth_office.Services.Implementations.DashboardServiceImpl;
import org.odk.tooth_office.Services.Interfaces.IDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DashboardController {

    private IDashboard dashboardService;

    @GetMapping("/dashboard")
    public DashboardDTO dashboard() {
        return dashboardService.getStatistiques();
    }
}
