# 🚀 Configuración del Frontend en Netlify

El frontend está desplegado en: **https://super-croquembouche-672a93.netlify.app**

## ✅ Configuración Actual

### Frontend (Netlify)
- **URL**: `https://super-croquembouche-672a93.netlify.app`
- **Variable de entorno**: `VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app`

### Backend (Railway)
- **URL**: `https://proyecto-nuclear-veterinaria-production.up.railway.app`
- **Variables de entorno necesarias**:
  ```
  CORS_ALLOWED_ORIGINS=https://super-croquembouche-672a93.netlify.app
  FRONTEND_URL=https://super-croquembouche-672a93.netlify.app
  ```

## 🔧 Pasos para Configurar CORS en Railway

**IMPORTANTE:** Debes configurar CORS en el backend para permitir requests desde Netlify.

1. Ve a tu proyecto en Railway
2. Selecciona el servicio backend: `proyecto-nuclear-veterinaria-production`
3. Ve a la pestaña **"Variables"**
4. Agrega o actualiza estas variables:

```
CORS_ALLOWED_ORIGINS=https://super-croquembouche-672a93.netlify.app
FRONTEND_URL=https://super-croquembouche-672a93.netlify.app
```

5. Railway redesplegará automáticamente el backend

## ✅ Verificación

Después de configurar CORS:

1. Abre el frontend: https://super-croquembouche-672a93.netlify.app
2. Abre las herramientas de desarrollador (F12)
3. Ve a la pestaña **"Network"**
4. Intenta hacer login
5. Verifica que:
   - Las requests vayan a: `https://proyecto-nuclear-veterinaria-production.up.railway.app/api/...`
   - No aparezcan errores de CORS en la consola

## 🐛 Troubleshooting

### Error: "CORS policy: No 'Access-Control-Allow-Origin' header"

**Problema:** CORS no está configurado correctamente.

**Solución:**
1. Verifica que `CORS_ALLOWED_ORIGINS` esté configurada en Railway
2. Verifica que el valor sea exactamente: `https://super-croquembouche-672a93.netlify.app`
3. Espera a que Railway redespliegue el backend
4. Limpia la caché del navegador y vuelve a intentar

### Error: "Network Error"

**Problema:** El frontend no puede conectarse al backend.

**Solución:**
1. Verifica que `VITE_API_URL` esté configurada en Netlify
2. Verifica que el backend esté accesible: https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/ping
3. Verifica que no haya problemas de red/firewall

### El frontend muestra "localhost:8080" en los logs

**Problema:** El frontend está usando la URL de desarrollo.

**Solución:**
1. Verifica que `VITE_API_URL` esté configurada en Netlify
2. Redespliega el frontend en Netlify
3. Limpia la caché del navegador

## 📝 Checklist

- [x] Frontend desplegado en Netlify: `https://super-croquembouche-672a93.netlify.app`
- [ ] `VITE_API_URL` configurada en Netlify con: `https://proyecto-nuclear-veterinaria-production.up.railway.app`
- [ ] `CORS_ALLOWED_ORIGINS` configurada en Railway con: `https://super-croquembouche-672a93.netlify.app`
- [ ] `FRONTEND_URL` configurada en Railway con: `https://super-croquembouche-672a93.netlify.app`
- [ ] Backend redesplegado después de agregar las variables CORS
- [ ] Login funciona desde el frontend desplegado
- [ ] No hay errores de CORS en la consola del navegador

## 🔗 URLs del Sistema

- **Frontend**: https://super-croquembouche-672a93.netlify.app
- **Backend**: https://proyecto-nuclear-veterinaria-production.up.railway.app
- **Backend API**: https://proyecto-nuclear-veterinaria-production.up.railway.app/api

