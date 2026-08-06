package org.odk.tooth_office.Mapper;

import org.springframework.stereotype.Component;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.DTO.DentisteResponseDTO;

@Component
public class DentisteMapper {

    public DentisteResponseDTO toResponseDTO(Dentiste dentiste) {
        if (dentiste == null) {
            return null;
        }
        DentisteResponseDTO dentisteDTO = new DentisteResponseDTO();

        dentisteDTO.setIdDentiste(dentiste.getId_utilisateur());
        dentisteDTO.setPrenom(dentiste.getPrenom());
        dentisteDTO.setNom(dentiste.getNom());
        dentisteDTO.setAdresse(dentiste.getAdresse());
        dentisteDTO.setEmail(dentiste.getEmail());
        dentisteDTO.setTelephone(dentiste.getTelephone());
        dentisteDTO.setRole(dentiste.getRole());
        dentisteDTO.setStatutCompte(dentiste.getStatutCompte());
        dentisteDTO.setSpecialite(dentiste.getSpecialite());
        dentisteDTO.setCreatedAt(dentiste.getCreatedAt());

        if (dentiste.getCabinet() != null) {
            dentisteDTO.setIdCabinet(dentiste.getCabinet().getIdCabinet());
        }
        return dentisteDTO;
    }


};
