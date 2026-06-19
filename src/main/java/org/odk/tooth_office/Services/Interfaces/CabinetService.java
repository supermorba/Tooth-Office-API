package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Secretaire;
import java.util.List;
import java.util.Optional;

public interface CabinetService {
    Cabinet creerCabinet(CabinetDTO dto);
    List<Cabinet> recupererTous();
    Optional<Cabinet> recupererParId(Integer id);
    Optional<Cabinet> recupererParNom(String nomCabinet);
    Cabinet modifierCabinet(Integer id, CabinetDTO dto);
    void supprimerCabinet(Integer id);
    List<Dentiste> afficherDentistesParCabinet(Integer idCabinet);
    List<Secretaire> afficherSecretairesParCabinet(Integer idCabinet);
    Optional<Secretaire> afficherUnSecretaireParCabinet(Integer idCabinet, Integer idSecretaire);
    Optional<Dentiste> afficherUnDentisteParCabinet(Integer idCabinet, Integer idDentiste);
}

