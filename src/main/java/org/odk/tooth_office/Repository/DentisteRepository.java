package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Dentiste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentisteRepository extends JpaRepository<Dentiste, Long> {
}
