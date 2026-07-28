package org.odk.tooth_office.Services.Interfaces;

import java.util.List;
import java.util.Optional;

import org.odk.tooth_office.DTO.AvisResponseDTO;
import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.DTO.CabinetResponseDTO;
import org.odk.tooth_office.DTO.DentisteResponseDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;

public interface CabinetService {
    CabinetResponseDTO creerCabinet(CabinetDTO dto);
    List<CabinetResponseDTO> recupererTous();
    Optional<CabinetResponseDTO> recupererParId(Integer id);
    Optional<CabinetResponseDTO> recupererParNom(String nomCabinet);
    CabinetResponseDTO modifierCabinet(Integer id, CabinetDTO dto);
    void supprimerCabinet(Integer id);
    List<DentisteResponseDTO> afficherDentistesParCabinet(Integer idCabinet);
    List<SecretaireResponseDTO> afficherSecretairesParCabinet(Integer idCabinet);
    Optional<SecretaireResponseDTO> afficherUnSecretaireParCabinet(Integer idCabinet, Integer idSecretaire);
    Optional<DentisteResponseDTO> afficherUnDentisteParCabinet(Integer idCabinet, Long idDentiste);
    List<AvisResponseDTO> afficherLesAvisParCabinet(Integer idCabinet);
    Optional<AvisResponseDTO> afficherUnAvisParCabinet(Integer idCabinet, Long idAvis);
    CabinetResponseDTO uploadLogo(Integer idCabinet, org.springframework.web.multipart.MultipartFile file);
    org.springframework.core.io.Resource getLogoResource(Integer idCabinet);
    String getLogoContentType(Integer idCabinet);
    CabinetResponseDTO supprimerLogo(Integer idCabinet);

    List<DentisteResponseDTO> getDentistesCabinetSecretaire(Long idSecretaire);

}

