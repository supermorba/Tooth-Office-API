package org.odk.tooth_office.Mapper;

import org.odk.tooth_office.DTO.SecretaireResponseDTO;
import org.odk.tooth_office.Entity.Secretaire;
import org.springframework.stereotype.Component;

@Component
public class SecretaireMapper {

    public static SecretaireResponseDTO toResponseDTO(Secretaire secretaire) {
        if (secretaire == null) {
            return null;
        }

        SecretaireResponseDTO dto = new SecretaireResponseDTO();

        dto.setIdSecretaire(secretaire.getIdSecretaire());
        dto.setNom(secretaire.getNom());
        dto.setPrenom(secretaire.getPrenom());
        dto.setEmail(secretaire.getEmail());
        dto.setAdresse(secretaire.getAdresse());
        dto.setRole(secretaire.getRole());
        dto.setTelephone(secretaire.getTelephone());
        dto.setStatutCompte(secretaire.getStatutCompte());
        dto.setCreatedAt(secretaire.getCreatedAt());

        if (secretaire.getCabinet() != null) {
            dto.setIdCabinet(secretaire.getCabinet().getIdCabinet());
        }

        if (secretaire.getChefCabinet() != null) {
            dto.setIdChefCabinet(secretaire.getChefCabinet().getId_utilisateur());
        }

        return dto;
    }
}
