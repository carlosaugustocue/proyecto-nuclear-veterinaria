<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Cliente' : 'Nuevo Cliente' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="saveCliente" ref="form">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="nombre"
                    label="Nombre *"
                    :rules="[rules.required, rules.nombre]"
                    variant="outlined"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="apellido"
                    label="Apellido *"
                    :rules="[rules.required, rules.apellido]"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="dni"
                    label="Documento de Identidad *"
                    :rules="[rules.required, rules.dni]"
                    variant="outlined"
                    hint="Entre 7 y 20 caracteres"
                    persistent-hint
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="email"
                    label="Email *"
                    type="email"
                    :rules="[rules.required, rules.email]"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="telefono"
                    label="Teléfono *"
                    :rules="[rules.required, rules.telefono]"
                    variant="outlined"
                    hint="Entre 7 y 15 dígitos (puede incluir + al inicio)"
                    persistent-hint
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="direccion"
                    label="Dirección *"
                    :rules="[rules.required, rules.direccion]"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="ciudad"
                    label="Ciudad"
                    variant="outlined"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="departamento"
                    label="Departamento"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="codigoPostal"
                    label="Código Postal"
                    variant="outlined"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="observaciones"
                    label="Observaciones"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <!-- Sección de Mascotas (solo al crear, no al editar) -->
              <v-row v-if="!isEditing" class="mt-4">
                <v-col cols="12">
                  <v-divider class="my-4"></v-divider>
                  <div class="d-flex justify-space-between align-center mb-4">
                    <h3 class="text-h6">Mascotas</h3>
                    <v-btn
                      color="primary"
                      prepend-icon="mdi-plus"
                      size="small"
                      @click="agregarMascota"
                    >
                      Agregar Mascota
                    </v-btn>
                  </div>

                  <v-card
                    v-for="(mascota, index) in mascotas"
                    :key="index"
                    class="mb-4"
                    variant="outlined"
                  >
                    <v-card-title class="d-flex justify-space-between align-center">
                      <span>Mascota {{ index + 1 }}</span>
                      <v-btn
                        icon="mdi-delete"
                        size="small"
                        variant="text"
                        color="error"
                        @click="eliminarMascota(index)"
                      ></v-btn>
                    </v-card-title>
                    <v-card-text>
                      <v-row>
                        <v-col cols="12" md="6">
                          <v-text-field
                            v-model="mascota.nombre"
                            label="Nombre de la Mascota *"
                            :rules="[rules.required]"
                            variant="outlined"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6">
                          <v-select
                            v-model="mascota.especie"
                            label="Especie *"
                            :items="especieOptions"
                            item-title="title"
                            item-value="value"
                            :rules="[rules.required]"
                            variant="outlined"
                          ></v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" md="6">
                          <v-select
                            v-model="mascota.razaSeleccionada"
                            label="Raza"
                            :items="getRazasPorEspecie(mascota.especie)"
                            item-title="nombre"
                            item-value="id"
                            clearable
                            variant="outlined"
                          ></v-select>
                        </v-col>
                        <v-col cols="12" md="6">
                          <v-text-field
                            v-model="mascota.fechaNacimiento"
                            label="Fecha de Nacimiento *"
                            type="date"
                            :rules="[rules.required]"
                            variant="outlined"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" md="6">
                          <v-select
                            v-model="mascota.sexo"
                            label="Sexo *"
                            :items="sexoOptions"
                            item-title="title"
                            item-value="value"
                            :rules="[rules.required]"
                            variant="outlined"
                          ></v-select>
                        </v-col>
                        <v-col cols="12" md="6">
                          <v-text-field
                            v-model="mascota.pesoKg"
                            label="Peso (kg)"
                            type="number"
                            step="0.1"
                            variant="outlined"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12" md="6">
                          <v-text-field
                            v-model="mascota.color"
                            label="Color"
                            variant="outlined"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" md="6">
                          <v-text-field
                            v-model="mascota.microchip"
                            label="Microchip"
                            variant="outlined"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>

                  <v-alert
                    v-if="mascotas.length === 0"
                    type="info"
                    variant="tonal"
                    class="mt-4"
                  >
                    No hay mascotas agregadas. Puedes agregar una o más mascotas ahora, o hacerlo más tarde.
                  </v-alert>
                </v-col>
              </v-row>

              <v-row class="mt-6">
                <v-col cols="12" md="6">
                  <v-btn
                    color="primary"
                    block
                    type="submit"
                    :loading="loading"
                  >
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Cliente
                  </v-btn>
                </v-col>

                <v-col cols="12" md="6">
                  <v-btn
                    color="secondary"
                    block
                    @click="$router.back()"
                  >
                    Cancelar
                  </v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
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

const loading = computed(() => clientesStore.loading || pacientesStore.loading)
const isEditing = computed(() => !!route.params.id)
const form = ref(null)

const nombre = ref('')
const apellido = ref('')
const dni = ref('')
const email = ref('')
const telefono = ref('')
const direccion = ref('')
const ciudad = ref('')
const departamento = ref('')
const codigoPostal = ref('')
const observaciones = ref('')

