# Configuración de Conexión Frontend-Backend

Esta guía explica cómo conectar el frontend con el backend desplegado en Railway.

## 🔗 URLs del Sistema

**Backend URL:** `https://proyecto-nuclear-veterinaria-production.up.railway.app`

**Frontend URL:** `https://super-croquembouche-672a93.netlify.app`

## 📋 Pasos para Configurar el Frontend

### Paso 1: Configurar Variable de Entorno en el Frontend

Dependiendo de dónde despliegues el frontend:

#### Si usas Netlify:
1. Ve a tu proyecto en Netlify
2. Ve a "Site settings" → "Environment variables"
3. Agrega:
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   ```
4. Haz clic en "Save"
5. Redespliega el sitio (Netlify lo hará automáticamente)

#### Si usas Railway:
1. Ve a tu servicio frontend en Railway
2. Ve a la pestaña "Variables"
3. Agrega:
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   ```
4. Railway redesplegará automáticamente

#### Si usas Vercel:
1. Ve a tu proyecto en Vercel
2. Ve a "Settings" → "Environment Variables"
3. Agrega:
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   ```
4. Selecciona los entornos (Production, Preview, Development)
5. Guarda y redespliega

### Paso 2: Configurar CORS en el Backend

**IMPORTANTE:** Después de desplegar el frontend, debes configurar CORS en Railway para permitir requests desde el dominio del frontend.

1. Ve a tu servicio backend en Railway: `proyecto-nuclear-veterinaria-production`
2. Ve a la pestaña "Variables"
3. Agrega o actualiza estas variables:

```
CORS_ALLOWED_ORIGINS=https://super-croquembouche-672a93.netlify.app
FRONTEND_URL=https://super-croquembouche-672a93.netlify.app
```

**Nota:** La URL del frontend es: `https://super-croquembouche-672a93.netlify.app`

Si tienes múltiples dominios (por ejemplo, producción y staging), sepáralos con comas:

```
CORS_ALLOWED_ORIGINS=https://tu-frontend.netlify.app,https://tu-frontend-staging.netlify.app
```

### Paso 3: Verificar la Conexión

1. Abre tu frontend desplegado en el navegador
2. Abre las herramientas de desarrollador (F12)
3. Ve a la pestaña "Network"
4. Intenta hacer login
5. Verifica que las requests vayan a: `https://proyecto-nuclear-veterinaria-production.up.railway.app/api/...`

## 🔍 Cómo Funciona

El frontend está configurado para:

1. **En desarrollo local:**
   - Usa el proxy de Vite: `/api` → `http://localhost:8080/api`
   - No necesitas configurar `VITE_API_URL`

2. **En producción:**
   - Lee `VITE_API_URL` de las variables de entorno
   - Hace requests a: `${VITE_API_URL}/api/...`
   - Ejemplo: `https://proyecto-nuclear-veterinaria-production.up.railway.app/api/v1/citas`

## 🐛 Troubleshooting

### Error: "Network Error" o "CORS Error"

**Problema:** El frontend no puede conectarse al backend.

**Solución:**
1. Verifica que `VITE_API_URL` esté configurada correctamente en el frontend
2. Verifica que `CORS_ALLOWED_ORIGINS` en el backend incluya la URL del frontend
3. Verifica que el backend esté accesible: `https://proyecto-nuclear-veterinaria-production.up.railway.app/api/health` (si tienes un endpoint de health)

### Error: "401 Unauthorized"

**Problema:** El token JWT no se está enviando o es inválido.

**Solución:**
1. Verifica que el login funcione correctamente
2. Verifica que el token se guarde en `localStorage`
3. Verifica que el token se envíe en el header `Authorization: Bearer <token>`

### El frontend muestra "localhost:8080" en los logs

**Problema:** El frontend está usando la URL de desarrollo.

**Solución:**
1. Verifica que `VITE_API_URL` esté configurada en las variables de entorno de producción
2. Reconstruye el frontend después de agregar la variable
3. En Netlify/Vercel, asegúrate de que la variable esté en el entorno correcto (Production)

## 📝 Checklist Final

- [ ] `VITE_API_URL` configurada en el frontend con: `https://proyecto-nuclear-veterinaria-production.up.railway.app`
- [ ] `CORS_ALLOWED_ORIGINS` configurada en el backend con: `https://super-croquembouche-672a93.netlify.app`
- [ ] `FRONTEND_URL` configurada en el backend con: `https://super-croquembouche-672a93.netlify.app`
- [ ] Frontend redesplegado después de agregar las variables
- [ ] Backend redesplegado después de agregar las variables CORS
- [ ] Login funciona desde el frontend desplegado
- [ ] Las requests van al backend correcto (verificar en Network tab)

## 🔐 Seguridad

- ✅ Nunca commitees `VITE_API_URL` en el código
- ✅ Usa variables de entorno para todas las URLs
- ✅ Configura CORS solo para los dominios que necesitas
- ✅ El backend valida el origen de las requests mediante CORS

