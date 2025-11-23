package cat.xaviersastre.daw.dwes.codisapunts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuració de Swagger/OpenAPI per documentar l'API REST
 */
@Configuration
public class ConfiguracioSwagger {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Spring Boot Guia Completa")
                        .version("1.0.0")
                        .description("API REST per a la gestió d'usuaris amb autenticació JWT")
                        .contact(new Contact()
                                .name("Xavier Sastre")
                                .email("xavier@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT per autenticar les peticions")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
