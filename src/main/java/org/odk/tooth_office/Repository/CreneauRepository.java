package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {

    /**
     * Récupère tous les créneaux disponibles pour un dentiste
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.disponible = true ORDER BY c.date ASC, c.heureDebut ASC")
    List<Creneau> findCreneauxDisponiblesParDentiste(@Param("dentisteId") Long dentisteId);

    /**
     * Récupère les créneaux d'un dentiste pour une date donnée
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.date = :date ORDER BY c.heureDebut ASC")
    List<Creneau> findCreneauxByDentisteAndDate(
            @Param("dentisteId") Long dentisteId,
            @Param("date") LocalDate date
    );

    /**
     * Récupère les créneaux disponibles d'un dentiste pour une date donnée
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.date = :date AND c.disponible = true ORDER BY c.heureDebut ASC")
    List<Creneau> findCreneauxDisponiblesPourJournee(
            @Param("dentisteId") Long dentisteId,
            @Param("date") LocalDate date
    );

    /**
     * Récupère tous les créneaux disponibles pour un dentiste (à partir d'une date donnée)
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.disponible = true AND c.date >= :dateDebut ORDER BY c.date ASC, c.heureDebut ASC")
    List<Creneau> findCreneauxDisponiblesFromDate(
            @Param("dentisteId") Long dentisteId,
            @Param("dateDebut") LocalDate dateDebut
    );

    /**
     * Vérifie s'il existe un créneau à une date/heure spécifique pour un dentiste
     */
    @Query("SELECT COUNT(c) > 0 FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.date = :date AND c.heureDebut = :heure")
    boolean existsCreneauAtDateTimeForDentiste(
            @Param("dentisteId") Long dentisteId,
            @Param("date") LocalDate date,
            @Param("heure") LocalTime heure
    );

    /**
     * Récupère les créneaux d'un dentiste dans une plage de dates
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.date BETWEEN :dateDebut AND :dateFin ORDER BY c.date ASC, c.heureDebut ASC")
    List<Creneau> findCreneauxByDentisteAndDateRange(
            @Param("dentisteId") Long dentisteId,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    /**
     * Récupère les créneaux non disponibles (bloqués) pour un dentiste
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.disponible = false ORDER BY c.date ASC, c.heureDebut ASC")
    List<Creneau> findCreneauxBloquesParDentiste(@Param("dentisteId") Long dentisteId);

    /**
     * Récupère les créneaux d'un dentiste pour une date ultérieure à aujourd'hui
     */
    @Query("SELECT c FROM Creneau c WHERE c.dentiste.id = :dentisteId " +
            "AND c.date > :dateActuelle ORDER BY c.date ASC, c.heureDebut ASC")
    List<Creneau> findFutureCreneauxForDentiste(
            @Param("dentisteId") Long dentisteId,
            @Param("dateActuelle") LocalDate dateActuelle
    );

    /**
     * Récupère les créneaux disponibles pour une date donnée (tous dentistes)
     */
    @Query("SELECT c FROM Creneau c WHERE c.date = :date AND c.disponible = true " +
            "ORDER BY c.dentiste.id ASC, c.heureDebut ASC")
    List<Creneau> findAllCreneauxDisponiblesForDate(@Param("date") LocalDate date);
}