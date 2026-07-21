package org.odk.tooth_office.Repository;

import org.odk.tooth_office.Entity.RendezVous;
import org.odk.tooth_office.Enum.EtatRdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    /**
     * Récupère tous les rendez-vous d'un patient
     */
    @Query("SELECT r FROM RendezVous r WHERE r.patient.id = :patientId ORDER BY r.dateRdv DESC")
    List<RendezVous> findByPatientId(@Param("patientId") Long patientId);

    /**
     * Récupère tous les rendez-vous d'un dentiste
     */
    @Query("SELECT r FROM RendezVous r WHERE r.dentiste.id = :dentisteId ORDER BY r.dateRdv DESC")
    List<RendezVous> findByDentisteId(@Param("dentisteId") Long dentisteId);

    /**
     * Récupère les rendez-vous d'un dentiste pour une période donnée
     */
    @Query("SELECT r FROM RendezVous r WHERE r.dentiste.id = :dentisteId " +
            "AND r.dateRdv BETWEEN :debut AND :fin " +
            "ORDER BY r.dateRdv ASC")
    List<RendezVous> findRdvParDentisteEtPeriode(
            @Param("dentisteId") Long dentisteId,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin
    );

    /**
     * Récupère les rendez-vous d'un patient pour une période donnée
     */
    @Query("SELECT r FROM RendezVous r WHERE r.patient.id = :patientId " +
            "AND r.dateRdv BETWEEN :debut AND :fin " +
            "ORDER BY r.dateRdv ASC")
    List<RendezVous> findRdvParPatientEtPeriode(
            @Param("patientId") Long patientId,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin
    );

    /**
     * Récupère les rendez-vous avec un état spécifique
     */
    @Query("SELECT r FROM RendezVous r WHERE r.etatRdv = :etat ORDER BY r.dateRdv DESC")
    List<RendezVous> findByEtat(@Param("etat") EtatRdv etat);

    /**
     * Vérifie si un patient a un rendez-vous à une date/heure donnée
     */
    @Query("SELECT COUNT(r) > 0 FROM RendezVous r WHERE r.patient.id = :patientId " +
            "AND r.dateRdv = :dateRdv AND r.etatRdv != 'ANNULE'")
    boolean existsRendezVousAMemeHeure(
            @Param("patientId") Long patientId,
            @Param("dateRdv") LocalDateTime dateRdv
    );

    /**
     * Récupère les rendez-vous liés à un créneau
     */
    @Query("SELECT r FROM RendezVous r WHERE r.creneau.idCreneau = :creneauId")
    List<RendezVous> findByCreneauId(@Param("creneauId") Long creneauId);

    /**
     * Récupère les rendez-vous d'une secrétaire
     */
    @Query("SELECT r FROM RendezVous r WHERE r.secretaire.id = :secretaireId ORDER BY r.dateRdv DESC")
    List<RendezVous> findBySecretaireId(@Param("secretaireId") Long secretaireId);

    /**
     * Récupère les rendez-vous à venir pour un dentiste (à partir d'aujourd'hui)
     */
    @Query("SELECT r FROM RendezVous r WHERE r.dentiste.id = :dentisteId " +
            "AND r.dateRdv >= :dateActuelle AND r.etatRdv != 'ANNULE' " +
            "ORDER BY r.dateRdv ASC")
    List<RendezVous> findUpcomingRdvForDentiste(
            @Param("dentisteId") Long dentisteId,
            @Param("dateActuelle") LocalDateTime dateActuelle
    );

    /**
     * Récupère les rendez-vous passés d'un patient
     */
    @Query("SELECT r FROM RendezVous r WHERE r.patient.id = :patientId " +
            "AND r.dateRdv < :dateActuelle ORDER BY r.dateRdv DESC")
    List<RendezVous> findPastRdvForPatient(
            @Param("patientId") Long patientId,
            @Param("dateActuelle") LocalDateTime dateActuelle
    );

    @Query("SELECT r from RendezVous r WHERE r.dentiste.cabinet.idCabinet=:idCabinet")
    List<RendezVous> findRdvByCabinet(@Param("idCabinet") Long idCabinet);
}
