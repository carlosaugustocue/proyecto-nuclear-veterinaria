# ANÁLISIS DE CALIDAD DEL SOFTWARE - SISTEMA VETERINARIO

**Fecha de Análisis:** 03 de Noviembre de 2025  
**Proyecto:** Sistema de Gestión Veterinaria  
**Revisor:** Análisis Automático  
**Versión del Proyecto:** 0.1.0-SNAPSHOT  
**Módulo Evaluado:** Sistema Completo

---

## INTRODUCCIÓN

La calidad del software es fundamental para garantizar un producto robusto, mantenible y seguro. En el contexto del **Sistema de Gestión Veterinaria**, un sistema crítico que manejará información sensible de pacientes, historiales clínicos y datos financieros, aplicar buenas prácticas de calidad no es opcional sino imperativo.

Este análisis se enfoca en evaluar:
1. **Legibilidad del código:** Esencial para facilitar el mantenimiento y la colaboración en equipo
2. **Estructura y organización:** Crítico para la escalabilidad y arquitectura por capas
3. **Seguridad:** Vital dado que maneja datos médicos sensibles y información de clientes
4. **Implementación de patrones de diseño:** Necesario según los requerimientos del proyecto
5. **Cobertura de pruebas:** Garantizar la calidad funcional del sistema

---

## ASPECTOS A EVALUAR

### 1. Legibilidad del Código ⚠️
**Importancia:** Una buena legibilidad permite que el equipo de desarrollo pueda entender rápidamente la lógica del negocio, facilita el onboarding de nuevos desarrolladores y reduce el tiempo de debugging. En un sistema médico como este, la claridad del código puede ser la diferencia entre un error menor y uno crítico.

**Estado Actual:** PARCIAL
- ✅ Uso de nombres descriptivos en clases y métodos
- ⚠️ Falta comentarios JavaDoc en clases críticas
- ⚠️ Algunos métodos complejos sin documentación
- ❌ Constantes sin documentación explicativa

### 2. Estructura y Organización ✅⚠️
**Importancia:** Una buena estructura y organización permite mantener la arquitectura por capas requerida, facilita la aplicación de patrones de diseño, y hace que el código sea más escalable y mantenible conforme el sistema crece.

**Estado Actual:** BUENO CON MEJORAS NECESARIAS
- ✅ Arquitectura en capas bien definida (domain, application, infrastructure, presentation)
- ✅ Separación de responsabilidades clara
- ✅ Estructura de paquetes lógica
- ⚠️ Falta implementación explícita de patrones de diseño
- ❌ Falta paquetes para valueobjects, factory, strategy, etc.

### 3. Seguridad ✅⚠️
**Importancia:** Dado que el sistema maneja información médica sensible (HIPAA compliance potencial), datos personales de clientes y transacciones financieras, la seguridad es crítica. Un breach podría tener consecuencias legales y de reputación graves.

**Estado Actual:** BUENO PERO INCOMPLETO
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Autenticación con JWT
- ✅ Validación de roles y permisos básica
- ⚠️ Falta validación exhaustiva de inputs (@Valid parcial)
- ⚠️ Falta protección contra ataques comunes (rate limiting, CSRF)
- ❌ No hay auditoría completa de acciones sensibles
- ❌ Falta manejo de intentos de login fallidos

---

## LISTA DE CHEQUEO APLICADA

### 📋 Legibilidad del Código

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿El código está bien comentado? | | ✗ | | Faltan comentarios JavaDoc en servicios críticos |
| ¿Se utilizan nombres descriptivos? | ✓ | | | Buen uso de nombres en general |
| ¿Está bien indentado y estructurado? | ✓ | | | Formato consistente |

**Puntuación: 66% (2/3)**

### 📋 Estructura y Organización

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Funciones/métodos realizan única tarea? | ✓ | | | Principio de responsabilidad única aplicado |
| ¿Estructura de directorios clara? | ✓ | | | Arquitectura por capas bien definida |
| ¿Se evita duplicación (DRY)? | ✓ | | | Buen uso de reutilización |

**Puntuación: 100% (3/3)**

