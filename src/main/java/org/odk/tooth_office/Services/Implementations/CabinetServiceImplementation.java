package org.odk.tooth_office.Services.Implementations;


import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.DTO.*;
import org.odk.tooth_office.DTO.MapperDTO.AvisMapper;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.Dentiste;
import org.odk.tooth_office.Mapper.CabinetMapper;
import org.odk.tooth_office.Mapper.DentisteMapper;
import org.odk.tooth_office.Mapper.SecretaireMapper;
import org.odk.tooth_office.Repository.CabinetRepository;
import org.odk.tooth_office.Repository.DentisteRepository;
import org.odk.tooth_office.Services.Interfaces.CabinetService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabinetServiceImplementation implements CabinetService {

    private final CabinetRepository cabinetRepository;
    private final CabinetMapper cabinetMapper;
    private final SecretaireMapper secretaireMapper;
    private final DentisteMapper dentisteMapper;
    private final DentisteRepository dentisteRepository;
    private final AvisMapper avisMapper;


    @Override
    public CabinetResponseDTO creerCabinet(CabinetDTO dto) {
        if (cabinetRepository.existsByTel(dto.getTel())) {
            throw new RuntimeException("Un cabinet possède déjà ce numéro de téléphone.");
        }
        Cabinet cabinet = new Cabinet();
        cabinet.setNomCabinet(dto.getNomCabinet());
        cabinet.setTel(dto.getTel());
        cabinet.setAdresse(dto.getAdresse());
        cabinet.setLogo(dto.getLogo());
        cabinet.setDescription(dto.getDescription());
        return cabinetMapper.toCabinet(cabinetRepository.save(cabinet));
    }

    @Override
    public List<CabinetResponseDTO> recupererTous() {
        List<CabinetResponseDTO> listCabinetDto= new ArrayList<>();
        listCabinetDto = cabinetRepository.findAll().stream()
                .map(cabinetMapper::toCabinet).toList();
        return listCabinetDto;
    }

    @Override
    public Optional<CabinetResponseDTO> recupererParId(Integer id) {
        return cabinetRepository.findById(id)
                .map(cabinetMapper::toCabinet);
    }

    @Override
    public Optional<CabinetResponseDTO> recupererParNom(String nomCabinet) {
        return cabinetRepository.findByNomCabinet(nomCabinet)
                .map(cabinetMapper::toCabinet);
    }

    @Override
    public CabinetResponseDTO modifierCabinet(Integer id, CabinetDTO dto) {
        Cabinet cabinetSauvegarde = cabinetRepository.findById(id).map(existing -> {
            existing.setNomCabinet(dto.getNomCabinet());
            existing.setTel(dto.getTel());
            existing.setAdresse(dto.getAdresse());
            existing.setLogo(dto.getLogo());
            existing.setDescription(dto.getDescription());
            return cabinetRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + id));

        // Conversion de l'entité vers CabinetResponseDTO
        CabinetResponseDTO response = new CabinetResponseDTO();
        response.setIdCabinet(cabinetSauvegarde.getIdCabinet());
        response.setNomCabinet(cabinetSauvegarde.getNomCabinet());
        response.setTel(cabinetSauvegarde.getTel());
        response.setAdresse(cabinetSauvegarde.getAdresse());
        response.setLogo(cabinetSauvegarde.getLogo());
        response.setDescription(cabinetSauvegarde.getDescription());
        // Ajoutez ici les autres champs de CabinetResponseDTO si nécessaire (listes, etc.)

        return response;
    }



    @Override
    public void supprimerCabinet(Integer id) {
        if (!cabinetRepository.existsById(id)) {
            throw new RuntimeException("Cabinet introuvable avec l'ID : " + id);
        }
        cabinetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<DentisteResponseDTO> afficherDentistesParCabinet(Integer idCabinet) {
        return cabinetRepository.findById(idCabinet)
                .map(c->c.getDentistes().stream().map(dentisteMapper::toResponseDTO))
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet)).toList();
    }

    @Override
    @Transactional
    public List<SecretaireResponseDTO> afficherSecretairesParCabinet(Integer idCabinet) {
        return cabinetRepository.findById(idCabinet)
                .map(e->e.getSecretaires().stream().map(secretaireMapper::toResponseDTO))
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet)).toList();
    }

    @Override
    @Transactional
    public Optional<SecretaireResponseDTO> afficherUnSecretaireParCabinet(Integer idCabinet, Integer idSecretaire) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        return cabinet.getSecretaires().stream()
                .filter(secretaire -> secretaire.getIdSecretaire() != null
                        && secretaire.getIdSecretaire().equals(idSecretaire.longValue()))
                .map(secretaireMapper::toResponseDTO).findFirst();
    }
    @Override
    @Transactional
    public Optional<DentisteResponseDTO> afficherUnDentisteParCabinet(Integer idCabinet, Long idDentiste) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));
        Dentiste dentiste1 = dentisteRepository.findById(idDentiste)
                .orElseThrow(() -> new RuntimeException("Dentiste introuvable avec l'ID : " + idDentiste));

        return cabinet.getDentistes().stream()
                .filter(dentiste -> Integer.parseInt(dentiste.getId_utilisateur().toString()) == idDentiste)
                .filter(dentiste -> Objects.equals(dentiste.getId_utilisateur(), dentiste1.getId_utilisateur()))
                .map(dentisteMapper::toResponseDTO).findFirst();
    }

    @Override
    @Transactional
    public Optional<AvisResponseDTO> afficherUnAvisParCabinet(
            Integer idCabinet,
            Long idAvis
    ) {


        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cabinet introuvable avec l'ID : " + idCabinet
                        )
                );


        return cabinet.getAvis()
                .stream()
                .filter(avis ->
                        avis.getId().equals(idAvis)
                )
                .map(avisMapper::toResponseDTO)
                .findFirst();
    }

    @Override
    @Transactional
    public List<AvisResponseDTO> afficherLesAvisParCabinet(Integer idCabinet) {


        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cabinet introuvable avec l'ID : " + idCabinet
                        )
                );


        return cabinet.getAvis()
                .stream()
                .map(avisMapper::toResponseDTO)
                .toList();
    }
}


