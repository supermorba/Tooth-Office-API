/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.odk.tooth_office.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author kalandew15
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="avis")
@Getter @Setter
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="note")
    private int note;

    @Column(name="description")
    private String description;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_cabinet", nullable=false)
    private Cabinet cabinet;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_patient", nullable=false)
    private Patient patient;
}
