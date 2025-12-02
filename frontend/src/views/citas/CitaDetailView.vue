<template>
  <v-container fluid class="pa-4 pa-md-6">
    <!-- Botón volver -->
    <v-row class="mb-4">
      <v-col cols="12">
            <v-btn
              @click="$router.back()"
          variant="text"
          prepend-icon="mdi-arrow-left"
          class="mb-2"
            >
          Volver
            </v-btn>
      </v-col>
    </v-row>

    <!-- Loading -->
    <v-row v-if="loading">
      <v-col cols="12" class="text-center">
        <v-progress-circular
          indeterminate
          color="primary"
          size="64"
        ></v-progress-circular>
        <p class="mt-4 text-grey">Cargando información de la cita...</p>
      </v-col>
    </v-row>

    <!-- Contenido principal -->
    <template v-else-if="cita">
      <!-- Header con ID y Estado -->
      <v-row class="mb-4">
        <v-col cols="12">
          <v-card class="elevation-3">
            <v-card-title class="bg-primary text-white d-flex align-center">
              <v-icon class="mr-2" size="28">mdi-calendar-clock</v-icon>
              <span class="text-h5">Cita #{{ cita.id }}</span>
              <v-spacer></v-spacer>
              <v-chip
                :color="getEstadoColor(obtenerEstado(cita))"
                size="large"
                class="font-weight-bold"
                text-color="white"
              >
                <v-icon start size="small">{{ getEstadoIcon(obtenerEstado(cita)) }}</v-icon>
                {{ formatearEstado(obtenerEstado(cita)) }}
              </v-chip>
          </v-card-title>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <!-- Columna izquierda - Información principal -->
        <v-col cols="12" md="8">
          <!-- Información del Paciente -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-blue-lighten-5 d-flex align-center">
              <v-icon color="blue" class="mr-2">mdi-paw</v-icon>
              <span class="font-weight-bold">Información del Paciente</span>
            </v-card-title>
            <v-card-text class="pa-4">
            <v-row>
              <v-col cols="12" md="6">
                  <div class="d-flex align-center mb-4">
                    <v-avatar color="blue" size="64" class="mr-4">
                      <v-icon color="white" size="32">mdi-paw</v-icon>
                    </v-avatar>
                    <div>
                      <div class="text-h6 font-weight-bold">{{ cita.pacienteNombre || 'Sin nombre' }}</div>
                      <div class="text-body-2 text-grey">
                        <v-chip size="small" color="blue" variant="outlined" class="mt-1">
                          {{ cita.pacienteEspecie || 'Especie no especificada' }}
                        </v-chip>
                      </div>
                </div>
                </div>
              </v-col>
                <v-col cols="12" md="6" class="d-flex align-center">
                  <v-btn
                    :to="`/pacientes/${cita.pacienteId}`"
                    color="blue"
                    variant="outlined"
                    prepend-icon="mdi-eye"
                  >
                    Ver Perfil del Paciente
                  </v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <!-- Información del Cliente/Propietario -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-green-lighten-5 d-flex align-center">
              <v-icon color="green" class="mr-2">mdi-account</v-icon>
              <span class="font-weight-bold">Propietario</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
              <v-col cols="12" md="6">
                  <div class="mb-3">
                    <div class="text-overline text-grey mb-1">Nombre</div>
                    <div class="text-h6">{{ cita.clienteNombre || 'No especificado' }}</div>
                  </div>
                  <div class="mb-3" v-if="cita.clienteEmail">
                    <div class="text-overline text-grey mb-1">Email</div>
                    <div class="d-flex align-center">
                      <v-icon size="small" class="mr-2">mdi-email</v-icon>
                      <a :href="`mailto:${cita.clienteEmail}`" class="text-decoration-none">
                        {{ cita.clienteEmail }}
                      </a>
                    </div>
                  </div>
                  <div v-if="cita.clienteTelefono">
                    <div class="text-overline text-grey mb-1">Teléfono</div>
                    <div class="d-flex align-center">
                      <v-icon size="small" class="mr-2">mdi-phone</v-icon>
                      <a :href="`tel:${cita.clienteTelefono}`" class="text-decoration-none">
                        {{ cita.clienteTelefono }}
                      </a>
                </div>
                </div>
              </v-col>
                <v-col cols="12" md="6" class="d-flex align-center">
                  <v-btn
                    :to="`/clientes/${cita.clienteId}`"
                    color="green"
                    variant="outlined"
                    prepend-icon="mdi-eye"
                  >
                    Ver Perfil del Cliente
                  </v-btn>
                </v-col>
            </v-row>
            </v-card-text>
          </v-card>

          <!-- Detalles de la Cita -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-purple-lighten-5 d-flex align-center">
              <v-icon color="purple" class="mr-2">mdi-calendar-text</v-icon>
              <span class="font-weight-bold">Detalles de la Cita</span>
            </v-card-title>
            <v-card-text class="pa-4">
            <v-row>
              <v-col cols="12" md="6">
                  <div class="mb-4">
                    <div class="text-overline text-grey mb-2">Fecha y Hora</div>
                    <div class="d-flex align-center">
                      <v-icon color="primary" class="mr-2">mdi-calendar</v-icon>
                      <div>
                        <div class="text-h6 font-weight-bold">{{ formatDate(cita.fecha) }}</div>
                        <div class="text-body-1 text-grey">
                          <v-icon size="small" class="mr-1">mdi-clock-outline</v-icon>
                          {{ formatTime(cita.hora) }}
                          <span v-if="cita.horaFinEstimada" class="ml-2">
                            - {{ formatTime(cita.horaFinEstimada) }}
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <div class="mb-4" v-if="cita.duracionEstimada">
                    <div class="text-overline text-grey mb-2">Duración Estimada</div>
                    <div class="d-flex align-center">
                      <v-icon color="info" class="mr-2">mdi-timer</v-icon>
                      <span class="text-body-1">{{ cita.duracionEstimada }} minutos</span>
                    </div>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                  <div class="mb-4" v-if="cita.tipoServicioNombre">
                    <div class="text-overline text-grey mb-2">Tipo de Servicio</div>
                    <div class="d-flex align-center">
                      <v-icon color="orange" class="mr-2">mdi-medical-bag</v-icon>
                      <div>
                        <div class="text-body-1 font-weight-bold">{{ cita.tipoServicioNombre }}</div>
                        <div v-if="cita.tipoServicioCategoria" class="text-caption text-grey">
                          {{ cita.tipoServicioCategoria }}
                        </div>
                        <div v-if="cita.tipoServicioPrecio" class="text-body-2 text-success mt-1">
                          ${{ formatCurrency(cita.tipoServicioPrecio) }}
                        </div>
                      </div>
                    </div>
                </div>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

              <div class="mb-3" v-if="cita.motivo">
                <div class="text-overline text-grey mb-2">Motivo de la Cita</div>
                <v-card variant="outlined" class="pa-3">
                  <p class="text-body-1 mb-0">{{ cita.motivo }}</p>
                </v-card>
              </div>

              <div v-if="cita.notas">
                <div class="text-overline text-grey mb-2">Notas Adicionales</div>
                <v-card variant="outlined" class="pa-3">
                  <p class="text-body-1 mb-0">{{ cita.notas }}</p>
                </v-card>
              </div>
            </v-card-text>
          </v-card>

          <!-- Motivo de Cancelación -->
          <v-card v-if="cita.motivoCancelacion" class="mb-4 elevation-2">
            <v-card-title class="bg-red-lighten-5 d-flex align-center">
              <v-icon color="red" class="mr-2">mdi-cancel</v-icon>
              <span class="font-weight-bold">Motivo de Cancelación</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-alert type="error" variant="tonal">
                {{ cita.motivoCancelacion }}
              </v-alert>
            </v-card-text>
          </v-card>

          <!-- Historial Clínico -->
          <v-card 
            v-if="estaEnProgreso(obtenerEstado(cita)) || obtenerEstado(cita).toLowerCase() === 'completada'"
            class="mb-4 elevation-2"
          >
            <v-card-title class="bg-teal-lighten-5 d-flex align-center">
              <v-icon color="teal" class="mr-2">mdi-clipboard-text</v-icon>
              <span class="font-weight-bold">Historial Clínico</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-alert 
                :type="obtenerEstado(cita).toLowerCase() === 'completada' ? 'success' : 'info'" 
                variant="tonal"
                class="mb-4"
              >
                <div class="d-flex align-center">
                  <v-icon class="mr-2">{{ obtenerEstado(cita).toLowerCase() === 'completada' ? 'mdi-check-circle' : 'mdi-information' }}</v-icon>
                  <div>
                    <div class="font-weight-bold mb-1">
                      {{ obtenerEstado(cita).toLowerCase() === 'completada' 
                        ? 'Cita Completada' 
                        : 'Cita en Progreso' }}
                    </div>
                    <div class="text-body-2">
                      {{ obtenerEstado(cita).toLowerCase() === 'completada'
                        ? 'Puede registrar los hallazgos de esta cita en el historial clínico del paciente.'
                        : 'Complete la consulta y registre los hallazgos en el historial clínico.' }}
                    </div>
                  </div>
                </div>
              </v-alert>
              <v-btn
                color="teal"
                :to="rutaHistorial"
                prepend-icon="mdi-file-document"
                block
                size="large"
              >
                {{ textoBotonHistorial }}
              </v-btn>
            </v-card-text>
          </v-card>

          <!-- Factura Asociada -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-amber-lighten-5 d-flex align-center">
              <v-icon color="amber-darken-2" class="mr-2">mdi-receipt</v-icon>
              <span class="font-weight-bold">Factura</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div v-if="loadingFactura" class="text-center py-4">
                <v-progress-circular indeterminate color="amber" size="32"></v-progress-circular>
                <div class="text-caption text-grey mt-2">Buscando factura...</div>
                </div>
              
              <div v-else-if="facturaAsociada">
                <v-alert type="success" variant="tonal" class="mb-4">
                  <div class="d-flex align-center justify-space-between">
                    <div>
                      <div class="font-weight-bold mb-1">Factura #{{ facturaAsociada.numeroFactura || facturaAsociada.id }}</div>
                      <div class="text-body-2">
                        <div class="mb-1">
                          <strong>Estado:</strong>
                          <v-chip
                            :color="getFacturaColor(facturaAsociada)"
                            size="small"
                            class="ml-2"
                            text-color="white"
                          >
                            {{ facturaAsociada.estadoNombre || facturaAsociada.estado || 'N/A' }}
                          </v-chip>
                        </div>
                        <div class="mb-1">
                          <strong>Total:</strong> ${{ formatCurrency(facturaAsociada.total || 0) }}
                        </div>
                        <div v-if="facturaAsociada.saldoPendiente > 0">
                          <strong>Saldo Pendiente:</strong> 
                          <span class="text-error">${{ formatCurrency(facturaAsociada.saldoPendiente) }}</span>
                        </div>
                        <div v-if="facturaAsociada.fechaEmision">
                          <strong>Fecha:</strong> {{ formatDate(facturaAsociada.fechaEmision) }}
                        </div>
                      </div>
                    </div>
                  </div>
                </v-alert>
                <v-btn
                  color="amber-darken-2"
                  :to="`/facturas/${facturaAsociada.id}`"
                  prepend-icon="mdi-eye"
                  block
                  size="large"
                >
                  Ver Factura Completa
                </v-btn>
              </div>
              
              <div v-else>
                <v-alert type="info" variant="tonal" class="mb-4">
                  <div class="d-flex align-center">
                    <v-icon class="mr-2">mdi-information</v-icon>
                    <div>
                      <div class="font-weight-bold mb-1">No hay factura asociada</div>
                      <div class="text-body-2">
                        {{ obtenerEstado(cita).toLowerCase() === 'completada'
                          ? 'Puede generar una factura para esta cita completada.'
                          : 'La factura se puede generar una vez que la cita esté completada.' }}
                      </div>
                    </div>
                  </div>
                </v-alert>
                <v-btn
                  v-if="obtenerEstado(cita).toLowerCase() === 'completada'"
                  color="amber-darken-2"
                  @click="crearFacturaDesdeCita"
                  prepend-icon="mdi-receipt-plus"
                  block
                  size="large"
                  :loading="creandoFactura"
                >
                  Crear Factura
                </v-btn>
                <v-btn
                  v-else
                  color="amber-darken-2"
                  variant="outlined"
                  prepend-icon="mdi-receipt"
                  block
                  size="large"
                  disabled
                >
                  Cita debe estar completada
                </v-btn>
              </div>
            </v-card-text>
          </v-card>
              </v-col>

        <!-- Columna derecha - Información secundaria y acciones -->
        <v-col cols="12" md="4">
          <!-- Información del Veterinario -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-orange-lighten-5 d-flex align-center">
              <v-icon color="orange" class="mr-2">mdi-doctor</v-icon>
              <span class="font-weight-bold">Veterinario</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div class="text-center mb-3">
                <v-avatar color="orange" size="80" class="mb-2">
                  <v-icon color="white" size="40">mdi-doctor</v-icon>
                </v-avatar>
                <div class="text-h6 font-weight-bold mt-2">{{ cita.veterinarioNombre || 'No asignado' }}</div>
                <div v-if="cita.veterinarioEmail" class="text-body-2 text-grey mt-1">
                  <v-icon size="small" class="mr-1">mdi-email</v-icon>
                  {{ cita.veterinarioEmail }}
                </div>
              </div>
            </v-card-text>
          </v-card>

          <!-- Timeline de Eventos -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-indigo-lighten-5 d-flex align-center">
              <v-icon color="indigo" class="mr-2">mdi-timeline</v-icon>
              <span class="font-weight-bold">Historial de Eventos</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-timeline side="end" density="compact">
                <v-timeline-item
                  v-if="cita.createdAt"
                  dot-color="primary"
                  size="small"
                >
                  <div class="text-caption text-grey mb-1">Creada</div>
                  <div class="text-body-2">{{ formatDateTime(cita.createdAt) }}</div>
                </v-timeline-item>

                <v-timeline-item
                  v-if="cita.fechaConfirmacion"
                  dot-color="success"
                  size="small"
                >
                  <div class="text-caption text-grey mb-1">Confirmada</div>
                  <div class="text-body-2">{{ formatDateTime(cita.fechaConfirmacion) }}</div>
                </v-timeline-item>

                <v-timeline-item
                  v-if="cita.fechaInicioAtencion"
                  dot-color="warning"
                  size="small"
                >
                  <div class="text-caption text-grey mb-1">Inicio de Atención</div>
                  <div class="text-body-2">{{ formatDateTime(cita.fechaInicioAtencion) }}</div>
                </v-timeline-item>

                <v-timeline-item
                  v-if="cita.fechaFinAtencion"
                  dot-color="success"
                  size="small"
                >
                  <div class="text-caption text-grey mb-1">Finalizada</div>
                  <div class="text-body-2">{{ formatDateTime(cita.fechaFinAtencion) }}</div>
                </v-timeline-item>

                <v-timeline-item
                  v-if="cita.updatedAt && cita.updatedAt !== cita.createdAt"
                  dot-color="grey"
                  size="small"
                >
                  <div class="text-caption text-grey mb-1">Última Actualización</div>
                  <div class="text-body-2">{{ formatDateTime(cita.updatedAt) }}</div>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>

          <!-- Acciones -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2">mdi-cog</v-icon>
              <span class="font-weight-bold">Acciones</span>
            </v-card-title>
            <v-card-text class="pa-3">
              <v-list density="compact">
                <!-- Acciones según el estado -->
                <v-list-item
                  v-if="obtenerEstado(cita).toLowerCase() === 'programada'"
                  @click="confirmarCita"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="success">mdi-check-circle</v-icon>
                  </template>
                  <v-list-item-title>Confirmar Cita</v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="obtenerEstado(cita).toLowerCase() === 'confirmada'"
                  @click="iniciarCita"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="warning">mdi-play-circle</v-icon>
                  </template>
                  <v-list-item-title>Iniciar Atención</v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="estaEnProgreso(obtenerEstado(cita))"
                  :to="rutaHistorial"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="primary">mdi-clipboard-text</v-icon>
                  </template>
                  <v-list-item-title>Continuar Consulta</v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="estaEnProgreso(obtenerEstado(cita))"
                  @click="completarCita"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="success">mdi-check-all</v-icon>
                  </template>
                  <v-list-item-title>Completar Cita</v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="obtenerEstado(cita).toLowerCase() === 'completada' && !facturaAsociada"
                  @click="crearFacturaDesdeCita"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                  :disabled="creandoFactura"
                >
                  <template v-slot:prepend>
                    <v-icon color="amber">mdi-receipt-plus</v-icon>
                  </template>
                  <v-list-item-title>
                    <span v-if="!creandoFactura">Crear Factura</span>
                    <span v-else>Creando...</span>
                  </v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="facturaAsociada"
                  :to="`/facturas/${facturaAsociada.id}`"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="amber">mdi-receipt</v-icon>
                  </template>
                  <v-list-item-title>Ver Factura</v-list-item-title>
                  <template v-slot:append>
                    <v-chip
                      :color="getFacturaColor(facturaAsociada)"
                      size="x-small"
                      text-color="white"
                    >
                      {{ facturaAsociada.estadoNombre || facturaAsociada.estado }}
                    </v-chip>
                  </template>
                </v-list-item>

                <v-divider class="my-2"></v-divider>

                <v-list-item
                  :to="`/citas/${cita.id}/editar`"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="info">mdi-pencil</v-icon>
                  </template>
                  <v-list-item-title>Editar Cita</v-list-item-title>
                </v-list-item>

                <v-list-item
                  v-if="obtenerEstado(cita).toLowerCase() !== 'completada' && obtenerEstado(cita).toLowerCase() !== 'cancelada'"
                  @click="mostrarDialogoCancelar"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="error">mdi-cancel</v-icon>
                  </template>
                  <v-list-item-title>Cancelar Cita</v-list-item-title>
                </v-list-item>

                <v-list-item
                  @click="deleteCita"
                  class="mb-2 rounded text-error"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="error">mdi-delete</v-icon>
                  </template>
                  <v-list-item-title>Eliminar Cita</v-list-item-title>
                </v-list-item>
              </v-list>
          </v-card-text>
        </v-card>

          <!-- Información del Sistema -->
          <v-card class="elevation-2">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2">mdi-information</v-icon>
              <span class="font-weight-bold">Información del Sistema</span>
            </v-card-title>
            <v-card-text class="pa-3">
              <div class="text-caption text-grey mb-2">Creada por</div>
              <div class="text-body-2 mb-3">{{ cita.createdBy || 'Sistema' }}</div>
              
              <div class="text-caption text-grey mb-2">Última modificación</div>
              <div class="text-body-2 mb-3">{{ cita.updatedBy || 'Sistema' }}</div>

              <v-chip
                :color="cita.estaActiva ? 'success' : 'error'"
                size="small"
                variant="flat"
              >
                {{ cita.estaActiva ? 'Activa' : 'Inactiva' }}
              </v-chip>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </template>

    <!-- Error o no encontrada -->
    <v-row v-else>
      <v-col cols="12">
        <v-card>
          <v-card-text class="pa-6 text-center">
            <v-icon color="error" size="64" class="mb-4">mdi-alert-circle</v-icon>
            <div class="text-h6 mb-2">Cita no encontrada</div>
            <div class="text-body-2 text-grey mb-4">
              La cita que buscas no existe o ha sido eliminada.
            </div>
            <v-btn
              to="/citas"
              color="primary"
              prepend-icon="mdi-arrow-left"
            >
              Volver a Citas
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Dialog para cancelar cita -->
    <v-dialog v-model="dialogoCancelar" max-width="500px">
      <v-card>
        <v-card-title class="bg-error text-white">
          <v-icon class="mr-2">mdi-cancel</v-icon>
          Cancelar Cita
        </v-card-title>
        <v-card-text class="pa-6">
          <v-textarea
            v-model="motivoCancelacion"
            label="Motivo de Cancelación *"
            rows="3"
            variant="outlined"
            :rules="[v => !!v || 'El motivo es obligatorio']"
          ></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" @click="dialogoCancelar = false">Cerrar</v-btn>
          <v-btn color="error" @click="cancelarCita" :loading="loading">Cancelar Cita</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCitasStore } from '@/stores/citasStore'
