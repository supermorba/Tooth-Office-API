package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.UtilisateurDTO;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    List<UtilisateurDTO> getAll();
    Optional<UtilisateurDTO> getById(Long id);
    UtilisateurDTO create(UtilisateurDTO dto);
    Optional<UtilisateurDTO> update(Long id, UtilisateurDTO dto);
    boolean delete(Long id);
}