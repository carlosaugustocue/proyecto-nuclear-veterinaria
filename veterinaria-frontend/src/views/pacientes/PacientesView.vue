<template>
  <div>
    <div class="d-flex justify-space-between align-center mb-6">
      <h1 class="text-h4 font-weight-bold">
        <v-icon class="mr-2">mdi-paw</v-icon>
        Gestión de Pacientes
      </h1>
      <v-btn color="primary" prepend-icon="mdi-plus" to="/pacientes/nuevo">
        Nuevo Paciente
      </v-btn>
    </div>

    <!-- Filtros -->
    <v-card class="mb-4">
      <v-card-text>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field
              v-model="search"
              prepend-inner-icon="mdi-magnify"
              label="Buscar por nombre o microchip"
              clearable
              hide-details
              @input="handleSearch"
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="3">
            <v-select
              v-model="filtroEspecie"
              :items="especiesOptions"
              label="Especie"
              clearable
              hide-details
              @update:model-value="handleFilterChange"
            ></v-select>
          </v-col>

          <v-col cols="12" md="3">
            <v-select
              v-model="filtroEstado"
              :items="estadosOptions"
              label="Estado"
              clearable
              hide-details
              @update:model-value="handleFilterChange"
            ></v-select>
          </v-col>

          <v-col cols="12" md="2">
            <v-btn
              block
              color="secondary"
              variant="outlined"
              @click="limpiarFiltros"
            >
              Limpiar Filtros
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <!-- Tarjetas de Estadísticas -->
    <v-row class="mb-4">
      <v-col cols="12" md="4">
        <v-card>
          <v-card-text>
            <div class="d-flex align-center">
              <v-icon size="40" color="primary" class="mr-4">mdi-paw</v-icon>
              <div>
                <div class="text-h5 font-weight-bold">{{ totalPacientes }}</div>
                <div class="text-caption text-grey-darken-1">Total Pacientes</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card>
          <v-card-text>
            <div class="d-flex align-center">
              <v-icon size="40" color="success" class="mr-4">mdi-check-circle</v-icon>
              <div>
                <div class="text-h5 font-weight-bold">{{ pacientesActivos }}</div>
                <div class="text-caption text-grey-darken-1">Pacientes Activos</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card>
          <v-card-text>
            <div class="d-flex align-center">
              <v-icon size="40" color="accent" class="mr-4">mdi-filter</v-icon>
              <div>
                <div class="text-h5 font-weight-bold">{{ pacientesFiltrados.length }}</div>
                <div class="text-caption text-grey-darken-1">Resultados Filtrados</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Tabla de Pacientes -->
    <v-card>
      <v-card-text>
        <v-data-table
          :headers="headers"
          :items="pacientesFiltrados"
          :loading="loading"
          :items-per-page="10"
          class="elevation-0"
        >
          <!-- Nombre con foto -->
          <template #item.nombre="{ item }">
            <div class="d-flex align-center py-2">
              <v-avatar size="40" class="mr-3">
                <v-img
                  v-if="item.fotoUrl"
                  :src="item.fotoUrl"
                  :alt="item.nombre"
                ></v-img>
                <v-icon v-else color="grey-lighten-1">mdi-paw</v-icon>
              </v-avatar>
              <div>
                <div class="font-weight-medium">{{ item.nombre }}</div>
                <div class="text-caption text-grey-darken-1">
                  {{ item.raza || 'Sin raza especificada' }}
                </div>
              </div>
            </div>
          </template>

          <!-- Especie con icono -->
          <template #item.especie="{ item }">
            <v-chip :color="getEspecieColor(item.especie)" variant="tonal" size="small">
              <v-icon start>{{ getEspecieIcon(item.especie) }}</v-icon>
              {{ getEspecieLabel(item.especie) }}
            </v-chip>
          </template>

          <!-- Edad calculada -->
          <template #item.edad="{ item }">
            {{ calcularEdad(item.fechaNacimiento) }} años
          </template>

          <!-- Sexo -->
          <template #item.sexo="{ item }">
            <v-chip :color="item.sexo === 'MACHO' ? 'blue' : 'pink'" variant="tonal" size="small">
              {{ item.sexo === 'MACHO' ? 'Macho' : 'Hembra' }}
            </v-chip>
          </template>

          <!-- Propietario -->
          <template #item.cliente="{ item }">
            <div v-if="item.cliente">
              <div class="font-weight-medium">{{ item.cliente.nombre }}</div>
              <div class="text-caption text-grey-darken-1">{{ item.cliente.email }}</div>
            </div>
            <span v-else class="text-grey">Sin propietario</span>
          </template>

          <!-- Estado -->
          <template #item.estado="{ item }">
            <v-chip :color="getEstadoColor(item.estado)" variant="tonal" size="small">
              {{ getEstadoLabel(item.estado) }}
            </v-chip>
          </template>

          <!-- Acciones -->
          <template #item.actions="{ item }">
            <div class="d-flex gap-1">
              <v-tooltip text="Ver detalles">
                <template #activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-eye"
                    size="small"
                    variant="text"
                    @click="verDetalle(item.id)"
                  ></v-btn>
                </template>
              </v-tooltip>

              <v-tooltip text="Editar">
                <template #activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-pencil"
                    size="small"
                    variant="text"
                    color="primary"
                    @click="editar(item.id)"
                  ></v-btn>
                </template>
              </v-tooltip>

              <v-tooltip text="Eliminar">
                <template #activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-delete"
                    size="small"
                    variant="text"
                    color="error"
                    @click="confirmarEliminacion(item)"
                  ></v-btn>
                </template>
              </v-tooltip>
            </div>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>

    <!-- Diálogo de Confirmación de Eliminación -->
    <v-dialog v-model="dialogEliminar" max-width="500">
      <v-card>
        <v-card-title class="text-h5">Confirmar Eliminación</v-card-title>
        <v-card-text>
          ¿Está seguro que desea eliminar al paciente <strong>{{ pacienteAEliminar?.nombre }}</strong>?
          Esta acción no se puede deshacer.
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn variant="text" @click="dialogEliminar = false">Cancelar</v-btn>
          <v-btn color="error" variant="flat" @click="eliminar">Eliminar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { usePacientesStore } from '@/store/pacientes'
