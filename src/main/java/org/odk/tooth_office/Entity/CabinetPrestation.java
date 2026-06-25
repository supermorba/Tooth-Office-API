package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CabinetPrestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private double prix;
    private String description ;
    @ManyToOne
    private Cabinet cabinet;
    @ManyToOne
    private Prestation prestation;

}
