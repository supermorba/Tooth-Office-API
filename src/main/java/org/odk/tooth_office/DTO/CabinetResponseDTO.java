package org.odk.tooth_office.DTO;

import jakarta.persistence.Column;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinetResponseDTO {
    private int idCabinet;
    private String nomCabinet;
    private String tel;
    private String adresse;
    private String logo;
    private String description;
    private Double noteMoyenne;
    private long nombreAvis;
}
