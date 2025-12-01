<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <v-card v-if="cliente">
          <v-card-title class="bg-primary text-white py-4 d-flex justify-space-between align-center">
            <div>
              <div class="text-h5">{{ cliente.nombreCompleto || `${cliente.nombre || ''} ${cliente.apellido || ''}`.trim() }}</div>
              <div class="text-subtitle-2 text-white text-opacity-75 mt-1">
                Cliente #{{ cliente.id }}
              </div>
            </div>
            <v-btn icon color="white" variant="text" @click="$router.back()">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>

          <v-card-text class="pa-6">
            <!-- Información del Cliente -->
            <v-row>
              <v-col cols="12">
                <h3 class="text-h6 mb-4">
                  <v-icon class="mr-2">mdi-account</v-icon>
                  Información del Cliente
                </h3>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-card variant="outlined" class="pa-4">
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Nombre</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.nombre || 'N/A' }}</div>
                  </div>
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Apellido</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.apellido || 'N/A' }}</div>
                  </div>
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Documento de Identidad</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.dni || cliente.documento || 'N/A' }}</div>
                  </div>
                  <div>
                    <div class="text-caption text-grey mb-1">Email</div>
                    <div class="text-body-1">
                      <a :href="`mailto:${cliente.email}`" class="text-primary text-decoration-none">
                        {{ cliente.email || 'N/A' }}
                      </a>
                    </div>
                  </div>
                </v-card>
              </v-col>

              <v-col cols="12" md="6">
                <v-card variant="outlined" class="pa-4">
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Teléfono</div>
                    <div class="text-body-1 font-weight-medium">
                      <a :href="`tel:${cliente.telefono}`" class="text-primary text-decoration-none">
                        {{ cliente.telefono || 'N/A' }}
                      </a>
                    </div>
                  </div>
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Dirección</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.direccion || 'N/A' }}</div>
                  </div>
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Ciudad</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.ciudad || 'N/A' }}</div>
                  </div>
                  <div class="mb-3">
                    <div class="text-caption text-grey mb-1">Departamento</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.departamento || 'N/A' }}</div>
                  </div>
                  <div v-if="cliente.codigoPostal">
                    <div class="text-caption text-grey mb-1">Código Postal</div>
                    <div class="text-body-1 font-weight-medium">{{ cliente.codigoPostal }}</div>
                  </div>
                </v-card>
              </v-col>
            </v-row>

            <v-row v-if="cliente.observaciones" class="mt-2">
              <v-col cols="12">
                <v-card variant="outlined" class="pa-4">
                  <div class="text-caption text-grey mb-1">Observaciones</div>
                  <div class="text-body-2">{{ cliente.observaciones }}</div>
                </v-card>
              </v-col>
            </v-row>

            <v-divider class="my-6"></v-divider>

            <!-- Sección de Mascotas -->
            <v-row>
              <v-col cols="12">
                <div class="d-flex justify-space-between align-center mb-4">
                  <h3 class="text-h6">
                    <v-icon class="mr-2">mdi-paw</v-icon>
                    Mascotas ({{ cliente.mascotas?.length || 0 }})
                  </h3>
                  <v-btn
                    color="primary"
                    prepend-icon="mdi-plus"
                    @click="mostrarFormularioMascota = true"
                  >
                    Agregar Mascota
                  </v-btn>
                </div>
              </v-col>
            </v-row>

            <!-- Lista de Mascotas -->
            <v-row v-if="cliente.mascotas && cliente.mascotas.length > 0">
              <v-col
                v-for="mascota in cliente.mascotas"
                :key="mascota.id"
                cols="12"
                md="6"
                lg="4"
              >
                <v-card
                  variant="outlined"
                  class="h-100"
                  :to="`/pacientes/${mascota.id}`"
                  style="cursor: pointer;"
                >
                  <v-card-title class="d-flex justify-space-between align-center">
                    <div class="d-flex align-center">
                      <v-avatar color="primary" size="40" class="mr-3">
                        <v-icon color="white">mdi-paw</v-icon>
                      </v-avatar>
                      <div>
                        <div class="text-h6">{{ mascota.nombre }}</div>
                        <div class="text-caption text-grey">{{ mascota.especie }}</div>
                      </div>
                    </div>
                  </v-card-title>
                  <v-card-text>
                    <div class="mb-2" v-if="mascota.raza">
                      <strong>Raza:</strong> {{ mascota.raza }}
                    </div>
                    <div class="mb-2" v-if="mascota.sexo">
                      <strong>Sexo:</strong> {{ mascota.sexo === 'MACHO' ? 'Macho' : mascota.sexo === 'HEMBRA' ? 'Hembra' : mascota.sexo }}
                    </div>
                    <div class="mb-2" v-if="mascota.fechaNacimiento">
                      <strong>Fecha de Nacimiento:</strong> {{ formatDate(mascota.fechaNacimiento) }}
                    </div>
                    <div v-if="mascota.pesoKg">
                      <strong>Peso:</strong> {{ mascota.pesoKg }} kg
                    </div>
                  </v-card-text>
                  <v-card-actions>
                    <v-btn
                      variant="text"
                      color="primary"
                      prepend-icon="mdi-eye"
                      @click.stop="$router.push(`/pacientes/${mascota.id}`)"
                    >
                      Ver Detalles
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>

            <v-alert
              v-else
              type="info"
              variant="tonal"
              class="mt-4"
            >
              No hay mascotas registradas para este cliente.
            </v-alert>

            <!-- Formulario para agregar mascota (Dialog) -->
            <v-dialog v-model="mostrarFormularioMascota" max-width="800" persistent>
              <v-card>
                <v-card-title class="bg-primary text-white">
                  <span>Agregar Nueva Mascota</span>
                  <v-spacer></v-spacer>
                  <v-btn icon color="white" variant="text" @click="cerrarFormularioMascota">
                    <v-icon>mdi-close</v-icon>
                  </v-btn>
                </v-card-title>

                <v-card-text class="pa-6">
                  <v-form ref="formMascotaRef" @submit.prevent="guardarMascota">
                    <v-row>
                      <v-col cols="12" md="6">
                        <v-text-field
                          v-model="nuevaMascota.nombre"
                          label="Nombre de la Mascota *"
                          :rules="[rules.required]"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" md="6">
                        <v-select
                          v-model="nuevaMascota.especie"
                          label="Especie *"
                          :items="especieOptions"
                          item-title="title"
                          item-value="value"
                          :rules="[rules.required]"
                        ></v-select>
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col cols="12" md="6">
                        <v-select
                          v-model="nuevaMascota.razaSeleccionada"
                          label="Raza"
                          :items="getRazasPorEspecie(nuevaMascota.especie)"
                          item-title="nombre"
                          item-value="id"
                          clearable
                        ></v-select>
                      </v-col>
                      <v-col cols="12" md="6">
                        <v-text-field
                          v-model="nuevaMascota.fechaNacimiento"
                          label="Fecha de Nacimiento *"
                          type="date"
                          :rules="[rules.required]"
                        ></v-text-field>
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col cols="12" md="6">
                        <v-select
                          v-model="nuevaMascota.sexo"
                          label="Sexo *"
                          :items="sexoOptions"
                          item-title="title"
                          item-value="value"
                          :rules="[rules.required]"
                        ></v-select>
                      </v-col>
                      <v-col cols="12" md="6">
                        <v-text-field
                          v-model="nuevaMascota.pesoKg"
                          label="Peso (kg)"
                          type="number"
                          step="0.1"
                        ></v-text-field>
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col cols="12" md="6">
                        <v-text-field
                          v-model="nuevaMascota.color"
                          label="Color"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" md="6">
                        <v-text-field
                          v-model="nuevaMascota.microchip"
                          label="Microchip"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>

                <v-card-actions class="pa-4">
                  <v-spacer></v-spacer>
                  <v-btn
                    color="secondary"
                    @click="cerrarFormularioMascota"
                  >
                    Cancelar
                  </v-btn>
                  <v-btn
                    color="primary"
                    :loading="guardandoMascota"
                    @click="guardarMascota"
                  >
                    Guardar Mascota
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <!-- Acciones -->
            <v-divider class="my-6"></v-divider>
            <v-row>
              <v-col cols="12" md="4">
                <v-btn
                  color="primary"
                  block
                  prepend-icon="mdi-pencil"
                  @click="$router.push(`/clientes/${cliente.id}/editar`)"
                >
                  Editar Cliente
                </v-btn>
              </v-col>
              <v-col cols="12" md="4">
                <v-btn
                  color="info"
                  block
                  prepend-icon="mdi-calendar"
                  @click="$router.push(`/citas/nueva?clienteId=${cliente.id}`)"
                >
                  Nueva Cita
                </v-btn>
              </v-col>
              <v-col cols="12" md="4">
                <v-btn
                  color="error"
                  block
                  prepend-icon="mdi-delete"
                  @click="deleteCliente"
                >
                  Eliminar Cliente
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>

        <v-card v-else>
          <v-card-text class="pa-6 text-center">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useClientesStore } from '@/stores/clientesStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const clientesStore = useClientesStore()
