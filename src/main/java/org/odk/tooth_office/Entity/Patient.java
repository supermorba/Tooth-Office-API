package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un patient.
 * Table séparée liée à Utilisateur via la stratégie JOINED (clé PK partagée).
 * Un patient peut être suivi par plusieurs cabinets.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Patient")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class Patient extends Utilisateur {

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    /** Cabinets qui suivent ce patient (relation M2M) */
    @ManyToMany
    @JoinTable(name = "PATIENT_CABINET", joinColumns = @JoinColumn(name = "id_patient"), inverseJoinColumns = @JoinColumn(name = "id_cabinet"))
    private List<Cabinet> cabinets = new ArrayList<>();
}