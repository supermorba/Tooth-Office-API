package org.odk.tooth_office.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odk.tooth_office.Repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final DataSource dataSource;

    @Override
    public void run(String... args) {
        if (utilisateurRepository.count() == 0) {
            log.info("Base de données vide. Exécution du script de données initiales (data.sql)...");
            try {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                populator.addScript(new ClassPathResource("data.sql"));
                populator.execute(dataSource);
                log.info("Initialisation de la base de données terminée avec succès.");
            } catch (Exception e) {
                log.error("Erreur lors du premier chargement de data.sql : {}", e.getMessage(), e);
            }
        } else {
            log.info("La base de données contient déjà des données ({} utilisateur(s)). Saut de l'initialisation data.sql.", utilisateurRepository.count());
        }
    }
}