import { useFacturasStore } from '@/stores/facturasStore'
import { useAuthStore } from '@/stores/authStore'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const citasStore = useCitasStore()
const facturasStore = useFacturasStore()
const authStore = useAuthStore()
const { showSuccess, showError } = useNotification()

const dialogoCancelar = ref(false)
const motivoCancelacion = ref('')
const facturaAsociada = ref(null)
const loadingFactura = ref(false)
const creandoFactura = ref(false)

const loading = computed(() => citasStore.loading)
const cita = computed(() => citasStore.currentCita)

// Helpers - Definir primero para que estén disponibles en los computed
const obtenerEstado = (cita) => {
  return cita?.estadoNombre || cita?.estado || ''
}

const estaEnProgreso = (estado) => {
  if (!estado) return false
  const estadoLower = estado.toLowerCase()
  return estadoLower.includes('atenci') || 
         estadoLower.includes('progreso') || 
         estadoLower === 'en_progreso'
}

// Computed para la ruta del historial
const rutaHistorial = computed(() => {
  if (!cita.value?.pacienteId) return '/historiales'
  
  const basePath = `/pacientes/${cita.value.pacienteId}/historial`
  const estado = obtenerEstado(cita.value).toLowerCase()
  
  if (estado.includes('progreso') || estado.includes('atenci')) {
    const params = new URLSearchParams({
      fromCita: cita.value.id.toString(),
      enProgreso: 'true',
      citaFecha: cita.value.fecha || '',
      citaHora: cita.value.hora || '',
      citaMotivo: cita.value.motivo || ''
    })
    return `${basePath}?${params.toString()}`
  }
  
  return basePath
})

