<template>
  <div>
    <v-btn
      variant="text"
      prepend-icon="mdi-arrow-left"
      @click="$router.back()"
      class="mb-4"
    >
      Volver
    </v-btn>

    <h1 class="text-h4 font-weight-bold mb-6">
      <v-icon class="mr-2">mdi-account-plus</v-icon>
      {{ isEdit ? 'Editar Cliente' : 'Nuevo Cliente' }}
    </h1>

    <v-card>
      <v-card-text>
        <v-form ref="formRef" @submit.prevent="handleSubmit">
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.nombre"
                label="Nombre *"
                :rules="[rules.required]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.apellido"
                label="Apellido *"
                :rules="[rules.required]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.dni"
                label="DNI/Cédula *"
                :rules="[rules.required, rules.dni]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.telefono"
                label="Teléfono *"
                :rules="[rules.required, rules.phone]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-text-field
                v-model="form.email"
                label="Email *"
                type="email"
                :rules="[rules.required, rules.email]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-text-field
                v-model="form.direccion"
                label="Dirección"
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.ciudad"
                label="Ciudad"
              ></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-textarea
                v-model="form.observaciones"
                label="Observaciones"
                rows="3"
              ></v-textarea>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey" @click="$router.back()">Cancelar</v-btn>
        <v-btn color="primary" :loading="loading" @click="handleSubmit">
          {{ isEdit ? 'Actualizar' : 'Guardar' }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useClientesStore } from '@/store/clientes'
import { useAppStore } from '@/store/app'

const route = useRoute()
const router = useRouter()
const clientesStore = useClientesStore()
const appStore = useAppStore()

const formRef = ref()
const loading = ref(false)
const isEdit = computed(() => !!route.params.id)

const form = reactive({
  nombre: '',
  apellido: '',
  dni: '',
  email: '',
  telefono: '',
  direccion: '',
  ciudad: '',
  observaciones: '',
})

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  email: (v) => /.+@.+\..+/.test(v) || 'Email inválido',
  dni: (v) => /^\d{8,10}$/.test(v) || 'DNI inválido (8-10 dígitos)',
  phone: (v) => /^\+?[\d\s-]{10,}$/.test(v) || 'Teléfono inválido',
}

onMounted(async () => {
  if (isEdit.value) {
    try {
      const cliente = await clientesStore.fetchById(Number(route.params.id))
      if (cliente) {
        Object.assign(form, cliente)
      }
    } catch (error) {
      appStore.showError('Cliente no encontrado')
      router.push('/clientes')
    }
  }
})

const handleSubmit = async () => {
  const { valid } = await formRef.value.validate()
  if (!valid) return

  loading.value = true

  try {
    if (isEdit.value) {
      await clientesStore.update(Number(route.params.id), form)
      appStore.showSuccess('Cliente actualizado correctamente')
    } else {
      await clientesStore.create(form)
      appStore.showSuccess('Cliente creado correctamente')
    }
    router.push('/clientes')
  } catch (error) {
    const message = error.response?.data?.message || 'Error al guardar cliente'
    appStore.showError(message)
  } finally {
    loading.value = false
  }
}
</script>
