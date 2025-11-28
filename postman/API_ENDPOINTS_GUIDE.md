# Guía Completa de Endpoints - API Veterinaria

## 📋 Índice
1. [Configuración Inicial](#configuración-inicial)
2. [Authentication (7)](#1-authentication)
3. [Clientes (12)](#2-clientes)
4. [Pacientes (16)](#3-pacientes)
5. [Citas (18)](#4-citas)
6. [Consultas (14)](#5-consultas)
7. [Diagnósticos (11)](#6-diagnósticos)
8. [Tratamientos (15)](#7-tratamientos)
9. [Exámenes (20)](#8-exámenes)
10. [Vacunas (15)](#9-vacunas)
11. [Historial Clínico (9)](#10-historial-clínico)
12. [Facturas (18)](#11-facturas)
13. [Usuarios (13)](#12-usuarios)
14. [Razas (18)](#13-razas)
15. [Tipos de Servicio (8)](#14-tipos-de-servicio)

---

## Configuración Inicial

### Base URL
```
http://localhost:8080/api
```

### Autenticación
Todos los endpoints (excepto `/auth/login` y `/auth/ping`) requieren JWT Token:
```
Authorization: Bearer {token}
```

### Headers Comunes
```
Content-Type: application/json
Accept: application/json
```

---

## 1. Authentication

### 1.1 Login ✅
```http
POST /auth/login
```
**Body:**
```json
{
  "email": "admin@veterinaria.com",
  "password": "admin123"
}
```
**Response 200:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "userId": 1,
  "email": "admin@veterinaria.com",
  "username": "admin",
  "nombre": "Administrador",
  "roles": ["ROLE_ADMIN"],
  "authorities": ["USUARIOS_VER", "USUARIOS_CREAR", ...]
}
```

### 1.2 Ping
```http
GET /auth/ping
```

### 1.3 Logout
```http
POST /auth/logout
Authorization: Bearer {token}
```

### 1.4 Cambiar Password
```http
POST /auth/cambiar-password
Authorization: Bearer {token}
```
**Body:**
```json
{
  "passwordActual": "admin123",
  "passwordNueva": "newpassword123",
  "confirmarPassword": "newpassword123"
}
```

### 1.5 Validar Token
```http
GET /auth/validar
Authorization: Bearer {token}
```

### 1.6 Usuario Actual
```http
GET /auth/me
Authorization: Bearer {token}
```

### 1.7 Recuperar Password
```http
POST /auth/recuperar-password
```
**Body:**
```json
{
  "email": "admin@veterinaria.com"
}
```

---

## 2. Clientes

### 2.1 Crear Cliente
```http
POST /v1/clientes
```
**Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez García",
  "dni": "12345678A",
  "email": "juan.perez@example.com",
  "telefono": "+34912345678",
  "direccion": "Calle Principal 123, Piso 2A",
  "ciudad": "Madrid",
  "departamento": "Madrid",
  "codigoPostal": "28001",
  "observaciones": "Cliente preferente"
}
```

### 2.2 Obtener Todos
```http
GET /v1/clientes
```

### 2.3 Obtener por ID
```http
GET /v1/clientes/{id}
```

### 2.4 Actualizar Cliente
```http
PUT /v1/clientes/{id}
```
**Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "telefono": "+34912345679",
  "direccion": "Calle Secundaria 456",
  "ciudad": "Barcelona",
  "observaciones": "Dirección actualizada"
}
```

### 2.5 Eliminar Cliente
```http
DELETE /v1/clientes/{id}
```

### 2.6 Buscar por Nombre o Apellido
```http
GET /v1/clientes/buscar?termino=Juan
```

### 2.7 Buscar por DNI
```http
GET /v1/clientes/dni/{dni}
```

### 2.8 Buscar por Email
```http
GET /v1/clientes/email?email=juan.perez@example.com
```

### 2.9 Clientes con Pacientes Activos
```http
GET /v1/clientes/con-pacientes-activos
```

### 2.10 Buscar por Ciudad
```http
GET /v1/clientes/ciudad/{ciudad}
```

---

## 3. Pacientes

### 3.1 Crear Paciente
```http
POST /v1/pacientes
```
**Body:**
```json
{
  "nombre": "Max",
  "especie": "PERRO",
  "raza": "Labrador Retriever",
  "fechaNacimiento": "2020-03-15",
  "sexo": "MACHO",
  "color": "Dorado",
  "pesoKg": 32.5,
  "fotoUrl": null,
  "observaciones": "Muy activo y sociable",
  "microchip": "ABC123456789012",
  "clienteId": 1
}
```
**Enums:**
- **especie**: PERRO, GATO, AVE, REPTIL, ROEDOR, OTRO
- **sexo**: MACHO, HEMBRA
- **estado**: ACTIVO, INACTIVO, FALLECIDO

### 3.2 Obtener Todos
```http
GET /v1/pacientes
```

### 3.3 Obtener por ID
```http
GET /v1/pacientes/{id}
```

### 3.4 Actualizar Paciente
```http
PUT /v1/pacientes/{id}
```
**Body:**
```json
{
  "raza": "Labrador Golden",
  "color": "Dorado claro",
  "pesoKg": 33.2,
  "estado": "ACTIVO",
  "observaciones": "Peso actualizado"
}
```

### 3.5 Eliminar
```http
DELETE /v1/pacientes/{id}
```

### 3.6 Buscar por Nombre
```http
GET /v1/pacientes/buscar?nombre=Max
```

### 3.7 Buscar por Especie
```http
GET /v1/pacientes/especie/{especie}
Ejemplo: /v1/pacientes/especie/PERRO
```

### 3.8 Buscar por Estado
```http
GET /v1/pacientes/estado/{estado}
Ejemplo: /v1/pacientes/estado/ACTIVO
```

### 3.9 Pacientes de un Cliente
```http
GET /v1/pacientes/cliente/{clienteId}
```

### 3.10 Pacientes Activos de Cliente
```http
GET /v1/pacientes/cliente/{clienteId}/activos
```

### 3.11 Buscar por Microchip
```http
GET /v1/pacientes/microchip/{microchip}
```

### 3.12 Cambiar Estado
```http
PATCH /v1/pacientes/{id}/cambiar-estado
```
**Body:**
```json
{
  "nuevoEstado": "FALLECIDO",
  "motivo": "Causa natural",
  "fechaCambio": "2024-01-20"
}
```

### 3.13 Contar por Estado
```http
GET /v1/pacientes/contar/estado/{estado}
```

### 3.14 Filtrar por Especie y Estado
```http
GET /v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO
```

---

## 4. Citas

### 4.1 Crear Cita
```http
POST /v1/citas
```
**Body:**
```json
{
  "pacienteId": 1,
  "veterinarioId": 1,
  "tipoServicioId": 1,
  "fechaCita": "2024-02-15",
  "horaCita": "10:30:00",
  "duracionMinutos": 30,
  "motivo": "Revisión anual",
  "notas": "Primera consulta del año"
}
```

### 4.2 Actualizar Cita
```http
PUT /v1/citas/{id}
```
**Body:**
```json
{
  "motivo": "Revisión anual + vacunación",
  "notas": "Traer cartilla de vacunación"
}
```

### 4.3 Obtener por ID
```http
GET /v1/citas/{id}
```

### 4.4 Listar Todas
```http
GET /v1/citas
```

### 4.5 Listar por Fecha
```http
GET /v1/citas/fecha/2024-02-15
```

### 4.6 Citas por Veterinario y Fecha
```http
GET /v1/citas/veterinario/{veterinarioId}/fecha/2024-02-15
```

### 4.7 Citas por Paciente
```http
GET /v1/citas/paciente/{pacienteId}
```

### 4.8 Citas por Cliente
```http
GET /v1/citas/cliente/{clienteId}
```

### 4.9 Próximas Citas del Paciente
```http
GET /v1/citas/paciente/{pacienteId}/proximas
```

### 4.10 Confirmar Cita
```http
PATCH /v1/citas/{id}/confirmar
```

### 4.11 Cancelar Cita
```http
PATCH /v1/citas/{id}/cancelar
```
**Body:**
```json
{
  "motivo": "Cliente no puede asistir"
}
```

### 4.12 Iniciar Atención
```http
PATCH /v1/citas/{id}/iniciar
```

### 4.13 Completar Cita
```http
PATCH /v1/citas/{id}/completar
```

### 4.14 Reagendar Cita
```http
PATCH /v1/citas/{id}/reagendar
```
**Body:**
```json
{
  "nuevaFecha": "2024-02-20",
  "nuevaHora": "11:00:00",
  "motivo": "Cambio solicitado por cliente"
}
```

### 4.15 Horarios Disponibles
```http
GET /v1/citas/disponibilidad/veterinario/{veterinarioId}?fecha=2024-02-15&duracion=30
```

### 4.16 Eliminar Cita
```http
DELETE /v1/citas/{id}
```

---

## 5. Consultas

### 5.1 Crear Consulta
```http
POST /v1/consultas
```
**Body:**
```json
{
  "historialClinicoId": 1,
  "veterinarioId": 1,
  "citaId": 1,
  "fechaConsulta": "2024-02-15T10:30:00",
  "motivo": "Revisión general",
  "anamnesis": "Paciente ha estado comiendo bien",
  "examenFisico": "Temperatura: 38.5°C, Peso: 32kg",
  "observaciones": "Todo normal"
}
```

### 5.2 Actualizar Consulta
```http
PUT /v1/consultas/{id}
```
**Body:**
```json
{
  "anamnesis": "Paciente con ligera pérdida de apetito",
  "examenFisico": "Temperatura: 38.7°C",
  "observaciones": "Prescribir tratamiento"
}
```

### 5.3 Finalizar Consulta
```http
PATCH /v1/consultas/{id}/finalizar
```
**Body:**
```json
{
  "resumen": "Revisión completada, todo normal",
  "recomendaciones": "Continuar con dieta actual"
}
```

### 5.4 Obtener por ID
```http
GET /v1/consultas/{id}
```

### 5.5 Listar por Paciente
```http
GET /v1/consultas/paciente/{pacienteId}
```

### 5.6 Listar por Veterinario
```http
GET /v1/consultas/veterinario/{veterinarioId}
```

### 5.7 Listar por Fecha
```http
GET /v1/consultas/fecha/2024-02-15
```

### 5.8 Consultas en Curso
```http
GET /v1/consultas/en-curso
```

### 5.9 Consultas con Seguimiento
```http
GET /v1/consultas/con-seguimiento
```

### 5.10 Última Consulta del Paciente
```http
GET /v1/consultas/paciente/{pacienteId}/ultima
```

### 5.11 Agregar Diagnóstico
```http
POST /v1/consultas/{consultaId}/diagnosticos
```
**Body:**
```json
{
  "tipoDiagnostico": "CLINICO",
  "descripcion": "Gastroenteritis leve",
  "gravedad": "LEVE",
  "principal": true,
  "codigoCie10": "K52.9",
  "observaciones": "Tratamiento sintomático"
}
```

### 5.12 Agregar Tratamiento
```http
POST /v1/consultas/{consultaId}/tratamientos
```
**Body:**
```json
{
  "tipoTratamiento": "MEDICAMENTO",
  "descripcion": "Omeprazol 20mg",
  "medicamento": "Omeprazol",
  "dosis": "20mg",
  "frecuencia": "Cada 12 horas",
  "viaAdministracion": "Oral",
  "duracionDias": 7,
  "fechaInicio": "2024-02-15",
  "requiereSupervision": false
}
```

### 5.13 Eliminar Consulta
```http
DELETE /v1/consultas/{id}
```

---

## 6. Diagnósticos

### 6.1 Crear Diagnóstico
```http
POST /v1/diagnosticos
```
**Body:**
```json
{
  "consultaId": 1,
  "tipoDiagnostico": "CLINICO",
  "descripcion": "Otitis externa bilateral",
  "gravedad": "MODERADA",
  "principal": true,
  "codigoCie10": "H60.9",
  "observaciones": "Requiere tratamiento antibiótico"
}
```
**Enums:**
- **tipoDiagnostico**: CLINICO, LABORATORIO, IMAGENOLOGIA, HISTOPATOLOGICO
- **gravedad**: LEVE, MODERADA, GRAVE, CRITICA

### 6.2 Obtener por ID
```http
GET /v1/diagnosticos/{id}
```

### 6.3 Listar por Paciente
```http
GET /v1/diagnosticos/paciente/{pacienteId}
```

### 6.4 Listar por Tipo
```http
GET /v1/diagnosticos/tipo/CLINICO
```

### 6.5 Listar por Gravedad
```http
GET /v1/diagnosticos/gravedad/GRAVE
```

### 6.6 Listar Graves o Críticos
```http
GET /v1/diagnosticos/graves-criticos
```

### 6.7 Listar Principales
```http
GET /v1/diagnosticos/principales
```

### 6.8 Listar por CIE-10
```http
GET /v1/diagnosticos/cie10/H60.9
```

### 6.9 Buscar por Descripción
```http
GET /v1/diagnosticos/buscar?descripcion=otitis
```

### 6.10 Listar por Rango de Fechas
```http
GET /v1/diagnosticos/rango-fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
```

### 6.11 Eliminar
```http
DELETE /v1/diagnosticos/{id}
```

---

## 7. Tratamientos

### 7.1 Crear Tratamiento
```http
POST /v1/tratamientos
```
**Body:**
```json
{
  "consultaId": 1,
  "tipoTratamiento": "MEDICAMENTO",
  "descripcion": "Antibiótico para otitis",
  "medicamento": "Amoxicilina",
  "dosis": "250mg",
  "frecuencia": "Cada 8 horas",
  "viaAdministracion": "Oral",
  "duracionDias": 10,
  "fechaInicio": "2024-02-15",
  "instrucciones": "Administrar con comida",
  "requiereSupervision": false,
  "efectosSecundarios": "Posible diarrea leve"
}
```
**Enums:**
- **tipoTratamiento**: MEDICAMENTO, CIRUGIA, TERAPIA, DIETA, REHABILITACION, OTRO

### 7.2 Obtener por ID
```http
GET /v1/tratamientos/{id}
```

### 7.3 Listar por Paciente
```http
GET /v1/tratamientos/paciente/{pacienteId}
```

### 7.4 Listar Activos por Paciente
```http
GET /v1/tratamientos/paciente/{pacienteId}/activos
```

### 7.5 Listar Vigentes por Paciente
```http
GET /v1/tratamientos/paciente/{pacienteId}/vigentes
```

### 7.6 Listar por Tipo
```http
GET /v1/tratamientos/tipo/MEDICAMENTO
```

### 7.7 Medicamentos Activos
```http
GET /v1/tratamientos/medicamentos-activos
```

### 7.8 Con Supervisión
```http
GET /v1/tratamientos/con-supervision
```

### 7.9 Próximos a Finalizar
```http
GET /v1/tratamientos/proximos-a-finalizar?dias=7
```

### 7.10 Suspendidos
```http
GET /v1/tratamientos/suspendidos
```

### 7.11 Por Rango de Fechas
```http
GET /v1/tratamientos/rango-fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
```

### 7.12 Suspender Tratamiento
```http
PATCH /v1/tratamientos/{id}/suspender
```

### 7.13 Calcular Fecha Fin
```http
PATCH /v1/tratamientos/{id}/calcular-fecha-fin
```

### 7.14 Eliminar
```http
DELETE /v1/tratamientos/{id}
```

---

## 8. Exámenes

### 8.1 Solicitar Examen
```http
POST /v1/examenes
```
**Body:**
```json
{
  "historialClinicoId": 1,
  "consultaId": 1,
  "tipoExamen": "HEMOGRAMA",
  "descripcion": "Hemograma completo",
  "indicaciones": "En ayunas",
  "urgente": false,
  "observaciones": "Control de rutina"
}
```
**Tipos comunes:**
- HEMOGRAMA, BIOQUIMICA, URINALISIS, RADIOGRAFIA, ECOGRAFIA, etc.

### 8.2 Obtener por ID
```http
GET /v1/examenes/{id}
```

### 8.3 Listar por Paciente
```http
GET /v1/examenes/paciente/{pacienteId}
```

### 8.4 Listar por Consulta
```http
GET /v1/examenes/consulta/{consultaId}
```

### 8.5 Listar por Tipo
```http
GET /v1/examenes/tipo/HEMOGRAMA
```

### 8.6 Listar por Estado
```http
GET /v1/examenes/estado/PENDIENTE
```
**Estados**: SOLICITADO, EN_PROCESO, COMPLETADO, CANCELADO

### 8.7 Pendientes
```http
GET /v1/examenes/pendientes
```

### 8.8 Completados
```http
GET /v1/examenes/completados
```

### 8.9 Con Resultados Anormales
```http
GET /v1/examenes/resultados-anormales
```

### 8.10 Urgentes
```http
GET /v1/examenes/urgentes
```

### 8.11 De Laboratorio
```http
GET /v1/examenes/laboratorio
```

### 8.12 De Imagenología
```http
GET /v1/examenes/imagenologia
```

### 8.13 Demorados
```http
GET /v1/examenes/demorados?dias=7
```

### 8.14 Por Rango de Fechas
```http
GET /v1/examenes/rango-fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
```

### 8.15 Registrar Resultados
```http
PATCH /v1/examenes/{id}/resultados
```
**Body:**
```json
{
  "resultados": "Hemoglobina: 15g/dL, Leucocitos: 8000/mm3",
  "interpretacion": "Valores dentro de rangos normales",
  "hallazgosAnormales": false,
  "archivoResultados": "url-del-archivo.pdf"
}
```

### 8.16 Marcar como Realizado
```http
PATCH /v1/examenes/{id}/marcar-realizado
```

### 8.17 Cancelar Examen
```http
PATCH /v1/examenes/{id}/cancelar?motivo=Paciente no se presentó
```

### 8.18 Eliminar
```http
DELETE /v1/examenes/{id}
```

---

## 9. Vacunas

### 9.1 Registrar Vacuna
```http
POST /v1/vacunas
```
**Body:**
```json
{
  "historialClinicoId": 1,
  "consultaId": 1,
  "tipoVacuna": "ANTIRRABICA",
  "nombreVacuna": "Rabisin",
  "laboratorio": "Merial",
  "lote": "ABC123",
  "fechaVacunacion": "2024-02-15",
  "fechaVencimiento": "2026-02-15",
  "dosis": "1ml",
  "viaAdministracion": "Subcutánea",
  "numeroSerie": 1,
  "requiereRefuerzo": true,
  "fechaProximoRefuerzo": "2025-02-15",
  "reaccionesAdversas": null,
  "observaciones": "Primera dosis"
}
```

### 9.2 Obtener por ID
```http
GET /v1/vacunas/{id}
```

### 9.3 Listar por Paciente
```http
GET /v1/vacunas/paciente/{pacienteId}
```

### 9.4 Listar por Tipo
```http
GET /v1/vacunas/tipo/ANTIRRABICA
```

### 9.5 Antirrábicas
```http
GET /v1/vacunas/antirrabicas
```

### 9.6 Con Refuerzo Próximo
```http
GET /v1/vacunas/refuerzo-proximo?dias=30
```

### 9.7 Con Refuerzo Vencido
```http
GET /v1/vacunas/refuerzo-vencido
```

### 9.8 Serie Incompleta
```http
GET /v1/vacunas/serie-incompleta
```

### 9.9 Serie Incompleta por Paciente
```http
GET /v1/vacunas/paciente/{pacienteId}/serie-incompleta
```

### 9.10 Con Reacciones Adversas
```http
GET /v1/vacunas/con-reacciones-adversas
```

### 9.11 Por Rango de Fechas
```http
GET /v1/vacunas/rango-fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
```

### 9.12 Calcular Próxima Dosis
```http
PATCH /v1/vacunas/{id}/calcular-proxima-dosis
```

### 9.13 Completar Serie
```http
PATCH /v1/vacunas/{id}/completar-serie
```

### 9.14 Eliminar
```http
DELETE /v1/vacunas/{id}
```

---

## 10. Historial Clínico

### 10.1 Crear Historial
```http
POST /v1/historiales-clinicos/paciente/{pacienteId}
```

### 10.2 Actualizar
```http
PUT /v1/historiales-clinicos/{id}
```
**Body:**
```json
{
  "alergias": "Penicilina, Polen",
  "condicionesCronicas": "Diabetes tipo 1",
  "cirugiasPrevias": "Esterilización (2021)",
  "medicamentosActuales": "Insulina 2U cada 12h",
  "observacionesGenerales": "Dieta especial baja en carbohidratos"
}
```

### 10.3 Obtener por ID
```http
GET /v1/historiales-clinicos/{id}
```

### 10.4 Obtener por Paciente
```http
GET /v1/historiales-clinicos/paciente/{pacienteId}
```

### 10.5 Listar Todos
```http
GET /v1/historiales-clinicos
```

### 10.6 Con Alergias
```http
GET /v1/historiales-clinicos/con-alergias
```

### 10.7 Con Condiciones Crónicas
```http
GET /v1/historiales-clinicos/con-condiciones-cronicas
```

### 10.8 Verificar si Existe
```http
GET /v1/historiales-clinicos/paciente/{pacienteId}/existe
```

### 10.9 Eliminar
```http
DELETE /v1/historiales-clinicos/{id}
```

---

## 11. Facturas

### 11.1 Crear Factura
```http
POST /v1/facturas
```
**Body:**
```json
{
  "clienteId": 1,
  "fechaEmision": "2024-02-15",
  "fechaVencimiento": "2024-03-15",
  "observaciones": "Servicios veterinarios febrero"
}
```

### 11.2 Actualizar
```http
PUT /v1/facturas/{id}
```
**Body:**
```json
{
  "fechaVencimiento": "2024-03-20",
  "observaciones": "Fecha de vencimiento extendida"
}
```

### 11.3 Obtener por ID
```http
GET /v1/facturas/{id}
```

### 11.4 Obtener por Número
```http
GET /v1/facturas/numero/FAC-2024-001
```

### 11.5 Listar por Cliente
```http
GET /v1/facturas/cliente/{clienteId}
```

### 11.6 Listar por Estado
```http
GET /v1/facturas/estado/PENDIENTE
```
**Estados**: PENDIENTE, PAGADA, PARCIAL, VENCIDA, ANULADA

### 11.7 Por Rango de Fechas
```http
GET /v1/facturas/fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
```

### 11.8 Vencidas
```http
GET /v1/facturas/vencidas
```

### 11.9 Pendientes
```http
GET /v1/facturas/pendientes
```

### 11.10 Con Saldo Pendiente
```http
GET /v1/facturas/con-saldo-pendiente
```

### 11.11 Agregar Detalle
```http
POST /v1/facturas/{facturaId}/detalles
```
**Body:**
```json
{
  "descripcion": "Consulta veterinaria",
  "cantidad": 1,
  "precioUnitario": 50.00
}
```

### 11.12 Eliminar Detalle
```http
DELETE /v1/facturas/{facturaId}/detalles/{detalleId}
```

### 11.13 Aplicar Descuento
```http
POST /v1/facturas/{facturaId}/descuentos
```
**Body:**
```json
{
  "descripcion": "Descuento cliente frecuente",
  "tipoDescuento": "PORCENTAJE",
  "monto": 10.00
}
```

### 11.14 Eliminar Descuento
```http
DELETE /v1/facturas/{facturaId}/descuentos/{descuentoId}
```

### 11.15 Registrar Pago
```http
POST /v1/facturas/{facturaId}/pagos
```
**Body:**
```json
{
  "monto": 100.00,
  "metodoPago": "EFECTIVO",
  "fechaPago": "2024-02-15",
  "referencia": "PAGO-001",
  "observaciones": "Pago completo"
}
```
**Métodos**: EFECTIVO, TARJETA, TRANSFERENCIA, CHEQUE

### 11.16 Anular Factura
```http
PATCH /v1/facturas/{id}/anular
```
**Body:**
```json
{
  "motivo": "Error en facturación",
  "autorizadoPor": "Admin"
}
```

### 11.17 Eliminar
```http
DELETE /v1/facturas/{id}
```

---

## 12. Usuarios

**Base URL**: `/usuarios` (sin /v1)

### 12.1 Listar Usuarios (Paginado)
```http
GET /usuarios?page=0&size=20
```

### 12.2 Obtener por ID
```http
GET /usuarios/{id}
```

### 12.3 Buscar
```http
GET /usuarios/buscar?termino=Juan&page=0&size=20
```

### 12.4 Por Tipo
```http
GET /usuarios/tipo/VETERINARIO?page=0&size=20
```
**Tipos**: VETERINARIO, RECEPCIONISTA, ADMINISTRADOR

### 12.5 Crear Usuario
```http
POST /usuarios
```
**Body:**
```json
{
  "username": "jperez",
  "email": "jperez@veterinaria.com",
  "password": "password123",
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "87654321",
  "telefono": "+34912345678",
  "tipo": "VETERINARIO",
  "especialidad": "Medicina general",
  "numeroLicencia": "VET-12345"
}
```

### 12.6 Actualizar Usuario
```http
PUT /usuarios/{id}
```
**Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "telefono": "+34912345679",
  "especialidad": "Cirugía"
}
```

### 12.7 Eliminar
```http
DELETE /usuarios/{id}
```

### 12.8 Desbloquear Cuenta
```http
POST /usuarios/{id}/desbloquear
```

### 12.9 Cambiar Password
```http
POST /usuarios/{id}/cambiar-password
```
**Body:**
```json
{
  "passwordActual": "oldpass",
  "passwordNueva": "newpass123",
  "confirmarPassword": "newpass123"
}
```

### 12.10 Obtener Sesiones Activas
```http
GET /usuarios/{id}/sesiones
```

### 12.11 Cerrar Sesión Específica
```http
DELETE /usuarios/{id}/sesiones/{sesionId}
```

### 12.12 Cerrar Todas las Sesiones
```http
DELETE /usuarios/{id}/sesiones
```

---

## 13. Razas

### 13.1 Crear Raza
```http
POST /v1/razas
```
**Body:**
```json
{
  "nombre": "Labrador Retriever",
  "especie": "PERRO",
  "descripcion": "Perro de tamaño grande, amigable y enérgico",
  "caracteristicas": "Pelo corto, color dorado/negro/chocolate",
  "pesoPromedio": 30.0,
  "alturaPromedio": 57.0,
  "esperanzaVida": 12,
  "temperamento": "Amigable, activo, leal",
  "predefinida": false
}
```

### 13.2 Obtener por ID
```http
GET /v1/razas/{id}
```

### 13.3 Obtener Todas
```http
GET /v1/razas
```

### 13.4 Obtener Activas
```http
GET /v1/razas/activas
```

### 13.5 Por Especie
```http
GET /v1/razas/especie/PERRO
```

### 13.6 Activas por Especie
```http
GET /v1/razas/especie/PERRO/activas
```

### 13.7 Específicas por Especie
```http
GET /v1/razas/especie/PERRO/especificas
```

### 13.8 Buscar por Nombre
```http
GET /v1/razas/buscar?nombre=Labrador
```

### 13.9 Predefinidas
```http
GET /v1/razas/predefinidas
```

### 13.10 Personalizadas
```http
GET /v1/razas/personalizadas
```

### 13.11 Actualizar
```http
PUT /v1/razas/{id}
```
**Body:**
```json
{
  "descripcion": "Descripción actualizada",
  "caracteristicas": "Características actualizadas"
}
```

### 13.12 Activar
```http
PATCH /v1/razas/{id}/activar
```

### 13.13 Desactivar
```http
PATCH /v1/razas/{id}/desactivar
```

### 13.14 Eliminar
```http
DELETE /v1/razas/{id}
```

### 13.15 Contar por Especie
```http
GET /v1/razas/contar/especie/PERRO
```

### 13.16 Estadísticas
```http
GET /v1/razas/estadisticas
```

---

## 14. Tipos de Servicio

### 14.1 Crear
```http
POST /v1/tipos-servicio
```
**Body:**
```json
{
  "nombre": "Consulta General",
  "descripcion": "Consulta veterinaria general",
  "categoria": "CONSULTA",
  "precio": 50.00,
  "duracionMinutos": 30
}
```

### 14.2 Actualizar
```http
PUT /v1/tipos-servicio/{id}
```
**Body:**
```json
{
  "nombre": "Consulta General Premium",
  "precio": 60.00,
  "duracionMinutos": 45
}
```

### 14.3 Obtener por ID
```http
GET /v1/tipos-servicio/{id}
```

### 14.4 Listar Todos
```http
GET /v1/tipos-servicio
```

### 14.5 Por Categoría
```http
GET /v1/tipos-servicio/categoria/CONSULTA
```

### 14.6 Obtener Categorías
```http
GET /v1/tipos-servicio/categorias
```

### 14.7 Desactivar
```http
DELETE /v1/tipos-servicio/{id}
```

### 14.8 Activar
```http
PATCH /v1/tipos-servicio/{id}/activar
```

---

## 📝 Notas Finales

### Variables de Entorno Sugeridas
```json
{
  "baseUrl": "http://localhost:8080/api",
  "authToken": "",
  "clienteId": 1,
  "pacienteId": 1,
  "citaId": 1,
  "consultaId": 1,
  "facturaId": 1
}
```

### Scripts de Test Útiles (Postman)

**Guardar Token:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("authToken", jsonData.token);
}
```

**Guardar ID de Respuesta:**
```javascript
if (pm.response.code === 201) {
    var jsonData = pm.response.json();
    pm.environment.set("pacienteId", jsonData.id);
}
```

### Códigos de Estado HTTP
- **200**: OK
- **201**: Created
- **204**: No Content
- **400**: Bad Request
- **401**: Unauthorized
- **403**: Forbidden
- **404**: Not Found
- **500**: Internal Server Error

---

**Total: 172 endpoints documentados** ✅
