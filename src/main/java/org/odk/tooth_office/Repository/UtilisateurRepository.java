package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByTelephone(String telephone);

    @Query("SELECT COUNT(u) > 0 FROM Utilisateur u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM Utilisateur u WHERE u.telephone = :telephone")
    boolean existsByTelephone(@Param("telephone") String telephone);
}
