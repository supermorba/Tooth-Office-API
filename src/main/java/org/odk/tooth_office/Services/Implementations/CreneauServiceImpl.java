package org.odk.tooth_office.Services.Implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odk.tooth_office.DTO.CreneauDTO;
import org.odk.tooth_office.DTO.CreneauDtoSurplace;
import org.odk.tooth_office.DTO.MapperDTO.CreneauMapper;
import org.odk.tooth_office.Entity.Creneau;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Repository.CreneauRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Repository.RendezVousRepository;
import org.odk.tooth_office.Services.Interfaces.CreneauService;
import org.odk.tooth_office.utils.FindCreneauForm;
import org.odk.tooth_office.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreneauServiceImpl implements CreneauService {

    private final CreneauMapper creneauMapper;
    private final CreneauRepository creneauRepository;
    private final DentisteRepository dentisteRepository;
    private final RendezVousRepository rendezVousRepository;

    /**
     * Génère les créneaux pour une journée donnée pour un dentiste
     */
    @Override
    public List<CreneauDTO> genererCreneauxPourJournee(LocalDate date, Long dentisteId) {
        log.info("Génération des créneaux pour le dentiste {} à la date {}", dentisteId, date);

        Dentiste dentiste = dentisteRepository.findById(dentisteId)
                .orElseThrow(() -> new RuntimeException("Dentiste non trouvé avec l'ID: " + dentisteId));

        List<Creneau> creneauxExistants = creneauRepository.findCreneauxByDentisteAndDate(dentisteId, date);
        if (!creneauxExistants.isEmpty()) {
            log.warn("Les créneaux existent déjà pour le dentiste {} à la date {}", dentisteId, date);
            return creneauxExistants.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        // Créer les créneaux par défaut (9h à 17h, créneaux de 30 minutes)
        LocalTime debut = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(17, 0);

        List<Creneau> creneaux = new java.util.ArrayList<>();
        LocalTime currentStart = debut;

        while (currentStart.isBefore(fin)) {
            LocalTime currentEnd = currentStart.plusMinutes(30);
            if (currentEnd.isAfter(fin)) {
                break;
            }

            Creneau creneau = new Creneau();
            creneau.setDate(date);
            creneau.setHeureDebut(currentStart);
            creneau.setHeureFin(currentEnd);
            creneau.setDisponible(true);
            creneau.setDentiste(dentiste);

            creneaux.add(creneau);
            currentStart = currentEnd;
        }

        List<Creneau> creneauxSauvegardes = creneauRepository.saveAll(creneaux);
        log.info("{} créneaux générés pour le dentiste {}", creneauxSauvegardes.size(), dentisteId);

        return creneauxSauvegardes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les créneaux disponibles pour un dentiste
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreneauDTO> obtenirCreneauxDisponiblesParDentiste(Long dentisteId) {
        log.info("Récupération des créneaux disponibles pour le dentiste {}", dentisteId);

        dentisteRepository.findById(dentisteId)
                .orElseThrow(() -> new RuntimeException("Dentiste non trouvé avec l'ID: " + dentisteId));

        List<Creneau> creneaux = creneauRepository.findCreneauxDisponiblesFromDate(
                dentisteId,
                LocalDate.now()
        );

        return creneaux.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Bloque un créneau (le rend indisponible)
     */
    @Override
    public void bloquerCreneau(Long idCreneau) {
        log.info("Blocage du créneau {}", idCreneau);

        Creneau creneau = creneauRepository.findById(idCreneau)
                .orElseThrow(() -> new RuntimeException("Créneau non trouvé avec l'ID: " + idCreneau));

        if (!creneau.isDisponible()) {
            log.warn("Le créneau {} est déjà bloqué", idCreneau);
            return;
        }

        creneau.setDisponible(false);
        creneauRepository.save(creneau);
        log.info("Créneau {} bloqué avec succès", idCreneau);
    }

    /**
     * Libère un créneau (le rend disponible)
     */
    @Override
    public void libererCreneau(Long idCreneau) {
        log.info("Libération du créneau {}", idCreneau);

        Creneau creneau = creneauRepository.findById(idCreneau)
                .orElseThrow(() -> new RuntimeException("Créneau non trouvé avec l'ID: " + idCreneau));

        if (creneau.isDisponible()) {
            log.warn("Le créneau {} est déjà disponible", idCreneau);
            return;
        }

        creneau.setDisponible(true);
        creneauRepository.save(creneau);
        log.info("Créneau {} libéré avec succès", idCreneau);
    }

    @Override
    public Creneau creerCreneauSurplace(CreneauDtoSurplace dto) {
        Dentiste dentiste = dentisteRepository.findById(dto.dentisteId())
                .orElseThrow(() -> new EntityNotFoundException("Dentiste introuvable"));

        Creneau creneau = creneauMapper.toEntity(dto);
        creneau.setDentiste(dentiste);

        return creneauRepository.save(creneau);

    }

    @Override
    public Response getCreneauxByDentisteAndDate(FindCreneauForm findCreneauForm) {
        try{
            List<Creneau> creneaus= creneauRepository.findCreneauxDisponiblesPourJournee(findCreneauForm.getDentiste(), findCreneauForm.getDate());
            return Response.succes("La liste des creneaux disponibles pour cette date", creneaus.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.error("Erreur lors de a recuperation des creneaux de ce dentiste pour cette date");
        }
    }

    @Override
    public List<CreneauDTO> genererModeleCreneaux(org.odk.tooth_office.DTO.ModeleCreneauxRequestDTO dto) {
        log.info("Génération du modèle de créneaux pour le dentiste {} du {} au {}", dto.getDentisteId(), dto.getDateDebut(), dto.getDateFin());

        Dentiste dentiste = dentisteRepository.findById(dto.getDentisteId())
                .orElseThrow(() -> new RuntimeException("Dentiste non trouvé avec l'ID: " + dto.getDentisteId()));

        int duree = (dto.getDureeMinutes() != null && dto.getDureeMinutes() > 0) ? dto.getDureeMinutes() : 30;
        List<Integer> joursActifs = (dto.getJoursTravailles() != null && !dto.getJoursTravailles().isEmpty())
                ? dto.getJoursTravailles()
                : List.of(1, 2, 3, 4, 5);

        boolean remplacer = Boolean.TRUE.equals(dto.getRemplacerExistants());

        // Si le remplacement est demandé, supprimer tous les créneaux non réservés sur la période
        if (remplacer) {
            List<Creneau> creneauxExistantsPeriode = creneauRepository.findCreneauxByDentisteAndDateRange(dentiste.getId_utilisateur(), dto.getDateDebut(), dto.getDateFin());
            List<Long> bookedCreneauIds = rendezVousRepository.findByDentisteId(dentiste.getId_utilisateur())
                    .stream()
                    .filter(r -> r.getCreneau() != null)
                    .map(r -> r.getCreneau().getIdCreneau())
                    .collect(Collectors.toList());

            List<Creneau> aSupprimer = creneauxExistantsPeriode.stream()
                    .filter(c -> c.getIdCreneau() != null && !bookedCreneauIds.contains(c.getIdCreneau()))
                    .collect(Collectors.toList());

            if (!aSupprimer.isEmpty()) {
                log.info("Suppression de {} créneaux non réservés pour remplacement de la période", aSupprimer.size());
                creneauRepository.deleteAll(aSupprimer);
            }
        }

        List<Creneau> nouveauxCreneaux = new java.util.ArrayList<>();
        LocalDate currentDate = dto.getDateDebut();

        while (!currentDate.isAfter(dto.getDateFin())) {
            int dayOfWeek = currentDate.getDayOfWeek().getValue(); // 1 (Mon) to 7 (Sun)

            if (joursActifs.contains(dayOfWeek)) {
                List<Creneau> creneauxExistants = creneauRepository.findCreneauxByDentisteAndDate(dentiste.getId_utilisateur(), currentDate);

                // Générer Matin
                if (dto.getHeureDebutMatin() != null && dto.getHeureFinMatin() != null) {
                    genererCreneauxPlage(nouveauxCreneaux, creneauxExistants, dentiste, currentDate, dto.getHeureDebutMatin(), dto.getHeureFinMatin(), duree);
                }

                // Générer Après-Midi
                if (dto.getHeureDebutApresMidi() != null && dto.getHeureFinApresMidi() != null) {
                    genererCreneauxPlage(nouveauxCreneaux, creneauxExistants, dentiste, currentDate, dto.getHeureDebutApresMidi(), dto.getHeureFinApresMidi(), duree);
                }
            }

            currentDate = currentDate.plusDays(1);
        }

        if (!nouveauxCreneaux.isEmpty()) {
            creneauRepository.saveAll(nouveauxCreneaux);
        }

        List<Creneau> tousLesCreneaux = creneauRepository.findCreneauxByDentisteAndDateRange(dto.getDentisteId(), dto.getDateDebut(), dto.getDateFin());
        return tousLesCreneaux.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private void genererCreneauxPlage(List<Creneau> aAjouter, List<Creneau> existants, Dentiste dentiste, LocalDate date, LocalTime debut, LocalTime fin, int duree) {
        LocalTime currentStart = debut;
        while (currentStart.isBefore(fin)) {
            LocalTime currentEnd = currentStart.plusMinutes(duree);
            if (currentEnd.isAfter(fin)) {
                break;
            }

            final LocalTime startFinal = currentStart;
            boolean existeDeja = existants.stream().anyMatch(c -> c.getHeureDebut().equals(startFinal));
            if (!existeDeja) {
                Creneau creneau = new Creneau();
                creneau.setDate(date);
                creneau.setHeureDebut(currentStart);
                creneau.setHeureFin(currentEnd);
                creneau.setDisponible(true);
                creneau.setDentiste(dentiste);
                aAjouter.add(creneau);
            }

            currentStart = currentEnd;
        }
    }

    @Override
    public void bloquerPlage(org.odk.tooth_office.DTO.BloquerPlageRequestDTO dto) {
        log.info("Blocage de plage {} pour dentiste {} le {}", dto.getPlage(), dto.getDentisteId(), dto.getDate());

        List<Creneau> creneaux = creneauRepository.findCreneauxByDentisteAndDate(dto.getDentisteId(), dto.getDate());
        if (creneaux.isEmpty()) return;

        LocalTime limiteMidi = LocalTime.of(13, 0);

        for (Creneau c : creneaux) {
            boolean concerne = false;
            if ("JOURNEE".equalsIgnoreCase(dto.getPlage())) {
                concerne = true;
            } else if ("MATIN".equalsIgnoreCase(dto.getPlage())) {
                concerne = c.getHeureDebut().isBefore(limiteMidi);
            } else if ("APRES_MIDI".equalsIgnoreCase(dto.getPlage())) {
                concerne = !c.getHeureDebut().isBefore(limiteMidi);
            }

            if (concerne) {
                c.setDisponible(!dto.isBloquer());
            }
        }

        creneauRepository.saveAll(creneaux);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreneauDTO> getCreneauxDentistePeriode(Long dentisteId, LocalDate dateDebut, LocalDate dateFin) {
        List<Creneau> creneaux = creneauRepository.findCreneauxByDentisteAndDateRange(dentisteId, dateDebut, dateFin);
        return creneaux.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Mappe une entité Creneau vers un DTO
     */
    private CreneauDTO mapToDTO(Creneau creneau) {
        CreneauDTO dto = new CreneauDTO();
        dto.setIdCreneau(creneau.getIdCreneau());
        dto.setDate(creneau.getDate());
        dto.setHeureDebut(creneau.getHeureDebut());
        dto.setHeureFin(creneau.getHeureFin());
        dto.setDisponible(creneau.isDisponible());
        dto.setDentisteId(creneau.getDentiste().getId_utilisateur());
        dto.setDentisteNom(creneau.getDentiste().getNom() + " " + creneau.getDentiste().getPrenom());
        return dto;
    }
}