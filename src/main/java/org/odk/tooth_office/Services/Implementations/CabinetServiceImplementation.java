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
import org.odk.tooth_office.Services.FileStorageService;
import org.odk.tooth_office.Services.Interfaces.CabinetService;
import org.odk.tooth_office.Entity.ChefCabinet;
import org.odk.tooth_office.Repository.ChefCabinetRepository;
import org.odk.tooth_office.Entity.Utilisateur;
import org.odk.tooth_office.Enum.RoleEnum;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.odk.tooth_office.security.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    private final FileStorageService fileStorageService;
    private final ChefCabinetRepository chefCabinetRepository;
    private final UtilisateurRepository utilisateurRepository;


    @Override
    @Transactional
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
        Cabinet saved = cabinetRepository.save(cabinet);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                Long userId = null;
                String username = auth.getName();
                if (auth.getPrincipal() instanceof CustomUserPrincipal principal) {
                    userId = principal.getUserId();
                }

                ChefCabinet chef = null;
                if (userId != null) {
                    chef = chefCabinetRepository.findById(userId).orElse(null);
                }
                if (chef == null && username != null && !username.isBlank()) {
                    chef = chefCabinetRepository.findByEmail(username)
                            .or(() -> chefCabinetRepository.findAll().stream()
                                    .filter(c -> username.equals(c.getEmail()) || username.equals(c.getTelephone()))
                                    .findFirst())
                            .orElse(null);
                }
                if (chef == null && userId != null) {
                    Utilisateur u = utilisateurRepository.findById(userId).orElse(null);
                    if (u != null && u.getRole() == RoleEnum.CHEF_CABINET) {
                        chef = new ChefCabinet();
                        chef.setId_utilisateur(u.getId_utilisateur());
                        chef.setNom(u.getNom());
                        chef.setPrenom(u.getPrenom());
                        chef.setEmail(u.getEmail());
                        chef.setMdp(u.getMdp());
                        chef.setTelephone(u.getTelephone());
                        chef.setAdresse(u.getAdresse());
                        chef.setRole(RoleEnum.CHEF_CABINET);
                        chef.setStatutCompte(u.getStatutCompte());
                        chef = chefCabinetRepository.save(chef);
                    }
                }
                if (chef == null && username != null) {
                    Utilisateur u = utilisateurRepository.findByEmail(username)
                            .or(() -> utilisateurRepository.findByTelephone(username))
                            .orElse(null);
                    if (u != null && u.getRole() == RoleEnum.CHEF_CABINET) {
                        chef = new ChefCabinet();
                        chef.setId_utilisateur(u.getId_utilisateur());
                        chef.setNom(u.getNom());
                        chef.setPrenom(u.getPrenom());
                        chef.setEmail(u.getEmail());
                        chef.setMdp(u.getMdp());
                        chef.setTelephone(u.getTelephone());
                        chef.setAdresse(u.getAdresse());
                        chef.setRole(RoleEnum.CHEF_CABINET);
                        chef.setStatutCompte(u.getStatutCompte());
                        chef = chefCabinetRepository.save(chef);
                    }
                }

                if (chef != null) {
                    if (chef.getCabinets() == null) {
                        chef.setCabinets(new ArrayList<>());
                    }
                    boolean exists = chef.getCabinets().stream()
                            .anyMatch(c -> Objects.equals(c.getIdCabinet(), saved.getIdCabinet()));
                    if (!exists) {
                        chef.getCabinets().add(saved);
                        chefCabinetRepository.save(chef);
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return cabinetMapper.toCabinet(saved);
    }

    @Override
    @Transactional
    public List<CabinetResponseDTO> recupererTous() {
        List<CabinetResponseDTO> listCabinetDto= new ArrayList<>();
        listCabinetDto = cabinetRepository.findAll().stream()
                .map(cabinetMapper::toCabinet).toList();
        return listCabinetDto;
    }

    @Override
    @Transactional
    public Optional<CabinetResponseDTO> recupererParId(Integer id) {
        return cabinetRepository.findById(id)
                .map(cabinetMapper::toCabinet);
    }

    @Override
    @Transactional
    public Optional<CabinetResponseDTO> recupererParNom(String nomCabinet) {
        return cabinetRepository.findByNomCabinet(nomCabinet)
                .map(cabinetMapper::toCabinet);
    }

    @Override
    @Transactional
    public CabinetResponseDTO modifierCabinet(Integer id, CabinetDTO dto) {
        Cabinet cabinetSauvegarde = cabinetRepository.findById(id).map(existing -> {
            existing.setNomCabinet(dto.getNomCabinet());
            existing.setTel(dto.getTel());
            existing.setAdresse(dto.getAdresse());
            existing.setLogo(dto.getLogo());
            existing.setDescription(dto.getDescription());
            return cabinetRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + id));

        return cabinetMapper.toCabinet(cabinetSauvegarde);
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
                .map(e->e.getSecretaires().stream().map(SecretaireMapper::toResponseDTO))
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
                .map(SecretaireMapper::toResponseDTO).findFirst();
    }
    @Override
    @Transactional
    public Optional<DentisteResponseDTO> afficherUnDentisteParCabinet(Integer idCabinet, Long idDentiste) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));
        Dentiste dentiste1 = dentisteRepository.findById(idDentiste)
                .orElseThrow(() -> new RuntimeException("Dentiste introuvable avec l'ID : " + idDentiste));

        return cabinet.getDentistes().stream()
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

    @Override
    @Transactional
    public CabinetResponseDTO uploadLogo(Integer idCabinet, MultipartFile file) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        String logoPath = fileStorageService.storeCabinetLogo(idCabinet, file);
        cabinet.setLogo(logoPath);
        Cabinet updatedCabinet = cabinetRepository.save(cabinet);
        return cabinetMapper.toCabinet(updatedCabinet);
    }

    @Override
    public Resource getLogoResource(Integer idCabinet) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        if (cabinet.getLogo() == null || cabinet.getLogo().isBlank()) {
            throw new RuntimeException("Le cabinet d'ID " + idCabinet + " ne possède pas de logo.");
        }

        return fileStorageService.loadAsResource(cabinet.getLogo());
    }

    @Override
    public String getLogoContentType(Integer idCabinet) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        if (cabinet.getLogo() == null || cabinet.getLogo().isBlank()) {
            return "image/png";
        }

        return fileStorageService.determineContentType(cabinet.getLogo());
    }

    @Override
    @Transactional
    public CabinetResponseDTO supprimerLogo(Integer idCabinet) {
        Cabinet cabinet = cabinetRepository.findById(idCabinet)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable avec l'ID : " + idCabinet));

        if (cabinet.getLogo() != null && !cabinet.getLogo().isBlank()) {
            fileStorageService.deleteFile(cabinet.getLogo());
            cabinet.setLogo(null);
            cabinet = cabinetRepository.save(cabinet);
        }

        return cabinetMapper.toCabinet(cabinet);
    }
}

