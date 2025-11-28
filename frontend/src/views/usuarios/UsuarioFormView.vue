<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Usuario' : 'Nuevo Usuario' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="saveUsuario" ref="formRef">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.nombre"
                    label="Nombre *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.apellido"
                    label="Apellido *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.email"
                    label="Email *"
                    type="email"
                    :rules="[rules.required, rules.email]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.username"
                    label="Usuario *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.password"
                    label="Contraseña *"
                    type="password"
                    :rules="isEditing ? [] : [rules.required]"
                    hint="Deja en blanco si no deseas cambiarla"
                    persistent-hint
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.tipoUsuario"
                    label="Tipo de Usuario *"
                    :items="tipoUsuarioOptions"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-select
                    v-model="form.roles"
                    label="Roles *"
                    :items="rolesOptions"
                    item-title="nombre"
                    item-value="nombre"
                    multiple
                    chips
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.telefono"
                    label="Teléfono"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-switch
                    v-model="form.isActive"
                    label="Usuario Activo"
                    color="primary"
                  ></v-switch>
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
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Usuario
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
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUsuariosStore } from '@/stores/usuariosStore'

const router = useRouter()
const route = useRoute()
const usuariosStore = useUsuariosStore()

const formRef = ref(null)
const loading = computed(() => usuariosStore.loading)

const isEditing = computed(() => !!route.params.id)

const tipoUsuarioOptions = ['VETERINARIO', 'RECEPCIONISTA', 'ADMIN']
const rolesOptions = [
  { nombre: 'ROLE_ADMIN' },
  { nombre: 'ROLE_VETERINARIO' },
  { nombre: 'ROLE_RECEPCIONISTA' }
]

const form = reactive({
  nombre: '',
  apellido: '',
  email: '',
  username: '',
  password: '',
  tipoUsuario: 'VETERINARIO',
  roles: [],
  telefono: '',
  isActive: true,
})

const rules = {
  required: value => !!value || 'Campo requerido',
  email: value => /.+@.+\..+/.test(value) || 'Email inválido',
}

const loadUsuario = async () => {
  if (isEditing.value) {
    try {
      const usuario = await usuariosStore.fetchUsuarioById(route.params.id)
      Object.assign(form, {
        nombre: usuario.nombre,
        apellido: usuario.apellido,
        email: usuario.email,
        username: usuario.username,
        password: '',
        tipoUsuario: usuario.tipoUsuario,
        roles: usuario.roles?.map(r => r.nombre) || [],
        telefono: usuario.telefono || '',
        isActive: usuario.isActive,
      })
    } catch (error) {
      console.error('Error loading usuario:', error)
      router.push('/usuarios')
    }
  }
}

const saveUsuario = async () => {
  try {
    const payload = {
      ...form,
      roles: form.roles.map(roleName => ({ nombre: roleName }))
    }

    // Si está editando y no se modificó la contraseña, eliminarla del payload
    if (isEditing.value && !payload.password) {
      delete payload.password
    }

    if (isEditing.value) {
      await usuariosStore.updateUsuario(route.params.id, payload)
    } else {
      await usuariosStore.createUsuario(payload)
    }

    router.push('/usuarios')
  } catch (error) {
    console.error('Error saving usuario:', error)
  }
}

onMounted(() => {
  loadUsuario()
})
</script>

<style scoped>
</style>
