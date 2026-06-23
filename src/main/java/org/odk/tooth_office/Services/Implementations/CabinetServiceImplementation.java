
package org.odk.tooth_office.Services.Implementations;


import org.odk.tooth_office.DTO.CabinetDTO;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Entity.Secretaire;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Services.Interfaces.CabinetService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CabinetServiceImplementation implements CabinetService {

    private final CabinetRepository cabinetRepository;

    public CabinetServiceImplementation(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    @Override
    public Cabinet creerCabinet(CabinetDTO dto) {
        if (cabinetRepository.existsByTel(dto.getTel())) {
            throw new RuntimeException("Un cabinet possède déjà ce numéro de téléphone.");
        }
        Cabinet cabinet = new Cabinet();
        cabinet.setNomCabinet(dto.getNomCabinet());
        cabinet.setTel(dto.getTel());
        cabinet.setAdresse(dto.getAdresse());
        cabinet.setLogo(dto.getLogo());
        cabinet.setDescription(dto.getDescription());
        return cabinetRepository.save(cabinet);
    }

    @Override
    public List<Cabinet> recupererTous() {
        return cabinetRepository.findAll();
    }

    @Override
    public Optional<Cabinet> recupererParId(Integer id) {
        return cabinetRepository.findById(id);
    }

    @Override
    public Optional<Cabinet> recupererParNom(String nomCabinet) {
        return cabinetRepository.findByNomCabinet(nomCabinet);
    }

    @Override
    public Cabinet modifierCabinet(Integer id, CabinetDTO dto) {
        return cabinetRepository.findById(id).map(existing -> {
            existing.setNomCabinet(dto.getNomCabinet());
            existing.setTel(dto.getTel());
            existing.setAdresse(dto.getAdresse());
            existing.setLogo(dto.getLogo());
            existing.setDescription(dto.getDescription());
            return cabinetRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + id));
    }

    @Override
    public void supprimerCabinet(Integer id) {
        if (!cabinetRepository.existsById(id)) {
            throw new RuntimeException("Cabinet introuvable avec l'ID : " + id);
        }
        cabinetRepository.deleteById(id);
    }

    // =========================================================================
    // IMPLEMENTATION DES NOUVELLES METHODES
    // =========================================================================

    @Override
    @Transactional // Nécessaire pour initialiser la collection LAZY en toute sécurité
    public List<Dentiste> afficherDentistesParCabinet(Integer idCabinet) {
        return cabinetRepository.findById(idCabinet)
                .map(Cabinet::getDentistes)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));
    }

    @Override
    @Transactional
    public List<Secretaire> afficherSecretairesParCabinet(Integer idCabinet) {
        return cabinetRepository.findById(idCabinet)
                .map(Cabinet::getSecretaires)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));
    }

    @Override
    @Transactional
    public Optional<Secretaire> afficherUnSecretaireParCabinet(Integer idCabinet, Integer idSecretaire) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        return cabinet.getSecretaires().stream()
                .filter(secretaire -> secretaire.getIdSecretaire() != null
                        && secretaire.getIdSecretaire().equals(idSecretaire.longValue()))
                .findFirst();
    }
    @Override
    @Transactional
    public Optional<Dentiste> afficherUnDentisteParCabinet(Integer idCabinet, Long idDentiste) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        return cabinet.getDentistes().stream()
                .filter(dentiste -> Integer.parseInt(dentiste.getId_utilisateur().toString()) == idDentiste) // Remplacez par la méthode appropriée selon votre entité Dentiste
                .filter(dentiste -> Objects.equals(dentiste.getId_utilisateur(), idDentiste)) // Remplacez par la méthode appropriée selon votre entité Dentiste
                .findFirst();
    }
}

