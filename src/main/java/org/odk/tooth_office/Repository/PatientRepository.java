package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("""
        SELECT DISTINCT r.patient
        FROM RendezVous r
        WHERE r.dentiste.id_utilisateur = :idDentiste
    """)
    List<Patient> findPatientsByDentiste(@Param("idDentiste") Long idDentiste);
}
