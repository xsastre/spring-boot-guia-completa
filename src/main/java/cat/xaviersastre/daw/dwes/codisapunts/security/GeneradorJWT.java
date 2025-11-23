package cat.xaviersastre.daw.dwes.codisapunts.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

/**
 * Generador de tokens JWT
 */
@Component
public class GeneradorJWT {
    
    @Value("${jwt.secret:clau-secreta-super-segura-de-minim-32-caracters-per-hsm256}")
    private String clauSecreta;
    
    @Value("${jwt.expiracio:86400000}")
    private long tempsExpiracio;
    
    /**
     * Genera un token JWT
     */
    public String generarToken(String usuari, Map<String, Object> claims) {
        Date ara = new Date();
        Date dataExpiracio = new Date(ara.getTime() + tempsExpiracio);
        
        SecretKey clau = Keys.hmacShaKeyFor(clauSecreta.getBytes());
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuari)
                .setIssuedAt(ara)
                .setExpiration(dataExpiracio)
                .signWith(clau, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Obté el nom d'usuari del token
     */
    public String obtenirUsuariDelToken(String token) {
        SecretKey clau = Keys.hmacShaKeyFor(clauSecreta.getBytes());
        
        return Jwts.parser()
                .verifyWith(clau)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    /**
     * Verifica si el token és vàlid
     */
    public boolean esTokenValid(String token) {
        try {
            SecretKey clau = Keys.hmacShaKeyFor(clauSecreta.getBytes());
            
            Jwts.parser()
                    .verifyWith(clau)
                    .build()
                    .parseSignedClaims(token);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}