// Computed para el texto del botón
const textoBotonHistorial = computed(() => {
  const estado = obtenerEstado(cita.value).toLowerCase()
  if (estado.includes('progreso') || estado.includes('atenci')) {
    return 'Continuar Consulta'
  }
  return 'Ver Historial Clínico'
})

const getCita = async () => {
  try {
    await citasStore.fetchCitaById(route.params.id)
    // Cargar factura asociada si existe
    if (cita.value?.id) {
      await cargarFacturaAsociada()
    }
  } catch (error) {
    console.error('Error al cargar cita:', error)
    showError('Error al cargar la información de la cita')
  }
}

const cargarFacturaAsociada = async () => {
  if (!cita.value?.id) {
    facturaAsociada.value = null
    return
  }
  
  loadingFactura.value = true
  try {
    // Verificar que la función existe
    if (typeof facturasStore.fetchFacturaByCitaId !== 'function') {
      console.error('fetchFacturaByCitaId no es una función en el store')
      // Intentar cargar todas las facturas y buscar manualmente
      await facturasStore.fetchFacturas()
      const factura = facturasStore.facturas.find(f => {
        const facturaCitaId = f.citaId
        const citaId = cita.value.id
        return facturaCitaId !== null && 
               facturaCitaId !== undefined && 
               (facturaCitaId === citaId || facturaCitaId.toString() === citaId.toString())
      })
      facturaAsociada.value = factura || null
    } else {
      const factura = await facturasStore.fetchFacturaByCitaId(cita.value.id)
      facturaAsociada.value = factura
    }
  } catch (error) {
    console.error('Error al cargar factura asociada:', error)
    // Intentar búsqueda manual como fallback
    try {
      await facturasStore.fetchFacturas()
      const factura = facturasStore.facturas.find(f => {
        const facturaCitaId = f.citaId
        const citaId = cita.value.id
        return facturaCitaId !== null && 
               facturaCitaId !== undefined && 
               (facturaCitaId === citaId || facturaCitaId.toString() === citaId.toString())
      })
      facturaAsociada.value = factura || null
    } catch (fallbackError) {
      console.error('Error en búsqueda manual de factura:', fallbackError)
      facturaAsociada.value = null
    }
  } finally {
    loadingFactura.value = false
  }
}