### 📋 Buenas Prácticas de Programación

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Se siguen convenciones de estilo? | ✓ | | | Java naming conventions aplicadas |
| ¿Se evitan 'magic numbers'? | | ✗ | | Algunos valores hardcodeados (timeouts, límites) |
| ¿Manejo adecuado de excepciones? | ✓ | | | GlobalExceptionHandler implementado |

**Puntuación: 66% (2/3)**

### 📋 Rendimiento

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Consultas a BD optimizadas? | | | ✓ | No hay consultas complejas aún |
| ¿Se evitan operaciones costosas en loops? | ✓ | | | Código eficiente en general |
| ¿Estructuras de datos adecuadas? | ✓ | | | Uso correcto de colecciones |

**Puntuación: 100% (2/2 aplicables)**

### 📋 Pruebas

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Existen pruebas unitarias principales? | | ✗ | | **CRÍTICO:** Muy pocas pruebas |
| ¿Cubren casos positivos y negativos? | | ✗ | | No hay cobertura suficiente |
| ¿Ejecución automática (CI/CD)? | | ✗ | | No configurado |

**Puntuación: 0% (0/3)** ❌

### 📋 Seguridad

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Validación/sanitización de inputs? | | ✗ | | Falta @Valid en varios endpoints |
| ¿Prácticas seguras datos sensibles? | ✓ | | | BCrypt para passwords |
| ¿Consideradas vulnerabilidades comunes? | | ✗ | | Sin protección XSS, CSRF, rate limiting |

**Puntuación: 33% (1/3)** ⚠️

### 📋 Documentación

| Item | Sí | No | N/A | Observaciones |
|------|----|----|-----|---------------|
| ¿Documentación configuración/despliegue? | | ✗ | | README básico, falta detalles |
| ¿Funciones/métodos documentados? | | ✗ | | Falta JavaDoc |
| ¿README explica proyecto y uso? | ✓ | | | Existe pero es básico |

**Puntuación: 33% (1/3)** ⚠️

---

## MÉTRICAS DE CALIDAD EN PRUEBAS FUNCIONALES

### 1. Cobertura de Requisitos
- **Total HUs Documentadas:** 56 ✅
- **HUs con pruebas asociadas:** 0 ❌
- **Porcentaje de cobertura:** 0% ❌
- **Priorización:** Realizada ✅

**Métrica:** 0% - Requisitos cubiertos por pruebas

### 2. Diseño de Pruebas
- **Casos de prueba estructurados:** ❌ No existen
- **Criterios de aceptación definidos:** ✅ En historias de usuario
- **Datos de prueba preparados:** ❌ No preparados

**Métrica:** 33% - Calidad del diseño de pruebas

### 3. Efectividad de Casos de Prueba
- **Tasa de éxito:** N/A (sin pruebas)
- **Tasa de defectos detectados:** N/A
- **Casos de prueba ejecutados:** 0

**Métrica:** 0% - Efectividad de pruebas

### 4. Reusabilidad y Modularidad
- **Tests parametrizados:** ❌ No implementados
- **Tests compartidos entre módulos:** ❌ No existen
- **Setup/teardown reutilizable:** ❌ No implementado

**Métrica:** 0% - Reusabilidad

### 5. Documentación y Comunicación
- **Calidad documentación pruebas:** N/A
- **Stakeholders que validan:** 0%
- **Informes de prueba:** ❌ No existen

**Métrica:** 0% - Documentación de pruebas

---

## ASPECTOS POSITIVOS ENCONTRADOS

### 1. Arquitectura Sólida ✅
El proyecto sigue una **arquitectura por capas bien definida** (Presentation → Application → Domain → Infrastructure), lo cual:
- Facilita el mantenimiento y escalabilidad
- Permite aplicar patrones de diseño de manera organizada
- Separa claramente las responsabilidades
- Cumple con principios SOLID

**Impacto:** Alta contribución a la calidad estructural del software.

### 2. Seguridad Base Implementada ✅
- **BCrypt para encriptación de contraseñas:** Protege credenciales de usuarios
- **JWT para autenticación:** Sistema stateless y escalable
- **Spring Security configurado:** Protección de endpoints por roles
- **GlobalExceptionHandler:** Manejo centralizado de errores sin exponer detalles sensibles

**Impacto:** Base sólida para la seguridad del sistema.

