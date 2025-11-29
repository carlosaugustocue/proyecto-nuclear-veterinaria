package com.veterinaria.application.service.notification;

import com.veterinaria.domain.enums.TipoNotificacion;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Servicio para envío de notificaciones por email.
 *
 * @author Sistema Veterinaria
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.nombre:Clínica Veterinaria}")
    private String nombreClinica;

    /**
     * Envía un email de forma asíncrona
     */
    @Async
    public void enviarEmail(String destinatario, String asunto, String contenidoHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            mailSender.send(message);
            log.info("Email enviado exitosamente a: {}", destinatario);

        } catch (MessagingException e) {
            log.error("Error al enviar email a {}: {}", destinatario, e.getMessage());
        }
    }

    /**
     * Notificación de cita creada (con link de confirmación)
     */
    public void notificarCitaCreada(String emailCliente, String nombreCliente,
                                      String nombrePaciente, LocalDate fecha,
                                      LocalTime hora, String tipoServicio, String token) {
        String asunto = "Confirmación de Cita - " + nombreClinica;
        String contenido = crearTemplateCitaCreada(nombreCliente, nombrePaciente,
                fecha, hora, tipoServicio, token);
        enviarEmail(emailCliente, asunto, contenido);
    }

    /**
     * Notificación al veterinario de que una cita fue confirmada por el cliente
     */
    public void notificarVeterinarioCitaConfirmada(String emailVeterinario, String nombreVeterinario,
                                                     String nombreCliente, String nombrePaciente,
                                                     LocalDate fecha, LocalTime hora) {
        String asunto = "Cita Confirmada - " + nombreClinica;
        String contenido = crearTemplateVeterinarioCitaConfirmada(nombreVeterinario,
                nombreCliente, nombrePaciente, fecha, hora);
        enviarEmail(emailVeterinario, asunto, contenido);
    }

    /**
     * Notificación de factura generada
     */
    public void notificarFacturaGenerada(String emailCliente, String nombreCliente,
                                          String numeroFactura, Double montoTotal,
                                          String detalles) {
        String asunto = "Factura #" + numeroFactura + " - " + nombreClinica;
        String contenido = crearTemplateFactura(nombreCliente, numeroFactura,
                montoTotal, detalles);
        enviarEmail(emailCliente, asunto, contenido);
    }

    /**
     * Notificación de consulta finalizada
     */
    public void notificarConsultaFinalizada(String emailCliente, String nombreCliente,
                                             String nombrePaciente, LocalDate fecha,
                                             String diagnostico, String tratamiento) {
        String asunto = "Resumen de Consulta - " + nombrePaciente;
        String contenido = crearTemplateConsulta(nombreCliente, nombrePaciente,
                fecha, diagnostico, tratamiento);
        enviarEmail(emailCliente, asunto, contenido);
    }

    /**
     * Recordatorio de cita (1 día antes)
     */
    public void enviarRecordatorioCita(String emailCliente, String nombreCliente,
                                        String nombrePaciente, LocalDate fecha,
                                        LocalTime hora, String tipoServicio) {
        String asunto = "Recordatorio: Cita Mañana - " + nombreClinica;
        String contenido = crearTemplateRecordatorio(nombreCliente, nombrePaciente,
                fecha, hora, tipoServicio);
        enviarEmail(emailCliente, asunto, contenido);
    }

    /**
     * Notificación de cita cancelada
     */
    public void notificarCitaCancelada(String emailCliente, String nombreCliente,
                                        String nombrePaciente, LocalDate fecha,
                                        String motivo) {
        String asunto = "Cita Cancelada - " + nombreClinica;
        String contenido = crearTemplateCitaCancelada(nombreCliente, nombrePaciente,
                fecha, motivo);
        enviarEmail(emailCliente, asunto, contenido);
    }

    // ==================== TEMPLATES HTML ====================

    private String crearTemplateCitaCreada(String nombreCliente, String nombrePaciente,
                                            LocalDate fecha, LocalTime hora, String servicio, String token) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        String linkConfirmacion = "http://localhost:3000/confirmar-cita?token=" + token;

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #4CAF50; }
                    .btn-confirmar { display: inline-block; background: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; margin: 20px 0; font-weight: bold; }
                    .warning-box { background: #fff3cd; padding: 10px; border-left: 4px solid #ffc107; margin: 15px 0; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Nueva Cita Programada</p>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Se ha programado una cita con los siguientes detalles:</p>

                        <div class="info-box">
                            <strong>Paciente:</strong> %s<br>
                            <strong>Fecha:</strong> %s<br>
                            <strong>Hora:</strong> %s<br>
                            <strong>Servicio:</strong> %s
                        </div>

                        <div class="warning-box">
                            <strong>⚠️ Acción Requerida:</strong> Por favor, confirme su asistencia haciendo clic en el botón de abajo.
                        </div>

                        <div style="text-align: center;">
                            <a href="%s" class="btn-confirmar">CONFIRMAR CITA</a>
                        </div>

                        <p><strong>Importante:</strong></p>
                        <ul>
                            <li>Por favor, llegue 10 minutos antes de su cita.</li>
                            <li>Si necesita cancelar o reprogramar, contáctenos con al menos 24 horas de anticipación.</li>
                            <li>Si no confirma su cita, podría ser cancelada automáticamente.</li>
                        </ul>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(formatoFecha), hora.format(formatoHora), servicio,
                linkConfirmacion, nombreClinica, LocalDate.now().getYear());
    }

    private String crearTemplateVeterinarioCitaConfirmada(String nombreVeterinario, String nombreCliente,
                                                           String nombrePaciente, LocalDate fecha, LocalTime hora) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #2196F3; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #2196F3; }
                    .success-badge { background: #4CAF50; color: white; padding: 5px 15px; border-radius: 15px; display: inline-block; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Notificación de Cita Confirmada</p>
                    </div>
                    <div class="content">
                        <p>Dr./Dra. <strong>%s</strong>,</p>

                        <p><span class="success-badge">✓ CONFIRMADA</span></p>

                        <p>El cliente ha confirmado su asistencia a la cita programada:</p>

                        <div class="info-box">
                            <strong>Cliente:</strong> %s<br>
                            <strong>Paciente:</strong> %s<br>
                            <strong>Fecha:</strong> %s<br>
                            <strong>Hora:</strong> %s
                        </div>

                        <p>La cita está lista y el cliente confirmó su asistencia.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreVeterinario, nombreCliente, nombrePaciente,
                fecha.format(formatoFecha), hora.format(formatoHora),
                nombreClinica, LocalDate.now().getYear());
    }

    private String crearTemplateFactura(String nombreCliente, String numeroFactura,
                                         Double montoTotal, String detalles) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #2196F3; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #2196F3; }
                    .total { font-size: 24px; color: #2196F3; font-weight: bold; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Factura Generada</p>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Se ha generado su factura con los siguientes detalles:</p>
                        
                        <div class="info-box">
                            <strong>Número de Factura:</strong> %s<br>
                            <strong>Fecha:</strong> %s<br>
                            <br>
                            <strong>Detalles:</strong><br>
                            %s<br>
                            <br>
                            <p class="total">Total: S/ %.2f</p>
                        </div>
                        
                        <p>Puede realizar el pago en nuestra clínica o mediante transferencia bancaria.</p>
                        <p>Gracias por confiar en nosotros.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, numeroFactura,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                detalles, montoTotal, nombreClinica, LocalDate.now().getYear());
    }

    private String crearTemplateConsulta(String nombreCliente, String nombrePaciente,
                                          LocalDate fecha, String diagnostico, String tratamiento) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #FF9800; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #FF9800; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Resumen de Consulta Médica</p>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Resumen de la consulta veterinaria de <strong>%s</strong>:</p>
                        
                        <div class="info-box">
                            <strong>Fecha de consulta:</strong> %s<br>
                            <br>
                            <strong>Diagnóstico:</strong><br>
                            %s<br>
                            <br>
                            <strong>Tratamiento prescrito:</strong><br>
                            %s
                        </div>
                        
                        <p>Siga las indicaciones del veterinario y no dude en contactarnos si tiene alguna duda.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                diagnostico, tratamiento, nombreClinica, LocalDate.now().getYear());
    }

    private String crearTemplateRecordatorio(String nombreCliente, String nombrePaciente,
                                              LocalDate fecha, LocalTime hora, String servicio) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #9C27B0; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #9C27B0; }
                    .reminder { background: #ffe0f0; padding: 10px; border-radius: 5px; text-align: center; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Recordatorio de Cita</p>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        
                        <div class="reminder">
                            <h2>🔔 Recordatorio: Tiene una cita mañana</h2>
                        </div>
                        
                        <div class="info-box">
                            <strong>Paciente:</strong> %s<br>
                            <strong>Fecha:</strong> %s<br>
                            <strong>Hora:</strong> %s<br>
                            <strong>Servicio:</strong> %s
                        </div>
                        
                        <p>Le esperamos mañana. Por favor, llegue 10 minutos antes.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hora.format(DateTimeFormatter.ofPattern("HH:mm")),
                servicio, nombreClinica, LocalDate.now().getYear());
    }

    private String crearTemplateCitaCancelada(String nombreCliente, String nombrePaciente,
                                               LocalDate fecha, String motivo) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #f44336; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #f44336; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Cita Cancelada</p>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Su cita ha sido cancelada:</p>
                        
                        <div class="info-box">
                            <strong>Paciente:</strong> %s<br>
                            <strong>Fecha:</strong> %s<br>
                            <strong>Motivo:</strong> %s
                        </div>
                        
                        <p>Si desea reagendar, puede contactarnos en cualquier momento.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                motivo, nombreClinica, LocalDate.now().getYear());
    }
}
