package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_prestation ;
    @Column(nullable = false, unique = true, length = 50)
    private String nom_prestation ;
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "prestation")
    private List<CabinetPrestation> cabinetPrestations = new ArrayList<>();


}