### 3. Separación de Responsabilidades ✅
- **DTOs separados de entidades:** No se exponen entidades directamente
- **Repositories bien definidos:** Abstracción de acceso a datos
- **Servicios con lógica de negocio:** Separada de controllers
- **Excepciones personalizadas:** Manejo específico de errores

**Impacto:** Código más mantenible y testeable.

### 4. Naming y Convenciones ✅
- Uso consistente de nomenclatura Java estándar
- Nombres descriptivos en clases, métodos y variables
- Estructura de paquetes lógica y clara

**Impacto:** Facilita la legibilidad y comprensión del código.

---

## HALLAZGOS - ASPECTOS CRÍTICOS

### 🔴 CRÍTICO 1: Ausencia de Pruebas Unitarias
**Descripción:** El proyecto prácticamente no tiene pruebas unitarias. Esto es crítico en un sistema médico.

**Impacto en Calidad:**
- ❌ No hay garantía de que el código funcione correctamente
- ❌ Los cambios pueden introducir regresiones sin detección
- ❌ Dificulta el refactoring seguro
- ❌ No hay documentación ejecutable del comportamiento esperado

**Riesgo:** **CRÍTICO** - Sistema no apto para producción sin tests

**Módulos sin cobertura:**
- AuthenticationService
- UsuarioService
- Todos los servicios de negocio
- Validaciones de seguridad
- Lógica de auditoría

### 🔴 CRÍTICO 2: Patrones de Diseño No Implementados
**Descripción:** Según el diagrama de clases, se requieren 10 patrones de diseño explícitos. Actualmente 0 están implementados formalmente.

**Impacto en Calidad:**
- ❌ No cumple con requisitos arquitectónicos del proyecto
- ❌ Código menos flexible y mantenible
- ❌ Dificulta implementación de funcionalidades complejas
- ❌ No aprovecha las ventajas de diseño orientado a objetos

**Riesgo:** **CRÍTICO** - Incumplimiento de especificaciones técnicas

**Patrones faltantes:**
1. Factory Method (Usuarios, Mascotas)
2. Singleton (GestorSesiones)
3. State Pattern (Estados de Cita)
4. Strategy Pattern (Precios, Notificaciones)
5. Builder Pattern (HistorialClinico)
6. Composite Pattern (Inventario)
7. Decorator Pattern (Servicios)
8. Proxy Pattern (Seguridad)
9. Observer Pattern (Eventos)
10. Template Method (ProcesoConsulta)

### 🔴 CRÍTICO 3: Funcionalidad Core No Implementada
**Descripción:** El 90% de las funcionalidades del negocio están sin implementar.

**Impacto en Calidad:**
- ❌ Sistema no funcional para el propósito principal
- ❌ No se puede validar requisitos de negocio
- ❌ Imposible realizar pruebas de integración

**Riesgo:** **CRÍTICO** - Sistema no viable

**Módulos faltantes:**
- Gestión de Pacientes/Mascotas (0%)
- Citas y Agenda (0%)
- Historia Clínica (0%)
- Inventario (0%)
- Facturación (0%)
- Notificaciones (0%)

### 🟡 ALTO 1: Seguridad Incompleta
**Descripción:** Aunque hay base de seguridad, faltan protecciones críticas.

**Impacto en Calidad:**
- ⚠️ Vulnerable a ataques de fuerza bruta (no hay rate limiting)
- ⚠️ Sin protección CSRF
- ⚠️ Validaciones de input incompletas
- ⚠️ No hay manejo de intentos fallidos de login

**Riesgo:** **ALTO** - Vulnerabilidades de seguridad

### 🟡 ALTO 2: Falta de Documentación
**Descripción:** El código carece de JavaDoc y documentación técnica adecuada.

**Impacto en Calidad:**
- ⚠️ Dificulta mantenimiento futuro
- ⚠️ Onboarding de nuevos desarrolladores más lento
- ⚠️ Riesgo de malinterpretación de lógica de negocio
- ⚠️ No hay documentación de API (Swagger no configurado)

**Riesgo:** **ALTO** - Mantenibilidad comprometida

### 🟡 MEDIO 1: Magic Numbers y Hardcoding
**Descripción:** Algunos valores están hardcodeados en el código.

