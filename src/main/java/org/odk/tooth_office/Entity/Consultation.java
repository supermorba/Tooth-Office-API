package org.odk.tooth_office.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date_consultation= new Date();

    private  Date updateAt;

    private String diagnostic;

    private String notes;

    @JsonProperty(defaultValue = "true")
    private boolean isEnabled = true;

    @ManyToOne
    private DossierMedical dossierMedical;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_rendez_vous",
            columnDefinition = "BIGINT",
            nullable = false
    )
    private RendezVous rendezVous;

    @ManyToOne
    private Dentiste dentiste;

}
