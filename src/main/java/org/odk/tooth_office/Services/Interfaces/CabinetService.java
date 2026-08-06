package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.DTO.AvisResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CabinetService {
    CabinetResponseDTO creerCabinet(CabinetDTO dto);
    List<CabinetResponseDTO> recupererTous();
    Optional<CabinetResponseDTO> recupererParId(Integer id);
    Optional<CabinetResponseDTO> recupererParNom(String nomCabinet);
    CabinetResponseDTO modifierCabinet(Integer id, CabinetDTO dto);
    void supprimerCabinet(Integer id);
    List<SecretaireResponseDTO> afficherSecretairesParCabinet(Integer idCabinet);
    Optional<SecretaireResponseDTO> afficherUnSecretaireParCabinet(Integer idCabinet, Integer idSecretaire);
    Optional<DentisteResponseDTO> afficherUnDentisteParCabinet(Integer idCabinet, Long idDentiste);
    List<AvisResponseDTO> afficherLesAvisParCabinet(Integer idCabinet);
    Optional<AvisResponseDTO> afficherUnAvisParCabinet(Integer idCabinet, Long idAvis);

    List<DentisteResponseDTO> getDentistesCabinetSecretaire(Long idSecretaire);

}