const crearFacturaDesdeCita = async () => {
  if (!cita.value) {
    showError('No hay información de la cita disponible')
    return
  }

  if (!cita.value.clienteId) {
    showError('No se puede crear la factura: falta información del cliente')
    return
  }

  // Verificar SIEMPRE si ya existe una factura antes de crear
  loadingFactura.value = true
  try {
    // Recargar todas las facturas para asegurarnos de tener la información más actualizada
    await facturasStore.fetchFacturas()
    
    // Buscar factura por citaId en todas las facturas
    const facturaExistente = facturasStore.facturas.find(f => {
      const facturaCitaId = f.citaId
      const citaId = cita.value.id
      return facturaCitaId !== null && 
             facturaCitaId !== undefined && 
             (facturaCitaId === citaId || facturaCitaId.toString() === citaId.toString())
    })
    
    if (facturaExistente) {
      facturaAsociada.value = facturaExistente
      showError('Ya existe una factura asociada a esta cita')
      loadingFactura.value = false
      return
    }
    
    // También intentar usar la función del store si existe
    if (typeof facturasStore.fetchFacturaByCitaId === 'function') {
      const factura = await facturasStore.fetchFacturaByCitaId(cita.value.id)
      if (factura) {
        facturaAsociada.value = factura
        showError('Ya existe una factura asociada a esta cita')
        loadingFactura.value = false
        return
      }
    }
  } catch (checkError) {
    console.error('Error al verificar factura existente:', checkError)
  } finally {
    loadingFactura.value = false
  }

  // Obtener usuario actual
  let usuario = authStore.user
  if (!usuario || !usuario.id) {
    usuario = await authStore.getCurrentUser()
    if (!usuario || !usuario.id) {
      // Buscar usuario por username si no tiene ID
      if (usuario && usuario.username) {
        try {
          const { useApi } = await import('@/composables/useApi')
          const { get } = useApi()
          const usuariosResponse = await get('/usuarios', {
            params: { page: 0, size: 100 }
          })
          const usuarios = usuariosResponse.data?.content || usuariosResponse.data || []
          const usuarioCompleto = usuarios.find(u => u.username === usuario.username || u.email === usuario.email)
          if (usuarioCompleto) {
            usuario = { ...usuario, id: usuarioCompleto.id }
          }
        } catch (error) {
          console.error('Error al buscar usuario completo:', error)
        }
      }
    }
  }

  if (!usuario || !usuario.id) {
    showError('No se pudo obtener la información del usuario para crear la factura')
    return
  }

  creandoFactura.value = true
  try {
    // Crear la factura primero (sin detalles, ya que el backend no los acepta en CreateFacturaRequest)
    const facturaData = {
      clienteId: cita.value.clienteId,
      citaId: cita.value.id,
      usuarioEmisorId: usuario.id,
      fechaEmision: new Date().toISOString().split('T')[0],
    }

    const nuevaFactura = await facturasStore.createFactura(facturaData)
    facturaAsociada.value = nuevaFactura
    
    // Si la cita tiene un tipo de servicio, agregarlo automáticamente como detalle
    if (cita.value.tipoServicioId && cita.value.tipoServicioPrecio) {
      try {
        const detalleData = {
          tipoServicioId: cita.value.tipoServicioId,
          descripcion: cita.value.tipoServicioNombre || 'Servicio de consulta',
          cantidad: 1,
          precioUnitario: cita.value.tipoServicioPrecio,
        }

        // Intentar usar la función del store si existe, si no, usar API directa
        if (typeof facturasStore.agregarDetalle === 'function') {
          await facturasStore.agregarDetalle(nuevaFactura.id, detalleData)
          // Recargar la factura para obtener los detalles actualizados
          await facturasStore.fetchFacturaById(nuevaFactura.id)
          facturaAsociada.value = facturasStore.currentFactura
        } else {
          // Fallback: usar API directa
          console.warn('agregarDetalle no está disponible en el store, usando API directa')
          const { useApi } = await import('@/composables/useApi')
          const { post, get } = useApi()
          
          await post(`/v1/facturas/${nuevaFactura.id}/detalles`, detalleData)
          
          // Recargar la factura
          const facturaResponse = await get(`/v1/facturas/${nuevaFactura.id}`)
          if (facturasStore.fetchFacturaById) {
            await facturasStore.fetchFacturaById(nuevaFactura.id)
            facturaAsociada.value = facturasStore.currentFactura
          } else {
            facturaAsociada.value = facturaResponse.data
          }
        }
        
        showSuccess('Factura creada exitosamente con el servicio de la cita')
      } catch (detalleError) {
        console.error('Error al agregar servicio a la factura:', detalleError)
        const errorMsg = detalleError?.response?.data?.userMessage || 
                        detalleError?.response?.data?.message || 
                        detalleError?.message ||
                        'Error desconocido'
        showError(`Factura creada, pero no se pudo agregar el servicio: ${errorMsg}. Puede agregarlo manualmente.`)
      }
    } else {
      showSuccess('Factura creada exitosamente')
    }
    
    // Redirigir a la factura creada
    router.push(`/facturas/${nuevaFactura.id}`)
  } catch (error) {
    console.error('Error al crear factura:', error)
    
    // Manejar el caso específico de factura ya existente
    const errorMessage = error.response?.data?.userMessage || 
                        error.response?.data?.message || 
                        error.message ||
                        'Error al crear la factura'
    
    if (errorMessage.includes('Ya existe una factura') || 
        errorMessage.includes('ya existe') ||
        error.response?.status === 400) {
      // Recargar facturas y buscar la existente
      showError('Ya existe una factura para esta cita. Recargando información...')
      setTimeout(async () => {
        await cargarFacturaAsociada()
        if (facturaAsociada.value) {
          showSuccess('Factura encontrada y cargada correctamente')
        }
      }, 500)
    } else {
      showError(errorMessage)
    }
  } finally {
    creandoFactura.value = false
  }
}

