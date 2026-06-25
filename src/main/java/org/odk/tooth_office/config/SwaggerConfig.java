package org.odk.tooth_office.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI toothOfficeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Tooth Office")
                        .description("Documentation de l'API de gestion et de prise de rendez-vous")
                        .version("1.0.0"));
    }
}