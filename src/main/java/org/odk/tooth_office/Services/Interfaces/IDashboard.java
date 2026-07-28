package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.ChefDashboardDTO;
import org.odk.tooth_office.DTO.DashboardDTO;

public interface IDashboard {
    DashboardDTO getStatistiques();
    ChefDashboardDTO getChefStatistiques(Long chefId);
}
