package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {
    @Query("SELECT d FROM DossierMedical d WHERE d.patient.id = :patientId")
    Optional<DossierMedical> findByPatientId(@Param("patientId") Long patientId);

}
