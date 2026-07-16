package org.odk.tooth_office.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String BEARER_AUTH_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI toothOfficeOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH_SCHEME, new SecurityScheme()
                                .name(BEARER_AUTH_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Saisissez ici le token JWT obtenu via /api/auth/login. Exemple : Bearer eyJhbGciOi...")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_SCHEME))
                .info(new Info()
                        .title("API Tooth Office")
                        .description("Documentation de l'API de gestion et de prise de rendez-vous avec authentification JWT")
                        .version("1.0.0"));
    }
}