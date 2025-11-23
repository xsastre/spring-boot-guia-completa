package cat.xaviersastre.daw.dwes.codisapunts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import cat.xaviersastre.daw.dwes.codisapunts.security.FiltreJWT;

/**
 * Configuració de Spring Security amb JWT
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracioSeguretat {
    
    private final FiltreJWT filtreJWT;
    private final UserDetailsService userDetailsService;
    
    public ConfiguracioSeguretat(FiltreJWT filtreJWT, UserDetailsService userDetailsService) {
        this.filtreJWT = filtreJWT;
        this.userDetailsService = userDetailsService;
    }
    
    /**
     * Codificador de contrasenyes amb BCrypt
     */
    @Bean
    public PasswordEncoder codificadorContrasenya() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Proveïdor d'autenticació DAO
     */
    @Bean
    public DaoAuthenticationProvider proveïdorAutenticacio() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(codificadorContrasenya());
        return provider;
    }
    
    /**
     * Manager d'autenticació
     */
    @Bean
    public AuthenticationManager managerAutenticacio(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Cadena de filtres de seguretat
     */
    @Bean
    public SecurityFilterChain cadenaFiltreSeguretat(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/h2-console/**").permitAll()
                .requestMatchers("/web/**", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(filtreJWT, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(proveïdorAutenticacio());
        
        return http.build();
    }
}