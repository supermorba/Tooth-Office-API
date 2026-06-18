package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Enum.StatutCompte;

import java.time.LocalDate;

//Lombok
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
//JPA
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_utilisateur ;
    private String nom ;
    private String prenom ;
    private String email ;
    private String mpd ;
    private String adresse ;
    private RoleEnum role ;
    private String telephone ;
    private StatutCompte statutCompte ;
    private LocalDate createdAt;
}
