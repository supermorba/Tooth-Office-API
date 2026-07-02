package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cabinet")
    private int idCabinet;

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



    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private List<Secretaire> secretaires;

    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private List<Dentiste> dentistes;

    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private List<Avis> avis;

    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private List<CabinetPrestation> cab_Ser;

    @ManyToMany (mappedBy = "cabinets", fetch = FetchType.LAZY)
    private List<ChefCabinet> ChefsCabinet;

}
