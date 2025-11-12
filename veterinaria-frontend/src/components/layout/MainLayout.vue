<template>
  <v-app>
    <!-- App Bar -->
    <v-app-bar elevation="2" color="primary">
      <v-app-bar-nav-icon @click="appStore.toggleDrawer"></v-app-bar-nav-icon>

      <v-toolbar-title class="d-flex align-center">
        <v-icon size="large" class="mr-2">mdi-paw</v-icon>
        <span class="text-h6">Sistema Veterinaria</span>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <!-- Notificaciones -->
      <v-btn icon>
        <v-badge color="error" content="3" overlap>
          <v-icon>mdi-bell</v-icon>
        </v-badge>
      </v-btn>

      <!-- Usuario -->
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn icon v-bind="props">
            <v-avatar color="secondary" size="36">
              <v-icon>mdi-account</v-icon>
            </v-avatar>
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-list-item-title>{{ authStore.userName }}</v-list-item-title>
            <v-list-item-subtitle>{{ authStore.userEmail }}</v-list-item-subtitle>
          </v-list-item>

          <v-divider></v-divider>

          <v-list-item prepend-icon="mdi-account" to="/perfil">
            <v-list-item-title>Mi Perfil</v-list-item-title>
          </v-list-item>

          <v-list-item prepend-icon="mdi-cog" to="/configuracion">
            <v-list-item-title>Configuración</v-list-item-title>
          </v-list-item>

          <v-divider></v-divider>

          <v-list-item prepend-icon="mdi-logout" @click="handleLogout">
            <v-list-item-title>Cerrar Sesión</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <!-- Navigation Drawer -->
    <v-navigation-drawer v-model="appStore.drawer" permanent>
      <v-list>
        <!-- Dashboard -->
        <v-list-item
          prepend-icon="mdi-view-dashboard"
          title="Dashboard"
          to="/"
          exact
        ></v-list-item>

        <v-divider class="my-2"></v-divider>

        <!-- Clientes -->
        <v-list-item prepend-icon="mdi-account-group" title="Clientes" to="/clientes"></v-list-item>

        <!-- Pacientes -->
        <v-list-item prepend-icon="mdi-paw" title="Pacientes" to="/pacientes"></v-list-item>

        <!-- Citas -->
        <v-list-item prepend-icon="mdi-calendar-clock" title="Citas" to="/citas"></v-list-item>

        <v-divider class="my-2"></v-divider>

        <!-- Consultas -->
        <v-list-item
          prepend-icon="mdi-stethoscope"
          title="Consultas"
          to="/consultas"
        ></v-list-item>

        <!-- Historial Médico -->
        <v-list-item
          prepend-icon="mdi-file-document"
          title="Historial Médico"
          to="/historial"
        ></v-list-item>

        <v-divider class="my-2"></v-divider>

        <!-- Facturación -->
        <v-list-item
          prepend-icon="mdi-receipt"
          title="Facturación"
          to="/facturas"
        ></v-list-item>

        <!-- Inventario -->
        <v-list-item
          prepend-icon="mdi-package-variant"
          title="Inventario"
          to="/inventario"
        ></v-list-item>

        <v-divider class="my-2"></v-divider>

        <!-- Reportes -->
        <v-list-item prepend-icon="mdi-chart-bar" title="Reportes" to="/reportes"></v-list-item>

        <!-- Configuración -->
        <v-list-item prepend-icon="mdi-cog" title="Configuración" to="/configuracion"></v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- Main Content -->
    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <!-- Snackbar Global -->
    <v-snackbar
      v-model="appStore.snackbar.show"
      :color="appStore.snackbar.color"
      :timeout="appStore.snackbar.timeout"
      location="top right"
    >
      {{ appStore.snackbar.message }}
      <template v-slot:actions>
        <v-btn icon="mdi-close" @click="appStore.hideSnackbar"></v-btn>
      </template>
    </v-snackbar>

    <!-- Loading Overlay -->
    <v-overlay :model-value="appStore.loading" class="align-center justify-center">
      <v-progress-circular color="primary" indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </v-app>
</template>

<script setup>
import { useAuthStore } from '@/store/auth'
import { useAppStore } from '@/store/app'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const appStore = useAppStore()
const router = useRouter()

const handleLogout = () => {
  authStore.logout()
  appStore.showInfo('Sesión cerrada correctamente')
  router.push('/login')
}
</script>

<style scoped>
.v-toolbar-title {
  font-weight: 600;
}
</style>