const getFacturaColor = (factura) => {
  const estado = (factura?.estadoNombre || factura?.estado || '').toLowerCase()
  if (estado === 'pagada' || estado === 'pagado') return 'success'
  if (estado === 'pendiente') return 'warning'
  if (estado === 'anulada') return 'error'
  return 'info'
}

const formatearEstado = (estado) => {
  if (!estado) return ''
  const estadoLower = estado.toLowerCase()
  if (estadoLower === 'en_progreso' || estadoLower.includes('progreso')) {
    return 'En Progreso'
  }
  if (estadoLower.includes('atenci')) {
    return 'En Atención'
  }
  return estado.charAt(0).toUpperCase() + estado.slice(1).toLowerCase()
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const formatTime = (timeString) => {
  if (!timeString) return ''
  return timeString.substring(0, 5)
}

const formatDateTime = (dateTimeString) => {
  if (!dateTimeString) return ''
  const date = new Date(dateTimeString)
  return date.toLocaleString('es-ES', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatCurrency = (amount) => {
  if (!amount) return '0'
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount)
}

const getEstadoColor = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci') || estadoLower.includes('progreso') || estadoLower === 'en_progreso') {
    return 'warning'
  }
  const colores = {
    'programada': 'info',
    'confirmada': 'success',
    'completada': 'success',
    'cancelada': 'error',
    'no asistió': 'grey',
    'no asistio': 'grey',
  }
  return colores[estadoLower] || 'grey'
}

