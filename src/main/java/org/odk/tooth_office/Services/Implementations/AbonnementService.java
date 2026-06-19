package org.odk.tooth_office.Services.Implementations;

import org.odk.tooth_office.DTO.AbonnementDTO;
import org.odk.tooth_office.Entity.Abonnement;
import org.odk.tooth_office.Enum.EtatAbonnement;
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

    // CORRECTION : Le type de retour est bien Abonnement, pas AbonnementService
    public Abonnement creerAbonnement(AbonnementDTO dto) {
        Abonnement abonnement = new Abonnement(
                dto.getDateDebut(), dto.getDateFin(), dto.getEtatAbonnement(),
                dto.getTypePaiement(), dto.getMontantTotal(), dto.getIdPlan(), dto.getIdCabinet()
        );
        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> recupererTous() {
        return abonnementRepository.findAll();
    }

    public Optional<Abonnement> recupererParId(Integer id) {
        return abonnementRepository.findById(id);
    }

    public List<Abonnement> recupererParCabinet(Integer idCabinet) {
        return abonnementRepository.findByIdCabinet(idCabinet);
    }

    public List<Abonnement> recupererParPlan(Integer idPlan) {
        return abonnementRepository.findByIdPlan(idPlan);
    }

    public Abonnement modifierAbonnement(Integer id, AbonnementDTO dto) {
        return abonnementRepository.findById(id).map(existing -> {
            existing.setDateDebut(dto.getDateDebut());
            existing.setDateFin(dto.getDateFin());
            existing.setEtatAbonnement(dto.getEtatAbonnement());
            existing.setTypePaiement(dto.getTypePaiement());
            existing.setMontantTotal(dto.getMontantTotal());
            existing.setIdPlan(dto.getIdPlan());
            existing.setIdCabinet(dto.getIdCabinet());
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