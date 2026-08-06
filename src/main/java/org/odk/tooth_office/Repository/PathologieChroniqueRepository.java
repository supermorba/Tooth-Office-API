package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.PathologieChronique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathologieChroniqueRepository extends JpaRepository<PathologieChronique, Long> {
    List<PathologieChronique> findByDossierMedicalId(Long dossierMedicalId);
}
