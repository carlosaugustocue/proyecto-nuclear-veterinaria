

# ÉPICA 1: Gestión de Usuarios y Seguridad

**Objetivo:**
Permitir la administración segura de usuarios, roles y accesos al sistema.

---

## HU-01: Gestión de Usuarios

**Como** administrador del sistema
**Quiero** gestionar los usuarios del sistema (crear, editar, eliminar y asignar roles)
**Para** controlar el acceso y los permisos de cada miembro del equipo

**Criterios de Aceptación:**

* El sistema permite crear nuevos usuarios con información básica (nombre, email, contraseña, rol)
* Se pueden asignar roles específicos: Administrador, Veterinario, Recepcionista, Asistente
* Es posible editar la información de usuarios existentes
* Se puede desactivar o eliminar usuarios del sistema
* Los usuarios solo pueden acceder a funcionalidades permitidas según su rol
* El sistema valida que el email sea único
* Las contraseñas deben cumplir requisitos de seguridad (mínimo 8 caracteres, mayúsculas, números)
* Se registra un log de auditoría de cambios en usuarios

**Entradas:**
- Nombre (String)
- Correo electrónico (String)
- Contraseña (String)
- Rol asignado (Enum)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-12: Registro de Intentos Fallidos de Inicio de Sesión

**Como** administrador
**Quiero** que el sistema registre todos los intentos de inicio de sesión fallidos
**Para** poder auditar y prevenir accesos no autorizados

**Criterios de Aceptación:**

* El sistema registra cada intento fallido con fecha, hora, usuario y dirección IP
* Si un usuario falla el inicio más de 5 veces, se genera una alerta
* Los registros se almacenan de forma cronológica en la base de datos
* Solo los administradores pueden visualizar el historial de intentos fallidos
* Los datos deben ser exportables a un archivo CSV o PDF

**Entradas:**
- Usuario (String)
- Fecha y hora del intento (LocalDateTime)
- Dirección IP (String)

**Prioridad:** Alta
**Estimación:** 5 Story Points

---

## HU-13: Bloqueo Temporal por Exceso de Intentos

**Como** usuario del sistema
**Quiero** que mi cuenta se bloquee temporalmente tras varios intentos fallidos
**Para** proteger mi cuenta frente a intentos de acceso indebidos

**Criterios de Aceptación:**

* El sistema bloquea automáticamente una cuenta después de 5 intentos fallidos consecutivos
* El bloqueo tiene una duración de 15 minutos
* Se notifica al usuario vía correo electrónico el motivo y duración del bloqueo
* El administrador puede desbloquear manualmente la cuenta
* Los intentos fallidos se reinician después del bloqueo exitoso

