package org.odk.tooth_office.Entity;

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

    private Date date_consultation;

    private  Date updateAt;

    private String diagnostic;

    private String notes;

    private boolean isEnabled = true;

    @ManyToOne
    private Dossier_medical dossierMedical;

    @ManyToOne
    private RendezVous rendezVous;

    @ManyToOne
    private Dentiste dentiste;

}
