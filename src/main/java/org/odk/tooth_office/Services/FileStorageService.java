package org.odk.tooth_office.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path uploadStorageLocation;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "webp", "gif", "svg");

    public FileStorageService(@Value("${app.upload.dir:uploads/logos}") String uploadDir) {
        Path basePath = Paths.get("").toAbsolutePath();
        Path backendDir;
        if (basePath.getFileName() != null && basePath.getFileName().toString().equals("backend")) {
            backendDir = basePath;
        } else if (Files.exists(basePath.resolve("backend"))) {
            backendDir = basePath.resolve("backend");
        } else {
            backendDir = basePath;
        }
        this.uploadStorageLocation = backendDir.resolve("uploads").resolve("logos").normalize();
        try {
            Files.createDirectories(this.uploadStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le dossier de stockage pour les logos dans le backend.", e);
        }
    }

    /**
     * Enregistre un fichier de logo pour un cabinet donné.
     * Supprime le précédent logo s'il existe.
     *
     * @param idCabinet identifiant du cabinet
     * @param file fichier image reçu
     * @return le chemin relatif du fichier sauvegardé (ex: uploads/logos/cabinet_1_17000000.png)
     */
    public String storeCabinetLogo(Integer idCabinet, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier image ne peut pas être vide.");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = getFileExtension(originalFilename).toLowerCase();

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !ALLOWED_EXTENSIONS.contains(extension))) {
            throw new IllegalArgumentException("Seuls les fichiers image (PNG, JPG, WEBP, GIF, SVG) sont autorisés.");
        }

        // Supprime les anciens fichiers de logo pour ce cabinet
        deleteExistingCabinetLogos(idCabinet);

        String newFilename = "cabinet_" + idCabinet + "_" + System.currentTimeMillis() + (extension.isEmpty() ? ".png" : "." + extension);

        try {
            if (newFilename.contains("..")) {
                throw new IllegalArgumentException("Nom de fichier invalide.");
            }

            Path targetLocation = this.uploadStorageLocation.resolve(newFilename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return "uploads/logos/" + newFilename;
        } catch (IOException ex) {
            throw new RuntimeException("Échec de l'enregistrement de l'image du logo.", ex);
        }
    }

    /**
     * Charge une ressource image de logo à partir de son nom de fichier ou de son chemin relatif.
     *
     * @param pathOrFilename nom du fichier ou chemin relatif
     * @return Resource image
     */
    public Resource loadAsResource(String pathOrFilename) {
        try {
            String filename = extractFilename(pathOrFilename);
            Path filePath = this.uploadStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Fichier de logo introuvable ou illisible : " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Fichier de logo introuvable.", ex);
        }
    }

    /**
     * Supprime le fichier de logo correspondant au chemin relatif.
     *
     * @param pathOrFilename chemin relatif ou nom de fichier
     */
    public void deleteFile(String pathOrFilename) {
        if (pathOrFilename == null || pathOrFilename.isBlank()) {
            return;
        }
        try {
            String filename = extractFilename(pathOrFilename);
            Path filePath = this.uploadStorageLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) {
        }
    }

    /**
     * Détermine le Content-Type HTTP pour une image.
     */
    public String determineContentType(String filename) {
        String ext = getFileExtension(filename).toLowerCase();
        return switch (ext) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            case "svg" -> "image/svg+xml";
            default -> "application/octet-stream";
        };
    }

    private void deleteExistingCabinetLogos(Integer idCabinet) {
        try {
            String prefix = "cabinet_" + idCabinet + "_";
            File folder = this.uploadStorageLocation.toFile();
            File[] matchingFiles = folder.listFiles((dir, name) -> name.startsWith(prefix));
            if (matchingFiles != null) {
                for (File f : matchingFiles) {
                    f.delete();
                }
            }
        } catch (Exception ignored) {
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private String extractFilename(String pathOrFilename) {
        if (pathOrFilename == null) return "";
        int lastSlash = pathOrFilename.lastIndexOf('/');
        if (lastSlash != -1) {
            return pathOrFilename.substring(lastSlash + 1);
        }
        return pathOrFilename;
    }
}
