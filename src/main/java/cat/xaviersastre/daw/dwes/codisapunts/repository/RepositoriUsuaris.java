package cat.xaviersastre.daw.dwes.codisapunts.repository;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Repositori per a la gestió de la persistència de l'entitat cat.xaviersastre.daw.dwes.codisapunts.model.Usuari
 */
@Repository
public interface RepositoriUsuaris extends JpaRepository<Usuari, Long> {
    
    /**
     * Busca un usuari pel seu mail
     */
    Optional<Usuari> findByMail(String mail);
    
    /**
     * Busca usuaris pel nom (ignorant majúscules i minúscules)
     */
    List<Usuari> findByNomContainingIgnoreCase(String nom);
    
    /**
     * Verifica si existeix un usuari amb el mail indicat
     */
    boolean existsByMail(String mail);
    
    /**
     * Consulta personalitzada: obtenir usuaris actius ordenats per nom
     */
    @Query("SELECT u FROM Usuari u WHERE u.actiu = true ORDER BY u.nom")
    List<Usuari> obtenirUsuarisActius();
    
    /**
     * Consulta nativa: obtenir usuaris per rol
     */
    @Query(value = "SELECT * FROM usuaris WHERE rol = ?1", nativeQuery = true)
    List<Usuari> obtenirUsuarisPer(String rol);
    
    /**
     * Comptar el nombre d'usuaris actius
     */
    long countByActiuTrue();
}