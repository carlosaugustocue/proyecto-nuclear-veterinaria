package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Tipos de notificación en el sistema.
 * Se utiliza con el patrón Factory Method y Observer para crear y enviar notificaciones.
 */
@Getter
public enum TipoNotificacion {
    EMAIL("Email", "Notificación por correo electrónico", "email"),
    SMS("SMS", "Notificación por mensaje de texto", "sms"),
    WHATSAPP("WhatsApp", "Notificación por WhatsApp", "whatsapp"),
    PUSH("Push", "Notificación push en la aplicación", "push"),
    SISTEMA("Sistema", "Notificación interna del sistema", "sistema");

    private final String displayName;
    private final String descripcion;
    private final String canal;

    TipoNotificacion(String displayName, String descripcion, String canal) {
        this.displayName = displayName;
        this.descripcion = descripcion;
        this.canal = canal;
    }
}
