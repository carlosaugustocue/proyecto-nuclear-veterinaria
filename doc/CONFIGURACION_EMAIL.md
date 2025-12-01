# Configuración de Email - Sistema Veterinaria

## Problema Común: Los emails no se envían

Si los emails de confirmación de citas no están llegando, verifica la siguiente configuración.

## 1. Variables de Entorno Requeridas

Crea un archivo `.env` en la raíz del proyecto (junto a `pom.xml`) con las siguientes variables:

```env
# Configuración de Email Gmail
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-contraseña-de-aplicacion
EMAIL_FROM=tu-email@gmail.com
```

## 2. Obtener Contraseña de Aplicación de Gmail

**IMPORTANTE**: Gmail requiere una "Contraseña de aplicación" en lugar de tu contraseña normal.

### Pasos para obtener la contraseña de aplicación:

1. **Habilita la verificación en dos pasos** (si no la tienes):
   - Ve a: https://myaccount.google.com/security
   - Activa "Verificación en dos pasos"

2. **Genera una contraseña de aplicación**:
   - Ve a: https://myaccount.google.com/apppasswords
   - Selecciona "Correo" como aplicación
   - Selecciona "Otro (nombre personalizado)" como dispositivo
   - Escribe "Sistema Veterinaria" o el nombre que prefieras
   - Haz clic en "Generar"
   - **Copia la contraseña de 16 caracteres** (se muestra solo una vez)

3. **Usa esta contraseña en tu archivo `.env`**:
   ```env
   EMAIL_PASSWORD=abcd efgh ijkl mnop
   ```
   (Sin espacios, o con espacios según prefieras - ambos funcionan)

## 3. Verificar Configuración

### Opción A: Usar el script load-env.sh

```bash
# 1. Crea el archivo .env con tus credenciales
cp env.example .env
# Edita .env con tus credenciales reales

# 2. Carga las variables de entorno
source load-env.sh

# 3. Inicia la aplicación
mvn spring-boot:run
```

### Opción B: Exportar manualmente

```bash
export EMAIL_HOST=smtp.gmail.com
export EMAIL_PORT=587
export EMAIL_USERNAME=tu-email@gmail.com
export EMAIL_PASSWORD=tu-contraseña-de-aplicacion
export EMAIL_FROM=tu-email@gmail.com

mvn spring-boot:run
```

### Opción C: Configurar en el IDE (IntelliJ IDEA)

1. Ve a `Run` → `Edit Configurations`
2. Selecciona tu configuración de Spring Boot
3. En "Environment variables", agrega:
   ```
   EMAIL_HOST=smtp.gmail.com
   EMAIL_PORT=587
   EMAIL_USERNAME=tu-email@gmail.com
   EMAIL_PASSWORD=tu-contraseña-de-aplicacion
   EMAIL_FROM=tu-email@gmail.com
   ```

## 4. Verificar que la Configuración Funciona

Al iniciar la aplicación, deberías ver en los logs algo como:

```
=== Configuración de Email ===
Email remitente (app.email.from): tu-email@gmail.com
Frontend URL: http://localhost:3000
Nombre clínica: Clínica Veterinaria UniHumboldt
...
```

Si ves `NO CONFIGURADO` en el email remitente, las variables de entorno no están cargadas correctamente.

## 5. Probar el Envío de Email

1. Crea una cita desde el frontend
2. Revisa los logs del backend. Deberías ver:
   ```
   Enviando email de confirmación de cita - Cliente: Nombre (email@ejemplo.com)
   Enviando email - De: tu-email@gmail.com, Para: email@ejemplo.com, Asunto: ...
   ✓ Email enviado exitosamente a: email@ejemplo.com
   ```

3. Si hay errores, verás:
   ```
   ❌ Error al enviar email a email@ejemplo.com: [mensaje de error]
   ```

## 6. Errores Comunes

### Error: "Authentication failed"
- **Causa**: Usaste tu contraseña normal en lugar de la contraseña de aplicación
- **Solución**: Genera una contraseña de aplicación desde https://myaccount.google.com/apppasswords

### Error: "Email remitente no está configurado"
- **Causa**: Las variables de entorno no están cargadas
- **Solución**: Verifica que el archivo `.env` existe y que las variables están exportadas antes de iniciar la aplicación

### Error: "Connection refused" o "Connection timeout"
- **Causa**: Problemas de red o firewall bloqueando el puerto 587
- **Solución**: Verifica tu conexión a internet y que el puerto 587 no esté bloqueado

### Los emails no llegan pero no hay errores en los logs
- **Causa**: El email puede estar en la carpeta de spam
- **Solución**: Revisa la carpeta de spam del destinatario

## 7. Configuración para Otros Proveedores

### Outlook/Hotmail
```env
EMAIL_HOST=smtp-mail.outlook.com
EMAIL_PORT=587
EMAIL_USERNAME=tu-email@outlook.com
EMAIL_PASSWORD=tu-contraseña
```

### Yahoo
```env
EMAIL_HOST=smtp.mail.yahoo.com
EMAIL_PORT=587
EMAIL_USERNAME=tu-email@yahoo.com
EMAIL_PASSWORD=tu-contraseña-de-aplicacion
```

## 8. Notas Importantes

- ⚠️ **NUNCA** commitees el archivo `.env` al repositorio (ya está en `.gitignore`)
- ⚠️ La contraseña de aplicación de Gmail solo se muestra una vez. Si la pierdes, genera una nueva
- ⚠️ Cada contraseña de aplicación es única. Si cambias de cuenta de Gmail, necesitas generar una nueva
- ✅ Los emails se envían de forma asíncrona, por lo que no bloquean la creación de la cita
- ✅ Si el envío de email falla, la cita se crea igualmente (solo se registra el error en los logs)

