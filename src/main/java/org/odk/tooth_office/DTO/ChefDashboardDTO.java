package org.odk.tooth_office.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefDashboardDTO {
    private long totalCabinets;
    private long totalDentistes;
    private long totalSecretaires;
    private long totalPatients;
    private long totalRendezVous;
    private List<CabinetResponseDTO> cabinets;
}
