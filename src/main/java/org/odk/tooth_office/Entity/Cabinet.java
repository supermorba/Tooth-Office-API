package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un cabinet dentaire.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Cabinet")
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cabinet")
    private Integer idCabinet;

    @Column(name = "nom_cabinet", nullable = false, length = 50)
    private String nomCabinet;

    @Column(unique = true, length = 50)
    private String tel;

    @Column(length = 60)
    private String adresse;

    @Column(length = 200)
    private String logo;

    @Column(length = 200)
    private String description;

    /** Tarif de base d'une consultation dans ce cabinet */
    @Column(name = "tarif_consultation")
    private Integer tarifConsultation;

    /** Dentistes exerçant dans ce cabinet */
    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Dentiste> dentistes = new ArrayList<>();

    /** Secrétaires travaillant dans ce cabinet */
    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Secretaire> secretaires = new ArrayList<>();

    /** Services offerts par ce cabinet */
    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CabinetService> services = new ArrayList<>();

    /** Patients suivis par ce cabinet (relation M2M) */
    @ManyToMany(mappedBy = "cabinets")
    private List<Patient> patients = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