**Impacto en Calidad:**
- ⚠️ Dificulta configuración y parametrización
- ⚠️ Reduce flexibilidad del sistema
- ⚠️ Complica testing con diferentes configuraciones

**Ejemplos encontrados:**
- Tiempos de expiración de tokens
- Límites de intentos de login
- Duraciones de sesión

**Riesgo:** **MEDIO** - Antipatrón de hardcoding

### 🟡 MEDIO 2: Value Objects No Implementados
**Descripción:** No hay implementación de Value Objects (Email, Telefono, Direccion, etc.)

**Impacto en Calidad:**
- ⚠️ Validaciones dispersas en múltiples lugares
- ⚠️ Posible inconsistencia en formato de datos
- ⚠️ Falta de encapsulación de lógica de validación

**Riesgo:** **MEDIO** - Diseño de dominio incompleto

---

## MÉTRICAS CONSOLIDADAS

### Puntuación por Categoría

| Categoría | Puntuación | Estado |
|-----------|-----------|---------|
| Legibilidad del Código | 66% | ⚠️ REGULAR |
| Estructura y Organización | 100% | ✅ EXCELENTE |
| Buenas Prácticas | 66% | ⚠️ REGULAR |
| Rendimiento | 100% | ✅ EXCELENTE |
| Pruebas | 0% | ❌ CRÍTICO |
| Seguridad | 33% | ❌ CRÍTICO |
| Documentación | 33% | ❌ CRÍTICO |

### Puntuación Global de Calidad

**CALIDAD GENERAL: 57% - INSUFICIENTE** ⚠️

**Desglose:**
- ✅ Aspectos Positivos: 30%
- ⚠️ Aspectos Regulares: 27%
- ❌ Aspectos Críticos: 43%

---

## ANÁLISIS DE RIESGOS

### Riesgos Críticos para Producción
1. **Sistema sin pruebas** → No apto para producción
2. **Funcionalidad core incompleta** → Sistema no viable
3. **Patrones no implementados** → Incumplimiento de especificaciones
4. **Seguridad incompleta** → Vulnerabilidades explotables

### Riesgos a Mediano Plazo
1. **Falta de documentación** → Deuda técnica creciente
2. **Sin CI/CD** → Despliegues manuales propensos a errores
3. **Value Objects faltantes** → Inconsistencia de datos
4. **Magic numbers** → Dificultad de configuración

---

## CONCLUSIONES

### 1. Arquitectura Sólida pero Implementación Incompleta
El proyecto tiene una **excelente base arquitectónica** con separación clara de capas y responsabilidades. Sin embargo, solo el **10% de la funcionalidad está implementada**, lo que hace que el sistema aún no sea viable.

### 2. Seguridad: Buena Base pero Incompleta
Aunque se han implementado mecanismos básicos de seguridad (BCrypt, JWT, Spring Security), **faltan protecciones críticas** como rate limiting, validación exhaustiva de inputs, y manejo de intentos fallidos. En un sistema médico, esto es **inaceptable**.

### 3. Ausencia Total de Pruebas: Riesgo Crítico
La **falta de pruebas unitarias** es el hallazgo más crítico. Un sistema que maneja información médica sensible **no puede ir a producción sin cobertura de pruebas**. Esto compromete la fiabilidad y calidad del software.

### 4. Patrones de Diseño: Requisito No Cumplido
El proyecto requiere **10 patrones de diseño específicos** según el diagrama de clases. Actualmente **ninguno está implementado explícitamente**. Esto es un incumplimiento directo de las especificaciones técnicas.

### 5. Documentación Insuficiente
La falta de JavaDoc, documentación de API (Swagger), y documentación técnica detallada **compromete la mantenibilidad** a largo plazo.

---

## RECOMENDACIONES Y PLAN DE ACCIÓN

### 🔴 ACCIONES INMEDIATAS (Prioridad Crítica)

#### 1. Implementar Pruebas Unitarias
**Objetivo:** Alcanzar mínimo 70% de cobertura en 2 semanas

**Acciones:**
- [ ] Configurar JaCoCo para medir cobertura
- [ ] Crear tests para AuthenticationService
- [ ] Crear tests para UsuarioService
- [ ] Crear tests para validaciones de seguridad
- [ ] Configurar CI/CD básico con GitHub Actions
- [ ] Establecer umbral mínimo de cobertura en build

