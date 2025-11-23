package cat.xaviersastre.daw.dwes.codisapunts.controller;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import cat.xaviersastre.daw.dwes.codisapunts.service.ServeiUsuaris;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador web per a les pàgines Thymeleaf
 */
@Controller
@RequestMapping("/web")
public class ControladorWeb {

    private final ServeiUsuaris serveiUsuaris;

    public ControladorWeb(ServeiUsuaris serveiUsuaris) {
        this.serveiUsuaris = serveiUsuaris;
    }

    /**
     * Pàgina d'inici amb el llistat d'usuaris
     */
    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("usuaris", serveiUsuaris.obtenirTots());
        } catch (Exception e) {
            model.addAttribute("usuaris", java.util.Collections.emptyList());
            model.addAttribute("error", "No s'han pogut carregar els usuaris. Assegura't que la base de dades està configurada.");
        }
        return "index";
    }

    /**
     * Formulari per crear un nou usuari
     */
    @GetMapping("/nou")
    public String nouUsuari(Model model) {
        model.addAttribute("usuari", new Usuari());
        model.addAttribute("accio", "Crear");
        return "formulari-usuari";
    }

    /**
     * Formulari per editar un usuari existent
     */
    @GetMapping("/editar/{id}")
    public String editarUsuari(@PathVariable Long id, Model model) {
        try {
            Usuari usuari = serveiUsuaris.obtenirPerID(id);
            model.addAttribute("usuari", usuari);
            model.addAttribute("accio", "Editar");
            return "formulari-usuari";
        } catch (Exception e) {
            model.addAttribute("error", "Usuari no trobat");
            return "redirect:/web";
        }
    }

    /**
     * Processa el formulari per guardar un usuari
     */
    @PostMapping("/guardar")
    public String guardarUsuari(@ModelAttribute Usuari usuari) {
        try {
            if (usuari.getId() != null) {
                serveiUsuaris.actualitzar(usuari.getId(), usuari);
            } else {
                serveiUsuaris.guardar(usuari);
            }
        } catch (Exception e) {
            // En cas d'error, redirigeix igualment
        }
        return "redirect:/web";
    }

    /**
     * Elimina un usuari
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuari(@PathVariable Long id) {
        try {
            serveiUsuaris.eliminar(id);
        } catch (Exception e) {
            // En cas d'error, redirigeix igualment
        }
        return "redirect:/web";
    }
}
