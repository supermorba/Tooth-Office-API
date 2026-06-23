package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.AbonnementDTO;
import org.odk.tooth_office.Entity.Abonnement;
import org.odk.tooth_office.Entity.Cabinet;
import org.odk.tooth_office.Entity.PlanAbonnement;
import org.odk.tooth_office.Enum.EtatAbonnement;
import org.odk.tooth_office.Enum.TypePaiement;
import org.odk.tooth_office.Repository.AbonnementRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;

    public AbonnementService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    public Abonnement creerAbonnement(AbonnementDTO dto) {
        PlanAbonnement plan = new PlanAbonnement();
        // Conversion de Integer vers Long
        if (dto.getIdPlan() != null) {
            plan.setIdPlan(dto.getIdPlan().longValue());
        }

        Cabinet cabinet = new Cabinet();
        // Conversion de Integer vers int
        if (dto.getIdCabinet() != null) {
            cabinet.setIdCabinet(dto.getIdCabinet());
        }

        Abonnement abonnement = new Abonnement(
                dto.getDateDebut(), dto.getDateFin(), dto.getEtatAbonnement(),
                dto.getTypePaiement(), dto.getMontantTotal(), plan, cabinet
        );
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> recupererTous() {
        return abonnementRepository.findAll();
    }

    public Optional<Abonnement> recupererParId(Integer id) {
        return abonnementRepository.findById(id);
    }

    public List<Abonnement> recupererParCabinet(int idCabinet) {
        return abonnementRepository.findByCabinet_IdCabinet(idCabinet);
    }

    public List<Abonnement> recupererParPlan(Long idPlan) {
        return abonnementRepository.findByPlanAbonnement_IdPlan(idPlan);
    }

    public Abonnement modifierAbonnement(Integer id, AbonnementDTO dto) {
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

            return abonnementRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Abonnement introuvable"));
    }

    public Abonnement changerStatut(Integer id, EtatAbonnement nouveauStatut) {
        return abonnementRepository.findById(id).map(existing -> {
            existing.setEtatAbonnement(nouveauStatut);
            return abonnementRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Abonnement introuvable"));
    }

    public void supprimerAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }
}