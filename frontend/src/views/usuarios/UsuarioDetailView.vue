<template>
  <v-container fluid class="pa-6">
    <v-row v-if="loading">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </v-col>
    </v-row>

    <template v-else-if="usuario">
      <v-row>
        <v-col cols="12">
          <div class="d-flex justify-space-between align-center mb-6">
            <h1>Detalle de Usuario</h1>
            <div>
              <v-btn
                color="primary"
                :to="`/usuarios/${usuario.id}/editar`"
                class="mr-2"
              >
                Editar
              </v-btn>
              <v-btn
                color="secondary"
                @click="$router.back()"
              >
                Volver
              </v-btn>
            </div>
          </div>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="6">
          <v-card class="mb-4">
            <v-card-title class="bg-primary text-white">
              Información Personal
            </v-card-title>
            <v-card-text class="pa-4">
              <v-list>
                <v-list-item>
                  <v-list-item-title>Nombre Completo</v-list-item-title>
                  <v-list-item-subtitle>{{ usuario.nombreCompleto }}</v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Email</v-list-item-title>
                  <v-list-item-subtitle>{{ usuario.email }}</v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Usuario</v-list-item-title>
                  <v-list-item-subtitle>{{ usuario.username }}</v-list-item-subtitle>
                </v-list-item>
                <v-list-item v-if="usuario.telefono">
                  <v-list-item-title>Teléfono</v-list-item-title>
                  <v-list-item-subtitle>{{ usuario.telefono }}</v-list-item-subtitle>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col cols="12" md="6">
          <v-card class="mb-4">
            <v-card-title class="bg-primary text-white">
              Información del Sistema
            </v-card-title>
            <v-card-text class="pa-4">
              <v-list>
                <v-list-item>
                  <v-list-item-title>Tipo de Usuario</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-chip color="primary">{{ usuario.tipoUsuarioStr }}</v-chip>
                  </v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Estado</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-chip :color="usuario.isActive ? 'success' : 'error'">
                      {{ usuario.estadoStr }}
                    </v-chip>
                  </v-list-item-subtitle>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Roles</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-chip
                      v-for="rol in usuario.roles"
                      :key="rol.id"
                      class="mr-2 mt-2"
                      color="secondary"
                    >
                      {{ rol.nombre }}
                    </v-chip>
                  </v-list-item-subtitle>
                </v-list-item>
                <v-list-item v-if="usuario.ultimoAcceso">
                  <v-list-item-title>Último Acceso</v-list-item-title>
                  <v-list-item-subtitle>{{ formatDate(usuario.ultimoAcceso) }}</v-list-item-subtitle>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-if="usuario.permisos && usuario.permisos.length">
        <v-col cols="12">
          <v-card>
            <v-card-title class="bg-primary text-white">
              Permisos
            </v-card-title>
            <v-card-text class="pa-4">
              <v-chip
                v-for="permiso in usuario.permisos"
                :key="permiso.id"
                class="mr-2 mb-2"
                size="small"
              >
                {{ permiso.nombre }}
              </v-chip>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </template>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUsuariosStore } from '@/stores/usuariosStore'

const route = useRoute()
const usuariosStore = useUsuariosStore()

const loading = computed(() => usuariosStore.loading)
const usuario = computed(() => usuariosStore.currentUsuario)

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('es-ES')
}

onMounted(async () => {
  await usuariosStore.fetchUsuarioById(route.params.id)
})
</script>

<style scoped>
</style>