import { storeToRefs } from 'pinia'

const router = useRouter()
const pacientesStore = usePacientesStore()
const { pacientesFiltrados, loading, totalPacientes, pacientesActivos } = storeToRefs(pacientesStore)

const search = ref('')
const filtroEspecie = ref(null)
const filtroEstado = ref(null)
const dialogEliminar = ref(false)
const pacienteAEliminar = ref(null)

const headers = [
  { title: 'Nombre', key: 'nombre', sortable: true },
  { title: 'Especie', key: 'especie', sortable: true },
  { title: 'Edad', key: 'edad', sortable: false },
  { title: 'Sexo', key: 'sexo', sortable: true },
  { title: 'Peso (kg)', key: 'pesoKg', sortable: true },
  { title: 'Propietario', key: 'cliente', sortable: false },
  { title: 'Estado', key: 'estado', sortable: true },
  { title: 'Acciones', key: 'actions', sortable: false, align: 'center' },
]

const especiesOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Otro', value: 'OTRO' },
]

const estadosOptions = [
  { title: 'Activo', value: 'ACTIVO' },
  { title: 'Inactivo', value: 'INACTIVO' },
  { title: 'Fallecido', value: 'FALLECIDO' },
]

onMounted(() => {
  pacientesStore.cargarPacientes()
})

function handleSearch() {
  pacientesStore.setFilters({ search: search.value })
}

function handleFilterChange() {
  pacientesStore.setFilters({
    especie: filtroEspecie.value,
    estado: filtroEstado.value,
  })
}

function limpiarFiltros() {
  search.value = ''
  filtroEspecie.value = null
  filtroEstado.value = null
  pacientesStore.clearFilters()
}

function verDetalle(id) {
  router.push(`/pacientes/${id}`)
}

function editar(id) {
  router.push(`/pacientes/${id}/editar`)
}

function confirmarEliminacion(paciente) {
  pacienteAEliminar.value = paciente
  dialogEliminar.value = true
}

async function eliminar() {
  if (pacienteAEliminar.value) {
    await pacientesStore.eliminarPaciente(pacienteAEliminar.value.id)
    dialogEliminar.value = false
    pacienteAEliminar.value = null
  }
}

function calcularEdad(fechaNacimiento) {
  if (!fechaNacimiento) return 0
  const hoy = new Date()
  const nacimiento = new Date(fechaNacimiento)
  let edad = hoy.getFullYear() - nacimiento.getFullYear()
  const mes = hoy.getMonth() - nacimiento.getMonth()
  if (mes < 0 || (mes === 0 && hoy.getDate() < nacimiento.getDate())) {
    edad--
  }
  return edad
}

function getEspecieIcon(especie) {
  const icons = {
    PERRO: 'mdi-dog',
    GATO: 'mdi-cat',
    AVE: 'mdi-bird',
    REPTIL: 'mdi-snake',
    ROEDOR: 'mdi-rodent',
    OTRO: 'mdi-paw',
  }
  return icons[especie] || 'mdi-paw'
}

function getEspecieColor(especie) {
  const colors = {
    PERRO: 'brown',
    GATO: 'orange',
    AVE: 'blue',
    REPTIL: 'green',
    ROEDOR: 'grey',
    OTRO: 'purple',
  }
  return colors[especie] || 'grey'
}

function getEspecieLabel(especie) {
  const labels = {
    PERRO: 'Perro',
    GATO: 'Gato',
    AVE: 'Ave',
    REPTIL: 'Reptil',
    ROEDOR: 'Roedor',
    OTRO: 'Otro',
  }
  return labels[especie] || especie
}

function getEstadoColor(estado) {
  const colors = {
    ACTIVO: 'success',
    INACTIVO: 'warning',
    FALLECIDO: 'error',
  }
  return colors[estado] || 'grey'
}

function getEstadoLabel(estado) {
  const labels = {
    ACTIVO: 'Activo',
    INACTIVO: 'Inactivo',
    FALLECIDO: 'Fallecido',
  }
  return labels[estado] || estado
}
</script>