**Herramientas:**
```xml
<!-- Agregar a pom.xml -->
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</dependency>
```

#### 2. Implementar Patrones de Diseño Faltantes
**Objetivo:** Implementar los 10 patrones requeridos

**Fase 1 (Seguridad):**
- [ ] **Singleton:** GestorSesiones explícito
- [ ] **Proxy:** ProxySeguro para servicios críticos
- [ ] **Factory Method:** FactoryUsuario

**Fase 2 (Negocio):**
- [ ] **Factory Method:** FactoryMascota
- [ ] **State Pattern:** Estados de Cita
- [ ] **Strategy Pattern:** EstrategiaPrecio y NotificacionStrategy

**Fase 3 (Complejidad):**
- [ ] **Builder Pattern:** BuilderHistorialClinico
- [ ] **Composite Pattern:** ComponenteInventario
- [ ] **Decorator Pattern:** DecoradorServicio
- [ ] **Template Method:** ProcesoConsulta
- [ ] **Observer Pattern:** EventosNotificacion

#### 3. Reforzar Seguridad
**Objetivo:** Cerrar vulnerabilidades críticas

**Acciones:**
- [ ] Implementar GestorIntentosFallidos
- [ ] Agregar rate limiting con Bucket4j
- [ ] Implementar @Valid en TODOS los endpoints
- [ ] Configurar CSRF protection
- [ ] Implementar auditoría completa de acciones sensibles
- [ ] Agregar validación de inputs personalizada

```java
// Ejemplo rate limiting
@RateLimiter(name = "login", fallbackMethod = "loginFallback")
public TokenSesion login(LoginRequest request) {
    // ...
}
```

### 🟡 ACCIONES A CORTO PLAZO (1-2 semanas)

#### 4. Implementar Value Objects
- [ ] Crear Email con validación
- [ ] Crear Telefono con formato
- [ ] Crear Direccion completa
- [ ] Crear SignosVitales
- [ ] Migrar campos primitivos a VOs

```java
public class Email {
    private final String valor;
    
    public Email(String email) {
        if (!esValido(email)) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.valor = email;
    }
    
    private boolean esValido(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
```

#### 5. Documentar Código
- [ ] Agregar JavaDoc a todas las clases públicas
- [ ] Documentar métodos complejos
- [ ] Configurar Springdoc OpenAPI
- [ ] Agregar @Operation y @Schema
- [ ] Crear README técnico detallado

