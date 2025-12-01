<template>
  <v-app>
    <v-app-bar v-if="isAuthenticated" color="primary" prominent>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Sistema Veterinaria</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-menu offset-y>
        <template v-slot:activator="{ props }">
          <v-btn icon v-bind="props">
            <v-icon>mdi-account</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item @click="goToProfile">
            <v-list-item-title>Mi Perfil</v-list-item-title>
          </v-list-item>
          <v-divider></v-divider>
          <v-list-item @click="logout">
            <v-list-item-title>Cerrar Sesión</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer v-if="isAuthenticated" v-model="drawer" temporary>
      <v-list dense>
        <v-list-item to="/" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-home</v-icon>
          </template>
          <v-list-item-title>Dashboard</v-list-item-title>
        </v-list-item>

        <v-list-item to="/citas" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-calendar</v-icon>
          </template>
          <v-list-item-title>Citas</v-list-item-title>
        </v-list-item>

        <v-list-item to="/pacientes" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-paw</v-icon>
          </template>
          <v-list-item-title>Mascotas</v-list-item-title>
        </v-list-item>

        <v-list-item to="/clientes" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-account</v-icon>
          </template>
          <v-list-item-title>Clientes</v-list-item-title>
        </v-list-item>

        <v-list-item to="/facturas" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-receipt</v-icon>
          </template>
          <v-list-item-title>Facturas</v-list-item-title>
        </v-list-item>

        <v-list-item to="/usuarios" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-account-group</v-icon>
          </template>
          <v-list-item-title>Usuarios</v-list-item-title>
        </v-list-item>

        <v-list-item to="/historiales" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-file-document</v-icon>
          </template>
          <v-list-item-title>Historia Clínica</v-list-item-title>
        </v-list-item>

        <v-list-item to="/inventario" @click="drawer = false">
          <template v-slot:prepend>
            <v-icon>mdi-package-variant</v-icon>
          </template>
          <v-list-item-title>Inventario</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <router-view />
    </v-main>

    <!-- Global Snackbar for notifications -->
    <v-snackbar
      v-model="snackbar.show"
      :color="snackbar.color"
      :timeout="snackbar.timeout"
      location="top right"
    >
      {{ snackbar.message }}
      <template v-slot:actions>
        <v-btn
          color="white"
          variant="text"
          @click="hideNotification"
        >
          Cerrar
        </v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const authStore = useAuthStore()
const drawer = ref(false)

const isAuthenticated = computed(() => authStore.isAuthenticated)

const { snackbar, hideNotification } = useNotification()

const goToProfile = () => {
  router.push('/profile')
}

const logout = async () => {
  await authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
</style>
