package com.veterinaria.presentation.controller;

import com.veterinaria.application.service.notification.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * Controlador temporal para probar funcionalidad de emails
 * ELIMINAR EN PRODUCCIÓN
 */
@Slf4j
@RestController
@RequestMapping("/api/test/email")
@RequiredArgsConstructor
public class TestEmailController {

    private final EmailService emailService;

    /**
     * Endpoint de prueba para verificar envío de emails
     * GET /api/test/email/send?destinatario=email@example.com
     */
    @GetMapping("/send")
    public ResponseEntity<Map<String, String>> testEmailSend(
            @RequestParam String destinatario) {

        log.info("TEST: Intentando enviar email de prueba a: {}", destinatario);

        try {
            emailService.notificarCitaCreada(
                destinatario,
                "Cliente de Prueba",
                "Max (Perro de prueba)",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                "Consulta General",
                "test-token-123456"
            );

            log.info("TEST: Email enviado (o encolado para envío asíncrono)");

            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Email encolado para envío asíncrono. Revisa los logs del servidor y tu bandeja de Mailtrap.",
                "destinatario", destinatario,
                "nota", "Si no ves el email en los logs, puede haber un problema con la configuración SMTP"
            ));

        } catch (Exception e) {
            log.error("TEST: Error al enviar email: {}", e.getMessage(), e);

            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "Error al enviar email: " + e.getMessage(),
                "destinatario", destinatario
            ));
        }
    }
}
