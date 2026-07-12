package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.CreneauDTO;
import org.odk.tooth_office.Entity.Creneau;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Services.Interfaces.CreneauService;
import org.odk.tooth_office.Repository.CreneauRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreneauServiceImpl implements CreneauService {

    private final CreneauRepository creneauRepository;
    private final DentisteRepository dentisteRepository;

    // Injection des repositories par constructeur
    public CreneauServiceImpl(CreneauRepository creneauRepository, DentisteRepository dentisteRepository) {
        this.creneauRepository = creneauRepository;
        this.dentisteRepository = dentisteRepository;
    }

    @Override
    @Transactional
    public List<CreneauDTO> genererCreneauxPourJournee(LocalDate date, Long dentisteId) {
        Dentiste dentiste = dentisteRepository.findById(dentisteId)
                .orElseThrow(() -> new RuntimeException("Dentiste introuvable avec l'ID : " + dentisteId));

        // Exemple de configuration d'une journée type : 08:00 à 17:00 avec 30 minutes par patient
        LocalTime heureDebutJournee = LocalTime.of(8, 0);
        LocalTime heureFinJournee = LocalTime.of(17, 0);
        int dureeCreneauMinutes = 30;

        List<Creneau> creneauxGeneres = new ArrayList<>();
        LocalTime courant = heureDebutJournee;

        while (courant.isBefore(heureFinJournee)) {
            LocalTime suivant = courant.plusMinutes(dureeCreneauMinutes);

            Creneau creneau = new Creneau();
            creneau.setDate(date);
            creneau.setHeureDebut(courant);
            creneau.setHeureFin(suivant);
            creneau.setDisponible(true);
            creneau.setDentiste(dentiste);

            creneauxGeneres.add(creneau);
            courant = suivant;
        }

        // Sauvegarde groupée en Base de données
        List<Creneau> savedCreneaux = creneauRepository.saveAll(creneauxGeneres);

        // Transformation de la liste d'entités en liste de DTOs pour le retour
        return savedCreneaux.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreneauDTO> obtenirCreneauxDisponiblesParDentiste(Long dentisteId) {
        // Attention : on utilise ici la méthode adaptée avec l'underscore (Id_utilisateur) liée à votre modèle
        return creneauRepository.findByDentisteId_utilisateurAndDisponibleTrue(dentisteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void bloquerCreneau(Long idCreneau) {
        Creneau creneau = creneauRepository.findById(idCreneau)
                .orElseThrow(() -> new RuntimeException("Créneau introuvable avec l'ID : " + idCreneau));

        if (!creneau.isDisponible()) {
            throw new IllegalStateException("Ce créneau est déjà occupé ou bloqué.");
        }

        creneau.setDisponible(false);
        creneauRepository.save(creneau);
    }

    @Override
    @Transactional
    public void libererCreneau(Long idCreneau) {
        Creneau creneau = creneauRepository.findById(idCreneau)
                .orElseThrow(() -> new RuntimeException("Créneau introuvable avec l'ID : " + idCreneau));

        creneau.setDisponible(true);
        creneauRepository.save(creneau);
    }

    /**
     * Méthode utilitaire (Mapper) pour convertir une entité Creneau en CreneauDTO.
     */
    private CreneauDTO mapToDTO(Creneau creneau) {
        CreneauDTO dto = new CreneauDTO();
        dto.setIdCreneau(creneau.getIdCreneau());
        dto.setDate(creneau.getDate());
        dto.setHeureDebut(creneau.getHeureDebut());
        dto.setHeureFin(creneau.getHeureFin());
        dto.setDisponible(creneau.isDisponible());

        // Données du dentiste lié
        if (creneau.getDentiste() != null) {
            dto.setDentisteId(creneau.getDentiste().getId_utilisateur()); // Votre clé primaire héritée
            dto.setDentisteNom("Dr. " + creneau.getDentiste().getNom());
        }

        return dto;
    }
}