const pacientesStore = usePacientesStore()
const { fetchRazas } = useReferenceData()
const { showSuccess, showError } = useNotification()

const cliente = computed(() => clientesStore.currentCliente)
const mostrarFormularioMascota = ref(false)
const guardandoMascota = ref(false)
const formMascotaRef = ref(null)
const razas = ref([])

const nuevaMascota = ref({
  nombre: '',
  especie: null,
  razaSeleccionada: null,
  fechaNacimiento: null,
  sexo: null,
  pesoKg: null,
  color: '',
  microchip: ''
})

const especieOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Otro', value: 'OTRO' }
]

const sexoOptions = [
  { title: 'Macho', value: 'MACHO' },
  { title: 'Hembra', value: 'HEMBRA' }
]

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  if (typeof dateString === 'string' && /^\d{4}-\d{2}-\d{2}/.test(dateString)) {
    const [year, month, day] = dateString.split('T')[0].split('-')
    const date = new Date(parseInt(year), parseInt(month) - 1, parseInt(day))
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    })
  }
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

const getRazasPorEspecie = (especie) => {
  if (!especie) return []
  return razas.value.filter(r => r.especie === especie)
}

const cerrarFormularioMascota = () => {
  mostrarFormularioMascota.value = false
  nuevaMascota.value = {
    nombre: '',
    especie: null,
    razaSeleccionada: null,
    fechaNacimiento: null,
    sexo: null,
    pesoKg: null,
    color: '',
    microchip: ''
  }
  if (formMascotaRef.value) {
    formMascotaRef.value.resetValidation()
  }
}

