<template>
  <div>
    <!-- Header -->
    <div class="d-flex justify-space-between align-center mb-6">
      <h1 class="text-h4 font-weight-bold">
        <v-icon class="mr-2">mdi-account-group</v-icon>
        Gestión de Clientes
      </h1>
      <v-btn color="primary" prepend-icon="mdi-plus" to="/clientes/nuevo">
        Nuevo Cliente
      </v-btn>
    </div>

    <!-- Filtros y Búsqueda -->
    <v-card class="mb-4">
      <v-card-text>
        <v-row>
          <v-col cols="12" md="8">
            <v-text-field
              v-model="search"
              prepend-inner-icon="mdi-magnify"
              label="Buscar cliente por nombre o apellido..."
              clearable
              @click:clear="handleClearSearch"
              @keyup.enter="handleSearch"
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="4">
            <v-btn color="primary" block @click="handleSearch">
              <v-icon left>mdi-magnify</v-icon>
              Buscar
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <!-- Tabla de Clientes -->
    <v-card>
      <v-card-text>
        <v-data-table
          :headers="headers"
          :items="clientesStore.clientes"
          :loading="clientesStore.loading"
          :search="tableSearch"
          class="elevation-1"
          items-per-page="10"
        >
          <!-- Estado -->
          <template v-slot:item.isActive="{ item }">
            <v-chip :color="item.isActive ? 'success' : 'error'" size="small">
              {{ item.isActive ? 'Activo' : 'Inactivo' }}
            </v-chip>
          </template>

          <!-- Acciones -->
          <template v-slot:item.actions="{ item }">
            <div class="d-flex gap-2">
              <v-tooltip text="Ver detalle">
                <template v-slot:activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-eye"
                    size="small"
                    variant="text"
                    :to="`/clientes/${item.id}`"
                  ></v-btn>
                </template>
              </v-tooltip>

              <v-tooltip text="Editar">
                <template v-slot:activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-pencil"
                    size="small"
                    variant="text"
                    color="primary"
                    :to="`/clientes/${item.id}/editar`"
                  ></v-btn>
                </template>
              </v-tooltip>

              <v-tooltip text="Eliminar">
                <template v-slot:activator="{ props }">
                  <v-btn
                    v-bind="props"
                    icon="mdi-delete"
                    size="small"
                    variant="text"
                    color="error"
                    @click="confirmDelete(item)"
                  ></v-btn>
                </template>
              </v-tooltip>
            </div>
          </template>

          <!-- Loading -->
          <template v-slot:loading>
            <v-skeleton-loader type="table-row@5"></v-skeleton-loader>
          </template>

          <!-- No data -->
          <template v-slot:no-data>
            <div class="text-center pa-4">
              <v-icon size="64" color="grey">mdi-account-off</v-icon>
              <p class="text-h6 mt-2">No hay clientes registrados</p>
              <v-btn color="primary" class="mt-2" to="/clientes/nuevo">
                Registrar Primer Cliente
              </v-btn>
            </div>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>

    <!-- Dialog de Confirmación -->
    <v-dialog v-model="dialogDelete" max-width="500px">
      <v-card>
        <v-card-title class="text-h5">Confirmar Eliminación</v-card-title>
        <v-card-text>
          ¿Está seguro que desea eliminar al cliente
          <strong>{{ clienteToDelete?.nombre }} {{ clienteToDelete?.apellido }}</strong>?
          <br /><br />
          Esta acción no se puede deshacer.
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" @click="dialogDelete = false">Cancelar</v-btn>
          <v-btn color="error" @click="deleteCliente" :loading="deleting">Eliminar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useClientesStore } from '@/store/clientes'
import { useAppStore } from '@/store/app'

const clientesStore = useClientesStore()
const appStore = useAppStore()

const search = ref('')
const tableSearch = ref('')
const dialogDelete = ref(false)
const clienteToDelete = ref(null)
const deleting = ref(false)

const headers = [
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Nombre', key: 'nombre', sortable: true },
  { title: 'Apellido', key: 'apellido', sortable: true },
  { title: 'DNI', key: 'dni' },
  { title: 'Email', key: 'email' },
  { title: 'Teléfono', key: 'telefono' },
  { title: 'Estado', key: 'isActive', sortable: true },
  { title: 'Acciones', key: 'actions', sortable: false, align: 'center' },
]

onMounted(async () => {
  try {
    await clientesStore.fetchAll()
  } catch (error) {
    appStore.showError('Error al cargar clientes')
  }
})

const handleSearch = async () => {
  if (!search.value || search.value.trim() === '') {
    await clientesStore.fetchAll()
    tableSearch.value = ''
    return
  }

  try {
    await clientesStore.search(search.value)
    tableSearch.value = ''
  } catch (error) {
    appStore.showError('Error en la búsqueda')
  }
}

const handleClearSearch = async () => {
  search.value = ''
  tableSearch.value = ''
  try {
    await clientesStore.fetchAll()
  } catch (error) {
    appStore.showError('Error al cargar clientes')
  }
}

const confirmDelete = (cliente) => {
  clienteToDelete.value = cliente
  dialogDelete.value = true
}

const deleteCliente = async () => {
  if (!clienteToDelete.value) return

  deleting.value = true
  try {
    await clientesStore.remove(clienteToDelete.value.id)
    appStore.showSuccess('Cliente eliminado correctamente')
    dialogDelete.value = false
    clienteToDelete.value = null
  } catch (error) {
    const message = error.response?.data?.message || 'Error al eliminar cliente'
    appStore.showError(message)
  } finally {
    deleting.value = false
  }
}
</script>

<style scoped>
.gap-2 {
  gap: 8px;
}
</style>
