package cat.xaviersastre.daw.dwes.codisapunts.controller;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import cat.xaviersastre.daw.dwes.codisapunts.service.ServeiUsuaris;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST per a la gestió d'usuaris
 */
@RestController
@RequestMapping("/api/usuaris")
public class ControladorUsuaris {

    private final ServeiUsuaris serveiUsuaris;

    public ControladorUsuaris(ServeiUsuaris serveiUsuaris) {
        this.serveiUsuaris = serveiUsuaris;
    }

    /**
     * Obté tots els usuaris
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuari>> obtenirTots() {
        return ResponseEntity.ok(serveiUsuaris.obtenirTots());
    }

    /**
     * Obté un usuari per ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Usuari> obtenirPerID(@PathVariable Long id) {
        return ResponseEntity.ok(serveiUsuaris.obtenirPerID(id));
    }

    /**
     * Crea un nou usuari
     */
    @PostMapping
    public ResponseEntity<Usuari> crear(@RequestBody Usuari usuari) {
        Usuari nouUsuari = serveiUsuaris.guardar(usuari);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouUsuari);
    }

    /**
     * Actualitza un usuari existent
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuari> actualitzar(@PathVariable Long id, @RequestBody Usuari usuari) {
        return ResponseEntity.ok(serveiUsuaris.actualitzar(id, usuari));
    }

    /**
     * Elimina un usuari
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        serveiUsuaris.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cerca usuaris pel nom
     */
    @GetMapping("/cerca")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Usuari>> cercar(@RequestParam String nom) {
        return ResponseEntity.ok(serveiUsuaris.cercar(nom));
    }

    /**
     * Obté els usuaris actius
     */
    @GetMapping("/actius")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuari>> obtenirActius() {
        return ResponseEntity.ok(serveiUsuaris.obtenirUsuarisActius());
    }
}
