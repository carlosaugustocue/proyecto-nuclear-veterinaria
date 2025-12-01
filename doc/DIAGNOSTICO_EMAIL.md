# Diagnóstico de Problemas con Email

## Problema: Los emails no se envían

Si los emails funcionaban antes pero ahora no, sigue estos pasos de diagnóstico:

## 1. Verificar Variables de Entorno

### Verificar que el archivo .env existe:
```bash
ls -la .env
```

### Verificar que las variables estén configuradas:
```bash
cat .env | grep EMAIL
```

Deberías ver algo como:
```
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-contraseña-de-aplicacion
EMAIL_FROM=tu-email@gmail.com
```

## 2. Verificar Logs del Backend al Iniciar

Al iniciar la aplicación, busca en los logs:

```
=== Configuración de Email ===
Email remitente (app.email.from): tu-email@gmail.com
Configuración SMTP - Username configurado: SÍ (desde variable de entorno)
Configuración SMTP - Password configurado: SÍ (desde variable de entorno)
✓ Configuración de email remitente OK
```

Si ves:
- `Email remitente: NO CONFIGURADO` → Las variables de entorno no se están cargando
- `Username configurado: NO` → `EMAIL_USERNAME` no está configurado
- `Password configurado: NO` → `EMAIL_PASSWORD` no está configurado

## 3. Verificar Logs al Enviar Email

Cuando intentas crear una cita, busca en los logs:

### Si el email se envía correctamente:
```
Enviando email - De: tu-email@gmail.com, Para: cliente@email.com, Asunto: ...
✓ Email enviado exitosamente a: cliente@email.com
```

### Si hay un error de autenticación:
```
❌ Error al enviar email a cliente@email.com: ...
🔐 PROBLEMA DE AUTENTICACIÓN:
   - Verifica que EMAIL_USERNAME y EMAIL_PASSWORD estén configurados
   - Para Gmail, usa una 'Contraseña de aplicación' (no tu contraseña normal)
   - Genera una nueva en: https://myaccount.google.com/apppasswords
```

### Si hay un problema de conexión:
```
🌐 PROBLEMA DE CONEXIÓN:
   - Verifica tu conexión a internet
   - Verifica que el puerto 587 no esté bloqueado por firewall
```

### Si se alcanzó el límite de Gmail:
```
📊 LÍMITE DE GMAIL ALCANZADO:
   - Gmail tiene límites de envío (500 emails/día para cuentas gratuitas)
   - Espera 24 horas o considera usar una cuenta de Google Workspace
```

## 4. Problemas Comunes y Soluciones

### Problema: Variables de entorno no se cargan

**Solución:**
1. Si usas IntelliJ IDEA:
   - Ve a `Run` → `Edit Configurations`
   - Selecciona tu configuración de Spring Boot
   - En `Environment variables`, agrega las variables del `.env`
   - O marca `Use environment variables from .env file`

2. Si usas línea de comandos:
   ```bash
   source load-env.sh
   mvn spring-boot:run
   ```

3. Si usas Maven directamente:
   ```bash
   export $(cat .env | xargs)
   mvn spring-boot:run
   ```

### Problema: Error 535 (Authentication Failed)

**Causa:** La contraseña de aplicación de Gmail es incorrecta o expiró.

**Solución:**
1. Ve a: https://myaccount.google.com/apppasswords
2. Genera una nueva contraseña de aplicación
3. Actualiza `EMAIL_PASSWORD` en tu archivo `.env`
4. Reinicia la aplicación

### Problema: Error de conexión o timeout

**Causa:** Problemas de red o firewall bloqueando el puerto 587.

**Solución:**
1. Verifica tu conexión a internet
2. Verifica que el puerto 587 no esté bloqueado
3. Prueba con otro puerto (465 para SSL) si es necesario

### Problema: Límite de Gmail alcanzado

**Causa:** Gmail limita a 500 emails/día para cuentas gratuitas.

**Solución:**
1. Espera 24 horas
2. O usa una cuenta de Google Workspace (límite más alto)
3. O usa otro proveedor de email (SendGrid, Mailgun, etc.)

### Problema: Funcionaba antes pero ahora no

**Posibles causas:**
1. Las variables de entorno se perdieron al reiniciar
2. La contraseña de aplicación expiró o fue revocada
3. Se alcanzó el límite de Gmail
4. Gmail bloqueó temporalmente la cuenta por actividad sospechosa

**Solución:**
1. Verifica los logs del backend al iniciar
2. Verifica los logs al intentar enviar un email
3. Revisa tu cuenta de Gmail por alertas de seguridad
4. Genera una nueva contraseña de aplicación si es necesario

## 5. Verificar Configuración de Gmail

1. **Verificación en dos pasos activada:**
   - Ve a: https://myaccount.google.com/security
   - Debe estar activada para usar contraseñas de aplicación

2. **Contraseña de aplicación válida:**
   - Ve a: https://myaccount.google.com/apppasswords
   - Verifica que exista una contraseña para "Sistema Veterinaria"
   - Si no existe o expiró, genera una nueva

3. **Sin bloqueos de seguridad:**
   - Ve a: https://myaccount.google.com/security
   - Revisa "Actividad reciente de seguridad"
   - Si hay alertas, resuélvelas

## 6. Probar Configuración Manualmente

Puedes probar la configuración con un script simple:

```bash
# Cargar variables de entorno
source load-env.sh

# Verificar que estén cargadas
echo "EMAIL_USERNAME: $EMAIL_USERNAME"
echo "EMAIL_PASSWORD: ${EMAIL_PASSWORD:0:4}..." # Solo primeros 4 caracteres por seguridad
```

## 7. Contactar Soporte

Si después de seguir estos pasos el problema persiste:

1. Comparte los logs completos del backend (especialmente la sección "=== Configuración de Email ===")
2. Comparte el error específico que aparece al intentar enviar un email
3. Verifica que no haya cambios recientes en la configuración de Gmail

