package org.odk.tooth_office.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads/logos}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path basePath = Paths.get("").toAbsolutePath();
        if (!basePath.getFileName().toString().equals("backend") && Files.exists(basePath.resolve("backend"))) {
            basePath = basePath.resolve("backend");
        }
        Path uploadPath = basePath.resolve("uploads").normalize();

        String uploadAbsolutePath = uploadPath.toUri().toString();
        if (!uploadAbsolutePath.endsWith("/")) {
            uploadAbsolutePath += "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadAbsolutePath);
    }
}
