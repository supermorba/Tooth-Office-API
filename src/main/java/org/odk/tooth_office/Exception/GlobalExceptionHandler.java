package org.odk.tooth_office.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Intercepte les IllegalArgumentException (ressource introuvable)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

//    @ExceptionHandler(AvisNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Map<String, String>> handleAvisNotFound(AvisNotFoundException ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("message", ex.getMessage());
//        error.put("status", "NOT_FOUND");
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

    // Intercepte toutes les autres exceptions non prévues
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", 500,
                "message", "Erreur interne : " + ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }
}
