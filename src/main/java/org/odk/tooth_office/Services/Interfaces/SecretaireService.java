package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.SecretaireDTO;
import org.odk.tooth_office.DTO.SecretaireResponseDTO;

import java.util.List;
import java.util.Optional;

public interface SecretaireService {
    List<SecretaireResponseDTO> recupererTous();
    Optional<SecretaireResponseDTO> getById(Long id);
    SecretaireResponseDTO create(SecretaireDTO dto);
    Optional<SecretaireResponseDTO> update(Long id, SecretaireDTO dto);

    boolean delete(Long id);
}