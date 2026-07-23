package org.odk.tooth_office.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clé primaire composite pour CabinetService (id_service + id_cabinet).
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class CabinetServiceId implements Serializable {

    private Integer idService;
    private Integer idCabinet;
}