const getEstadoIcon = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci') || estadoLower.includes('progreso') || estadoLower === 'en_progreso') {
    return 'mdi-progress-clock'
  }
  const iconos = {
    'programada': 'mdi-calendar-clock',
    'confirmada': 'mdi-check-circle',
    'completada': 'mdi-check-all',
    'cancelada': 'mdi-cancel',
    'no asistió': 'mdi-account-off',
    'no asistio': 'mdi-account-off',
  }
  return iconos[estadoLower] || 'mdi-circle'
}

// Acciones
const confirmarCita = async () => {
  try {
    await citasStore.confirmarCita(cita.value.id)
    showSuccess('Cita confirmada exitosamente')
    await getCita()
  } catch (error) {
    showError(error.userMessage || 'Error al confirmar la cita')
  }
}

const iniciarCita = async () => {
  try {
    const citaIniciada = await citasStore.iniciarCita(cita.value.id)
    showSuccess('Atención iniciada exitosamente')
    await getCita()
    
    // Redirigir al historial clínico
    if (citaIniciada && citaIniciada.pacienteId) {
      router.push({
        path: `/pacientes/${citaIniciada.pacienteId}/historial`,
        query: {
          fromCita: citaIniciada.id,
          citaFecha: citaIniciada.fecha,
          citaHora: citaIniciada.hora,
          citaMotivo: citaIniciada.motivo,
          enProgreso: 'true'
        }
      })
    }
  } catch (error) {
    showError(error.userMessage || 'Error al iniciar la atención')
  }
}

