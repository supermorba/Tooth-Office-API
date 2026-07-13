package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.DTO.CreneauDTO;
import org.odk.tooth_office.DTO.CreneauDtoSurplace;
import org.odk.tooth_office.Entity.Creneau;

import java.time.LocalDate;
import java.util.List;

public interface CreneauService {
    // Pour le Dentiste ou la Secrétaire : Générer les créneaux de la journée
    List<CreneauDTO> genererCreneauxPourJournee(LocalDate date, Long dentisteId);

    // Pour le Patient ou la Secrétaire : Consulter les disponibilités d'un dentiste
    List<CreneauDTO> obtenirCreneauxDisponiblesParDentiste(Long dentisteId);

    // Pour la Secrétaire : Bloquer manuellement un créneau (ex: urgence ou pause)
    void bloquerCreneau(Long idCreneau);

    // Libérer un créneau suite à une annulation
    void libererCreneau(Long idCreneau);
    Creneau creerCreneauSurplace(CreneauDtoSurplace dto);

}
