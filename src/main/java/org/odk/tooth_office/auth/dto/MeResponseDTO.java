package org.odk.tooth_office.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String statutCompte;
}