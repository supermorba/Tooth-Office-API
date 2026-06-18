package org.odk.tooth_office.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCabinet;
    private String nom_cabinet;
    private String tel;
    private String adresse;
    private String logo;
    private String description;

}
