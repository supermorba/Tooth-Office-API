package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.MedicamentEnCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentEnCoursRepository extends JpaRepository<MedicamentEnCours, Long> {
    List<MedicamentEnCours> findByDossierMedicalId(Long dossierMedicalId);
}
