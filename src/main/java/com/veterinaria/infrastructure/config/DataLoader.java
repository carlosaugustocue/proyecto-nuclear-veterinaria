package com.veterinaria.infrastructure.config;

import com.veterinaria.application.repository.PermisoRepository;
import com.veterinaria.application.repository.ProductoRepository;
import com.veterinaria.application.repository.RazaRepository;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.constants.PermisosDelSistema;
import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.entity.patients.Raza;
import com.veterinaria.domain.entity.security.Permiso;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.domain.enums.UnidadMedida;
import com.veterinaria.domain.service.EncriptadorContrasena;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Carga datos iniciales en la base de datos.
 * Se ejecuta automáticamente al iniciar la aplicación.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RazaRepository razaRepository;
    private final com.veterinaria.application.repository.TipoServicioRepository tipoServicioRepository;
    private final ProductoRepository productoRepository;
    private final EncriptadorContrasena encriptadorContrasena;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Iniciando carga de datos iniciales...");

        try {
            // 1. Crear permisos
            Map<String, Permiso> permisos = crearPermisos();
            log.info("Permisos creados: {}", permisos.size());

            // 2. Crear roles
            Map<String, Rol> roles = crearRoles(permisos);
            log.info("Roles creados: {}", roles.size());

            // 3. Crear usuario administrador
            crearUsuarioAdmin(roles);
            log.info("Usuario administrador creado");

            // 4. Crear catálogo de razas predefinidas
            crearRazasPredefinidas();
            log.info("Catálogo de razas predefinidas creado");

            // 5. Crear tipos de servicio predefinidos
            crearTiposServicioPredefinidos();
            log.info("Tipos de servicio predefinidos creados");

            // 6. Crear productos iniciales del inventario
            crearProductosIniciales();
            log.info("Productos iniciales del inventario creados");

            log.info("Carga de datos iniciales completada exitosamente");

        } catch (Exception e) {
            log.error("Error cargando datos iniciales: {}", e.getMessage(), e);
        }
    }

    /**
     * Crea todos los permisos del sistema
     */
    private Map<String, Permiso> crearPermisos() {
        Map<String, Permiso> permisosMap = new HashMap<>();

        // Permisos de CITAS
        permisosMap.put(PermisosDelSistema.CITAS_CREAR, 
                crearPermiso(PermisosDelSistema.CITAS_CREAR, "Crear citas", "CITAS"));
        permisosMap.put(PermisosDelSistema.CITAS_VER, 
                crearPermiso(PermisosDelSistema.CITAS_VER, "Ver citas", "CITAS"));
        permisosMap.put(PermisosDelSistema.CITAS_EDITAR, 
                crearPermiso(PermisosDelSistema.CITAS_EDITAR, "Editar citas", "CITAS"));
        permisosMap.put(PermisosDelSistema.CITAS_CANCELAR, 
                crearPermiso(PermisosDelSistema.CITAS_CANCELAR, "Cancelar citas", "CITAS"));
        permisosMap.put(PermisosDelSistema.CITAS_REAGENDAR, 
                crearPermiso(PermisosDelSistema.CITAS_REAGENDAR, "Reagendar citas", "CITAS"));

        // Permisos de HISTORIAL CLÍNICO
        permisosMap.put(PermisosDelSistema.HISTORIAL_VER,
                crearPermiso(PermisosDelSistema.HISTORIAL_VER, "Ver historial clínico", "HISTORIAL"));
        permisosMap.put(PermisosDelSistema.HISTORIAL_CREAR,
                crearPermiso(PermisosDelSistema.HISTORIAL_CREAR, "Crear historial clínico", "HISTORIAL"));
        permisosMap.put(PermisosDelSistema.HISTORIAL_EDITAR,
                crearPermiso(PermisosDelSistema.HISTORIAL_EDITAR, "Editar historial clínico", "HISTORIAL"));
        permisosMap.put(PermisosDelSistema.HISTORIAL_EXPORTAR,
                crearPermiso(PermisosDelSistema.HISTORIAL_EXPORTAR, "Exportar historial clínico", "HISTORIAL"));

        // Permisos de CONSULTAS
        permisosMap.put("CONSULTAS_CREAR",
                crearPermiso("CONSULTAS_CREAR", "Crear consultas", "CONSULTAS"));
        permisosMap.put("CONSULTAS_VER",
                crearPermiso("CONSULTAS_VER", "Ver consultas", "CONSULTAS"));
        permisosMap.put("CONSULTAS_EDITAR",
                crearPermiso("CONSULTAS_EDITAR", "Editar consultas", "CONSULTAS"));
        permisosMap.put("CONSULTAS_ELIMINAR",
                crearPermiso("CONSULTAS_ELIMINAR", "Eliminar consultas", "CONSULTAS"));
        permisosMap.put("CONSULTAS_FINALIZAR",
                crearPermiso("CONSULTAS_FINALIZAR", "Finalizar consultas", "CONSULTAS"));
        permisosMap.put("DIAGNOSTICOS_CREAR",
                crearPermiso("DIAGNOSTICOS_CREAR", "Crear diagnósticos", "CONSULTAS"));
        permisosMap.put("TRATAMIENTOS_CREAR",
                crearPermiso("TRATAMIENTOS_CREAR", "Crear tratamientos", "CONSULTAS"));

        // Permisos de FACTURACIÓN (v1 - actualizados)
        permisosMap.put("FACTURAS_CREAR",
                crearPermiso("FACTURAS_CREAR", "Crear facturas", "FACTURACION"));
        permisosMap.put("FACTURAS_VER",
                crearPermiso("FACTURAS_VER", "Ver facturas", "FACTURACION"));
        permisosMap.put("FACTURAS_EDITAR",
                crearPermiso("FACTURAS_EDITAR", "Editar facturas", "FACTURACION"));
        permisosMap.put("FACTURAS_ELIMINAR",
                crearPermiso("FACTURAS_ELIMINAR", "Eliminar facturas", "FACTURACION"));
        permisosMap.put("FACTURAS_ANULAR",
                crearPermiso("FACTURAS_ANULAR", "Anular facturas", "FACTURACION"));
        permisosMap.put("FACTURAS_APLICAR_DESCUENTO",
                crearPermiso("FACTURAS_APLICAR_DESCUENTO", "Aplicar descuentos", "FACTURACION"));
        permisosMap.put("PAGOS_REGISTRAR",
                crearPermiso("PAGOS_REGISTRAR", "Registrar pagos", "FACTURACION"));

        // Permisos de FACTURACIÓN (legacy - mantener compatibilidad)
        permisosMap.put(PermisosDelSistema.FACTURA_CREAR,
                crearPermiso(PermisosDelSistema.FACTURA_CREAR, "Crear facturas (legacy)", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_VER,
                crearPermiso(PermisosDelSistema.FACTURA_VER, "Ver facturas (legacy)", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_ANULAR,
                crearPermiso(PermisosDelSistema.FACTURA_ANULAR, "Anular facturas (legacy)", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_DESCUENTO,
                crearPermiso(PermisosDelSistema.FACTURA_DESCUENTO, "Aplicar descuentos (legacy)", "FACTURACION"));

        // Permisos de INVENTARIO
        permisosMap.put(PermisosDelSistema.INVENTARIO_VER, 
                crearPermiso(PermisosDelSistema.INVENTARIO_VER, "Ver inventario", "INVENTARIO"));
        permisosMap.put(PermisosDelSistema.INVENTARIO_INGRESAR, 
                crearPermiso(PermisosDelSistema.INVENTARIO_INGRESAR, "Ingresar productos", "INVENTARIO"));
        permisosMap.put(PermisosDelSistema.INVENTARIO_DAR_BAJA, 
                crearPermiso(PermisosDelSistema.INVENTARIO_DAR_BAJA, "Dar de baja productos", "INVENTARIO"));
        permisosMap.put(PermisosDelSistema.INVENTARIO_ASIGNAR, 
                crearPermiso(PermisosDelSistema.INVENTARIO_ASIGNAR, "Asignar productos", "INVENTARIO"));

        // Permisos de USUARIOS
        permisosMap.put(PermisosDelSistema.USUARIOS_CREAR, 
                crearPermiso(PermisosDelSistema.USUARIOS_CREAR, "Crear usuarios", "USUARIOS"));
        permisosMap.put(PermisosDelSistema.USUARIOS_VER, 
                crearPermiso(PermisosDelSistema.USUARIOS_VER, "Ver usuarios", "USUARIOS"));
        permisosMap.put(PermisosDelSistema.USUARIOS_EDITAR, 
                crearPermiso(PermisosDelSistema.USUARIOS_EDITAR, "Editar usuarios", "USUARIOS"));
        permisosMap.put(PermisosDelSistema.USUARIOS_ELIMINAR, 
                crearPermiso(PermisosDelSistema.USUARIOS_ELIMINAR, "Eliminar usuarios", "USUARIOS"));

        // Permisos de PACIENTES
        permisosMap.put(PermisosDelSistema.PACIENTES_CREAR, 
                crearPermiso(PermisosDelSistema.PACIENTES_CREAR, "Crear pacientes", "PACIENTES"));
        permisosMap.put(PermisosDelSistema.PACIENTES_VER, 
                crearPermiso(PermisosDelSistema.PACIENTES_VER, "Ver pacientes", "PACIENTES"));
        permisosMap.put(PermisosDelSistema.PACIENTES_EDITAR, 
                crearPermiso(PermisosDelSistema.PACIENTES_EDITAR, "Editar pacientes", "PACIENTES"));
        permisosMap.put(PermisosDelSistema.PACIENTES_ELIMINAR, 
                crearPermiso(PermisosDelSistema.PACIENTES_ELIMINAR, "Eliminar pacientes", "PACIENTES"));

        // Permisos de CLIENTES
        permisosMap.put(PermisosDelSistema.CLIENTES_CREAR, 
                crearPermiso(PermisosDelSistema.CLIENTES_CREAR, "Crear clientes", "CLIENTES"));
        permisosMap.put(PermisosDelSistema.CLIENTES_VER, 
                crearPermiso(PermisosDelSistema.CLIENTES_VER, "Ver clientes", "CLIENTES"));
        permisosMap.put(PermisosDelSistema.CLIENTES_EDITAR, 
                crearPermiso(PermisosDelSistema.CLIENTES_EDITAR, "Editar clientes", "CLIENTES"));
        permisosMap.put(PermisosDelSistema.CLIENTES_ELIMINAR, 
                crearPermiso(PermisosDelSistema.CLIENTES_ELIMINAR, "Eliminar clientes", "CLIENTES"));

        // Permisos de REPORTES
        permisosMap.put(PermisosDelSistema.REPORTES_VENTAS, 
                crearPermiso(PermisosDelSistema.REPORTES_VENTAS, "Ver reporte de ventas", "REPORTES"));
        permisosMap.put(PermisosDelSistema.REPORTES_INVENTARIO, 
                crearPermiso(PermisosDelSistema.REPORTES_INVENTARIO, "Ver reporte de inventario", "REPORTES"));
        permisosMap.put(PermisosDelSistema.REPORTES_CITAS, 
                crearPermiso(PermisosDelSistema.REPORTES_CITAS, "Ver reporte de citas", "REPORTES"));
        permisosMap.put(PermisosDelSistema.REPORTES_AUDITORIA, 
                crearPermiso(PermisosDelSistema.REPORTES_AUDITORIA, "Ver reporte de auditoría", "REPORTES"));

        // Permisos de CONFIGURACIÓN
        permisosMap.put(PermisosDelSistema.CONFIG_VER, 
                crearPermiso(PermisosDelSistema.CONFIG_VER, "Ver configuración", "CONFIGURACION"));
        permisosMap.put(PermisosDelSistema.CONFIG_EDITAR, 
                crearPermiso(PermisosDelSistema.CONFIG_EDITAR, "Editar configuración", "CONFIGURACION"));

        return permisosMap;
    }

    /**
     * Crea un permiso si no existe
     */
    private Permiso crearPermiso(String codigo, String nombre, String modulo) {
        return permisoRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    Permiso permiso = Permiso.builder()
                            .codigo(codigo)
                            .nombre(nombre)
                            .modulo(modulo)
                            .descripcion("Permiso para " + nombre.toLowerCase())
                            .build();
                    permiso.setIsActive(true);
                    return permisoRepository.save(permiso);
                });
    }

    /**
     * Crea roles del sistema
     */
    private Map<String, Rol> crearRoles(Map<String, Permiso> permisos) {
        Map<String, Rol> rolesMap = new HashMap<>();

        // ROL ADMINISTRADOR - Todos los permisos
        rolesMap.put("ROLE_ADMIN", crearRol("ROLE_ADMIN", "Administrador", 
                "Acceso completo al sistema", new ArrayList<>(permisos.values())));

        // ROL VETERINARIO - Permisos médicos
        List<Permiso> permisosVeterinario = Arrays.asList(
                permisos.get(PermisosDelSistema.CITAS_VER),
                permisos.get(PermisosDelSistema.CITAS_CREAR),
                permisos.get(PermisosDelSistema.CITAS_EDITAR),
                permisos.get(PermisosDelSistema.HISTORIAL_VER),
                permisos.get(PermisosDelSistema.HISTORIAL_CREAR),
                permisos.get(PermisosDelSistema.HISTORIAL_EDITAR),
                permisos.get(PermisosDelSistema.HISTORIAL_EXPORTAR),
                // Permisos de CONSULTAS
                permisos.get("CONSULTAS_VER"),
                permisos.get("CONSULTAS_CREAR"),
                permisos.get("CONSULTAS_EDITAR"),
                permisos.get("CONSULTAS_FINALIZAR"),
                permisos.get("DIAGNOSTICOS_CREAR"),
                permisos.get("TRATAMIENTOS_CREAR"),
                // Permisos de PACIENTES
                permisos.get(PermisosDelSistema.PACIENTES_VER),
                permisos.get(PermisosDelSistema.PACIENTES_CREAR),
                permisos.get(PermisosDelSistema.PACIENTES_EDITAR),
                permisos.get(PermisosDelSistema.CLIENTES_VER),
                permisos.get(PermisosDelSistema.INVENTARIO_VER)
        );
        rolesMap.put("ROLE_VETERINARIO", crearRol("ROLE_VETERINARIO", "Veterinario",
                "Acceso a funciones médicas", permisosVeterinario));

        // ROL RECEPCIONISTA - Permisos administrativos básicos
        List<Permiso> permisosRecepcionista = Arrays.asList(
                permisos.get(PermisosDelSistema.CITAS_VER),
                permisos.get(PermisosDelSistema.CITAS_CREAR),
                permisos.get(PermisosDelSistema.CITAS_REAGENDAR),
                permisos.get(PermisosDelSistema.PACIENTES_VER),
                permisos.get(PermisosDelSistema.PACIENTES_CREAR),
                permisos.get(PermisosDelSistema.CLIENTES_VER),
                permisos.get(PermisosDelSistema.CLIENTES_CREAR),
                permisos.get(PermisosDelSistema.CLIENTES_EDITAR),
                // Facturas v1
                permisos.get("FACTURAS_VER"),
                permisos.get("FACTURAS_CREAR"),
                // Facturas legacy (compatibilidad)
                permisos.get(PermisosDelSistema.FACTURA_VER),
                permisos.get(PermisosDelSistema.FACTURA_CREAR)
        );
        rolesMap.put("ROLE_RECEPCIONISTA", crearRol("ROLE_RECEPCIONISTA", "Recepcionista",
                "Acceso a funciones de recepción", permisosRecepcionista));

        return rolesMap;
    }

    /**
     * Crea un rol si no existe, o actualiza sus permisos si ya existe
     */
    private Rol crearRol(String nombre, String nombreDisplay, String descripcion, List<Permiso> permisos) {
        Optional<Rol> rolExistente = rolRepository.findByNombre(nombre);

        if (rolExistente.isPresent()) {
            // Rol existe: actualizar permisos
            Rol rol = rolExistente.get();
            log.info("Actualizando permisos del rol: {}", nombre);

            // Limpiar permisos actuales y agregar todos los nuevos
            rol.getPermisos().clear();
            permisos.forEach(rol::agregarPermiso);

            return rolRepository.save(rol);
        } else {
            // Rol no existe: crear nuevo
            log.info("Creando nuevo rol: {}", nombre);
            Rol rol = Rol.builder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .permisos(new ArrayList<>())
                    .build();
            rol.setIsActive(true);

            // Agregar permisos
            permisos.forEach(rol::agregarPermiso);

            return rolRepository.save(rol);
        }
    }

    /**
     * Crea usuario administrador por defecto
     */
    private void crearUsuarioAdmin(Map<String, Rol> roles) {
        String emailAdmin = "admin@veterinaria.com";
        
        if (usuarioRepository.findByEmail(emailAdmin).isEmpty()) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .email(emailAdmin)
                    .password(encriptadorContrasena.encriptar("Admin123!"))
                    .nombre("Administrador")
                    .apellido("Sistema")
                    .tipoUsuario(TipoUsuario.VETERINARIO)
                    .cuentaBloqueada(false)
                    .cuentaExpirada(false)
                    .credencialesExpiradas(false)
                    .intentosFallidos(0)
                    .requiereCambioPassword(false)
                    .roles(new HashSet<>())
                    .build();
            
            admin.setIsActive(true);
            admin.agregarRol(roles.get("ROLE_ADMIN"));
            admin.actualizarUltimoAcceso();
            
            usuarioRepository.save(admin);
            
            log.info("Usuario administrador creado:");
            log.info("Email: {}", emailAdmin);
            log.info("Password: Admin123!");
        }
    }

    /**
     * Crea catálogo predefinido de razas comunes.
     * Implementa HU-09: Tipos de Mascotas
     */
    private void crearRazasPredefinidas() {
        // Solo cargar si no existen razas
        if (razaRepository.count() > 0) {
            log.info("Catálogo de razas ya existe, omitiendo creación");
            return;
        }

        log.info("Creando catálogo de razas predefinidas...");

        // PERROS - Razas comunes
        crearRaza("Labrador Retriever", TipoEspecie.PERRO, "Raza amigable, activa y versátil", "Grande", 30.0);
        crearRaza("Golden Retriever", TipoEspecie.PERRO, "Raza cariñosa, inteligente y leal", "Grande", 32.0);
        crearRaza("Pastor Alemán", TipoEspecie.PERRO, "Raza inteligente, protectora y versátil", "Grande", 35.0);
        crearRaza("Bulldog Francés", TipoEspecie.PERRO, "Raza compacta, cariñosa y juguetona", "Pequeño", 10.0);
        crearRaza("Beagle", TipoEspecie.PERRO, "Raza curiosa, amigable y activa", "Mediano", 12.0);
        crearRaza("Poodle", TipoEspecie.PERRO, "Raza inteligente, hipoalergénica y elegante", "Mediano", 20.0);
        crearRaza("Chihuahua", TipoEspecie.PERRO, "Raza pequeña, valiente y leal", "Pequeño", 2.5);
        crearRaza("Yorkshire Terrier", TipoEspecie.PERRO, "Raza pequeña, enérgica y cariñosa", "Pequeño", 3.0);
        crearRaza("Rottweiler", TipoEspecie.PERRO, "Raza fuerte, leal y protectora", "Grande", 45.0);
        crearRaza("Boxer", TipoEspecie.PERRO, "Raza enérgica, juguetona y protectora", "Grande", 30.0);
        crearRaza("Dachshund", TipoEspecie.PERRO, "Raza alargada, valiente y curiosa", "Pequeño", 9.0);
        crearRaza("Shih Tzu", TipoEspecie.PERRO, "Raza cariñosa, juguetona y adaptable", "Pequeño", 6.0);
        crearRaza("Doberman", TipoEspecie.PERRO, "Raza inteligente, leal y atlética", "Grande", 40.0);
        crearRaza("Schnauzer", TipoEspecie.PERRO, "Raza alerta, inteligente y enérgica", "Mediano", 15.0);
        crearRaza("Cocker Spaniel", TipoEspecie.PERRO, "Raza amigable, activa y cariñosa", "Mediano", 13.0);

        // PERROS - Mestizo
        crearRazaMestiza("Mestizo", TipoEspecie.PERRO, "Perro de raza mixta");

        // GATOS - Razas comunes
        crearRaza("Siamés", TipoEspecie.GATO, "Raza vocal, social y elegante", null, 4.0);
        crearRaza("Persa", TipoEspecie.GATO, "Raza tranquila, pelaje largo y cara achatada", null, 4.5);
        crearRaza("Maine Coon", TipoEspecie.GATO, "Raza grande, amigable y pelaje largo", null, 7.0);
        crearRaza("Bengalí", TipoEspecie.GATO, "Raza activa, manchada y atlética", null, 5.5);
        crearRaza("Ragdoll", TipoEspecie.GATO, "Raza tranquila, cariñosa y dócil", null, 6.0);
        crearRaza("British Shorthair", TipoEspecie.GATO, "Raza robusta, tranquila y pelaje denso", null, 5.0);
        crearRaza("Sphynx", TipoEspecie.GATO, "Raza sin pelo, cariñosa y activa", null, 4.0);
        crearRaza("Abisinio", TipoEspecie.GATO, "Raza activa, curiosa y elegante", null, 4.0);
        crearRaza("Scottish Fold", TipoEspecie.GATO, "Raza orejas dobladas, tranquila y cariñosa", null, 4.5);
        crearRaza("Angora", TipoEspecie.GATO, "Raza elegante, pelaje sedoso y activa", null, 4.0);
        crearRaza("Birmano", TipoEspecie.GATO, "Raza puntos de color, ojos azules y tranquila", null, 4.5);
        crearRaza("Devon Rex", TipoEspecie.GATO, "Raza pelaje rizado, juguetona y activa", null, 3.5);
        crearRaza("Exótico", TipoEspecie.GATO, "Similar al persa pero pelo corto", null, 4.5);
        crearRaza("Doméstico Pelo Corto", TipoEspecie.GATO, "Gato común de pelo corto", null, 4.0);
        crearRaza("Doméstico Pelo Largo", TipoEspecie.GATO, "Gato común de pelo largo", null, 4.5);

        // GATOS - Mestizo
        crearRazaMestiza("Mestizo", TipoEspecie.GATO, "Gato de raza mixta");
        crearRazaMestiza("Criollo", TipoEspecie.GATO, "Gato sin raza definida");

        // AVES - Razas comunes
        crearRaza("Canario", TipoEspecie.AVE, "Ave pequeña, cantora y colorida", "Pequeño", 0.02);
        crearRaza("Periquito", TipoEspecie.AVE, "Ave pequeña, social y fácil de cuidar", "Pequeño", 0.03);
        crearRaza("Loro Amazona", TipoEspecie.AVE, "Ave mediana, inteligente y longeva", "Mediano", 0.4);
        crearRaza("Cacatúa", TipoEspecie.AVE, "Ave cresta, cariñosa y ruidosa", "Mediano", 0.5);
        crearRaza("Guacamayo", TipoEspecie.AVE, "Ave grande, colorida e inteligente", "Grande", 1.0);
        crearRaza("Agapornis", TipoEspecie.AVE, "Ave pequeña, social y cariñosa", "Pequeño", 0.05);
        crearRaza("Ninfa", TipoEspecie.AVE, "Ave cresta, sociable y juguetona", "Pequeño", 0.1);
        crearRazaMestiza("Otra", TipoEspecie.AVE, "Otra especie de ave");

        // REPTILES
        crearRaza("Iguana Verde", TipoEspecie.REPTIL, "Lagarto herbívoro grande", "Grande", 5.0);
        crearRaza("Gecko Leopardo", TipoEspecie.REPTIL, "Lagarto pequeño, dócil y nocturno", "Pequeño", 0.08);
        crearRaza("Tortuga de Orejas Rojas", TipoEspecie.REPTIL, "Tortuga acuática popular", "Mediano", 2.0);
        crearRaza("Pitón Bola", TipoEspecie.REPTIL, "Serpiente dócil y de tamaño manejable", "Mediano", 1.5);
        crearRaza("Pogona", TipoEspecie.REPTIL, "Dragón barbudo, dócil y diurno", "Mediano", 0.5);
        crearRazaMestiza("Otro", TipoEspecie.REPTIL, "Otra especie de reptil");

        // ROEDORES
        crearRaza("Hamster Sirio", TipoEspecie.ROEDOR, "Roedor solitario, nocturno y popular", "Pequeño", 0.12);
        crearRaza("Cobayo", TipoEspecie.ROEDOR, "Roedor social, dócil y vocal", "Pequeño", 1.0);
        crearRaza("Conejo Holandés", TipoEspecie.ROEDOR, "Conejo pequeño, dócil y social", "Pequeño", 2.0);
        crearRaza("Conejo Cabeza de León", TipoEspecie.ROEDOR, "Conejo pelaje melena, cariñoso", "Pequeño", 1.5);
        crearRaza("Chinchilla", TipoEspecie.ROEDOR, "Roedor pelaje denso, nocturno", "Pequeño", 0.6);
        crearRaza("Jerbo", TipoEspecie.ROEDOR, "Roedor cola larga, activo y social", "Pequeño", 0.08);
        crearRazaMestiza("Otro", TipoEspecie.ROEDOR, "Otra especie de roedor");

        // OTRO - Categoría general
        crearRazaMestiza("Otro", TipoEspecie.OTRO, "Otra especie no clasificada");

        log.info("Catálogo de razas predefinidas creado exitosamente");
    }

    /**
     * Crea una raza si no existe
     */
    private void crearRaza(String nombre, TipoEspecie especie, String descripcion,
                          String tamanio, Double peso) {
        razaRepository.findByNombreAndEspecie(nombre, especie)
                .orElseGet(() -> {
                    Raza raza = Raza.builder()
                            .nombre(nombre)
                            .especie(especie)
                            .descripcion(descripcion)
                            .esPredefinida(true)
                            .esMestizo(false)
                            .tamanioTipico(tamanio)
                            .pesoPromedioKg(peso)
                            .build();
                    raza.setIsActive(true);
                    return razaRepository.save(raza);
                });
    }

    /**
     * Crea una raza mestiza
     */
    private void crearRazaMestiza(String nombre, TipoEspecie especie, String descripcion) {
        razaRepository.findByNombreAndEspecie(nombre, especie)
                .orElseGet(() -> {
                    Raza raza = Raza.builder()
                            .nombre(nombre)
                            .especie(especie)
                            .descripcion(descripcion)
                            .esPredefinida(true)
                            .esMestizo(true)
                            .build();
                    raza.setIsActive(true);
                    return razaRepository.save(raza);
                });
    }

    /**
     * Crea catálogo predefinido de tipos de servicio veterinarios.
     * Implementa servicios comunes ofrecidos en la clínica.
     */
    private void crearTiposServicioPredefinidos() {
        // Solo cargar si no existen tipos de servicio
        if (tipoServicioRepository.count() > 0) {
            log.info("Tipos de servicio ya existen, omitiendo creación");
            return;
        }

        log.info("Creando tipos de servicio predefinidos...");

        // CONSULTAS
        crearTipoServicio(
                "Consulta General",
                "Consulta médica veterinaria general",
                30,  // 30 minutos
                50000.0,
                "Consultas",
                false
        );

        crearTipoServicio(
                "Consulta Especializada",
                "Consulta con veterinario especialista",
                45,  // 45 minutos
                80000.0,
                "Consultas",
                true
        );

        crearTipoServicio(
                "Control Post-operatorio",
                "Revisión médica después de cirugía",
                20,  // 20 minutos
                30000.0,
                "Consultas",
                false
        );

        crearTipoServicio(
                "Control de Salud",
                "Chequeo general preventivo",
                25,  // 25 minutos
                40000.0,
                "Consultas",
                false
        );

        // VACUNACIÓN Y PREVENCIÓN
        crearTipoServicio(
                "Vacunación Múltiple",
                "Aplicación de vacunas preventivas",
                15,  // 15 minutos
                35000.0,
                "Prevención",
                false
        );

        crearTipoServicio(
                "Vacunación Antirrábica",
                "Vacuna contra la rabia",
                10,  // 10 minutos
                25000.0,
                "Prevención",
                false
        );

        crearTipoServicio(
                "Desparasitación Interna",
                "Tratamiento antiparasitario interno",
                10,  // 10 minutos
                20000.0,
                "Prevención",
                false
        );

        crearTipoServicio(
                "Desparasitación Externa",
                "Tratamiento contra pulgas y garrapatas",
                10,  // 10 minutos
                20000.0,
                "Prevención",
                false
        );

        // CIRUGÍAS
        crearTipoServicio(
                "Esterilización/Castración",
                "Cirugía de esterilización o castración",
                120,  // 2 horas
                150000.0,
                "Cirugía",
                true
        );

        crearTipoServicio(
                "Cirugía Menor",
                "Procedimiento quirúrgico menor",
                60,  // 1 hora
                100000.0,
                "Cirugía",
                true
        );

        crearTipoServicio(
                "Cirugía Mayor",
                "Procedimiento quirúrgico complejo",
                180,  // 3 horas
                300000.0,
                "Cirugía",
                true
        );

        crearTipoServicio(
                "Limpieza Dental",
                "Profilaxis dental con sedación",
                90,  // 1.5 horas
                120000.0,
                "Cirugía",
                true
        );

        // DIAGNÓSTICO
        crearTipoServicio(
                "Radiografía",
                "Estudio radiográfico",
                30,  // 30 minutos
                60000.0,
                "Diagnóstico",
                false
        );

        crearTipoServicio(
                "Ecografía",
                "Estudio ecográfico",
                45,  // 45 minutos
                80000.0,
                "Diagnóstico",
                false
        );

        crearTipoServicio(
                "Análisis de Sangre Completo",
                "Hemograma y química sanguínea",
                15,  // 15 minutos (toma de muestra)
                70000.0,
                "Diagnóstico",
                false
        );

        crearTipoServicio(
                "Análisis de Orina",
                "Urianálisis completo",
                10,  // 10 minutos (toma de muestra)
                40000.0,
                "Diagnóstico",
                false
        );

        // ESTÉTICA
        crearTipoServicio(
                "Baño Medicado",
                "Baño terapéutico con champú medicado",
                45,  // 45 minutos
                40000.0,
                "Estética",
                false
        );

        crearTipoServicio(
                "Baño Básico",
                "Baño e higiene básica",
                30,  // 30 minutos
                30000.0,
                "Estética",
                false
        );

        crearTipoServicio(
                "Peluquería Completa",
                "Corte, baño y arreglo completo",
                60,  // 1 hora
                50000.0,
                "Estética",
                false
        );

        crearTipoServicio(
                "Corte de Uñas",
                "Recorte de uñas",
                15,  // 15 minutos
                15000.0,
                "Estética",
                false
        );

        // HOSPITALIZACIÓN
        crearTipoServicio(
                "Hospitalización 24h",
                "Internación con monitoreo 24 horas",
                1440,  // 24 horas en minutos
                80000.0,
                "Hospitalización",
                true
        );

        crearTipoServicio(
                "Terapia de Fluidos",
                "Fluidoterapia endovenosa",
                60,  // 1 hora
                50000.0,
                "Hospitalización",
                false
        );

        // URGENCIAS
        crearTipoServicio(
                "Urgencia Veterinaria",
                "Atención de emergencia médica",
                30,  // 30 minutos
                100000.0,
                "Urgencias",
                false
        );

        crearTipoServicio(
                "Urgencia Crítica",
                "Atención de emergencia crítica",
                60,  // 1 hora
                150000.0,
                "Urgencias",
                false
        );

        // OTROS SERVICIOS
        crearTipoServicio(
                "Colocación de Microchip",
                "Implantación de microchip de identificación",
                15,  // 15 minutos
                35000.0,
                "Otros",
                false
        );

        crearTipoServicio(
                "Eutanasia",
                "Eutanasia humanitaria",
                30,  // 30 minutos
                80000.0,
                "Otros",
                true
        );

        log.info("Tipos de servicio predefinidos creados exitosamente");
    }

    /**
     * Crea un tipo de servicio si no existe
     */
    private void crearTipoServicio(String nombre, String descripcion, Integer duracionEstimada,
                                   Double precioBase, String categoria, Boolean requiereConfirmacion) {
        tipoServicioRepository.findByNombre(nombre)
                .orElseGet(() -> {
                    com.veterinaria.domain.entity.appointments.TipoServicio servicio =
                            com.veterinaria.domain.entity.appointments.TipoServicio.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .duracionEstimada(duracionEstimada)
                            .precioBase(precioBase)
                            .categoria(categoria)
                            .requiereConfirmacion(requiereConfirmacion)
                            .build();
                    servicio.setIsActive(true);
                    return tipoServicioRepository.save(servicio);
                });
    }

    /**
     * Crea productos iniciales del inventario para una clínica veterinaria.
     * Incluye alimentos, medicamentos, vacunas, materiales y accesorios comunes.
     */
    private void crearProductosIniciales() {
        // Solo cargar si no existen productos
        if (productoRepository.count() > 0) {
            log.info("Productos del inventario ya existen, omitiendo creación");
            return;
        }

        log.info("Creando productos iniciales del inventario...");

        // ========== ALIMENTOS ==========
        // Alimentos para Perros
        crearProducto("ALIM-PERRO-001", "Alimento Premium para Perros Adultos 15kg", 
                "Alimento balanceado premium para perros adultos, raza mediana a grande", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 50, 10, 
                45000.0, 65000.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-PERRO-002", "Alimento Premium para Perros Cachorros 12kg", 
                "Alimento balanceado premium para cachorros, rico en proteínas", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 30, 5, 
                50000.0, 75000.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-PERRO-003", "Alimento para Perros Senior 10kg", 
                "Alimento especializado para perros mayores de 7 años", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 25, 5, 
                42000.0, 62000.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-PERRO-004", "Alimento para Perros Pequeños 8kg", 
                "Alimento balanceado para razas pequeñas", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 40, 8, 
                38000.0, 58000.0, "Proveedor Alimentos Premium", null, null);

        // Alimentos para Gatos
        crearProducto("ALIM-GATO-001", "Alimento Premium para Gatos Adultos 10kg", 
                "Alimento balanceado premium para gatos adultos", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 35, 7, 
                48000.0, 72000.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-GATO-002", "Alimento Premium para Gatitos 8kg", 
                "Alimento balanceado premium para gatitos, rico en DHA", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 30, 6, 
                52000.0, 78000.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-GATO-003", "Alimento Húmedo para Gatos (Lata 400g)", 
                "Alimento húmedo en lata para gatos adultos", 
                CategoriaProducto.ALIMENTO, UnidadMedida.UNIDAD, 100, 20, 
                3500.0, 5500.0, "Proveedor Alimentos Premium", null, null);
        
        crearProducto("ALIM-GATO-004", "Alimento para Gatos Castrados 7kg", 
                "Alimento especializado para gatos castrados, control de peso", 
                CategoriaProducto.ALIMENTO, UnidadMedida.KILOGRAMO, 30, 6, 
                45000.0, 68000.0, "Proveedor Alimentos Premium", null, null);

        // Snacks y Premios
        crearProducto("SNACK-001", "Premios para Perros (Bolsa 500g)", 
                "Premios naturales para perros, entrenamiento y recompensa", 
                CategoriaProducto.ALIMENTO, UnidadMedida.PAQUETE, 50, 10, 
                12000.0, 20000.0, "Proveedor Snacks", null, null);
        
        crearProducto("SNACK-002", "Snacks para Gatos (Bolsa 200g)", 
                "Snacks crujientes para gatos, sabor pescado", 
                CategoriaProducto.ALIMENTO, UnidadMedida.PAQUETE, 60, 12, 
                8000.0, 15000.0, "Proveedor Snacks", null, null);

        // ========== MEDICAMENTOS ==========
        // Antibióticos
        crearProducto("MED-ANT-001", "Amoxicilina 500mg (Caja 20 tabletas)", 
                "Antibiótico de amplio espectro para infecciones bacterianas", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 30, 5, 
                25000.0, 40000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(24), "LOT-2025-001");
        
        crearProducto("MED-ANT-002", "Cefalexina 500mg (Caja 16 cápsulas)", 
                "Antibiótico para infecciones de piel y tejidos blandos", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 25, 5, 
                28000.0, 45000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(18), "LOT-2025-002");

        // Antiinflamatorios
        crearProducto("MED-ANTI-001", "Carprofeno 25mg (Caja 30 tabletas)", 
                "Antiinflamatorio no esteroideo para dolor y artritis", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 20, 4, 
                35000.0, 55000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(30), "LOT-2025-003");
        
        crearProducto("MED-ANTI-002", "Meloxicam 1.5mg/ml (Frasco 15ml)", 
                "Antiinflamatorio oral para perros y gatos", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.FRASCO, 25, 5, 
                18000.0, 30000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(24), "LOT-2025-004");

        // Antiparasitarios
        crearProducto("MED-PAR-001", "Fenbendazol 500mg (Caja 6 tabletas)", 
                "Antiparasitario interno de amplio espectro", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 40, 8, 
                15000.0, 25000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(36), "LOT-2025-005");
        
        crearProducto("MED-PAR-002", "Praziquantel 50mg (Caja 4 tabletas)", 
                "Antiparasitario para tenias y cestodos", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 35, 7, 
                12000.0, 20000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(36), "LOT-2025-006");

        // Antihistamínicos
        crearProducto("MED-ALER-001", "Cetirizina 10mg (Caja 20 tabletas)", 
                "Antihistamínico para alergias y picaduras", 
                CategoriaProducto.MEDICAMENTO, UnidadMedida.CAJA, 30, 6, 
                18000.0, 30000.0, "Laboratorio Veterinario", 
                java.time.LocalDate.now().plusMonths(24), "LOT-2025-007");

        // ========== VACUNAS ==========
        crearProducto("VAC-001", "Vacuna Múltiple Canina (Dosis)", 
                "Vacuna contra moquillo, hepatitis, parvovirus y parainfluenza", 
                CategoriaProducto.VACUNA, UnidadMedida.UNIDAD, 50, 10, 
                15000.0, 30000.0, "Laboratorio Vacunas", 
                java.time.LocalDate.now().plusMonths(12), "VAC-2025-001");
        
        crearProducto("VAC-002", "Vacuna Antirrábica (Dosis)", 
                "Vacuna contra la rabia para perros y gatos", 
                CategoriaProducto.VACUNA, UnidadMedida.UNIDAD, 60, 12, 
                12000.0, 25000.0, "Laboratorio Vacunas", 
                java.time.LocalDate.now().plusMonths(12), "VAC-2025-002");
        
        crearProducto("VAC-003", "Vacuna Múltiple Felina (Dosis)", 
                "Vacuna contra panleucopenia, calicivirus y rinotraqueitis", 
                CategoriaProducto.VACUNA, UnidadMedida.UNIDAD, 45, 9, 
                16000.0, 32000.0, "Laboratorio Vacunas", 
                java.time.LocalDate.now().plusMonths(12), "VAC-2025-003");
        
        crearProducto("VAC-004", "Vacuna contra Leishmaniosis (Dosis)", 
                "Vacuna preventiva contra leishmaniosis canina", 
                CategoriaProducto.VACUNA, UnidadMedida.UNIDAD, 20, 4, 
                45000.0, 80000.0, "Laboratorio Vacunas", 
                java.time.LocalDate.now().plusMonths(12), "VAC-2025-004");

        // ========== MATERIAL MÉDICO ==========
        crearProducto("MAT-001", "Jeringas Desechables 3ml (Caja 100 unidades)", 
                "Jeringas desechables estériles de 3ml", 
                CategoriaProducto.MATERIAL, UnidadMedida.CAJA, 20, 4, 
                15000.0, 25000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-002", "Jeringas Desechables 5ml (Caja 100 unidades)", 
                "Jeringas desechables estériles de 5ml", 
                CategoriaProducto.MATERIAL, UnidadMedida.CAJA, 20, 4, 
                18000.0, 28000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-003", "Agujas 21G (Caja 100 unidades)", 
                "Agujas desechables estériles calibre 21G", 
                CategoriaProducto.MATERIAL, UnidadMedida.CAJA, 25, 5, 
                12000.0, 20000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-004", "Guantes Quirúrgicos Talla M (Caja 100 unidades)", 
                "Guantes de látex estériles talla mediana", 
                CategoriaProducto.MATERIAL, UnidadMedida.CAJA, 15, 3, 
                25000.0, 40000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-005", "Gasas Estériles 10x10cm (Paquete 50 unidades)", 
                "Gasas estériles para curaciones y procedimientos", 
                CategoriaProducto.MATERIAL, UnidadMedida.PAQUETE, 30, 6, 
                8000.0, 15000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-006", "Vendas Elásticas 10cm x 5m (Unidad)", 
                "Vendas elásticas para inmovilización y vendajes", 
                CategoriaProducto.MATERIAL, UnidadMedida.UNIDAD, 40, 8, 
                5000.0, 10000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-007", "Algodón Estéril 500g", 
                "Algodón estéril para limpieza y curaciones", 
                CategoriaProducto.MATERIAL, UnidadMedida.UNIDAD, 25, 5, 
                12000.0, 20000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-008", "Alcohol Isopropílico 1L", 
                "Alcohol para desinfección de material y superficies", 
                CategoriaProducto.MATERIAL, UnidadMedida.LITRO, 20, 4, 
                15000.0, 25000.0, "Proveedor Material Médico", null, null);
        
        crearProducto("MAT-009", "Clorhexidina 2% 500ml", 
                "Solución antiséptica para limpieza de heridas", 
                CategoriaProducto.MATERIAL, UnidadMedida.FRASCO, 15, 3, 
                18000.0, 30000.0, "Proveedor Material Médico", 
                java.time.LocalDate.now().plusMonths(24), "LOT-2025-008");
        
        crearProducto("MAT-010", "Sutura Nylon 3-0 (Caja 12 unidades)", 
                "Hilo de sutura no absorbible para cirugías", 
                CategoriaProducto.MATERIAL, UnidadMedida.CAJA, 10, 2, 
                35000.0, 60000.0, "Proveedor Material Médico", null, null);

        // ========== INSUMOS ==========
        crearProducto("INS-001", "Suero Fisiológico 500ml (Bolsa)", 
                "Solución salina estéril para fluidoterapia", 
                CategoriaProducto.INSUMO, UnidadMedida.UNIDAD, 30, 6, 
                8000.0, 15000.0, "Proveedor Insumos Médicos", 
                java.time.LocalDate.now().plusMonths(18), "LOT-2025-009");
        
        crearProducto("INS-002", "Ringer Lactato 500ml (Bolsa)", 
                "Solución de Ringer lactato para fluidoterapia", 
                CategoriaProducto.INSUMO, UnidadMedida.UNIDAD, 25, 5, 
                9000.0, 16000.0, "Proveedor Insumos Médicos", 
                java.time.LocalDate.now().plusMonths(18), "LOT-2025-010");
        
        crearProducto("INS-003", "Catéter Intravenoso 20G (Unidad)", 
                "Catéter para administración de fluidos intravenosos", 
                CategoriaProducto.INSUMO, UnidadMedida.UNIDAD, 50, 10, 
                3000.0, 6000.0, "Proveedor Insumos Médicos", null, null);
        
        crearProducto("INS-004", "Tubos de Sangre EDTA (Caja 100 unidades)", 
                "Tubos para toma de muestras de sangre con anticoagulante", 
                CategoriaProducto.INSUMO, UnidadMedida.CAJA, 10, 2, 
                45000.0, 75000.0, "Proveedor Insumos Médicos", null, null);
        
        crearProducto("INS-005", "Jeringas para Anestesia 10ml (Caja 50 unidades)", 
                "Jeringas especiales para administración de anestesia", 
                CategoriaProducto.INSUMO, UnidadMedida.CAJA, 15, 3, 
                20000.0, 35000.0, "Proveedor Insumos Médicos", null, null);

        // ========== ACCESORIOS ==========
        crearProducto("ACC-001", "Collar Elizabetano Talla M (Unidad)", 
                "Collar isabelino para prevenir lamido de heridas", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 30, 6, 
                12000.0, 25000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-002", "Collar Elizabetano Talla G (Unidad)", 
                "Collar isabelino grande para perros grandes", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 25, 5, 
                15000.0, 28000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-003", "Arnés Post-operatorio Talla M (Unidad)", 
                "Arnés para protección post-quirúrgica", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 20, 4, 
                18000.0, 32000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-004", "Bozal Ajustable Talla M (Unidad)", 
                "Bozal de malla ajustable para perros medianos", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 15, 3, 
                10000.0, 20000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-005", "Transportadora para Gatos (Unidad)", 
                "Transportadora plástica para gatos, tamaño mediano", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 10, 2, 
                35000.0, 60000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-006", "Plato de Alimentación Acero Inoxidable (Unidad)", 
                "Plato de acero inoxidable para perros y gatos", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 40, 8, 
                8000.0, 15000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-007", "Bebedero Automático (Unidad)", 
                "Bebedero automático con filtro para perros y gatos", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 20, 4, 
                25000.0, 45000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-008", "Cama Ortopédica Talla M (Unidad)", 
                "Cama ortopédica para perros con problemas articulares", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 8, 2, 
                120000.0, 200000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-009", "Juguetes Interactivos para Perros (Unidad)", 
                "Juguetes para estimulación mental y ejercicio", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 50, 10, 
                15000.0, 28000.0, "Proveedor Accesorios", null, null);
        
        crearProducto("ACC-010", "Rascador para Gatos (Unidad)", 
                "Rascador vertical con plataformas para gatos", 
                CategoriaProducto.ACCESORIO, UnidadMedida.UNIDAD, 15, 3, 
                45000.0, 75000.0, "Proveedor Accesorios", null, null);

        log.info("Productos iniciales del inventario creados exitosamente");
    }

    /**
     * Crea un producto si no existe
     */
    private void crearProducto(String codigo, String nombre, String descripcion,
                               CategoriaProducto categoria, UnidadMedida unidadMedida,
                               Integer stockActual, Integer stockMinimo,
                               Double precioCompra, Double precioVenta,
                               String proveedor, LocalDate fechaVencimiento, String lote) {
        productoRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    Producto producto = Producto.builder()
                            .codigo(codigo)
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .categoria(categoria)
                            .unidadMedida(unidadMedida)
                            .stockActual(stockActual)
                            .stockMinimo(stockMinimo)
                            .precioCompra(precioCompra)
                            .precioVenta(precioVenta)
                            .proveedor(proveedor)
                            .fechaVencimiento(fechaVencimiento)
                            .lote(lote)
                            .build();
                    producto.setIsActive(true);
                    return productoRepository.save(producto);
                });
    }
}
