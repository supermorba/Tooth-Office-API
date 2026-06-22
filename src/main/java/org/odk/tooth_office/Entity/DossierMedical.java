package org.odk.tooth_office.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity @Table @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String antecedents;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String historiques;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_patient",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_DossierMedical")
    )
    private Patient patient;
}