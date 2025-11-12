<template>
  <div>
    <h1 class="text-h4 font-weight-bold mb-6">Dashboard</h1>

    <!-- KPIs Cards -->
    <v-row>
      <v-col cols="12" sm="6" md="3">
        <v-card color="primary" dark>
          <v-card-text>
            <div class="d-flex justify-space-between align-center">
              <div>
                <p class="text-caption mb-1">Clientes Activos</p>
                <h2 class="text-h3 font-weight-bold">245</h2>
              </div>
              <v-icon size="48">mdi-account-group</v-icon>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card color="success" dark>
          <v-card-text>
            <div class="d-flex justify-space-between align-center">
              <div>
                <p class="text-caption mb-1">Pacientes Registrados</p>
                <h2 class="text-h3 font-weight-bold">412</h2>
              </div>
              <v-icon size="48">mdi-paw</v-icon>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card color="secondary" dark>
          <v-card-text>
            <div class="d-flex justify-space-between align-center">
              <div>
                <p class="text-caption mb-1">Citas del Día</p>
                <h2 class="text-h3 font-weight-bold">18</h2>
              </div>
              <v-icon size="48">mdi-calendar-clock</v-icon>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card color="warning" dark>
          <v-card-text>
            <div class="d-flex justify-space-between align-center">
              <div>
                <p class="text-caption mb-1">Pendientes</p>
                <h2 class="text-h3 font-weight-bold">7</h2>
              </div>
              <v-icon size="48">mdi-alert-circle</v-icon>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row class="mt-4">
      <!-- Citas del Día -->
      <v-col cols="12" md="8">
        <v-card>
          <v-card-title class="d-flex align-center">
            <v-icon class="mr-2">mdi-calendar-today</v-icon>
            Citas del Día
          </v-card-title>
          <v-card-text>
            <v-table>
              <thead>
                <tr>
                  <th>Hora</th>
                  <th>Paciente</th>
                  <th>Cliente</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>09:00</td>
                  <td>Max</td>
                  <td>Juan Pérez</td>
                  <td>
                    <v-chip color="success" size="small">Confirmada</v-chip>
                  </td>
                </tr>
                <tr>
                  <td>10:30</td>
                  <td>Luna</td>
                  <td>María García</td>
                  <td>
                    <v-chip color="warning" size="small">Pendiente</v-chip>
                  </td>
                </tr>
                <tr>
                  <td>12:00</td>
                  <td>Rocky</td>
                  <td>Carlos López</td>
                  <td>
                    <v-chip color="success" size="small">Confirmada</v-chip>
                  </td>
                </tr>
              </tbody>
            </v-table>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Accesos Rápidos -->
      <v-col cols="12" md="4">
        <v-card>
          <v-card-title class="d-flex align-center">
            <v-icon class="mr-2">mdi-flash</v-icon>
            Accesos Rápidos
          </v-card-title>
          <v-card-text>
            <v-list>
              <v-list-item prepend-icon="mdi-plus" to="/clientes/nuevo">
                <v-list-item-title>Nuevo Cliente</v-list-item-title>
              </v-list-item>
              <v-list-item prepend-icon="mdi-plus" to="/pacientes/nuevo">
                <v-list-item-title>Nuevo Paciente</v-list-item-title>
              </v-list-item>
              <v-list-item prepend-icon="mdi-calendar-plus" to="/citas/nueva">
                <v-list-item-title>Nueva Cita</v-list-item-title>
              </v-list-item>
              <v-list-item prepend-icon="mdi-receipt" to="/facturas/nueva">
                <v-list-item-title>Nueva Factura</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script setup>
// Dashboard con datos estáticos por ahora
// Aquí se conectarían con APIs para obtener estadísticas reales
</script>
