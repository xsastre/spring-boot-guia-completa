package cat.xaviersastre.daw.dwes.codisapunts.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filtre JWT que intercepta les peticions HTTP per validar tokens
 * S'executa una vegada per petició
 */
@Component
public class FiltreJWT extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(FiltreJWT.class);
    
    private final GeneradorJWT generadorJWT;
    private final UserDetailsService userDetailsService;
    
    public FiltreJWT(GeneradorJWT generadorJWT, UserDetailsService userDetailsService) {
        this.generadorJWT = generadorJWT;
        this.userDetailsService = userDetailsService;
    }
    
    /**
     * Mètode principal del filtre que s'executa per a cada petició
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String token = extraureToken(request);
            
            if (token != null && generadorJWT.esTokenValid(token)) {
                String usuari = generadorJWT.obtenirUsuariDelToken(token);
                
                logger.debug("Token vàlid per a l'usuari: {}", usuari);
                
                // Carrega els detalls de l'usuari desde la base de dades
                UserDetails userDetails = userDetailsService.loadUserByUsername(usuari);
                
                // Crea un token d'autenticació
                UsernamePasswordAuthenticationToken autenticacio =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                
                // Estableix l'autenticació al context de seguretat
                SecurityContextHolder.getContext().setAuthentication(autenticacio);
                
                logger.debug("Autenticació establerta per a l'usuari: {}", usuari);
            } else if (token != null) {
                logger.warn("Token invàlid o expirat");
            }
        } catch (Exception e) {
            logger.error("No es pot establir autenticació de l'usuari", e);
        }
        
        // Continua amb la cadena de filtres
        filterChain.doFilter(request, response);
    }
    
    /**
     * Extreu el token JWT de la capçalera Authorization
     * Format espertat: "Bearer <token>"
     * 
     * @param request la petició HTTP
     * @return el token JWT sense "Bearer " o null si no existeix
     */
    private String extraureToken(HttpServletRequest request) {
        String capçalera = request.getHeader("Authorization");
        
        if (capçalera != null && capçalera.startsWith("Bearer ")) {
            logger.debug("Token trobat a la capçalera Authorization");
            return capçalera.substring(7); // Elimina "Bearer "
        }
        
        return null;
    }
    
    /**
     * Determina si el filtre s'ha d'executar per a una petició específica
     * Per defecte s'executa per a totes les peticions
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String ruta = request.getRequestURI();
        
        // No aplicar filtre a les rutes públiques
        return ruta.startsWith("/api/auth/") || 
               ruta.startsWith("/swagger-ui") || 
               ruta.startsWith("/v3/api-docs");
    }
}