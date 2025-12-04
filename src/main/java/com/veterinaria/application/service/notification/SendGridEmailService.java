package com.veterinaria.application.service.notification;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Servicio de email usando SendGrid API REST.
 * Esta implementación usa la API REST de SendGrid en lugar de SMTP,
 * lo que permite enviar emails desde Railway sin problemas de puertos bloqueados.
 * 
 * Se activa cuando SENDGRID_API_KEY está configurado.
 * Implementa los mismos métodos que EmailService para ser intercambiable.
 * 
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@ConditionalOnExpression(
    "T(java.lang.System).getenv('SENDGRID_API_KEY') != null && " +
    "!T(java.lang.System).getenv('SENDGRID_API_KEY').trim().isEmpty()"
)
public class SendGridEmailService {

    private SendGrid sendGrid;
    
    @Value("${app.email.from:}")
    private String emailFrom;

    @Value("${app.nombre:Clínica Veterinaria}")
    private String nombreClinica;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${app.contacto.telefono:}")
    private String telefonoClinica;

    @Value("${app.contacto.direccion:}")
    private String direccionClinica;

    @Value("${app.contacto.email:${app.email.from}}")
    private String emailContacto;

    @PostConstruct
    public void inicializar() {
        String apiKey = System.getenv("SENDGRID_API_KEY");
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            this.sendGrid = new SendGrid(apiKey);
            log.info("=== SendGrid Email Service Inicializado ===");
            log.info("✓ SendGrid API Key configurado");
            log.info("✓ Email remitente: {}", emailFrom != null && !emailFrom.isEmpty() ? emailFrom : "NO CONFIGURADO");
            log.info("✓ Frontend URL: {}", frontendUrl);
            log.info("⚠️  IMPORTANTE: Verifica que el remitente esté verificado en SendGrid");
        } else {
            log.warn("⚠️ SendGrid API Key no está configurado. El servicio de email no estará disponible.");
        }
    }

    /**
     * Envía un email usando SendGrid API REST
     */
    @Async("taskExecutor")
    public void enviarEmail(String destinatario, String asunto, String contenidoHtml) {
        try {
            if (sendGrid == null) {
                log.error("❌ ERROR: SendGrid no está inicializado. Verifica SENDGRID_API_KEY.");
                return;
            }

            if (emailFrom == null || emailFrom.trim().isEmpty()) {
                log.error("❌ ERROR: Email remitente (app.email.from) no está configurado.");
                return;
            }

            log.info("Enviando email vía SendGrid - De: {}, Para: {}, Asunto: {}", emailFrom, destinatario, asunto);

            // Codificar el asunto con MIME para caracteres especiales (UTF-8)
            String asuntoCodificado = codificarAsunto(asunto);
            
            // Codificar también el nombre del remitente si contiene caracteres especiales
            String nombreRemitenteCodificado = codificarAsunto(nombreClinica);
            
            Email from = new Email(emailFrom, nombreRemitenteCodificado);
            Email to = new Email(destinatario);
            // SendGrid maneja UTF-8 automáticamente cuando el HTML tiene <meta charset="UTF-8">
            // No incluir charset en el content type porque SendGrid no lo acepta
            Content content = new Content("text/html", contenidoHtml);
            Mail mail = new Mail(from, asuntoCodificado, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            long inicio = System.currentTimeMillis();
            Response response = sendGrid.api(request);
            long duracion = System.currentTimeMillis() - inicio;

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("✓ Email enviado exitosamente vía SendGrid a: {} (Asunto: {}) en {}ms", 
                        destinatario, asunto, duracion);
            } else {
                log.error("❌ Error al enviar email vía SendGrid - Status: {}, Body: {}", 
                        response.getStatusCode(), response.getBody());
                log.error("Detalles: {}", response.getHeaders());
            }

        } catch (Exception e) {
            log.error("❌ Error inesperado al enviar email vía SendGrid a {}: {}", 
                    destinatario, e.getMessage(), e);
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
                                          String numeroFactura, LocalDate fechaEmision,
                                          Double subtotal, Double totalDescuentos,
                                          Double totalImpuestos, Double montoTotal,
                                          String detalles) {
        String asunto = "Factura #" + numeroFactura + " - " + nombreClinica;
        String contenido = crearTemplateFactura(nombreCliente, numeroFactura, fechaEmision,
                subtotal, totalDescuentos, totalImpuestos, montoTotal, detalles);
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

    // ==================== UTILIDADES ====================
    
    /**
     * Codifica el asunto del email usando MIME encoding para caracteres especiales UTF-8
     * Esto asegura que caracteres como "í", "á", "é", etc. se muestren correctamente
     * Usa quoted-printable (Q) que es más estándar para asuntos de email
     */
    private String codificarAsunto(String asunto) {
        if (asunto == null || asunto.isEmpty()) {
            return asunto;
        }
        try {
            // Verificar si el asunto contiene caracteres no-ASCII
            boolean tieneCaracteresEspeciales = asunto.chars().anyMatch(c -> c > 127);
            
            if (tieneCaracteresEspeciales) {
                // Codificar usando MIME quoted-printable para caracteres especiales
                // Esto es más estándar para asuntos de email que Base64
                return MimeUtility.encodeText(asunto, "UTF-8", "Q");
            }
            // Si no tiene caracteres especiales, devolver tal cual
            return asunto;
        } catch (UnsupportedEncodingException e) {
            log.warn("No se pudo codificar el asunto con MIME, usando asunto original: {}", e.getMessage());
            return asunto;
        }
    }
    
    /**
     * Formatea un número en formato colombiano (punto para miles, coma para decimales)
     * Ejemplo: 100000.50 -> "100.000,50"
     */
    private String formatearMonedaColombiana(Double valor) {
        if (valor == null) {
            return "0,00";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN); // Usa punto para miles, coma para decimales
        DecimalFormat formatter = new DecimalFormat("#,##0.00", symbols);
        return formatter.format(valor);
    }
    
    // ==================== TEMPLATES HTML ====================
    // Los templates son idénticos a EmailService para mantener consistencia

    private String crearTemplateCitaCreada(String nombreCliente, String nombrePaciente,
                                            LocalDate fecha, LocalTime hora, String servicio, String token) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        String linkConfirmacion = frontendUrl + "/confirmar-cita?token=" + token;

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
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(formatoFecha), hora.format(formatoHora), servicio,
                linkConfirmacion, nombreClinica, LocalDate.now().getYear(),
                crearFooterContacto());
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
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreVeterinario, nombreCliente, nombrePaciente,
                fecha.format(formatoFecha), hora.format(formatoHora),
                nombreClinica, LocalDate.now().getYear(),
                crearFooterContacto());
    }

    private String crearTemplateFactura(String nombreCliente, String numeroFactura,
                                         LocalDate fechaEmision, Double subtotal,
                                         Double totalDescuentos, Double totalImpuestos,
                                         Double montoTotal, String detalles) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 700px; margin: 0 auto; padding: 20px; }
                    .header { background: #2196F3; color: white; padding: 20px; text-align: center; }
                    .content { background: #f9f9f9; padding: 20px; margin: 20px 0; }
                    .info-box { background: white; padding: 20px; margin: 10px 0; border-left: 4px solid #2196F3; }
                    .total-box { background: #e3f2fd; padding: 15px; margin: 15px 0; border-radius: 4px; }
                    .total { font-size: 28px; color: #1976d2; font-weight: bold; text-align: right; }
                    .subtotal-line { text-align: right; padding: 5px 0; }
                    .footer { text-align: center; color: #666; font-size: 12px; padding: 20px; }
                    table { width: 100%%; border-collapse: collapse; margin: 15px 0; }
                    th { background-color: #f5f5f5; padding: 10px; text-align: left; border-bottom: 2px solid #ddd; }
                    td { padding: 10px; border-bottom: 1px solid #eee; }
                    .text-right { text-align: right; }
                    .text-bold { font-weight: bold; }
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
                            <div style="margin-bottom: 15px;">
                            <strong>Número de Factura:</strong> %s<br>
                                <strong>Fecha de Emisión:</strong> %s<br>
                            </div>
                            
                            <div style="margin: 20px 0;">
                                <strong>Detalles de la Factura:</strong>
                                %s
                            </div>
                            
                            <div class="total-box">
                                <div class="subtotal-line">
                                    <strong>Subtotal:</strong> S/ %s
                                </div>
                                %s
                                %s
                                <div class="total" style="margin-top: 10px; padding-top: 10px; border-top: 2px solid #1976d2;">
                                    Total: S/ %s
                                </div>
                            </div>
                        </div>
                        
                        <p>Puede realizar el pago en nuestra clínica o mediante transferencia bancaria.</p>
                        <p>Gracias por confiar en nosotros.</p>
                        %s
                    </div>
                    <div class="footer">
                        <p>Este es un mensaje automático, por favor no responder.</p>
                        <p>%s - © %d</p>
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                nombreClinica, 
                nombreCliente, 
                numeroFactura,
                fechaEmision != null ? fechaEmision.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                detalles,
                formatearMonedaColombiana(subtotal),
                totalDescuentos != null && totalDescuentos > 0 ? 
                    String.format("<div class=\"subtotal-line\" style=\"color: #d32f2f;\"><strong>Descuentos:</strong> -S/ %s</div>", formatearMonedaColombiana(totalDescuentos)) : "",
                totalImpuestos != null && totalImpuestos > 0 ? 
                    String.format("<div class=\"subtotal-line\"><strong>Impuestos:</strong> S/ %s</div>", formatearMonedaColombiana(totalImpuestos)) : "",
                formatearMonedaColombiana(montoTotal),
                crearInfoContacto(),
                nombreClinica, 
                LocalDate.now().getYear(),
                crearFooterContacto()
            );
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
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                diagnostico, tratamiento, nombreClinica, LocalDate.now().getYear(),
                crearFooterContacto());
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
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hora.format(DateTimeFormatter.ofPattern("HH:mm")),
                servicio, nombreClinica, LocalDate.now().getYear(),
                crearFooterContacto());
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
                        %s
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombreClinica, nombreCliente, nombrePaciente,
                fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                motivo, nombreClinica, LocalDate.now().getYear(),
                crearFooterContacto());
    }

    /**
     * Crea el bloque de información de contacto para los emails
     */
    private String crearInfoContacto() {
        StringBuilder info = new StringBuilder();
        if (telefonoClinica != null && !telefonoClinica.trim().isEmpty()) {
            info.append("<p><strong>Teléfono:</strong> ").append(telefonoClinica).append("</p>");
        }
        if (direccionClinica != null && !direccionClinica.trim().isEmpty()) {
            info.append("<p><strong>Dirección:</strong> ").append(direccionClinica).append("</p>");
        }
        if (emailContacto != null && !emailContacto.trim().isEmpty()) {
            info.append("<p><strong>Email:</strong> ").append(emailContacto).append("</p>");
        }
        return info.toString();
    }

    /**
     * Crea el footer con información de contacto
     */
    private String crearFooterContacto() {
        StringBuilder footer = new StringBuilder();
        boolean tieneInfo = (telefonoClinica != null && !telefonoClinica.trim().isEmpty()) ||
                           (direccionClinica != null && !direccionClinica.trim().isEmpty()) ||
                           (emailContacto != null && !emailContacto.trim().isEmpty());
        
        if (tieneInfo) {
            footer.append("<p>");
            boolean primero = true;
            if (telefonoClinica != null && !telefonoClinica.trim().isEmpty()) {
                if (!primero) footer.append(" | ");
                footer.append("Tel: ").append(telefonoClinica);
                primero = false;
            }
            if (direccionClinica != null && !direccionClinica.trim().isEmpty()) {
                if (!primero) footer.append(" | ");
                footer.append("Dir: ").append(direccionClinica);
                primero = false;
            }
            if (emailContacto != null && !emailContacto.trim().isEmpty()) {
                if (!primero) footer.append(" | ");
                footer.append("Email: ").append(emailContacto);
            }
            footer.append("</p>");
        }
        return footer.toString();
    }
}
