# 🔧 Solución al Error de CORS con Netlify

## ❌ Error Actual

```
Access to XMLHttpRequest at 'https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/login' 
from origin 'https://6931178...--super-croquembouche-672a93.netlify.app' 
has been blocked by CORS policy: Response to preflight request doesn't pass access control check: 
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

## ✅ Solución

El código ya está actualizado para soportar Netlify, pero **necesitas redesplegar el backend** en Railway.

### Paso 1: Verificar que el código esté en el repositorio

El código ya incluye:
- Patrón `https://*.netlify.app` para todos los dominios de Netlify
- Soporte para variable de entorno `CORS_ALLOWED_ORIGINS`

### Paso 2: Hacer commit y push de los cambios

Si aún no has hecho commit de los cambios en `SecurityConfig.java`:

```bash
cd /home/ksp/IdeaProjects/pn-veterinaria
git add src/main/java/com/veterinaria/infrastructure/security/config/SecurityConfig.java
git commit -m "Fix: Agregar soporte CORS para Netlify"
git push
```

### Paso 3: Verificar que Railway redespliegue automáticamente

Railway debería detectar el push y redesplegar automáticamente. Si no:

1. Ve a Railway → Tu proyecto → Servicio backend
2. Ve a la pestaña "Deployments"
3. Haz clic en "Redeploy" en el último deployment

### Paso 4: (Opcional) Configurar variable de entorno en Railway

Aunque el patrón `https://*.netlify.app` debería funcionar, puedes agregar la URL específica:

1. Ve a Railway → Tu proyecto → Servicio backend
2. Ve a la pestaña "Variables"
3. Agrega o actualiza:
   ```
   CORS_ALLOWED_ORIGINS=https://super-croquembouche-672a93.netlify.app
   FRONTEND_URL=https://super-croquembouche-672a93.netlify.app
   ```

### Paso 5: Verificar que funcione

1. Espera a que Railway termine de redesplegar (1-2 minutos)
2. Abre el frontend: https://super-croquembouche-672a93.netlify.app
3. Abre las herramientas de desarrollador (F12)
4. Intenta hacer login
5. Verifica que no haya errores de CORS

## 🔍 Verificación del Código

El código en `SecurityConfig.java` ya incluye:

```java
originPatterns.add("https://*.netlify.app");  // Dominios de Netlify
```

Esto debería cubrir:
- `https://super-croquembouche-672a93.netlify.app`
- `https://6931178...--super-croquembouche-672a93.netlify.app` (preview deployments)
- Cualquier otro dominio de Netlify

## 🐛 Si el problema persiste

### Verificar que el backend esté redesplegado

1. Ve a Railway → Logs del servicio backend
2. Busca en los logs que el backend haya iniciado recientemente
3. Verifica que no haya errores de compilación

### Verificar la configuración de CORS

Puedes probar el endpoint directamente con curl:

```bash
curl -X OPTIONS https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/login \
  -H "Origin: https://super-croquembouche-672a93.netlify.app" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type" \
  -v
```

Deberías ver headers como:
```
Access-Control-Allow-Origin: https://super-croquembouche-672a93.netlify.app
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
```

### Verificar que las rutas públicas permitan OPTIONS

El código ya permite OPTIONS en todas las rutas a través de CORS, pero verifica que `/api/auth/login` esté en las rutas públicas (ya lo está).

## 📝 Checklist

- [ ] Código actualizado con soporte para `https://*.netlify.app`
- [ ] Cambios commiteados y pusheados al repositorio
- [ ] Railway ha redesplegado el backend
- [ ] (Opcional) Variable `CORS_ALLOWED_ORIGINS` configurada en Railway
- [ ] Backend accesible: https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/ping
- [ ] Login funciona desde el frontend sin errores de CORS

## 🎯 Resumen

**El problema:** El backend no tiene los headers CORS necesarios porque no se ha redesplegado con el código actualizado.

**La solución:** 
1. Asegúrate de que los cambios estén en el repositorio
2. Railway redesplegará automáticamente
3. O redespliega manualmente en Railway

El código ya está listo, solo necesita ser desplegado.

