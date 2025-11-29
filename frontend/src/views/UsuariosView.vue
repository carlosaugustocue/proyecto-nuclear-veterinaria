<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Usuarios</h1>
          <v-btn
            to="/usuarios/nuevo"
            color="primary"
            prepend-icon="mdi-plus"
          >
            Nuevo Usuario
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar usuario"
          prepend-icon="mdi-magnify"
          clearable
          @update:model-value="fetchUsuarios"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.tipoUsuario"
          label="Tipo Usuario"
          :items="tipoUsuarioOptions"
          clearable
          @update:model-value="fetchUsuarios"
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.activo"
          label="Estado"
          :items="estadoOptions"
          clearable
          @update:model-value="fetchUsuarios"
        ></v-select>
      </v-col>
    </v-row>

    <!-- Tabla de usuarios -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-data-table
            :headers="headers"
            :items="usuarios"
            :loading="loading"
            :sort-by="[{ key: 'nombreCompleto', order: 'asc' }]"
            class="elevation-1"
          >
            <template v-slot:item.tipoUsuarioStr="{ item }">
              <v-chip color="primary">
                {{ item.tipoUsuarioStr }}
              </v-chip>
            </template>

            <template v-slot:item.estadoStr="{ item }">
              <v-chip :color="item.isActive ? 'success' : 'error'" text-color="white">
                {{ item.estadoStr }}
              </v-chip>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-btn
                size="small"
                color="primary"
                variant="text"
                icon="mdi-eye"
                :to="`/usuarios/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="mdi-pencil"
                :to="`/usuarios/${item.id}/editar`"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="mdi-delete"
                @click="deleteUsuario(item.id)"
              ></v-btn>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" text class="my-6">
                No hay usuarios registrados
              </v-alert>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { reactive, onMounted, computed } from 'vue'
import { useUsuariosStore } from '@/stores/usuariosStore'

const usuariosStore = useUsuariosStore()

const filters = reactive({
  searchText: '',
  tipoUsuario: null,
  activo: null,
})

const tipoUsuarioOptions = ['VETERINARIO', 'RECEPCIONISTA', 'ADMIN']
const estadoOptions = [
  { title: 'Activo', value: true },
  { title: 'Inactivo', value: false }
]

const headers = [
  { title: 'Nombre', value: 'nombreCompleto' },
  { title: 'Email', value: 'email' },
  { title: 'Usuario', value: 'username' },
  { title: 'Tipo', value: 'tipoUsuarioStr' },
  { title: 'Roles', value: 'rolesStr' },
  { title: 'Estado', value: 'estadoStr' },
  { title: 'Acciones', value: 'actions', sortable: false },
]

const loading = computed(() => usuariosStore.loading)
const usuarios = computed(() => usuariosStore.usuarios)

const fetchUsuarios = async () => {
  try {
    await usuariosStore.fetchUsuarios(filters)
  } catch (error) {
    console.error('Error al cargar usuarios:', error)
  }
}

const deleteUsuario = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
    try {
      await usuariosStore.deleteUsuario(id)
    } catch (error) {
      console.error('Error al eliminar usuario:', error)
    }
  }
}

onMounted(() => {
  fetchUsuarios()
})
</script>

<style scoped>
</style>
