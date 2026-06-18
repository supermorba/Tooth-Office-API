package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Traitement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_traitement;
    private String type;
    private String description;
    private int duree;

}
