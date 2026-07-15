package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("SELECT c FROM Consultation c WHERE c.isEnabled = true")
    List<Consultation> getAll();

    @Query("SELECT c FROM Consultation c WHERE c.id =:id AND c.isEnabled = true")
    Optional<Consultation> getConsultationById(@Param("id") Long id);

    @Query("SELECT c FROM Consultation c WHERE c.dossierMedical.patient.id =:patient AND c.isEnabled= true")
    List<Consultation> getByPatient(@Param("patient") Long id);

    @Query("SELECT c FROM Consultation c WHERE c.dentiste.id =:dentiste AND c.isEnabled= true")
    List<Consultation> getByDentiste(@Param("dentiste") Long id);

    @Query("SELECT COUNT(c) > 0 FROM Consultation c WHERE c.dossierMedical.patient.id = :patient ")
    boolean patientHadConsultation(@Param("patient") Long patient);

    @Query("SELECT COUNT(c) > 0 FROM Consultation c WHERE c.dentiste.id = :dentiste ")
    boolean dentisteHadConsultation(@Param("dentiste") Long dentiste);



}
