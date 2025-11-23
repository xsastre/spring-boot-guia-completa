package cat.xaviersastre.daw.dwes.codisapunts.mapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * Gestor centralitzat d'excepcions per a tota l'aplicació
 */
@RestControllerAdvice
public class ManejadorExcepcions {
    
    /**
     * Maneja excepcions de tipus cat.xaviersastre.daw.dwes.codisapunts.exception.UsuariNoTrobatException
     */
    @ExceptionHandler(UsuariNoTrobatException.class)
    public ResponseEntity<?> manejadorUsuariNoTrobat(
            UsuariNoTrobatException e,
            WebRequest request) {
        
        Map<String, Object> resposta = crearRespostaError(
                "USUARI_NO_TROBAT",
                e.getMessage(),
                404
        );
        
        return ResponseEntity.status(404).body(resposta);
    }
    
    /**
     * Maneja excepcions d'arguments invàlids
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> manejadorArgumentInvalid(
            IllegalArgumentException e,
            WebRequest request) {
        
        Map<String, Object> resposta = crearRespostaError(
                "ARGUMENT_INVALID",
                e.getMessage(),
                400
        );
        
        return ResponseEntity.status(400).body(resposta);
    }
    
    /**
     * Maneja excepcions genèriques
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejadorExcepcioGenerica(
            Exception e,
            WebRequest request) {
        
        Map<String, Object> resposta = crearRespostaError(
                "ERROR_INTERN",
                "S'ha produït un error intern al servidor",
                500
        );
        
        return ResponseEntity.status(500).body(resposta);
    }
    
    /**
     * Crea una estructura estàndard de resposta d'error
     */
    private Map<String, Object> crearRespostaError(String codi, String missatge, int status) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("codi", codi);
        resposta.put("missatge", missatge);
        resposta.put("status", status);
        resposta.put("timestamp", LocalDateTime.now());
        return resposta;
    }
}