const guardarMascota = async () => {
  if (!formMascotaRef.value) return
  
  const { valid } = await formMascotaRef.value.validate()
  if (!valid) return

  // Validar campos requeridos
  if (!nuevaMascota.value.nombre || !nuevaMascota.value.especie || 
      !nuevaMascota.value.fechaNacimiento || !nuevaMascota.value.sexo) {
    showError('Por favor completa todos los campos requeridos')
    return
  }

  guardandoMascota.value = true
  try {
    // Obtener el nombre de la raza si está seleccionada
    const razaNombre = nuevaMascota.value.razaSeleccionada
      ? razas.value.find(r => r.id === nuevaMascota.value.razaSeleccionada)?.nombre
      : null

    const mascotaData = {
      nombre: nuevaMascota.value.nombre,
      clienteId: cliente.value.id,
      especie: nuevaMascota.value.especie,
      raza: razaNombre,
      fechaNacimiento: nuevaMascota.value.fechaNacimiento,
      sexo: nuevaMascota.value.sexo,
      pesoKg: nuevaMascota.value.pesoKg ? parseFloat(nuevaMascota.value.pesoKg) : null,
      color: nuevaMascota.value.color || null,
      microchip: nuevaMascota.value.microchip || null,
      observaciones: null,
    }

    await pacientesStore.createPaciente(mascotaData)
    
    // Recargar el cliente para actualizar la lista de mascotas
    await clientesStore.fetchClienteById(route.params.id)
    
    showSuccess('Mascota agregada exitosamente')
    cerrarFormularioMascota()
  } catch (error) {
    console.error('Error al guardar mascota:', error)
    showError(error.response?.data?.message || 'Error al guardar la mascota')
  } finally {
    guardandoMascota.value = false
  }
}

const getCliente = async () => {
  try {
    await clientesStore.fetchClienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar cliente:', error)
    showError('Error al cargar la información del cliente')
  }
}

const deleteCliente = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar este cliente? Esta acción no se puede deshacer.')) {
    try {
      await clientesStore.deleteCliente(route.params.id)
      showSuccess('Cliente eliminado exitosamente')
      router.push('/clientes')
    } catch (error) {
      console.error('Error al eliminar cliente:', error)
      showError('Error al eliminar el cliente')
    }
  }
}

onMounted(async () => {
  // Cargar razas para el formulario de mascotas
  try {
    razas.value = await fetchRazas()
  } catch (error) {
    console.error('Error al cargar razas:', error)
  }
  
  getCliente()
})
</script>

<style scoped>
</style>

