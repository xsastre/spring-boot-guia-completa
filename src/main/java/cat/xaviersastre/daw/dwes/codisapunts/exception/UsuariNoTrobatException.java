package cat.xaviersastre.daw.dwes.codisapunts.exception;

/**
 * Excepció personalitzada per quan un usuari no es troba
 */
public class UsuariNoTrobatException extends RuntimeException {
    
    public UsuariNoTrobatException(String message) {
        super(message);
    }
    
    public UsuariNoTrobatException(String message, Throwable cause) {
        super(message, cause);
    }
}