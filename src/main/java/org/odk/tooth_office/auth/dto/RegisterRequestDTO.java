package org.odk.tooth_office.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.odk.tooth_office.Enum.RoleEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Le numéro de téléphone doit être valide")
    private String telephone;

    private String adresse;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleEnum role;

    // Champs spécifiques selon le rôle
    private LocalDate dateNaissance; // Pour le Patient
    private List<Integer> cabinetIds; // Pour le Chef de Cabinet
}
