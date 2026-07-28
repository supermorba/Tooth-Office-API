package org.odk.tooth_office.Controller;

import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.ChefDashboardDTO;
import org.odk.tooth_office.DTO.DashboardDTO;
import org.odk.tooth_office.Services.Interfaces.IDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DashboardController {

    private final IDashboard dashboardService;

    @GetMapping("/dashboard")
    public DashboardDTO dashboard() {
        return dashboardService.getStatistiques();
    }

    @GetMapping("/dashboard/chef/{chefId}")
    public ChefDashboardDTO getChefDashboard(@PathVariable Long chefId) {
        return dashboardService.getChefStatistiques(chefId);
    }
}
