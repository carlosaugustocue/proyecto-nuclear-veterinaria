package com.veterinaria.domain.enums;

/**
 * Tipos de notificaciones del sistema
 */
public enum TipoNotificacion {
    CITA_CREADA("Confirmación de Cita"),
    CITA_RECORDATORIO("Recordatorio de Cita"),
    CITA_CANCELADA("Cita Cancelada"),
    FACTURA_GENERADA("Factura Generada"),
    CONSULTA_FINALIZADA("Resumen de Consulta"),
    VACUNA_PROXIMA("Recordatorio de Vacuna"),
    SEGUIMIENTO_PENDIENTE("Recordatorio de Seguimiento"),
    STOCK_BAJO("Alerta de Stock Bajo"),
    PRODUCTO_VENCIDO("Alerta de Producto Vencido");

    private final String descripcion;

    TipoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
