package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.AllergieIntolerance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergieIntoleranceRepository extends JpaRepository<AllergieIntolerance, Long> {
    List<AllergieIntolerance> findByDossierMedicalId(Long dossierMedicalId);
}
