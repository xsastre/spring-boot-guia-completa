package cat.xaviersastre.daw.dwes.codisapunts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

/**
 * DTO per a les credencials de login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredencialLogin {
    
    @Email(message = "El format del mail és invàlid")
    @NotBlank(message = "El mail no pot estar buit")
    private String mail;
    
    @NotBlank(message = "La contrasenya no pot estar buida")
    @Size(min = 6, message = "La contrasenya ha de tindre almenys 6 caràcters")
    private String contrasenya;
}