package cat.xaviersastre.daw.dwes.codisapunts.service;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import cat.xaviersastre.daw.dwes.codisapunts.repository.RepositoriUsuaris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import cat.xaviersastre.daw.dwes.codisapunts.exception.UsuariNoTrobatException;
import java.util.List;

/**
 * Servei de lògica de negoci per a la gestió d'usuaris
 */
@Service
public class ServeiUsuaris {
    
    private static final Logger logger = LoggerFactory.getLogger(ServeiUsuaris.class);
    
    private final RepositoriUsuaris repositori;
    private final PasswordEncoder passwordEncoder;
    
    public ServeiUsuaris(RepositoriUsuaris repositori, PasswordEncoder passwordEncoder) {
        this.repositori = repositori;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Obté tots els usuaris
     */
    public List<Usuari> obtenirTots() {
        logger.info("Obtenint tots els usuaris");
        return repositori.findAll();
    }
    
    /**
     * Obté un usuari per ID
     */
    public Usuari obtenirPerID(Long id) {
        logger.info("Obtenint usuari amb ID: {}", id);
        return repositori.findById(id)
                .orElseThrow(() -> new UsuariNoTrobatException("ID: " + id));
    }
    
    /**
     * Obté un usuari per mail
     */
    public Usuari obtenirPerMail(String mail) {
        logger.info("Obtenint usuari amb mail: {}", mail);
        return repositori.findByMail(mail)
                .orElseThrow(() -> new UsuariNoTrobatException("Mail: " + mail));
    }
    
    /**
     * Guarda un nou usuari
     */
    @Transactional
    public Usuari guardar(Usuari usuari) {
        logger.info("Intentant guardar usuari amb mail: {}", usuari.getMail());
        
        if (repositori.existsByMail(usuari.getMail())) {
            logger.warn("Intentat guardar usuari amb mail que ja existeix: {}", usuari.getMail());
            throw new IllegalArgumentException("El mail ja existeix");
        }
        
        usuari.setContrasenya(passwordEncoder.encode(usuari.getContrasenya()));
        Usuari usuariGuardat = repositori.save(usuari);
        logger.info("cat.xaviersastre.daw.dwes.codisapunts.model.Usuari guardat correctament amb ID: {}", usuariGuardat.getId());
        return usuariGuardat;
    }
    
    /**
     * Actualitza un usuari existent
     */
    @Transactional
    public Usuari actualitzar(Long id, Usuari usuari) {
        logger.info("Actualitzant usuari amb ID: {}", id);
        
        Usuari usuariExistent = obtenirPerID(id);
        usuariExistent.setNom(usuari.getNom());
        usuariExistent.setRol(usuari.getRol());
        
        Usuari usuariActualitzat = repositori.save(usuariExistent);
        logger.info("cat.xaviersastre.daw.dwes.codisapunts.model.Usuari actualitzat correctament");
        return usuariActualitzat;
    }
    
    /**
     * Elimina un usuari
     */
    @Transactional
    public void eliminar(Long id) {
        logger.info("Eliminant usuari amb ID: {}", id);
        
        if (!repositori.existsById(id)) {
            throw new UsuariNoTrobatException("ID: " + id);
        }
        
        repositori.deleteById(id);
        logger.info("cat.xaviersastre.daw.dwes.codisapunts.model.Usuari eliminat correctament");
    }
    
    /**
     * Cerca usuaris pel nom
     */
    public List<Usuari> cercar(String nom) {
        logger.info("Cercant usuaris amb nom: {}", nom);
        return repositori.findByNomContainingIgnoreCase(nom);
    }
    
    /**
     * Obté els usuaris actius
     */
    public List<Usuari> obtenirUsuarisActius() {
        logger.info("Obtenint usuaris actius");
        return repositori.obtenirUsuarisActius();
    }
}