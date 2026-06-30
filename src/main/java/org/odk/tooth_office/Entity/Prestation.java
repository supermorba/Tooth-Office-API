package org.odk.tooth_office.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, length = 50)
    private String nomPrestation ;
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "prestation")
    @JsonIgnoreProperties("prestation")
    private List<CabinetPrestation> cabinetPrestations = new ArrayList<>();


}
