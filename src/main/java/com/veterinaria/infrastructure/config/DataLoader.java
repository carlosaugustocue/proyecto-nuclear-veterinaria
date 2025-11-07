package com.veterinaria.infrastructure.config;

import com.veterinaria.application.repository.PermisoRepository;
import com.veterinaria.application.repository.RazaRepository;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.constants.PermisosDelSistema;
import com.veterinaria.domain.entity.patients.Raza;
import com.veterinaria.domain.entity.security.Permiso;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.domain.service.EncriptadorContrasena;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        // Permisos de FACTURACIÓN
        permisosMap.put(PermisosDelSistema.FACTURA_CREAR, 
                crearPermiso(PermisosDelSistema.FACTURA_CREAR, "Crear facturas", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_VER, 
                crearPermiso(PermisosDelSistema.FACTURA_VER, "Ver facturas", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_ANULAR, 
                crearPermiso(PermisosDelSistema.FACTURA_ANULAR, "Anular facturas", "FACTURACION"));
        permisosMap.put(PermisosDelSistema.FACTURA_DESCUENTO, 
                crearPermiso(PermisosDelSistema.FACTURA_DESCUENTO, "Aplicar descuentos", "FACTURACION"));

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
                permisos.get(PermisosDelSistema.FACTURA_VER),
                permisos.get(PermisosDelSistema.FACTURA_CREAR)
        );
        rolesMap.put("ROLE_RECEPCIONISTA", crearRol("ROLE_RECEPCIONISTA", "Recepcionista", 
                "Acceso a funciones de recepción", permisosRecepcionista));

        return rolesMap;
    }

    /**
     * Crea un rol si no existe
     */
    private Rol crearRol(String nombre, String nombreDisplay, String descripcion, List<Permiso> permisos) {
        return rolRepository.findByNombre(nombre)
                .orElseGet(() -> {
                    Rol rol = Rol.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .permisos(new ArrayList<>())
                            .build();
                    rol.setIsActive(true);
                    
                    // Agregar permisos
                    permisos.forEach(rol::agregarPermiso);
                    
                    return rolRepository.save(rol);
                });
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
}
