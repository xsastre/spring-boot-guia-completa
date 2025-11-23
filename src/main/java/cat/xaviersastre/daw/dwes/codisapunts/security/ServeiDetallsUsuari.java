package cat.xaviersastre.daw.dwes.codisapunts.security;

import cat.xaviersastre.daw.dwes.codisapunts.model.Usuari;
import cat.xaviersastre.daw.dwes.codisapunts.repository.RepositoriUsuaris;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementació de UserDetailsService per a Spring Security
 * Carrega els detalls de l'usuari des de la base de dades
 */
@Service
public class ServeiDetallsUsuari implements UserDetailsService {

    private final RepositoriUsuaris repositoriUsuaris;

    public ServeiDetallsUsuari(RepositoriUsuaris repositoriUsuaris) {
        this.repositoriUsuaris = repositoriUsuaris;
    }

    /**
     * Carrega un usuari pel seu nom d'usuari (mail en aquest cas)
     */
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuari usuari = repositoriUsuaris.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuari no trobat amb mail: " + mail));

        return User.builder()
                .username(usuari.getMail())
                .password(usuari.getContrasenya())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuari.getRol().name())))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!usuari.getActiu())
                .build();
    }
}
