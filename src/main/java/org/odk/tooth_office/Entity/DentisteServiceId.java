package org.odk.tooth_office.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clé primaire composite pour DentisteService (id_service + id_dentiste).
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class DentisteServiceId implements Serializable {

    private Integer idService;
    private Long idDentiste;
}
