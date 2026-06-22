package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.AdminSystemDTO;

import java.util.List;
import java.util.Optional;

public interface AdminSystemService {
    List<AdminSystemDTO> getAll();
    Optional<AdminSystemDTO> getById(Long id);
    AdminSystemDTO create(AdminSystemDTO dto);
    Optional<AdminSystemDTO> update(Long id, AdminSystemDTO dto);
    boolean delete(Long id);
}