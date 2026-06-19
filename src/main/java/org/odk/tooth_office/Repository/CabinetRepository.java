package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CabinetRepository extends JpaRepository<Cabinet, Integer> {

    Optional<Cabinet> findByNomCabinet(String nomCabinet);
    boolean existsByTel(String tel);

}

