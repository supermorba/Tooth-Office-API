package org.odk.tooth_office.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public String encodeIfNeeded(String rawOrEncodedPassword) {
        if (rawOrEncodedPassword == null || rawOrEncodedPassword.isBlank()) {
            return rawOrEncodedPassword;
        }

        if (rawOrEncodedPassword.startsWith("$2a$")
                || rawOrEncodedPassword.startsWith("$2b$")
                || rawOrEncodedPassword.startsWith("$2y$")) {
            return rawOrEncodedPassword;
        }

        return passwordEncoder.encode(rawOrEncodedPassword);
    }

    public boolean matches(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null) {
            return false;
        }

        if (storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$")) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }

        return rawPassword.equals(storedPassword);
    }

    public boolean needsRehash(String storedPassword) {
        return storedPassword != null
                && !storedPassword.isBlank()
                && !(storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$"));
    }
}