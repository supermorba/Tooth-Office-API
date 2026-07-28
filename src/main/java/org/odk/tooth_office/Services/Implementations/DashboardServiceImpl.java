package org.odk.tooth_office.Services.Implementations;

import lombok.AllArgsConstructor;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.ChefDashboardDTO;
import org.odk.tooth_office.DTO.DashboardDTO;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Repository.PatientRepository;
import org.odk.tooth_office.Repository.RendezVousRepository;
import org.odk.tooth_office.Repository.SecretaireRepository;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.Services.Interfaces.ChefCabinetService;
import org.odk.tooth_office.Services.Interfaces.IDashboard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements IDashboard {

    private final UtilisateurRepository utilisateurRepository;
    private final PatientRepository patientRepository;
    private final DentisteRepository dentisteRepository;
    private final SecretaireRepository secretaireRepository;
    private final CabinetRepository cabinetRepository;
    private final ChefCabinetService chefCabinetService;
    private final RendezVousRepository rendezVousRepository;

    @Override
    public DashboardDTO getStatistiques() {
        DashboardDTO dto = new DashboardDTO();
        dto.setUsers(utilisateurRepository.count());
        dto.setPatients(patientRepository.count());
        dto.setDentistes(dentisteRepository.count());
        dto.setCabinets(cabinetRepository.count());
        return dto;
    }

    @Override
    public ChefDashboardDTO getChefStatistiques(Long chefId) {
        List<CabinetResponseDTO> cabinets = chefCabinetService.getCabinetsChefCabinets(chefId);

        long totalDentistes = 0;
        long totalSecretaires = 0;
        long totalRendezVous = 0;

        if (cabinets != null) {
            for (CabinetResponseDTO cabinet : cabinets) {
                totalDentistes += dentisteRepository.countByCabinetIdCabinet(cabinet.getIdCabinet());
                totalSecretaires += secretaireRepository.countByCabinetIdCabinet(cabinet.getIdCabinet());
                try {
                    List<?> rdvs = rendezVousRepository.findRdvByCabinet((long) cabinet.getIdCabinet());
                    if (rdvs != null) {
                        totalRendezVous += rdvs.size();
                    }
                } catch (Exception ignored) {}
            }
        }

        long totalPatients = patientRepository.count();

        return ChefDashboardDTO.builder()
                .totalCabinets(cabinets != null ? cabinets.size() : 0)
                .totalDentistes(totalDentistes)
                .totalSecretaires(totalSecretaires)
                .totalPatients(totalPatients)
                .totalRendezVous(totalRendezVous)
                .cabinets(cabinets)
                .build();
    }
}
