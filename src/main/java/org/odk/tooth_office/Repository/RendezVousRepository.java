package org.odk.tooth_office.Repository;


import java.util.List;

import org.odk.tooth_office.Entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    /*List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByDentisteId(Long dentisteId);*/

    @Query("SELECT r FROM RendezVous r WHERE r.patient.id_utilisateur = :patientId")
    List<RendezVous> findByPatientId(Long patientId);
    @Query("SELECT r FROM RendezVous r WHERE r.dentiste.id_utilisateur = :dentisteId")
    List<RendezVous> findByDentisteId(Long dentisteId);
}
