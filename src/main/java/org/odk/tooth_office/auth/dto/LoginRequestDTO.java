package org.odk.tooth_office.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    /**
     * Identifiant de connexion : email ou numéro de téléphone.
     * L'un des deux doit être fourni, mais pas forcément les deux.
     */
    @Email(message = "L'email doit être valide si fourni")
    private String email;

    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}