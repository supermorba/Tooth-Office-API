package org.odk.tooth_office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Creneau")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_creneau")
    private Integer idCreneau;

    @Column(name = "debut", nullable = false)
    private LocalDateTime debut;

    @Column(name = "fin", nullable = false)
    private LocalDateTime fin;
}
