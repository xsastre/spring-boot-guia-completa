package cat.xaviersastre.daw.dwes.codisapunts.controller;

import cat.xaviersastre.daw.dwes.codisapunts.dto.CredencialLogin;
import cat.xaviersastre.daw.dwes.codisapunts.dto.RespostaLogin;
import cat.xaviersastre.daw.dwes.codisapunts.security.GeneradorJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador per a la gestió d'autenticació
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticació", description = "API per a l'autenticació d'usuaris")
public class ControladorAutenticacio {

    private final AuthenticationManager authenticationManager;
    private final GeneradorJWT generadorJWT;

    public ControladorAutenticacio(AuthenticationManager authenticationManager, 
                                   GeneradorJWT generadorJWT) {
        this.authenticationManager = authenticationManager;
        this.generadorJWT = generadorJWT;
    }

    /**
     * Endpoint per fer login i obtenir un token JWT
     */
    @Operation(summary = "Autenticació d'usuari", description = "Autentica un usuari i retorna un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticació correcta - retorna token JWT"),
            @ApiResponse(responseCode = "401", description = "Credencials incorrectes")
    })
    @PostMapping("/login")
    public ResponseEntity<RespostaLogin> login(@Valid @RequestBody CredencialLogin credencials) {
        // Autenticar l'usuari
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credencials.getMail(),
                        credencials.getContrasenya()
                )
        );

        // Generar el token JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", authentication.getAuthorities());
        
        String token = generadorJWT.generarToken(authentication.getName(), claims);

        return ResponseEntity.ok(new RespostaLogin(token, "Login correcte"));
    }

    /**
     * Endpoint per verificar que el servidor està actiu
     */
    @Operation(summary = "Estat del servidor", description = "Verifica que el servidor està actiu i funcionant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor actiu")
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("status", "UP");
        resposta.put("missatge", "El servidor està funcionant correctament");
        return ResponseEntity.ok(resposta);
    }
}