```xml
<!-- Agregar Springdoc -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

#### 6. Eliminar Hardcoding
- [ ] Mover constantes a SecurityConstants
- [ ] Parametrizar en application.properties
- [ ] Crear ConfiguracionSistema entity
- [ ] Implementar @ConfigurationProperties

```properties
# application.properties
app.security.jwt.expiration=3600000
app.security.login.max-attempts=5
app.security.session.timeout=1800000
```

### 🟢 ACCIONES A MEDIANO PLAZO (3-4 semanas)

#### 7. Implementar Funcionalidad Core
Seguir el plan del INVENTARIO_DESARROLLO.md:
- [ ] Pacientes/Mascotas (HU-02, HU-09)
- [ ] Citas básico (HU-03, HU-26)
- [ ] Historia Clínica básica (HU-06, HU-07)
- [ ] Inventario básico (HU-04)
- [ ] Facturación básica (HU-05)

#### 8. Configurar CI/CD Completo
- [ ] GitHub Actions con build automático
- [ ] Tests automáticos en cada push
- [ ] Análisis de código con SonarCloud
- [ ] Deploy automático a staging
- [ ] Quality gates configurados

#### 9. Implementar Caché
- [ ] Configurar Spring Cache
- [ ] Cachear permisos de usuario
- [ ] Cachear configuración del sistema
- [ ] Estrategia de invalidación

### 📊 MÉTRICAS DE SEGUIMIENTO

Establecer las siguientes métricas para monitorear mejoras:

| Métrica | Actual | Objetivo Mes 1 | Objetivo Mes 2 | Objetivo Final |
|---------|--------|----------------|----------------|----------------|
| Cobertura de Tests | 0% | 40% | 70% | 85% |
| Patrones Implementados | 0/10 | 5/10 | 8/10 | 10/10 |
| HUs Implementadas | 4/56 (7%) | 15/56 (27%) | 30/56 (54%) | 56/56 (100%) |
| Vulnerabilidades Críticas | 4 | 2 | 0 | 0 |
| Deuda Técnica (horas) | ~500h | 400h | 200h | <50h |
| Calidad General | 57% | 70% | 85% | 95% |

---

## CRITERIOS DE ACEPTACIÓN PARA PRODUCCIÓN

El sistema NO debe ir a producción hasta cumplir:

### Requisitos Mínimos Obligatorios
- [ ] ✅ Cobertura de tests ≥ 70%
- [ ] ✅ Todos los patrones de diseño implementados
- [ ] ✅ Funcionalidad core completa (Pacientes, Citas, Historia)
- [ ] ✅ Seguridad reforzada (rate limiting, validaciones completas)
- [ ] ✅ Sin vulnerabilidades críticas o altas
- [ ] ✅ Documentación API completa (Swagger)
- [ ] ✅ CI/CD configurado con quality gates
- [ ] ✅ Auditoría completa de acciones sensibles
- [ ] ✅ Manejo robusto de errores y excepciones
- [ ] ✅ Configuración de base de datos MySQL

### Requisitos Deseables
- [ ] 🎯 Cobertura de tests ≥ 85%
- [ ] 🎯 Todas las HUs implementadas
- [ ] 🎯 Pruebas de integración
- [ ] 🎯 Pruebas de carga
- [ ] 🎯 Documentación técnica completa
- [ ] 🎯 Manual de usuario
- [ ] 🎯 Logging estructurado
- [ ] 🎯 Monitoreo y alertas

---

## ESTIMACIÓN DE ESFUERZO

### Resumen de Esfuerzo Requerido

| Categoría | Horas Estimadas | Prioridad |
|-----------|----------------|-----------|
| Tests Unitarios | 80h | 🔴 Crítica |
| Patrones de Diseño | 120h | 🔴 Crítica |
| Seguridad Reforzada | 40h | 🔴 Crítica |
| Funcionalidad Core | 300h | 🔴 Crítica |
| Value Objects | 24h | 🟡 Alta |
| Documentación | 40h | 🟡 Alta |
| CI/CD | 16h | 🟡 Alta |
| Refactoring | 60h | 🟢 Media |

**TOTAL ESTIMADO:** ~680 horas (~4.5 meses para 1 desarrollador)

---

## CONCLUSIÓN FINAL

El proyecto **Sistema de Gestión Veterinaria** tiene una **base arquitectónica excelente** pero se encuentra en una **fase muy inicial de desarrollo** (10% completado). 

### ⚠️ ESTADO ACTUAL: NO APTO PARA PRODUCCIÓN

**Fortalezas:**
- ✅ Arquitectura por capas bien diseñada
- ✅ Separación de responsabilidades clara
- ✅ Base de seguridad implementada
- ✅ Convenciones de código consistentes

**Debilidades Críticas:**
- ❌ Sin pruebas unitarias (0% cobertura)
- ❌ Patrones de diseño no implementados (0/10)
- ❌ 90% de funcionalidad core pendiente
- ❌ Seguridad incompleta con vulnerabilidades

### 🎯 PRÓXIMOS PASOS PRIORITARIOS

1. **INMEDIATO:** Implementar pruebas unitarias para módulo de seguridad
2. **SEMANA 1-2:** Implementar patrones de diseño críticos (Singleton, Factory, Proxy)
3. **SEMANA 3-4:** Reforzar seguridad y cerrar vulnerabilidades
4. **MES 2-3:** Desarrollar funcionalidad core (Pacientes, Citas, Historia Clínica)
5. **MES 4:** Testing exhaustivo, documentación y preparación para producción

**Con dedicación y siguiendo este plan de acción, el proyecto puede alcanzar un nivel de calidad aceptable para producción en aproximadamente 4-5 meses.**

---

**Documento generado:** 2025-11-03  
**Próxima revisión recomendada:** 2025-11-17 (2 semanas)
