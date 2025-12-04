# Opciones para el Repositorio del Frontend

Esta guía explica las opciones para organizar el repositorio del frontend.

## 📋 Opciones Disponibles

### ✅ Opción 1: Mismo Repositorio (Recomendado)

**Mantener el frontend en la carpeta `frontend/` del mismo repositorio.**

#### Ventajas:
- ✅ Un solo repositorio para gestionar
- ✅ Cambios de frontend y backend sincronizados
- ✅ Historial de commits unificado
- ✅ Más fácil de mantener
- ✅ Netlify/Railway pueden construir desde subcarpetas

#### Desventajas:
- ⚠️ Repositorio más grande
- ⚠️ No puedes versionar frontend y backend por separado

#### Configuración en Netlify:

1. Conecta el mismo repositorio
2. Configura:
   - **Base directory**: `frontend`
   - **Build command**: `npm ci && npm run build`
   - **Publish directory**: `frontend/dist`

#### Configuración en Railway:

1. Crea un nuevo servicio en el mismo proyecto
2. Conecta el mismo repositorio
3. Configura:
   - **Root Directory**: `frontend`
   - Railway detectará el `Dockerfile` automáticamente

---

### 🔀 Opción 2: Repositorio Separado

**Mover la carpeta `frontend/` a un repositorio independiente.**

#### Ventajas:
- ✅ Repositorios más pequeños
- ✅ Versionado independiente
- ✅ Despliegues completamente independientes
- ✅ Mejor para equipos grandes

#### Desventajas:
- ⚠️ Dos repositorios que mantener
- ⚠️ Cambios no sincronizados automáticamente
- ⚠️ Más complejo de gestionar

#### Pasos para crear repositorio separado:

1. **Crear nuevo repositorio en GitHub:**
   ```bash
   # En GitHub, crea un nuevo repositorio llamado "pn-veterinaria-frontend"
   ```

2. **Mover la carpeta frontend:**
   ```bash
   # Opción A: Copiar (mantiene el original)
   cp -r frontend ../pn-veterinaria-frontend
   cd ../pn-veterinaria-frontend
   git init
   git add .
   git commit -m "Initial commit: Frontend del Sistema Veterinaria"
   git remote add origin https://github.com/tu-usuario/pn-veterinaria-frontend.git
   git push -u origin main
   
   # Opción B: Mover (elimina del repositorio original)
   git mv frontend ../pn-veterinaria-frontend
   # Luego inicializa el nuevo repositorio
   ```

3. **Actualizar .gitignore del backend:**
   ```bash
   # En el repositorio backend, agrega a .gitignore:
   echo "frontend/" >> .gitignore
   ```

4. **Configurar Netlify:**
   - Conecta el nuevo repositorio
   - Configura:
     - **Build command**: `npm ci && npm run build`
     - **Publish directory**: `dist`

---

## 🎯 Recomendación

**Para tu caso, recomiendo la Opción 1 (mismo repositorio)** porque:

1. ✅ Es más simple de mantener
2. ✅ Netlify y Railway soportan construir desde subcarpetas
3. ✅ Cambios sincronizados entre frontend y backend
4. ✅ Un solo lugar para todo el código

**Solo usa la Opción 2 si:**
- Tienes un equipo grande trabajando en paralelo
- Quieres versionar frontend y backend completamente por separado
- Necesitas diferentes permisos de acceso

---

## 📝 Configuración Rápida (Opción 1 - Mismo Repositorio)

### Netlify:

1. Ve a [netlify.com](https://netlify.com)
2. Click en "Add new site" → "Import an existing project"
3. Selecciona tu repositorio actual
4. Configura:
   ```
   Base directory: frontend
   Build command: npm ci && npm run build
   Publish directory: frontend/dist
   ```
5. Agrega variable de entorno:
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   ```

### Railway:

1. En tu proyecto de Railway, click en "New Service"
2. Selecciona "Deploy from GitHub repo"
3. Selecciona tu repositorio actual
4. Configura:
   ```
   Root Directory: frontend
   ```
5. Railway detectará el `Dockerfile` automáticamente
6. Agrega variable de entorno:
   ```
   VITE_API_URL=https://proyecto-nuclear-veterinaria-production.up.railway.app
   ```

---

## 🔄 Si ya moviste el frontend (Opción 2)

Si ya creaste un repositorio separado, asegúrate de:

1. ✅ Actualizar `.gitignore` del backend para ignorar `frontend/`
2. ✅ Configurar `VITE_API_URL` en el nuevo repositorio
3. ✅ Configurar CORS en el backend con la nueva URL del frontend
4. ✅ Actualizar la documentación

---

## ❓ Preguntas Frecuentes

### ¿Puedo cambiar de opción después?

**Sí**, pero requiere trabajo:
- De Opción 1 a 2: Mover la carpeta y crear nuevo repositorio
- De Opción 2 a 1: Mover el código de vuelta y actualizar configuraciones

### ¿Qué pasa con el historial de Git?

- **Opción 1**: Mantiene todo el historial
- **Opción 2**: Puedes usar `git filter-branch` o `git subtree` para preservar historial

### ¿Cuál es mejor para CI/CD?

Ambas funcionan bien. La Opción 1 es más simple para configurar pipelines.

---

## 📚 Recursos

- [Netlify - Build Settings](https://docs.netlify.com/configure-builds/overview/)
- [Railway - Root Directory](https://docs.railway.app/deploy/builds#root-directory)
- [Git Subtree](https://www.atlassian.com/git/tutorials/git-subtree)

