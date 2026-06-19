package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("SELECT c FROM Consultation c WHERE Consultation.dossierMedical.patient.id_utilisateur =: patient")
    List<Consultation> getByPatient(@Param("patient") Long id);


}
