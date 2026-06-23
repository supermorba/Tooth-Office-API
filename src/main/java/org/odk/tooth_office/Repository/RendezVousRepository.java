package org.odk.tooth_office.Repository;


import org.odk.tooth_office.Entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByDentisteId(Long dentisteId);
}
