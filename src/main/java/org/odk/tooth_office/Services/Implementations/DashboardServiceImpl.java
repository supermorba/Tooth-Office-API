package org.odk.tooth_office.Services.Implementations;

import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.DashboardDTO;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.Services.Interfaces.IDashboard;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements IDashboard {

    private UtilisateurRepository utilisateurRepository;
    private PatientRepository patientRepository;
    private DentisteRepository dentisteRepository;
    private CabinetRepository cabinetRepository;

    @Override
    public DashboardDTO getStatistiques() {


            DashboardDTO dto = new DashboardDTO();

            dto.setUsers(utilisateurRepository.count());
            dto.setPatients(patientRepository.count());
            dto.setDentistes(dentisteRepository.count());
            dto.setCabinets(cabinetRepository.count());

            return dto;

    }
}
