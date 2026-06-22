package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {

//    Optional<DossierMedical> findByPatientId(Long patientId);

}
