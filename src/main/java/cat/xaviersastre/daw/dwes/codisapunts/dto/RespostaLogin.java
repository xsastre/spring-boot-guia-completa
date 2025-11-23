package cat.xaviersastre.daw.dwes.codisapunts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO per a la resposta de login amb el token JWT
 */
@Data
@AllArgsConstructor
public class RespostaLogin {
    
    private String token;
    private String missatge;
    
    public RespostaLogin(String token) {
        this.token = token;
        this.missatge = "Login correcte";
    }
}