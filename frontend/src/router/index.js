import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import LoginView from '@/views/LoginView.vue'
import DashboardView from '@/views/DashboardView.vue'
import CitasView from '@/views/CitasView.vue'
import PacientesView from '@/views/PacientesView.vue'
import ClientesView from '@/views/ClientesView.vue'
import FacturasView from '@/views/FacturasView.vue'
import UsuariosView from '@/views/UsuariosView.vue'
import HistorialesView from '@/views/HistorialesView.vue'
import InventarioView from '@/views/InventarioView.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: { requiresAuth: false },
  },
  {
    path: '/confirmar-cita',
    name: 'ConfirmarCita',
    component: () => import('@/views/public/ConfirmarCitaView.vue'),
    meta: { requiresAuth: false },
  },
  {
    path: '/',
    name: 'Dashboard',
    component: DashboardView,
    meta: { requiresAuth: true },
  },
  {
    path: '/citas',
    name: 'Citas',
    component: CitasView,
    meta: { requiresAuth: true },
  },
  {
    path: '/citas/nueva',
    name: 'NuevaCita',
    component: () => import('@/views/citas/CitaFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/citas/:id',
    name: 'VerCita',
    component: () => import('@/views/citas/CitaDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes',
    name: 'Pacientes',
    component: PacientesView,
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes/nuevo',
    name: 'NuevoPaciente',
    component: () => import('@/views/pacientes/PacienteFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes/:id',
    name: 'VerPaciente',
    component: () => import('@/views/pacientes/PacienteDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes/:id/editar',
    name: 'EditarPaciente',
    component: () => import('@/views/pacientes/PacienteFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes/:id/historial',
    name: 'HistorialPaciente',
    component: () => import('@/views/pacientes/HistorialPacienteView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/clientes',
    name: 'Clientes',
    component: ClientesView,
    meta: { requiresAuth: true },
  },
  {
    path: '/clientes/nuevo',
    name: 'NuevoCliente',
    component: () => import('@/views/clientes/ClienteFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/clientes/:id',
    name: 'VerCliente',
    component: () => import('@/views/clientes/ClienteDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/facturas',
    name: 'Facturas',
    component: FacturasView,
    meta: { requiresAuth: true },
  },
  {
    path: '/facturas/nueva',
    name: 'NuevaFactura',
    component: () => import('@/views/facturas/FacturaFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/facturas/:id',
    name: 'VerFactura',
    component: () => import('@/views/facturas/FacturaDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/facturas/:id/editar',
    name: 'EditarFactura',
    component: () => import('@/views/facturas/FacturaFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/usuarios',
    name: 'Usuarios',
    component: UsuariosView,
    meta: { requiresAuth: true },
  },
  {
    path: '/usuarios/nuevo',
    name: 'NuevoUsuario',
    component: () => import('@/views/usuarios/UsuarioFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/usuarios/:id',
    name: 'VerUsuario',
    component: () => import('@/views/usuarios/UsuarioDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/usuarios/:id/editar',
    name: 'EditarUsuario',
    component: () => import('@/views/usuarios/UsuarioFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/historiales',
    name: 'Historiales',
    component: HistorialesView,
    meta: { requiresAuth: true },
  },
  {
    path: '/inventario',
    name: 'Inventario',
    component: InventarioView,
    meta: { requiresAuth: true },
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  // Validar token si existe
  if (authStore.token && !authStore.user) {
    await authStore.getCurrentUser()
  }

  // Si la ruta requiere autenticación
  if (to.meta.requiresAuth) {
    if (!authStore.isAuthenticated) {
      next('/login')
    } else {
      next()
    }
  } else {
    // Si ya está autenticado y quiere ir a login, redirigir a dashboard
    if (to.path === '/login' && authStore.isAuthenticated) {
      next('/')
    } else {
      next()
    }
  }
})

export default router
