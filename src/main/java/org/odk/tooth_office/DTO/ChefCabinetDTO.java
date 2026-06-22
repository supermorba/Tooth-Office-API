package org.odk.tooth_office.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChefCabinetDTO extends UtilisateurDTO {
    private List<Integer> cabinetIds;
}