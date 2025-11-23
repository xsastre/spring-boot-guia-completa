package cat.xaviersastre.daw.dwes.codisapunts.controller;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import cat.xaviersastre.daw.dwes.codisapunts.service.ServeiUsuaris;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Usuaris", description = "API per a la gestió d'usuaris")
public class ControladorUsuaris {

    private final ServeiUsuaris serveiUsuaris;

    public ControladorUsuaris(ServeiUsuaris serveiUsuaris) {
        this.serveiUsuaris = serveiUsuaris;
    }

    /**
     * Obté tots els usuaris
     */
    @Operation(summary = "Obté tots els usuaris", description = "Retorna una llista amb tots els usuaris del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Llista d'usuaris obtinguda correctament"),
            @ApiResponse(responseCode = "403", description = "No autoritzat - requereix rol ADMIN")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuari>> obtenirTots() {
        return ResponseEntity.ok(serveiUsuaris.obtenirTots());
    }

    /**
     * Obté un usuari per ID
     */
    @Operation(summary = "Obté un usuari per ID", description = "Retorna les dades d'un usuari específic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuari trobat"),
            @ApiResponse(responseCode = "404", description = "Usuari no trobat"),
            @ApiResponse(responseCode = "403", description = "No autoritzat")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Usuari> obtenirPerID(@PathVariable Long id) {
        return ResponseEntity.ok(serveiUsuaris.obtenirPerID(id));
    }

    /**
     * Crea un nou usuari
     */
    @Operation(summary = "Crea un nou usuari", description = "Crea un nou usuari al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuari creat correctament"),
            @ApiResponse(responseCode = "400", description = "Dades incorrectes")
    })
    @PostMapping
    public ResponseEntity<Usuari> crear(@RequestBody Usuari usuari) {
        Usuari nouUsuari = serveiUsuaris.guardar(usuari);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouUsuari);
    }

    /**
     * Actualitza un usuari existent
     */
    @Operation(summary = "Actualitza un usuari", description = "Actualitza les dades d'un usuari existent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuari actualitzat correctament"),
            @ApiResponse(responseCode = "404", description = "Usuari no trobat"),
            @ApiResponse(responseCode = "403", description = "No autoritzat - requereix rol ADMIN")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuari> actualitzar(@PathVariable Long id, @RequestBody Usuari usuari) {
        return ResponseEntity.ok(serveiUsuaris.actualitzar(id, usuari));
    }

    /**
     * Elimina un usuari
     */
    @Operation(summary = "Elimina un usuari", description = "Elimina un usuari del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuari eliminat correctament"),
            @ApiResponse(responseCode = "404", description = "Usuari no trobat"),
            @ApiResponse(responseCode = "403", description = "No autoritzat - requereix rol ADMIN")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        serveiUsuaris.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cerca usuaris pel nom
     */
    @Operation(summary = "Cerca usuaris pel nom", description = "Cerca usuaris que continguin el nom especificat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerca realitzada correctament"),
            @ApiResponse(responseCode = "403", description = "No autoritzat")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/cerca")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Usuari>> cercar(@RequestParam String nom) {
        return ResponseEntity.ok(serveiUsuaris.cercar(nom));
    }

    /**
     * Obté els usuaris actius
     */
    @Operation(summary = "Obté els usuaris actius", description = "Retorna una llista amb els usuaris actius")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Llista d'usuaris actius obtinguda correctament"),
            @ApiResponse(responseCode = "403", description = "No autoritzat - requereix rol ADMIN")
    })
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/actius")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuari>> obtenirActius() {
        return ResponseEntity.ok(serveiUsuaris.obtenirUsuarisActius());
    }
}
