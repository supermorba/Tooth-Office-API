package org.odk.tooth_office.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Entity.Patient;

@Entity @Table @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Dossier_medical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String antecedents;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String historique;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_patient",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_Dossier_medical")
    )
    private Patient patient;
}