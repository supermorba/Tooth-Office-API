package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.DossierMedical;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {

    @EntityGraph(attributePaths = {
            "allergiesIntolerances",
            "antecedents",
            "pathologiesChroniques",
            "medicamentsEnCours",
            "patient"
    })
    @Query("SELECT d FROM DossierMedical d WHERE d.patient.id_utilisateur = :patientId")
    Optional<DossierMedical> findByPatientId(@Param("patientId") Long patientId);

    @EntityGraph(attributePaths = {
            "allergiesIntolerances",
            "antecedents",
            "pathologiesChroniques",
            "medicamentsEnCours",
            "patient"
    })
    @Override
    Optional<DossierMedical> findById(Long id);

    @Query("SELECT COUNT(d) > 0 FROM DossierMedical d WHERE d.patient.id_utilisateur = :patientId")
    boolean existsByPatientId(@Param("patientId") Long patientId);

    @EntityGraph(attributePaths = {
            "allergiesIntolerances",
            "antecedents",
            "pathologiesChroniques",
            "medicamentsEnCours",
            "patient"
    })
    @Override
    List<DossierMedical> findAll();
}
