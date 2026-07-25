package org.odk.tooth_office.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DossierMedicalDTO {
    @Schema(description = "Identifiant unique du dossier médical", example = "7")
    private Long id;

    @Schema(description = "Historique médical ou dentaire du patient", example = "Extraction d'une molaire en 2024")
    private String historiques;

    @NotNull
    @Schema(description = "Identifiant du patient associé au dossier", example = "5")
    private Long patientId;

    @Valid
    @Schema(description = "Allergies et intolérances (alerte rouge si gravité SEVERE)")
    private List<AllergieIntoleranceDTO> allergiesIntolerances = new ArrayList<>();

    @Valid
    @Schema(description = "Antécédents médicaux, chirurgicaux, familiaux et habitudes de vie")
    private List<AntecedentDTO> antecedents = new ArrayList<>();

    @Valid
    @Schema(description = "Pathologies chroniques et ALD")
    private List<PathologieChroniqueDTO> pathologiesChroniques = new ArrayList<>();

    @Valid
    @Schema(description = "Traitements médicamenteux en cours avec posologie")
    private List<MedicamentEnCoursDTO> medicamentsEnCours = new ArrayList<>();

    @Schema(description = "Indique la présence d'une allergie sévère nécessitant une alerte visuelle prioritaire")
    private boolean alerteAllergie;
}
