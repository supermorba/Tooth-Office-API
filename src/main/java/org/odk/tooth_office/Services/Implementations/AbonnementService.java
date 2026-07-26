package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.AbonnementDTO;
import org.odk.tooth_office.Entity.Abonnement;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Repository.AbonnementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;

    public AbonnementService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    // Convertit l'entité JPA en DTO
    private AbonnementDTO convertToDTO(Abonnement abonnement) {
        if (abonnement == null) return null;

        AbonnementDTO dto = new AbonnementDTO();
        dto.setDateDebut(abonnement.getDateDebut());
        dto.setDateFin(abonnement.getDateFin());
        dto.setEtatAbonnement(abonnement.getEtatAbonnement());
        dto.setTypePaiement(abonnement.getTypePaiement());
        dto.setMontantTotal(abonnement.getMontantTotal());

        if (abonnement.getPlanAbonnement() != null && abonnement.getPlanAbonnement().getIdPlan() != null) {
            dto.setIdPlan(abonnement.getPlanAbonnement().getIdPlan().intValue());
        }

        if (abonnement.getCabinet() != null) {
            dto.setIdCabinet(abonnement.getCabinet().getIdCabinet());
        }

        return dto;
    }

    public AbonnementDTO creerAbonnement(AbonnementDTO dto) {
        PlanAbonnement plan = new PlanAbonnement();
        if (dto.getIdPlan() != null) {
            plan.setIdPlan(dto.getIdPlan().longValue());
        }

        Cabinet cabinet = new Cabinet();
        if (dto.getIdCabinet() != null) {
            cabinet.setIdCabinet(dto.getIdCabinet());
        }

        Abonnement abonnement = new Abonnement(
                dto.getDateDebut(), dto.getDateFin(), dto.getEtatAbonnement(),
                dto.getTypePaiement(), dto.getMontantTotal(), plan, cabinet
        );

        Abonnement sauve = abonnementRepository.save(abonnement);
        return convertToDTO(sauve);
    }

    public List<AbonnementDTO> recupererTous() {
        return abonnementRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AbonnementDTO> recupererParId(Integer id) {
        return abonnementRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<AbonnementDTO> recupererParCabinet(int idCabinet) {
        return abonnementRepository.findByCabinet_IdCabinet(idCabinet)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AbonnementDTO> recupererParPlan(Long idPlan) {
        return abonnementRepository.findByPlanAbonnement_IdPlan(idPlan)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AbonnementDTO modifierAbonnement(Integer id, AbonnementDTO dto) {
        return abonnementRepository.findById(id).map(existing -> {
            existing.setDateDebut(dto.getDateDebut());
            existing.setDateFin(dto.getDateFin());
            existing.setEtatAbonnement(dto.getEtatAbonnement());
            existing.setTypePaiement(dto.getTypePaiement());
            existing.setMontantTotal(dto.getMontantTotal());

            PlanAbonnement plan = new PlanAbonnement();
            if (dto.getIdPlan() != null) {
                plan.setIdPlan(dto.getIdPlan().longValue());
            }
            existing.setPlanAbonnement(plan);

            Cabinet cabinet = new Cabinet();
            if (dto.getIdCabinet() != null) {
                cabinet.setIdCabinet(dto.getIdCabinet());
            }
            existing.setCabinet(cabinet);

            Abonnement maj = abonnementRepository.save(existing);
            return convertToDTO(maj);
        }).orElseThrow(() -> new RuntimeException("Abonnement introuvable"));
    }

    public AbonnementDTO changerStatut(Integer id, EtatAbonnement nouveauStatut) {
        return abonnementRepository.findById(id).map(existing -> {
            existing.setEtatAbonnement(nouveauStatut);
            Abonnement maj = abonnementRepository.save(existing);
            return convertToDTO(maj);
        }).orElseThrow(() -> new RuntimeException("Abonnement introuvable"));
    }

    public void supprimerAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }
}