// Mascotas
const mascotas = ref([])
const razas = ref([])

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
  email: (v) => !v || /.+@.+\..+/.test(v) || 'El email debe ser válido',
  dni: (v) => {
    if (!v) return 'Este campo es requerido'
    if (v.length < 7 || v.length > 20) return 'El DNI debe tener entre 7 y 20 caracteres'
    return true
  },
  telefono: (v) => {
    if (!v) return 'Este campo es requerido'
    // Remover espacios, guiones y paréntesis para validar
    const telefonoLimpio = v.replace(/[\s\-\(\)]/g, '')
    if (!/^[+]?[0-9]{7,15}$/.test(telefonoLimpio)) {
      return 'El teléfono debe tener entre 7 y 15 dígitos (puede incluir + al inicio)'
    }
    return true
  },
  nombre: (v) => {
    if (!v) return 'Este campo es requerido'
    if (v.length < 2 || v.length > 100) return 'El nombre debe tener entre 2 y 100 caracteres'
    return true
  },
  apellido: (v) => {
    if (!v) return 'Este campo es requerido'
    if (v.length < 2 || v.length > 100) return 'El apellido debe tener entre 2 y 100 caracteres'
    return true
  },
  direccion: (v) => {
    if (!v) return 'Este campo es requerido'
    if (v.length > 300) return 'La dirección no puede exceder 300 caracteres'
    return true
  },
}

const agregarMascota = () => {
  mascotas.value.push({
    nombre: '',
    especie: null,
    razaSeleccionada: null,
    fechaNacimiento: null,
    sexo: null,
    pesoKg: null,
    color: '',
    microchip: ''
  })
}

const eliminarMascota = (index) => {
  mascotas.value.splice(index, 1)
}

const getRazasPorEspecie = (especie) => {
  if (!especie) return []
  return razas.value.filter(r => r.especie === especie)
}

const saveCliente = async () => {
  // Validar formulario antes de enviar
  const { valid } = await form.value.validate()
  if (!valid) {
    showError('Por favor completa todos los campos requeridos correctamente')
    return
  }

  try {
    // Limpiar el teléfono (remover espacios, guiones, paréntesis)
    const telefonoLimpio = telefono.value.replace(/[\s\-\(\)]/g, '')
    
    const formData = {
      nombre: nombre.value.trim(),
      apellido: apellido.value.trim(),
      dni: dni.value.trim(),
      email: email.value.trim(),
      telefono: telefonoLimpio,
      direccion: direccion.value.trim(),
      ciudad: ciudad.value?.trim() || null,
      departamento: departamento.value?.trim() || null,
      codigoPostal: codigoPostal.value?.trim() || null,
      observaciones: observaciones.value?.trim() || null,
    }

    let clienteId
    if (isEditing.value) {
      await clientesStore.updateCliente(route.params.id, formData)
      clienteId = route.params.id
    } else {
      const nuevoCliente = await clientesStore.createCliente(formData)
      clienteId = nuevoCliente.id
      
      // Crear mascotas si hay alguna
      if (mascotas.value.length > 0) {
        for (const mascota of mascotas.value) {
          // Validar campos requeridos de la mascota
          if (!mascota.nombre || !mascota.especie || !mascota.fechaNacimiento || !mascota.sexo) {
            showError('Por favor completa todos los campos requeridos de las mascotas')
            return
          }

          // Obtener el nombre de la raza si está seleccionada
          const razaNombre = mascota.razaSeleccionada
            ? razas.value.find(r => r.id === mascota.razaSeleccionada)?.nombre
            : null

          const mascotaData = {
            nombre: mascota.nombre,
            clienteId: clienteId,
            especie: mascota.especie,
            raza: razaNombre,
            fechaNacimiento: mascota.fechaNacimiento,
            sexo: mascota.sexo,
            pesoKg: mascota.pesoKg ? parseFloat(mascota.pesoKg) : null,
            color: mascota.color || null,
            microchip: mascota.microchip || null,
            observaciones: null,
          }

          await pacientesStore.createPaciente(mascotaData)
        }
        showSuccess(`Cliente y ${mascotas.value.length} mascota(s) creados exitosamente`)
      } else {
        showSuccess('Cliente creado exitosamente')
      }
    }
    
    router.push('/clientes')
  } catch (error) {
    console.error('Error al guardar cliente:', error)
    
    // Mejorar el manejo de errores para mostrar mensajes más claros
    let errorMessage = 'Error al guardar el cliente'
    
    if (error.response?.data) {
      const errorData = error.response.data
      
      // Manejar errores de validación de Bean Validation
      if (errorData.errors) {
        if (Array.isArray(errorData.errors)) {
          errorMessage = errorData.errors
            .map(err => err.message || `${err.field}: ${err.defaultMessage || 'Error de validación'}`)
            .join(', ')
        } else if (typeof errorData.errors === 'object') {
          const erroresArray = Object.entries(errorData.errors)
            .map(([campo, mensajes]) => {
              const mensajesArray = Array.isArray(mensajes) ? mensajes : [mensajes]
              return mensajesArray.map(msg => `${campo}: ${msg}`).join(', ')
            })
            .flat()
          errorMessage = erroresArray.join(', ')
        }
      } else if (errorData.message) {
        errorMessage = errorData.message
      } else if (errorData.error) {
        errorMessage = errorData.error
      }
    } else if (error.userMessage) {
      errorMessage = error.userMessage
    }
    
    showError(errorMessage)
  }
}

onMounted(async () => {
  // Cargar razas para los selects de mascotas
  try {
    razas.value = await fetchRazas()
  } catch (error) {
    console.error('Error al cargar razas:', error)
  }

  if (isEditing.value) {
    try {
      await clientesStore.fetchClienteById(route.params.id)
      const cliente = clientesStore.currentCliente
      if (cliente) {
        nombre.value = cliente.nombre || ''
        apellido.value = cliente.apellido || ''
        dni.value = cliente.dni || ''
        email.value = cliente.email || ''
        telefono.value = cliente.telefono || ''
        direccion.value = cliente.direccion || ''
        ciudad.value = cliente.ciudad || ''
        departamento.value = cliente.departamento || ''
        codigoPostal.value = cliente.codigoPostal || ''
        observaciones.value = cliente.observaciones || ''
      }
    } catch (error) {
      console.error('Error al cargar cliente:', error)
    }
  }
})
</script>

<style scoped>
</style>
