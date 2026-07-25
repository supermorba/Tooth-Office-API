package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Antecedent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntecedentRepository extends JpaRepository<Antecedent, Long> {
    List<Antecedent> findByDossierMedicalId(Long dossierMedicalId);
}