**Entradas:**
- Usuario (String)
- Número de intentos fallidos (int)
- Tiempo de bloqueo (int)
- Correo electrónico para notificación (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-14: Recuperación de Contraseña mediante Correo Electrónico

**Como** usuario registrado
**Quiero** poder restablecer mi contraseña en caso de olvido
**Para** recuperar el acceso al sistema sin intervención del administrador

**Criterios de Aceptación:**

* El usuario puede solicitar recuperación ingresando su email
* Se envía un correo con enlace temporal de restablecimiento
* El enlace expira en 30 minutos
* El sistema exige ingresar una nueva contraseña cumpliendo las políticas de seguridad
* Se registra la fecha y hora del cambio de contraseña

**Entradas:**
- Correo electrónico (String)
- Nueva contraseña (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-15: Validación de Roles y Permisos en Tiempo Real

**Como** administrador del sistema
**Quiero** que los permisos se validen dinámicamente según el rol del usuario
**Para** garantizar que solo se acceda a las funciones correspondientes

**Criterios de Aceptación:**

* El sistema valida el rol antes de ejecutar cualquier acción sensible
* Las vistas y botones se adaptan según el perfil del usuario
* Si un usuario intenta acceder a una función restringida, se muestra un mensaje de error
* Los permisos pueden ser actualizados sin necesidad de cerrar sesión
* El registro de intentos de acceso no autorizado se almacena en la auditoría

**Entradas:**
- Rol del usuario (Enum)
- Acción solicitada (String)
- Permisos asociados (List<String>)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-16: Registro de Auditoría de Inicios de Sesión

**Como** administrador
**Quiero** visualizar un registro detallado de los accesos al sistema
**Para** tener trazabilidad de las acciones de los usuarios

**Criterios de Aceptación:**

* Se registra fecha, hora, usuario y tipo de acción (inicio, cierre, intento fallido)
* El sistema permite filtrar por usuario, fecha o tipo de evento
* Los registros no pueden ser editados ni eliminados
* Se pueden exportar en formato PDF o Excel
* Solo usuarios con rol de administrador pueden acceder a la auditoría completa

**Entradas:**
- Usuario (String)
- Fecha y hora (LocalDateTime)
- Tipo de acción (Enum)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-17: Notificación de Nuevos Accesos desde Dispositivos Desconocidos

**Como** usuario del sistema
**Quiero** recibir una notificación cuando se detecte un inicio de sesión desde un dispositivo nuevo
**Para** asegurarme de que mi cuenta no ha sido comprometida

**Criterios de Aceptación:**

* El sistema identifica el dispositivo por IP o huella de navegador
* Si el dispositivo no ha sido registrado, se envía una alerta al correo del usuario
* La notificación incluye ubicación aproximada, fecha y hora del acceso
* El usuario puede marcar el dispositivo como confiable
* Los dispositivos confiables quedan registrados en el perfil del usuario

**Entradas:**
- Usuario (String)
- Dirección IP o huella del navegador (String)
- Ubicación aproximada (String)
- Fecha y hora del acceso (LocalDateTime)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-18: Gestión de Sesiones Activas por Usuario

**Como** administrador
**Quiero** poder visualizar y controlar las sesiones activas de todos los usuarios
**Para** garantizar el uso correcto y seguro del sistema

**Criterios de Aceptación:**

* El sistema muestra en tiempo real las sesiones activas por usuario, con IP y hora de inicio
* El administrador puede cerrar sesiones activas manualmente
* Si un usuario inicia sesión en otro dispositivo, la sesión anterior se cierra automáticamente
* Los usuarios pueden visualizar sus propias sesiones desde el perfil
* Se registra en la auditoría cualquier cierre de sesión forzado

**Entradas:**
- Usuario (String)
- Dirección IP (String)
- Hora de inicio de sesión (LocalDateTime)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

---

# ÉPICA 2: Gestión de Pacientes

**Objetivo:**
Registrar, consultar y mantener la información de pacientes y sus propietarios de manera segura, actualizada y trazable.

---

## HU-02: Gestión de Pacientes

**Como** recepcionista o veterinario
**Quiero** registrar y mantener actualizada la información de los pacientes (mascotas)
**Para** tener un registro completo y organizado de cada animal

**Criterios de Aceptación:**

* Se puede registrar un nuevo paciente con: nombre, especie, raza, fecha de nacimiento, sexo, color, peso
* Cada paciente está vinculado a un propietario (cliente)
* Es posible editar la información del paciente en cualquier momento
* Se puede buscar pacientes por nombre, especie o propietario
* El sistema permite adjuntar fotos del paciente
* Se muestra el historial resumido del paciente (última visita, próxima cita, estado)
* Se puede marcar pacientes como inactivos o fallecidos
* Se valida que los campos obligatorios estén completos

**Entradas:**
- Nombre del paciente (String)
- Especie (Enum)
- Raza (String)
- Fecha de nacimiento (Date)
- Sexo (Enum)
- Color (String)
- Peso (double)
- Propietario asociado (String)
- Foto del paciente (File)

**Prioridad:** Alta
**Estimación:** 13 Story Points

---

## HU-09: Tipos de Mascotas

**Como** administrador o recepcionista
**Quiero** gestionar un catálogo de tipos de mascotas (especies y razas)
**Para** estandarizar la información al registrar pacientes

**Criterios de Aceptación:**

* Se puede crear, editar y eliminar especies (perro, gato, ave, reptil, etc.)
* Para cada especie se pueden definir múltiples razas
* El sistema incluye un catálogo predefinido de especies y razas comunes
* Es posible agregar razas personalizadas o “mestizo”
* Al registrar un paciente, se selecciona especie y raza de listas desplegables
* El sistema permite buscar y filtrar pacientes por tipo de mascota
* Se pueden generar estadísticas por especie y raza
* Las razas obsoletas se pueden desactivar sin eliminar registros históricos

**Entradas:**
- Especie (Enum)
- Raza (String)
- Estado (Enum)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-19: Búsqueda Avanzada de Pacientes por Filtros

**Como** veterinario o recepcionista
**Quiero** realizar búsquedas avanzadas de pacientes mediante múltiples filtros
**Para** encontrar información específica de forma rápida y eficiente

**Criterios de Aceptación:**

* Se pueden aplicar filtros por nombre, especie, raza, propietario o estado (activo/inactivo)
* Los resultados se muestran en una tabla con columnas personalizables
* Se puede exportar el resultado de la búsqueda a PDF o Excel
* El sistema permite ordenar por fecha de registro o última consulta
* Los filtros se pueden combinar y guardar como “búsquedas frecuentes”

**Entradas:**
- Nombre del paciente (String)
- Especie (Enum)
- Raza (String)
- Propietario (String)
- Estado (Enum)
- Fecha de registro o última consulta (Date)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-20: Asociación Automática Paciente–Propietario

**Como** recepcionista
**Quiero** asociar automáticamente un paciente a su propietario existente
**Para** evitar registros duplicados y mantener la base de datos organizada

**Criterios de Aceptación:**

* El sistema sugiere propietarios existentes según nombre o número de contacto
* Si el propietario no existe, se permite crear uno nuevo desde el mismo formulario
* Cada paciente debe estar vinculado a un propietario único
* La relación paciente-propietario se muestra en la ficha clínica
* Si se cambia de propietario, el sistema conserva el historial previo del paciente

**Entradas:**
- Nombre o contacto del propietario (String)
- Datos del paciente (Object)
- Asociación paciente-propietario (boolean)


**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-21: Historial Breve de Consultas en la Ficha del Paciente

**Como** veterinario
**Quiero** visualizar un resumen del historial clínico reciente del paciente
**Para** tener contexto antes de atender una nueva consulta

**Criterios de Aceptación:**

* En la ficha del paciente se muestra un historial breve con últimas 3 consultas
* Cada entrada incluye fecha, diagnóstico y tratamiento
* Se puede acceder al historial clínico completo con un clic
* Los datos se cargan automáticamente desde el módulo de historia clínica
* Solo usuarios con rol médico o administrativo pueden acceder a esta vista

**Entradas:**
- Identificador del paciente (int)
- Fecha (Date)
- Diagnóstico (String)
- Tratamiento (String)

**Prioridad:** Alta
**Estimación:** 5 Story Points

---

## HU-22: Control de Acceso por Rol a la Información del Paciente

**Como** administrador
**Quiero** definir qué roles pueden ver o editar la información de los pacientes
**Para** proteger la confidencialidad de los datos médicos y personales

**Criterios de Aceptación:**

* Los recepcionistas pueden ver información básica, pero no modificar historial clínico
* Los veterinarios pueden editar información médica y ver datos del propietario
* Los administradores tienen acceso total
* Los intentos de acceso no autorizado se registran en la auditoría
* Los permisos se pueden modificar desde la configuración del sistema

**Entradas:**
- Rol del usuario (Enum)
- Permisos de acceso (List<String>)
- Módulo o sección del sistema (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-23: Registro de Mascotas Fallecidas o Inactivas

**Como** veterinario
**Quiero** registrar el estado de las mascotas como fallecidas o inactivas
**Para** mantener actualizado el censo y evitar programar citas innecesarias

**Criterios de Aceptación:**

* Se puede marcar una mascota como fallecida o inactiva desde su ficha
* El sistema solicita motivo y fecha del cambio de estado
* Las mascotas inactivas no aparecen en búsquedas por defecto
* Se pueden reactivar pacientes inactivos si es necesario
* Los registros de mascotas fallecidas permanecen en la base de datos

**Entradas:**
- Estado del paciente (Enum)
- Motivo del cambio (String)
- Fecha de modificación (Date)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-24: Exportación del Perfil del Paciente a PDF

**Como** veterinario o administrador
**Quiero** exportar la información completa del paciente a un archivo PDF
**Para** compartir o respaldar su historial de manera formal

**Criterios de Aceptación:**

* El sistema permite exportar la ficha completa del paciente (datos, propietario, historial breve, citas)
* El PDF incluye el logotipo de la clínica y fecha de generación
* Los campos vacíos no se muestran en el documento
* Solo usuarios con permisos específicos pueden generar la exportación
* El documento se guarda automáticamente en la carpeta de reportes

**Entradas:**
- Identificador del paciente (int)
- Formato de exportación (Enum)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

---

# ÉPICA 3: Gestión de Citas y Agenda

**Objetivo:**
Facilitar la programación, modificación y control de citas veterinarias con recordatorios automáticos y disponibilidad en tiempo real.

---

## HU-03: Gestión de Citas

**Como** recepcionista
**Quiero** programar, reagendar y cancelar citas para los pacientes
**Para** organizar eficientemente la agenda de los veterinarios

**Criterios de Aceptación:**

* Se puede crear una cita seleccionando: fecha, hora, paciente, veterinario, tipo de servicio y motivo
* El sistema muestra la disponibilidad del veterinario en tiempo real
* Es posible reagendar una cita existente
* Se puede cancelar una cita con registro del motivo
* El sistema envía recordatorios automáticos al cliente (24 horas antes)
* Se visualiza la agenda diaria, semanal y mensual
* Se pueden filtrar citas por estado: Programada, En curso, Completada, Cancelada
* El sistema alerta sobre conflictos de horario
* Se permite agregar notas adicionales a la cita

**Entradas:**
- Fecha (Date)
- Hora (String)
- Paciente (String)
- Veterinario (String)
- Tipo de servicio (Enum)
- Motivo de la cita (String)
- Estado de la cita (Enum)
- Notas adicionales (String)

**Prioridad:** Alta
**Estimación:** 13 Story Points

---

## HU-25: Reagendamiento de Citas con Notificación Automática

**Como** recepcionista
**Quiero** modificar la fecha, hora o veterinario asignado de una cita
**Para** ajustar la agenda cuando ocurran imprevistos

**Criterios de Aceptación:**

* Solo se pueden reagendar citas con al menos 24 horas de anticipación
* El sistema notifica automáticamente al cliente y al veterinario afectados
* Se registra el motivo de la modificación
* La disponibilidad del nuevo horario se valida antes de confirmar
* La cita actualizada mantiene el historial de cambios realizados

**Entradas:**
- Identificador de la cita (int)
- Nueva fecha y hora (LocalDateTime)
- Nuevo veterinario asignado (String)
- Motivo de reagendamiento (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-26: Verificación de Disponibilidad del Veterinario

**Como** recepcionista
**Quiero** que el sistema verifique la disponibilidad del veterinario al agendar una cita
**Para** evitar conflictos o solapamientos en el horario

**Criterios de Aceptación:**

* El sistema muestra los horarios ocupados y disponibles en una vista de calendario
* No se pueden agendar citas en horarios ya reservados
* Si el veterinario tiene permisos de descanso o vacaciones, no aparece como disponible
* Los horarios se actualizan automáticamente al confirmar o cancelar citas
* Los administradores pueden forzar la asignación en caso de emergencia

**Entradas:**
- Veterinario (String)
- Fecha y hora solicitadas (LocalDateTime)

**Prioridad:** Alta
**Estimación:** 5 Story Points

---

## HU-27: Cancelación de Citas con Registro de Motivo

**Como** recepcionista o veterinario
**Quiero** cancelar una cita indicando el motivo
**Para** mantener trazabilidad y comunicación con el cliente

**Criterios de Aceptación:**

* La cancelación requiere motivo obligatorio (ej. indisponibilidad, cliente ausente, reprogramación)
* El sistema envía notificación inmediata al cliente con el motivo
* Las citas canceladas no pueden ser modificadas, solo reprogramadas
* El registro de cancelación se guarda en el historial del paciente
* Se puede generar un reporte de citas canceladas por período

**Entradas:**
- Identificador de la cita (int)
- Motivo de cancelación (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-28: Notificaciones Automáticas por WhatsApp o Correo

**Como** cliente
**Quiero** recibir recordatorios automáticos de mis citas
**Para** no olvidar la fecha y hora de atención

**Criterios de Aceptación:**

* Se envían notificaciones 24 horas y 1 hora antes de la cita
* El mensaje incluye: nombre del paciente, hora, veterinario y tipo de servicio
* El usuario puede elegir recibir notificaciones por WhatsApp, correo o ambas
* Los mensajes enviados se registran en la base de datos
* El sistema no reenvía notificaciones duplicadas

**Entradas:**
- Canal de notificación (Enum)
- Nombre del paciente (String)
- Hora de cita (String)
- Veterinario asignado (String)
- Tipo de servicio (Enum)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-29: Restricción de Horarios No Hábiles

**Como** recepcionista
**Quiero** que el sistema restrinja el agendamiento en horarios no hábiles
**Para** garantizar que las citas se programen solo dentro del horario de atención

**Criterios de Aceptación:**

* Los horarios hábiles se configuran desde el módulo de parámetros del sistema
* El sistema impide agendar citas fuera de esos rangos
* Se muestra un mensaje explicativo cuando se intenta reservar fuera del horario válido
* Los feriados o días no laborables se excluyen automáticamente
* Solo los administradores pueden crear excepciones

**Entradas:**
- Horario hábil configurado (List<String>)
- Fecha y hora de la cita (LocalDateTime)
- Día no laborable (boolean)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-30: Agenda Visual (Diaria, Semanal, Mensual)

**Como** veterinario o recepcionista
**Quiero** visualizar las citas en un calendario interactivo
**Para** facilitar la gestión de la agenda y evitar solapamientos

**Criterios de Aceptación:**

* Se puede cambiar la vista entre día, semana y mes
* Cada cita muestra color y estado (Programada, En curso, Completada, Cancelada)
* Se puede filtrar por veterinario o tipo de servicio
* Al hacer clic en una cita, se abre el detalle completo
* Las citas pasadas se muestran en tono gris, las próximas en color activo

**Entradas:**
- Vista seleccionada (Enum)
- Filtro por veterinario o servicio (String)


**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-31: Recordatorio de Citas para Veterinarios y Clientes

**Como** veterinario o cliente
**Quiero** recibir recordatorios automáticos de mis próximas citas
**Para** estar informado y evitar ausencias o retrasos

**Criterios de Aceptación:**

* Los veterinarios reciben un resumen diario de sus citas programadas
* Los clientes reciben recordatorios personalizados por canal preferido
* El sistema permite configurar la hora de envío del recordatorio
* Se guarda el historial de recordatorios enviados
* Si una cita cambia de horario, se envía un nuevo aviso actualizado

**Entradas:**
- Usuario (String)
- Canal de notificación (Enum)
- Hora de envío del recordatorio (LocalTime)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

---

# ÉPICA 4: Atención Médica y Historial Clínico

**Objetivo:**
Registrar, consultar y mantener toda la información clínica de los pacientes de forma estructurada, segura y trazable.

---

## HU-06: Historial Clínico

**Como** veterinario
**Quiero** registrar y consultar el historial clínico completo de cada paciente
**Para** brindar una atención médica informada y dar seguimiento a tratamientos

**Criterios de Aceptación:**

* Se puede crear una entrada de historial médico con: fecha, motivo, diagnóstico, tratamiento, observaciones
* Cada entrada se vincula a una consulta o visita específica
* Es posible registrar signos vitales (temperatura, peso, frecuencia cardíaca, frecuencia respiratoria)
* Se pueden adjuntar archivos (radiografías, análisis de laboratorio, imágenes)
* El sistema muestra el historial cronológico completo del paciente
* Solo veterinarios autorizados pueden crear y editar entradas médicas
* Se puede generar un reporte PDF del historial completo
* El historial incluye vacunas, desparasitaciones y cirugías realizadas

**Entradas:**
- Fecha (Date)
- Motivo de consulta (String)
- Diagnóstico (String)
- Tratamiento (String)
- Observaciones (String)
- Signos vitales (Object)
- Archivos adjuntos (List<File>)

**Prioridad:** Alta
**Estimación:** 13 Story Points

---

## HU-07: Prestación de Servicios y Consulta

**Como** veterinario
**Quiero** registrar los servicios prestados durante una consulta o procedimiento
**Para** documentar el trabajo realizado y generar los cobros correspondientes

**Criterios de Aceptación:**

* Se puede iniciar una consulta desde una cita programada
* El sistema permite seleccionar múltiples servicios de un catálogo predefinido
* Se pueden agregar servicios personalizados con descripción y precio
* Cada servicio se registra con fecha, hora, veterinario responsable y observaciones
* Los servicios se vinculan automáticamente al historial del paciente
* Se puede prescribir medicamentos que se descuentan automáticamente del inventario
* El sistema calcula el costo total de los servicios prestados
* Los servicios registrados se pueden facturar directamente
* Se registra el tiempo de inicio y fin de la consulta

**Entradas:**
- Cita asociada (int)
- Servicios prestados (List<String>)
- Descripción y precio del servicio (String)
- Veterinario responsable (String)
- Medicamentos prescritos (List<String>)
- Hora de inicio y fin de consulta (LocalDateTime)

**Prioridad:** Alta
**Estimación:** 13 Story Points

---

## HU-11: Triage

**Como** asistente veterinario o enfermero
**Quiero** registrar el triage y los signos vitales del paciente al ingresar
**Para** priorizar la atención según la gravedad del caso

**Criterios de Aceptación:**

* Se puede registrar temperatura, pulso, frecuencia respiratoria, nivel de conciencia y estado general
* El sistema asigna un color de prioridad (rojo, amarillo o verde) según los valores ingresados
* Los datos de triage se asocian automáticamente a la historia clínica del paciente
* Los registros no pueden ser modificados una vez guardados
* Solo personal autorizado puede realizar y validar el triage

**Entradas:**
- Temperatura (double)
- Pulso (int)
- Frecuencia respiratoria (int)
- Nivel de conciencia (Enum)
- Estado general (Enum)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-32: Registro de Diagnósticos y Tratamientos

**Como** veterinario
**Quiero** registrar los diagnósticos y tratamientos aplicados durante la consulta
**Para** mantener un seguimiento clínico claro y documentado

**Criterios de Aceptación:**

* Se pueden agregar múltiples diagnósticos por paciente
* Los diagnósticos se validan con un catálogo de patologías predefinido
* Se pueden prescribir medicamentos, dosis y duración del tratamiento
* El tratamiento queda vinculado al historial clínico y a la consulta
* Se puede generar un resumen impreso del diagnóstico y tratamiento

**Entradas:**
- Diagnóstico (String)
- Tratamiento (String)
- Medicamento (String)
- Dosis (String)
- Duración (int)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-33: Registro de Evolución Médica

**Como** veterinario tratante
**Quiero** registrar la evolución médica del paciente después de cada control
**Para** dar seguimiento al progreso del tratamiento y ajustar decisiones clínicas

**Criterios de Aceptación:**

* Se puede registrar una evolución con fecha, observaciones y usuario responsable
* Cada evolución se vincula a un caso o tratamiento previo
* Los registros no pueden ser editados, solo se pueden agregar nuevas observaciones
* El sistema muestra una línea de tiempo cronológica de evoluciones
* Solo el veterinario tratante o supervisor puede registrar la evolución

**Entradas:**
- Fecha (Date)
- Observaciones (String)
- Usuario responsable (String)
- Caso o tratamiento asociado (String)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-34: Adjuntar Documentos Clínicos

**Como** veterinario
**Quiero** subir documentos médicos (radiografías, exámenes, informes)
**Para** centralizar toda la información clínica del paciente en un solo lugar

**Criterios de Aceptación:**

* Se pueden adjuntar archivos en formato PDF, JPG o PNG
* Cada documento se asocia a una consulta o evolución específica
* Los archivos deben estar firmados digitalmente por el veterinario
* Solo usuarios con permisos clínicos pueden visualizar o descargar los documentos
* Los documentos se almacenan con trazabilidad (fecha, hora, autor)

**Entradas:**
- Archivo (File)
- Consulta o evolución asociada (int)
- Firma digital del veterinario (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-35: Consentimiento Clínico Digital

**Como** propietario del paciente
**Quiero** firmar electrónicamente el consentimiento para procedimientos médicos
**Para** autorizar tratamientos de forma rápida y segura

**Criterios de Aceptación:**

* El propietario puede firmar digitalmente desde la app o portal web
* El consentimiento se vincula automáticamente al historial clínico del paciente
* No se puede iniciar un procedimiento sin consentimiento firmado
* La firma incluye nombre, fecha y hora de registro
* Los consentimientos firmados no pueden eliminarse ni modificarse

**Entradas:**
- Firma electrónica del propietario (String)
- Nombre (String)
- Fecha y hora de registro (LocalDateTime)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-36: Firma Electrónica y Trazabilidad de Cambios

**Como** administrador o auditor médico
**Quiero** mantener trazabilidad completa de las modificaciones en la historia clínica
**Para** garantizar la integridad y seguridad de los registros médicos

**Criterios de Aceptación:**

* Toda modificación genera un registro con usuario, fecha, hora y tipo de cambio
* Los documentos firmados electrónicamente no pueden ser alterados
* Los cambios quedan visibles en un log de auditoría clínica
* El sistema permite filtrar auditorías por paciente, usuario o fecha
* Los datos de auditoría solo pueden ser consultados por perfiles autorizados

**Entradas:**
- Usuario (String)
- Fecha y hora (LocalDateTime)
- Tipo de cambio (Enum)
- Paciente asociado (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---
---

# ÉPICA 5: Gestión Financiera e Inventario

**Objetivo:**
Controlar las operaciones de facturación, compras, stock y vencimientos de medicamentos e insumos, garantizando la trazabilidad financiera y operativa de la clínica.

---

## HU-04: Inventario

**Como** veterinario o administrador
**Quiero** gestionar el inventario de medicamentos, insumos y productos
**Para** mantener un control adecuado del stock y evitar desabastecimientos

**Criterios de Aceptación:**

* Se pueden registrar productos con: nombre, código, categoría, cantidad, precio, fecha de vencimiento
* El sistema actualiza automáticamente el stock cuando se utiliza o vende un producto
* Se generan alertas cuando un producto alcanza el stock mínimo
* Se pueden realizar ajustes de inventario con justificación
* Es posible generar reportes de movimientos de inventario
* Se muestran alertas de productos próximos a vencer (30 días antes)
* Se puede buscar productos por nombre, código o categoría
* El sistema registra el historial de movimientos por producto

**Entradas:**
- Nombre del producto (String)
- Código (String)
- Categoría (String)
- Cantidad (int)
- Precio (double)
- Fecha de vencimiento (Date)
- Stock mínimo (int)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-05: Facturación

**Como** recepcionista o administrador
**Quiero** generar facturas por los servicios y productos vendidos
**Para** llevar un control financiero de la clínica

**Criterios de Aceptación:**

* Se puede crear una factura seleccionando servicios prestados y productos vendidos
* La factura se asocia automáticamente a un cliente y paciente
* El sistema calcula automáticamente subtotales, descuentos, impuestos y total
* Es posible aplicar descuentos por porcentaje o monto fijo
* Se pueden imprimir o enviar facturas por email en formato PDF
* El sistema registra el método de pago (efectivo, tarjeta, transferencia)
* Se puede consultar el historial de facturas por cliente o fecha
* Es posible anular facturas con registro de motivo
* Se generan reportes de ventas diarias, mensuales y anuales

**Entradas:**
- Servicios prestados (List<String>)
- Productos vendidos (List<String>)
- Cliente (String)
- Método de pago (Enum)
- Descuento aplicado (double)
- Impuestos (double)
- Total de la factura (double)


**Prioridad:** Alta
**Estimación:** 13 Story Points

---

## HU-37: Registro de Ingresos de Inventario

**Como** administrador o encargado de compras
**Quiero** registrar los nuevos ingresos de medicamentos e insumos
**Para** mantener actualizado el inventario con trazabilidad de lotes y proveedores

**Criterios de Aceptación:**

* Se pueden ingresar productos con lote, cantidad, proveedor, fecha de vencimiento y código ICA
* El sistema valida que todos los campos obligatorios estén completos
* Cada lote se registra de forma independiente con fecha de ingreso
* Los medicamentos vencidos se marcan automáticamente como no disponibles
* Los proveedores deben estar registrados previamente en el sistema

**Entradas:**
- Producto (String)
- Lote (String)
- Cantidad (int)
- Proveedor (String)
- Fecha de vencimiento (Date)
- Código ICA (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-38: Alertas de Vencimiento de Productos

**Como** veterinario o encargado de inventario
**Quiero** recibir alertas sobre productos próximos a vencer
**Para** evitar el uso o venta de medicamentos vencidos

**Criterios de Aceptación:**

* El sistema muestra alertas visuales por color (verde, amarillo, rojo) según proximidad de vencimiento
* Se generan notificaciones automáticas al responsable del área
* Los productos vencidos se bloquean para su asignación
* Las alertas se reflejan también en los reportes de inventario
* Las notificaciones se registran con fecha y responsable

**Entradas:**
- Producto (String)
- Fecha de vencimiento (Date)
- Responsable del área (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-39: Asignación Automática de Medicamentos a Pacientes

**Como** veterinario
**Quiero** asignar medicamentos a los pacientes directamente desde el inventario
**Para** agilizar el proceso de prescripción y mantener actualizado el stock

**Criterios de Aceptación:**

* Se pueden seleccionar medicamentos disponibles desde el módulo de historia clínica
* Al asignar un medicamento, el sistema descuenta automáticamente del stock
* Se valida que el producto no esté vencido o bloqueado
* El registro queda vinculado al tratamiento y consulta del paciente
* Solo usuarios autorizados pueden realizar esta operación

**Entradas:**
- Medicamento seleccionado (String)
- Paciente (String)
- Consulta asociada (int)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-40: Registro de Bajas de Inventario

**Como** administrador o encargado de inventario
**Quiero** registrar las bajas de productos con justificación y responsable
**Para** mantener un control transparente sobre pérdidas o descartes

**Criterios de Aceptación:**

* Se deben registrar motivo, cantidad y responsable de la baja
* El sistema rechaza bajas sin justificación o sin firma responsable
* Los productos dados de baja se eliminan del stock activo, pero permanecen en el historial
* Se puede consultar el reporte de bajas por fecha o motivo
* Las bajas no justificadas no pueden eliminarse del registro

**Entradas:**
- Producto (String)
- Cantidad (int)
- Motivo de baja (String)
- Responsable de la baja (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-41: Control de Acceso por Rol a la Gestión de Medicamentos

**Como** administrador
**Quiero** restringir las operaciones de inventario y medicamentos según el rol del usuario
**Para** garantizar que solo el personal autorizado realice modificaciones

**Criterios de Aceptación:**

* Solo veterinarios pueden asignar medicamentos a pacientes
* Los recepcionistas pueden consultar el inventario, pero no editarlo
* Los administradores tienen acceso completo a la gestión de productos
* Los intentos de acceso no autorizado se registran en el log del sistema
* Los permisos pueden actualizarse dinámicamente desde la configuración

**Entradas:**
- Rol del usuario (Enum)
- Permisos de inventario (List<String>)
- Intentos de acceso (int)


**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-42: Reporte Financiero y de Inventario

**Como** administrador
**Quiero** generar reportes financieros e inventarios detallados
**Para** analizar ventas, movimientos y stock de forma consolidada

**Criterios de Aceptación:**

* Se pueden generar reportes de ventas por período, producto o servicio
* El sistema muestra ingresos, egresos, descuentos e impuestos
* Se pueden exportar los reportes en PDF o Excel
* El reporte de inventario incluye estado de stock, productos vencidos y bajas registradas
* Solo usuarios con rol contable o administrativo pueden acceder a esta sección

**Entradas:**
- Período del reporte (String)
- Producto o servicio (String)
- Formato de exportación (Enum)

**Prioridad:** Alta
**Estimación:** 13 Story Points

---
---

# ÉPICA 6: Configuración y Parametrización del Sistema

**Objetivo:**
Permitir la personalización del sistema, ajustando parámetros operativos, roles, horarios, impuestos y notificaciones de acuerdo con las necesidades de la clínica veterinaria.

---

## HU-08: Configuración del Sistema

**Como** administrador
**Quiero** configurar parámetros generales del sistema
**Para** personalizar el funcionamiento según las necesidades de la clínica

**Criterios de Aceptación:**

* Se pueden configurar datos de la clínica (nombre, dirección, teléfono, logo)
* Es posible definir horarios de atención por día de la semana
* Se pueden configurar tipos de servicios con precios base
* El sistema permite personalizar categorías de productos para inventario
* Se puede configurar el formato de facturas y documentos
* Es posible definir niveles de stock mínimo por categoría
* Se pueden establecer porcentajes de impuestos aplicables
* El sistema permite configurar notificaciones por email y SMS
* Se puede definir la duración estándar de las citas por tipo de servicio

**Entradas:**
- Datos de la clínica (Object)
- Horarios de atención (List<String>)
- Tipos de servicio (List<String>)
- Categorías de productos (List<String>)
- Formato de factura (Enum)
- Impuestos aplicables (double)
- Configuración de notificaciones (List<String>)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-43: Configuración de Tipos de Servicio y Precios Base

**Como** administrador
**Quiero** definir los tipos de servicios y precios base de la clínica
**Para** mantener uniformidad y control en la prestación de servicios

**Criterios de Aceptación:**

* Se pueden crear, editar y eliminar tipos de servicios (consulta, vacunación, cirugía, etc.)
* Cada servicio tiene un precio base configurable
* Los servicios pueden asociarse a categorías para reportes
* Los cambios se reflejan automáticamente en el módulo de facturación y agenda
* Solo usuarios con rol de administrador pueden modificar precios

**Entradas:**
- Tipo de servicio (String)
- Precio base (double)
- Categoría asociada (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-44: Definición de Horarios de Atención y Días No Laborales

**Como** administrador
**Quiero** establecer los horarios de atención, días hábiles y feriados
**Para** que el sistema limite las citas a los horarios disponibles

**Criterios de Aceptación:**

* Se pueden definir horarios de apertura y cierre para cada día
* Se pueden marcar días no laborales y festivos
* Las citas no pueden agendarse fuera del horario configurado
* El sistema muestra advertencias cuando se intenta reservar en un día bloqueado
* Los horarios pueden actualizarse en tiempo real sin reiniciar el sistema

**Entradas:**
- Día de la semana (Enum)
- Horario de apertura y cierre (String)
- Días no laborales o festivos (List<Date>)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-45: Parametrización de Impuestos y Descuentos

**Como** administrador o encargado financiero
**Quiero** configurar impuestos y descuentos aplicables
**Para** asegurar cálculos correctos en las facturas y reportes contables

**Criterios de Aceptación:**

* Se pueden establecer porcentajes de IVA u otros impuestos
* Se pueden definir descuentos generales o por tipo de cliente
* Los parámetros afectan automáticamente la facturación y reportes
* El sistema guarda un historial de cambios en impuestos
* Solo usuarios con rol contable o administrativo pueden modificar estos valores

**Entradas:**
- Porcentaje de impuesto (double)
- Descuento general o por tipo de cliente (double)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-46: Personalización de Notificaciones Automáticas

**Como** administrador
**Quiero** configurar el contenido y canales de las notificaciones automáticas
**Para** adaptar la comunicación con los clientes según las políticas de la clínica

**Criterios de Aceptación:**

* Se pueden personalizar los mensajes de confirmación, recordatorio y seguimiento
* Es posible elegir canales: correo electrónico, SMS o WhatsApp
* Se puede incluir el logo y nombre de la clínica en los mensajes
* Las plantillas de mensajes se pueden previsualizar antes de guardar
* Los cambios aplican inmediatamente a nuevas notificaciones programadas

**Entradas:**
- Mensajes de confirmación o recordatorio (String)
- Canal de envío (Enum)
- Logo y nombre de la clínica (String)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-47: Configuración de Niveles de Stock Mínimo

**Como** administrador o encargado de inventario
**Quiero** definir el stock mínimo de cada categoría de producto
**Para** generar alertas automáticas cuando se alcancen niveles críticos

**Criterios de Aceptación:**

* Se pueden establecer niveles mínimos por producto o categoría
* Cuando el stock llega al mínimo, el sistema genera una alerta visual
* Los niveles mínimos pueden actualizarse sin afectar el inventario existente
* Las alertas se reflejan en el panel principal del módulo de inventario
* Solo usuarios con permiso de inventario pueden modificar los valores mínimos

**Entradas:**
- Producto o categoría (String)
- Nivel de stock mínimo (int)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-48: Configuración de Roles y Permisos Personalizados

**Como** administrador
**Quiero** crear y asignar roles personalizados con permisos específicos
**Para** controlar el acceso de los usuarios a los diferentes módulos del sistema

**Criterios de Aceptación:**

* Se pueden crear nuevos roles y definir permisos por módulo
* Es posible clonar la configuración de roles existentes
* Los permisos se aplican inmediatamente sin requerir cierre de sesión
* El sistema muestra advertencia al intentar eliminar un rol en uso
* Solo el administrador principal puede crear o eliminar roles

**Entradas:**
- Nombre del rol (String)
- Permisos por módulo (List<String>)
- Roles existentes a clonar (String)

**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-49: Actualización de Datos Generales de la Clínica

**Como** administrador
**Quiero** actualizar los datos institucionales y de contacto de la clínica
**Para** mantener la información vigente en documentos y notificaciones oficiales

**Criterios de Aceptación:**

* Se pueden actualizar nombre, NIT, dirección, correo, teléfono y logotipo
* Los cambios se reflejan automáticamente en facturas, reportes y correos
* El logotipo puede cargarse en formato PNG o JPG
* El sistema guarda un historial de actualizaciones con fecha y usuario
* Solo usuarios con permisos de configuración pueden realizar modificaciones

**Entradas:**
- Nombre de la clínica (String)
- NIT (String)
- Dirección (String)
- Correo electrónico (String)
- Teléfono (String)
- Logotipo (File)

**Prioridad:** Media
**Estimación:** 5 Story Points

---
---

# ÉPICA 7: Seguimiento y Fidelización del Cliente

**Objetivo:**
Mantener comunicación activa con los clientes, realizar seguimiento post consulta y promover la fidelización mediante recordatorios, campañas y alertas personalizadas.

---

## HU-10: Seguimiento al Cliente

**Como** veterinario o recepcionista
**Quiero** registrar el seguimiento posterior a las consultas o tratamientos
**Para** garantizar el bienestar continuo del paciente y fortalecer la relación con el cliente

**Criterios de Aceptación:**

* Se pueden registrar seguimientos con fecha, motivo, medio de contacto y observaciones
* El sistema envía recordatorios automáticos para revisiones posteriores
* Se puede visualizar el historial de seguimientos por paciente o cliente
* Los seguimientos se asocian a la última consulta o procedimiento realizado
* Solo personal autorizado puede agregar o modificar registros de seguimiento
* El cliente puede confirmar o reagendar su cita de control desde el portal o aplicación

**Entradas:**
- Fecha (Date)
- Motivo (String)
- Medio de contacto (Enum)
- Observaciones (String)
- Cliente o paciente asociado (String)


**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-50: Recordatorios de Vacunación y Desparasitación

**Como** cliente
**Quiero** recibir recordatorios automáticos de vacunación y desparasitación
**Para** mantener al día la salud de mi mascota

**Criterios de Aceptación:**

* El sistema calcula automáticamente las fechas de próximas vacunas y desparasitaciones
* Se envían recordatorios por WhatsApp, correo o notificación en la app
* El mensaje incluye el nombre del paciente, tipo de vacuna y fecha recomendada
* El veterinario puede modificar la fecha de recordatorio si es necesario
* Se registran los recordatorios enviados y sus respuestas

**Entradas:**
- Nombre del paciente (String)
- Tipo de vacuna o desparasitación (String)
- Fecha recomendada (Date)
- Canal de recordatorio (Enum)


**Prioridad:** Alta
**Estimación:** 8 Story Points

---

## HU-51: Envío de Campañas Promocionales

**Como** administrador o área de marketing
**Quiero** enviar campañas promocionales y de fidelización a los clientes
**Para** aumentar la retención y promover servicios de la clínica

**Criterios de Aceptación:**

* Se pueden crear campañas con nombre, descripción, fecha de envío y público objetivo
* Se permite segmentar por tipo de cliente, especie o historial clínico
* Los mensajes pueden incluir texto, imagen y enlace
* El sistema permite envío masivo por correo o WhatsApp
* Se guarda un registro de campañas enviadas con estadísticas básicas (entregados, abiertos, respondidos)

**Entradas:**
- Nombre de campaña (String)
- Descripción (String)
- Fecha de envío (Date)
- Público objetivo (String)
- Tipo de cliente o especie (Enum)
- Mensaje (String)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-52: Clasificación y Segmentación de Clientes

**Como** administrador o recepcionista
**Quiero** clasificar a los clientes según su comportamiento y nivel de fidelidad
**Para** ofrecerles servicios personalizados y campañas adecuadas

**Criterios de Aceptación:**

* El sistema clasifica automáticamente a los clientes según frecuencia de visitas y pagos
* Se pueden crear categorías personalizadas (Ej: “Nuevo”, “Frecuente”, “Inactivo”, “VIP”)
* Se permite editar la categoría manualmente
* Los reportes muestran clientes agrupados por categoría
* Solo administradores pueden crear o eliminar categorías

**Entradas:**
- Cliente (String)
- Frecuencia de visitas (int)
- Categoría asignada (Enum)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-53: Alertas de Clientes Inactivos

**Como** recepcionista o veterinario
**Quiero** recibir alertas de clientes que no asisten hace tiempo
**Para** contactarlos y ofrecer seguimiento o promociones

**Criterios de Aceptación:**

* El sistema identifica automáticamente clientes sin citas en un período configurable (ej. 6 meses)
* Se generan alertas en el panel de seguimiento
* Es posible enviar recordatorios automáticos a los clientes inactivos
* Los clientes contactados cambian su estado a “en seguimiento”
* Los reportes permiten visualizar los clientes reactivados

**Entradas:**
- Período de inactividad (int)
- Cliente identificado (String)
- Canal de contacto (Enum)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-54: Registro de Interacciones con Clientes

**Como** recepcionista o administrador
**Quiero** registrar cada interacción con el cliente (llamadas, correos, mensajes)
**Para** tener trazabilidad de la comunicación y mejorar el servicio

**Criterios de Aceptación:**

* Se pueden registrar llamadas, correos, mensajes y visitas
* Cada registro incluye fecha, hora, responsable y tipo de contacto
* Se puede asociar la interacción a un cliente o caso clínico
* El sistema permite consultar el historial completo de interacciones
* Solo el personal con rol administrativo o de atención puede crear registros

**Entradas:**
- Tipo de interacción (Enum)
- Fecha y hora (LocalDateTime)
- Responsable (String)
- Cliente o caso clínico (String)

**Prioridad:** Media
**Estimación:** 5 Story Points

---

## HU-55: Encuestas de Satisfacción Post Servicio

**Como** administrador
**Quiero** enviar encuestas de satisfacción después de cada atención
**Para** medir la calidad del servicio y obtener retroalimentación de los clientes

**Criterios de Aceptación:**

* Las encuestas se envían automáticamente 24 horas después de la cita
* Se pueden configurar las preguntas y opciones de respuesta
* Los resultados se almacenan en el perfil del cliente
* El sistema genera reportes de satisfacción promedio por período o veterinario
* Se permite exportar los resultados a formato Excel o PDF

**Entradas:**
- Preguntas configuradas (List<String>)
- Opciones de respuesta (List<String>)
- Fecha de envío (Date)
- Cliente asociado (String)

**Prioridad:** Media
**Estimación:** 8 Story Points

---

## HU-56: Reporte de Fidelización y Seguimiento

**Como** administrador
**Quiero** generar reportes de fidelización y seguimiento de clientes
**Para** analizar el nivel de compromiso y efectividad de las campañas

**Criterios de Aceptación:**

* Se generan reportes por rango de fechas y tipo de campaña
* Los reportes incluyen métricas: número de seguimientos, clientes activos, inactivos y reactivados
* Se puede filtrar por veterinario o tipo de cliente
* El sistema permite exportar los reportes en PDF o Excel
* Solo usuarios con permisos de marketing o administración pueden acceder

**Entradas:**
- Rango de fechas (String)
- Tipo de campaña (Enum)
- Filtros (List<String>)
- Formato de exportación (Enum)


**Prioridad:** Media
**Estimación:** 8 Story Points

---
