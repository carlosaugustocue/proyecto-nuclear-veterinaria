# Configurar SendGrid en Railway

Railway bloquea los puertos SMTP (587, 465) en sus planes gratuitos, lo que impide usar Gmail u otros servicios SMTP directamente. SendGrid es una excelente alternativa porque usa API REST (HTTPS) en lugar de SMTP.

## 1. Crear cuenta en SendGrid

1. Ve a [SendGrid](https://sendgrid.com/) y crea una cuenta gratuita
2. El plan gratuito permite enviar hasta **100 emails por día**

## 2. Obtener API Key

1. En el panel de SendGrid, ve a **Settings** → **API Keys**
2. Haz clic en **Create API Key**
3. Asigna un nombre (ej: "Veterinaria Railway")
4. Selecciona **Full Access** o **Restricted Access** con permisos de "Mail Send"
5. **IMPORTANTE**: Copia la API Key inmediatamente (solo se muestra una vez)
6. Guárdala en un lugar seguro

## 3. Verificar remitente (Sender)

**OPCIÓN A: Single Sender Verification (Recomendado para empezar rápido)**

1. En el onboarding, haz clic en **"Skip to dashboard"** (arriba a la derecha)
2. En el dashboard, ve a **Settings** → **Sender Authentication**
3. Selecciona **Verify a Single Sender**
4. Completa el formulario con:
   - **From Email Address**: Tu email personal o el que usarás como remitente (ej: `caaranzazu_230@cue.edu.co`)
   - **From Name**: Nombre que aparecerá (ej: "Clínica Veterinaria")
   - **Reply To**: Email para respuestas (puede ser el mismo)
   - **Company Address**: Dirección de tu empresa/clínica
5. Verifica el email que recibirás de SendGrid
6. Una vez verificado, podrás usar ese email como remitente

**OPCIÓN B: Domain Authentication (Para producción, requiere dominio propio)**

Si tienes un dominio propio y quieres mejor deliverability:
1. En el onboarding, ingresa tu dominio (ej: `tudominio.com`)
2. Selecciona si quieres "brand the link" (recomendado: Yes)
3. Sigue las instrucciones para agregar registros DNS en tu proveedor de dominio
4. Una vez verificado el dominio, podrás usar cualquier email de ese dominio como remitente

**Para empezar rápido, usa la Opción A (Single Sender) y luego puedes migrar a Domain Authentication más adelante.**

## 4. Configurar variables en Railway

En el dashboard de Railway, para tu servicio backend, agrega estas variables de entorno:

```env
# SendGrid (tiene prioridad sobre SMTP si está configurado)
SENDGRID_API_KEY=SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Email remitente (debe estar verificado en SendGrid)
EMAIL_FROM=noreply@tudominio.com

# Otras variables opcionales
APP_NOMBRE=Clínica Veterinaria
APP_FRONTEND_URL=https://tu-frontend.netlify.app
APP_CONTACTO_TELEFONO=+1234567890
APP_CONTACTO_DIRECCION=Tu dirección
APP_CONTACTO_EMAIL=contacto@tudominio.com
```

**NOTA**: Si `SENDGRID_API_KEY` está configurado, el sistema usará SendGrid automáticamente en lugar de SMTP.

## 5. Verificar funcionamiento

1. Despliega tu aplicación en Railway
2. Revisa los logs al iniciar - deberías ver:
   ```
   === SendGrid Email Service Inicializado ===
   ✓ SendGrid API Key configurado
   ✓ Email remitente: noreply@tudominio.com
   ```
3. Prueba enviando un email (crear una cita, registrar un pago, etc.)
4. Revisa los logs para confirmar:
   ```
   ✓ Email enviado exitosamente vía SendGrid a: cliente@email.com (Asunto: ...) en XXXms
   ```

## 6. Troubleshooting

### Error: "The from address does not match a verified Sender Identity"

**Solución**: El email remitente (`EMAIL_FROM`) debe estar verificado en SendGrid. Ve a Settings → Sender Authentication y verifica el remitente.

### Error: "API key does not have permission"

**Solución**: Asegúrate de que la API Key tenga permisos de "Mail Send". Crea una nueva API Key con permisos completos si es necesario.

### Error: "Daily sending quota exceeded"

**Solución**: El plan gratuito de SendGrid tiene un límite de 100 emails/día. Espera 24 horas o actualiza a un plan de pago.

### Los emails no se envían

1. Verifica que `SENDGRID_API_KEY` esté configurado correctamente en Railway
2. Verifica que `EMAIL_FROM` esté verificado en SendGrid
3. Revisa los logs del backend para ver errores específicos
4. Verifica que el destinatario sea un email válido

## 7. Ventajas de SendGrid sobre SMTP

- ✅ Funciona con Railway (no requiere puertos SMTP)
- ✅ Más rápido (API REST vs SMTP)
- ✅ Mejor tracking y analytics
- ✅ Plan gratuito generoso (100 emails/día)
- ✅ Mejor deliverability (menos spam)
- ✅ APIs para gestión avanzada

## 8. Alternativas a SendGrid

Si prefieres otra opción, también puedes usar:
- **Mailgun**: Similar a SendGrid, 5,000 emails/mes gratis
- **Resend**: Moderno, 3,000 emails/mes gratis
- **Postmark**: Excelente deliverability, 100 emails/mes gratis
- **AWS SES**: Muy económico, pero requiere configuración AWS

Para usar cualquiera de estos, necesitarías crear un servicio similar a `SendGridEmailService` adaptado a su API.

