package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.AdminSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSystemRepository extends JpaRepository<AdminSystem, Long> {
}