package org.odk.tooth_office.DTO;


import lombok.*;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DentisteResponseDTO {
    private Long idDentiste;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private RoleEnum role;
    private String telephone;
    private String specialite;
    private StatutCompte statutCompte;
    private LocalDate createdAt;
    private Integer idCabinet;


}
