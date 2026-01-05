package com.todolist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sentry-test")
    public String test() {
        throw new RuntimeException("TEST SENTRY SELF HOSTED");
    }

    /**
     * Endpoint qui contacte JSONPlaceholder
     */
    @GetMapping("/external-service")
    public ResponseEntity<Map<String, Object>> testJsonPlaceholder() {
        log.info("Appel de l'API JSONPlaceholder");
        
        try {
            String url = "https://jsonplaceholder.typicode.com/posts/1";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", response);
            result.put("message", "Appel réussi à JSONPlaceholder");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Erreur lors de l'appel à JSONPlaceholder: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'appel à JSONPlaceholder", e);
        }
    }

    /**
     * Endpoint qui provoque une division par zéro
     */
    @GetMapping("/error1")
    public ResponseEntity<String> testDivideByZero() {
        log.info("Test de division par zéro");
        
        int a = 10;
        int b = 0;
        int result = a / b; // ArithmeticException
        
        return ResponseEntity.ok("Résultat: " + result);
    }

    @GetMapping("/send-error-console")
    public String testSendErrorToConsole() {
        log.error("Test d'envoie d'erreur à la console");
        return "Erreur envoyée à la console";
    }

    /**
     * Endpoint qui gère une exception personnalisée
     */
    @GetMapping("/error2")
    public ResponseEntity<Map<String, String>> testHandledException() {
        log.info("Test d'exception gérée");
        
        try {
            // Simuler une opération qui échoue
            throw new IllegalArgumentException("Ceci est une exception gérée intentionnellement");
        } catch (IllegalArgumentException e) {
            log.error("Exception capturée et gérée: {}", e.getMessage(), e);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Une erreur a été gérée avec succès");
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Endpoint qui attend 3 secondes avant de répondre
     */
    @GetMapping("/slow-response")
    public ResponseEntity<Map<String, String>> testSlowResponse() {
        log.info("Test de réponse lente (3 secondes)");
        
        try {
            Thread.sleep(3000); // Attendre 3 secondes
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Réponse après 3 secondes d'attente");
            response.put("delay", "3000ms");
            
            log.info("Réponse lente complétée");
            return ResponseEntity.ok(response);
        } catch (InterruptedException e) {
            log.error("Thread interrompu pendant l'attente", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrompu", e);
        }
    }

    /**
     * Endpoint qui log simplement une erreur dans la console
     */
    @GetMapping("/log-error")
    public ResponseEntity<Map<String, String>> testLogError(@RequestParam(defaultValue = "Erreur de test") String message) {
        log.info("Test de logging d'erreur");
        
        // Simuler différents niveaux de log
        log.warn("ATTENTION: Ceci est un warning de test");
        log.error("ERREUR: {}", message);
        log.error("Stack trace simulée", new RuntimeException("Exception pour démonstration du log"));
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Erreur loggée avec succès dans la console");
        response.put("loggedMessage", message);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint qui provoque une NullPointerException
     */
    @GetMapping("/null-pointer")
    public ResponseEntity<String> testNullPointer() {
        log.info("Test de NullPointerException");
        
        String nullString = null;
        int length = nullString.length(); // NullPointerException
        
        return ResponseEntity.ok("Length: " + length);
    }

    /**
     * Endpoint qui provoque une exception avec un message personnalisé
     */
    @GetMapping("/custom-error")
    public ResponseEntity<String> testCustomError(@RequestParam(defaultValue = "Erreur personnalisée") String errorMessage) {
        log.info("Test d'erreur personnalisée avec message: {}", errorMessage);
        
        throw new RuntimeException("ERREUR CUSTOM: " + errorMessage);
    }

    /**
     * Endpoint de santé pour vérifier que le contrôleur fonctionne
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "Test controller is working");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        return ResponseEntity.ok(response);
    }
}
