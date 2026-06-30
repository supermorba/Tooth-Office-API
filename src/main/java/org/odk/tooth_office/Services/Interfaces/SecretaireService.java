package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.SecretaireDTO;

import java.util.List;
import java.util.Optional;

public interface SecretaireService {
    List<SecretaireDTO> getAll();
    Optional<SecretaireDTO> getById(Long id);
    SecretaireDTO create(SecretaireDTO dto);
    Optional<SecretaireDTO> update(Long id, SecretaireDTO dto);
    boolean delete(Long id);
}