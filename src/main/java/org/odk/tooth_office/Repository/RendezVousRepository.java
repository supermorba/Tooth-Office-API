package org.odk.tooth_office.Repository;


import org.odk.tooth_office.Entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByPatientId_utilisateur(Long patientId);
    List<RendezVous> findByDentisteId_utilisateur(Long dentisteId);
}
