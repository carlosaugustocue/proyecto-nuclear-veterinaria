# Guía de Despliegue del Frontend

Esta guía explica cómo desplegar el frontend del Sistema de Gestión Veterinaria en diferentes plataformas.

**Nota:** El frontend está en un repositorio/directorio separado: `/home/ksp/IdeaProjects/frontend`

## 📋 Opciones de Despliegue

### Opción 1: Desplegar en Railway (Recomendado si quieres todo en Railway)

Railway permite desplegar el frontend como un servicio separado en el mismo proyecto.

#### Pasos:

1. **Crear un nuevo servicio en Railway:**
   - En tu proyecto de Railway, haz clic en "New Service"
   - Selecciona "GitHub Repo" y conecta el repositorio del frontend (directorio separado)
   - O selecciona "Empty Service" y luego "Deploy from GitHub repo"

2. **Configurar el servicio:**
   - Railway detectará automáticamente el `Dockerfile` en la raíz del repositorio
   - El Dockerfile incluido usa Nginx para servir los archivos estáticos
   - Asegúrate de que el `railway.json` esté configurado correctamente

3. **Variables de entorno:**
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   NODE_VERSION=20
   ```

3. **Obtener la URL del backend:**
   - Ve a tu servicio backend en Railway
   - Copia la URL pública (ej: `https://tu-backend.railway.app`)
   - Configura `VITE_API_URL` en el servicio frontend con esa URL

#### Ventajas:
- ✅ Todo en Railway (fácil de gestionar)
- ✅ Despliegues automáticos desde GitHub
- ✅ Repositorio separado para frontend y backend

#### Desventajas:
- ⚠️ Railway no está optimizado específicamente para frontend estático
- ⚠️ Puede ser más costoso que Netlify/Vercel para frontend
- ⚠️ Requiere gestionar dos repositorios separados

---

### Opción 2: Desplegar en Netlify (Recomendado para frontend)

Netlify está optimizado para aplicaciones frontend y ofrece CDN global, despliegues rápidos y un plan gratuito generoso.

#### Pasos:

1. **Preparar el repositorio:**
   - El frontend está en un directorio/repositorio separado: `/home/ksp/IdeaProjects/frontend`
   - Conecta este repositorio directamente en Netlify

2. **Crear cuenta en Netlify:**
   - Ve a [netlify.com](https://netlify.com)
   - Inicia sesión con GitHub

3. **Conectar el repositorio:**
   - Haz clic en "Add new site" → "Import an existing project"
   - Selecciona el repositorio del frontend (directorio separado)
   - Configura:
     - **Build command**: `npm ci && npm run build`
     - **Publish directory**: `dist`

4. **Variables de entorno:**
   - Ve a "Site settings" → "Environment variables"
   - Agrega:
     ```
     VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
     ```

5. **Configuración adicional:**
   - El archivo `netlify.toml` ya está configurado con las redirecciones necesarias para Vue Router
   - Netlify detectará automáticamente este archivo

6. **Desplegar:**
   - Netlify desplegará automáticamente en cada push a la rama principal
   - Obtendrás una URL como: `https://tu-app.netlify.app`

#### Ventajas:
- ✅ Optimizado para frontend (CDN global, rápido)
- ✅ Plan gratuito generoso
- ✅ Despliegues instantáneos
- ✅ Preview deployments para PRs
- ✅ SSL automático

#### Desventajas:
- ⚠️ Servicio diferente al backend (pero esto no es realmente un problema)
- ⚠️ Requiere gestionar dos repositorios separados

---

### Opción 3: Desplegar en Vercel

Similar a Netlify, Vercel también está optimizado para frontend.

#### Pasos:

1. **Crear cuenta en Vercel:**
   - Ve a [vercel.com](https://vercel.com)
   - Inicia sesión con GitHub

2. **Importar proyecto:**
   - Haz clic en "Add New Project"
   - Selecciona el repositorio del frontend (directorio separado)
   - Configura:
     - **Framework Preset**: Vite
     - **Build Command**: `npm run build`
     - **Output Directory**: `dist`

3. **Variables de entorno:**
   - Agrega `VITE_API_URL` con la URL de tu backend

4. **Desplegar:**
   - Vercel desplegará automáticamente

---

## 🔧 Configuración de Variables de Entorno

### Desarrollo Local

Crea un archivo `.env.local` en la carpeta `frontend/`:

```env
VITE_API_URL=http://localhost:8080
```

### Producción

#### Railway:
```
VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
```

#### Netlify/Vercel:
```
VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
```

**Importante:** No incluyas `/api` al final de `VITE_API_URL`. El código lo agrega automáticamente.

---

## 🔐 Configuración de CORS en el Backend

Asegúrate de que el backend permita requests desde el dominio del frontend.

En `application-prod.properties`:

```properties
# CORS - Permitir requests desde el frontend
app.cors.allowed-origins=${FRONTEND_URL:https://tu-frontend.netlify.app,https://tu-frontend.railway.app}
app.cors.allowed-methods=GET,POST,PUT,PATCH,DELETE,OPTIONS
app.cors.allowed-headers=*
app.cors.allow-credentials=true
```

O configura la variable de entorno `FRONTEND_URL` en Railway con la URL de tu frontend.

---

## 📝 Checklist de Despliegue

- [ ] Configurar `VITE_API_URL` con la URL del backend
- [ ] Verificar que el backend tenga CORS configurado para el dominio del frontend
- [ ] Probar el login desde el frontend desplegado
- [ ] Verificar que las rutas de Vue Router funcionen (SPA)
- [ ] Probar las funcionalidades principales (citas, pacientes, etc.)

---

## 🐛 Troubleshooting

### Error: "Network Error" o "CORS Error"

**Problema:** El frontend no puede conectarse al backend.

**Solución:**
1. Verifica que `VITE_API_URL` esté configurada correctamente
2. Verifica que el backend tenga CORS habilitado para el dominio del frontend
3. Verifica que el backend esté accesible públicamente

### Error: "404 Not Found" en rutas de Vue Router

**Problema:** Al refrescar la página, aparece un 404.

**Solución:**
- En Netlify: El archivo `netlify.toml` ya está configurado con las redirecciones necesarias
- En Railway: El Dockerfile con Nginx ya está configurado para SPA
- En Vercel: Vercel detecta automáticamente las SPAs y las configura

### El frontend muestra "localhost:8080" en los logs

**Problema:** El frontend está usando la URL de desarrollo.

**Solución:**
- Verifica que `VITE_API_URL` esté configurada en las variables de entorno de producción
- Reconstruye el frontend después de agregar la variable

---

## 📚 Recursos Adicionales

- [Netlify Documentation](https://docs.netlify.com/)
- [Vercel Documentation](https://vercel.com/docs)
- [Railway Documentation](https://docs.railway.app/)
- [Vite Environment Variables](https://vitejs.dev/guide/env-and-mode.html)

