package org.odk.tooth_office.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odk.tooth_office.Enum.GraviteAllergie;
import org.odk.tooth_office.Enum.TypeAllergie;

@Entity
@Table(name = "allergie_intolerance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllergieIntolerance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeAllergie type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GraviteAllergie gravite;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_medical_id", nullable = false)
    private DossierMedical dossierMedical;
}
