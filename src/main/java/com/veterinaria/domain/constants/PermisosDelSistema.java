package com.veterinaria.domain.constants;

/**
 * Constantes con los códigos de permisos del sistema.
 * Evita el uso de magic strings en el código.
 *
 * @author Sistema Veterinaria
 */
public final class PermisosDelSistema {

    private PermisosDelSistema() {
        throw new UnsupportedOperationException("Clase de constantes no instanciable");
    }

    // ========== CITAS ==========
    public static final String CITAS_CREAR = "CITAS_CREAR";
    public static final String CITAS_VER = "CITAS_VER";
    public static final String CITAS_EDITAR = "CITAS_EDITAR";
    public static final String CITAS_CANCELAR = "CITAS_CANCELAR";
    public static final String CITAS_REAGENDAR = "CITAS_REAGENDAR";

    // ========== HISTORIAL CLÍNICO ==========
    public static final String HISTORIAL_VER = "HISTORIAL_VER";
    public static final String HISTORIAL_CREAR = "HISTORIAL_CREAR";
    public static final String HISTORIAL_EDITAR = "HISTORIAL_EDITAR";
    public static final String HISTORIAL_EXPORTAR = "HISTORIAL_EXPORTAR";

    // ========== FACTURACIÓN ==========
    public static final String FACTURA_CREAR = "FACTURA_CREAR";
    public static final String FACTURA_VER = "FACTURA_VER";
    public static final String FACTURA_ANULAR = "FACTURA_ANULAR";
    public static final String FACTURA_DESCUENTO = "FACTURA_DESCUENTO";

    // ========== INVENTARIO ==========
    public static final String INVENTARIO_VER = "INVENTARIO_VER";
    public static final String INVENTARIO_INGRESAR = "INVENTARIO_INGRESAR";
    public static final String INVENTARIO_DAR_BAJA = "INVENTARIO_DAR_BAJA";
    public static final String INVENTARIO_ASIGNAR = "INVENTARIO_ASIGNAR";

    // ========== USUARIOS ==========
    public static final String USUARIOS_CREAR = "USUARIOS_CREAR";
    public static final String USUARIOS_VER = "USUARIOS_VER";
    public static final String USUARIOS_EDITAR = "USUARIOS_EDITAR";
    public static final String USUARIOS_ELIMINAR = "USUARIOS_ELIMINAR";

    // ========== PACIENTES ==========
    public static final String PACIENTES_CREAR = "PACIENTES_CREAR";
    public static final String PACIENTES_VER = "PACIENTES_VER";
    public static final String PACIENTES_EDITAR = "PACIENTES_EDITAR";
    public static final String PACIENTES_ELIMINAR = "PACIENTES_ELIMINAR";

    // ========== CLIENTES ==========
    public static final String CLIENTES_CREAR = "CLIENTES_CREAR";
    public static final String CLIENTES_VER = "CLIENTES_VER";
    public static final String CLIENTES_EDITAR = "CLIENTES_EDITAR";
    public static final String CLIENTES_ELIMINAR = "CLIENTES_ELIMINAR";

    // ========== REPORTES ==========
    public static final String REPORTES_VENTAS = "REPORTES_VENTAS";
    public static final String REPORTES_INVENTARIO = "REPORTES_INVENTARIO";
    public static final String REPORTES_CITAS = "REPORTES_CITAS";
    public static final String REPORTES_AUDITORIA = "REPORTES_AUDITORIA";

    // ========== CONFIGURACIÓN ==========
    public static final String CONFIG_VER = "CONFIG_VER";
    public static final String CONFIG_EDITAR = "CONFIG_EDITAR";
}
