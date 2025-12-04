# Configurar Email en Railway

## Problema

El servicio de email no está funcionando porque las variables de entorno `EMAIL_HOST`, `EMAIL_USERNAME`, `EMAIL_PASSWORD` y `EMAIL_PORT` no están configuradas correctamente en Railway.

## Solución: Configurar Variables de Entorno en Railway

### Paso 1: Acceder a las Variables de Entorno

1. Ve a tu proyecto en Railway: https://railway.app
2. Selecciona el servicio del **backend** (no el de MySQL)
3. Ve a la pestaña **"Variables"** o **"Environment"**

### Paso 2: Agregar las Variables de Email

Agrega las siguientes variables de entorno con estos valores exactos:

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `EMAIL_HOST` | `smtp.gmail.com` | Servidor SMTP de Gmail |
| `EMAIL_PORT` | `587` | Puerto SMTP (TLS) |
| `EMAIL_USERNAME` | `caaranzazu_230@cue.edu.co` | Tu dirección de email de Gmail |
| `EMAIL_PASSWORD` | `xhhnbvtbcajxjsnt` | Contraseña de aplicación de Gmail |
| `EMAIL_FROM` | `caaranzazu_230@cue.edu.co` | Email remitente (opcional, usa EMAIL_USERNAME si no se configura) |

### Paso 3: Verificar la Configuración

**IMPORTANTE**: Asegúrate de que:

1. ✅ Las variables están en el servicio **backend** (no en MySQL)
2. ✅ Los nombres de las variables son **exactamente** como se muestran arriba (mayúsculas)
3. ✅ No hay espacios extra antes o después de los valores
4. ✅ No están entre comillas (Railway las agrega automáticamente si es necesario)

### Paso 4: Redesplegar

Después de agregar las variables:

1. Railway debería redesplegar automáticamente
2. Si no, puedes forzar un redespliegue desde la pestaña **"Deployments"**

### Paso 5: Verificar en los Logs

Después del redespliegue, verifica en los logs que aparezca:

```
=== Diagnóstico de Email ===
EMAIL_HOST (System.getenv): smtp.gmail.com
✓ EMAIL_HOST configurado: smtp.gmail.com
✓ spring.mail.host establecido como propiedad del sistema
```

Y también:

```
=== MailConfig - Diagnóstico ===
EMAIL_HOST (env): smtp.gmail.com
=== Creando bean JavaMailSender ===
✓ Configurando JavaMailSender con host: smtp.gmail.com
✓ JavaMailSender configurado - Host: smtp.gmail.com, Port: 587, Username: caaranzazu_230@cue.edu.co
```

Y finalmente:

```
=== Configuración de Email ===
Email remitente (app.email.from): caaranzazu_230@cue.edu.co
✓ Configuración de email remitente OK
```

## Notas Importantes

### Contraseña de Aplicación de Gmail

Si `EMAIL_PASSWORD` es una "Contraseña de aplicación" de Gmail, asegúrate de que:
- ✅ Está activada la verificación en 2 pasos en tu cuenta de Google
- ✅ La contraseña de aplicación se generó correctamente
- ✅ No tiene espacios ni caracteres extra

### Si las Variables No Aparecen

Si después de configurar las variables en Railway, los logs siguen mostrando "NO ENCONTRADO":

1. **Verifica que estás en el servicio correcto**: Debe ser el servicio del backend, no el de MySQL
2. **Verifica el nombre de las variables**: Deben ser exactamente `EMAIL_HOST`, `EMAIL_USERNAME`, `EMAIL_PASSWORD`, `EMAIL_PORT` (en mayúsculas)
3. **Forza un redespliegue**: A veces Railway necesita un redespliegue manual para cargar las nuevas variables
4. **Revisa si hay variables duplicadas**: Asegúrate de que no hay múltiples definiciones de la misma variable

## Configuración Actual Requerida

Basado en tu configuración anterior, estas son las variables que necesitas:

```bash
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=caaranzazu_230@cue.edu.co
EMAIL_PASSWORD=xhhnbvtbcajxjsnt
EMAIL_FROM=caaranzazu_230@cue.edu.co
```

## Verificación Rápida

Para verificar rápidamente si las variables están configuradas, puedes usar el endpoint de diagnóstico (si está disponible) o revisar los logs de inicio de la aplicación.

Si todo está configurado correctamente, deberías ver en los logs:
- ✅ `✓ EMAIL_HOST configurado: smtp.gmail.com`
- ✅ `✓ Configurando JavaMailSender con host: smtp.gmail.com`
- ✅ `=== Configuración de Email ===` (del EmailService)

Si ves `⚠️ EMAIL_HOST NO está configurado`, significa que las variables no están disponibles para la aplicación.

