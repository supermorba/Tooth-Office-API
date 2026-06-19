package org.odk.tooth_office.Repository;


import org.odk.tooth_office.Entity.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    List<Abonnement> findByIdCabinet(Integer idCabinet);
    List<Abonnement> findByIdPlan(Integer idPlan);
}