const completarCita = async () => {
  try {
    await citasStore.completarCita(cita.value.id)
    showSuccess('Cita completada exitosamente')
    await getCita()
  } catch (error) {
    showError(error.userMessage || 'Error al completar la cita')
  }
}

const mostrarDialogoCancelar = () => {
  motivoCancelacion.value = ''
  dialogoCancelar.value = true
}

const cancelarCita = async () => {
  if (!motivoCancelacion.value || motivoCancelacion.value.trim() === '') {
    showError('Debe proporcionar un motivo de cancelación')
    return
  }

  try {
    await citasStore.cancelarCita(cita.value.id, motivoCancelacion.value)
    showSuccess('Cita cancelada exitosamente')
    dialogoCancelar.value = false
    motivoCancelacion.value = ''
    await getCita()
  } catch (error) {
    showError(error.userMessage || 'Error al cancelar la cita')
  }
}

const deleteCita = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar esta cita? Esta acción no se puede deshacer.')) {
    try {
      await citasStore.deleteCita(cita.value.id)
      showSuccess('Cita eliminada exitosamente')
      router.push('/citas')
    } catch (error) {
      showError(error.userMessage || 'Error al eliminar la cita')
    }
  }
}

onMounted(() => {
  getCita()
})
</script>

<style scoped>
.cursor-pointer {
  cursor: pointer;
  transition: background-color 0.2s;
}

.cursor-pointer:hover {
  background-color: rgba(0, 0, 0, 0.04);
}
</style>
