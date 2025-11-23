package cat.xaviersastre.daw.dwes.codisapunts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entitat cat.xaviersastre.daw.dwes.codisapunts.model.Usuari - Representa una taula d'usuaris a la base de dades
 */
@Entity
@Table(name = "usuaris")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuari {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String mail;
    
    @Column(nullable = false, length = 150)
    private String nom;
    
    @Column(nullable = false)
    private String contrasenya;
    
    @Column(name = "data_creacio")
    private LocalDateTime dataCreacio;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean actiu;
    
    /**
     * Callback que s'executa abans de guardar l'entitat
     */
    @PrePersist
    protected void onCreate() {
        dataCreacio = LocalDateTime.now();
        actiu = true;
        rol = Rol.USER; // cat.xaviersastre.daw.dwes.codisapunts.model.Rol per defecte
    }
}