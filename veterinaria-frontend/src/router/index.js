import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/store/auth'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/LoginView.vue'),
    meta: { requiresAuth: false, layout: 'auth' },
  },
  {
    path: '/',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/DashboardView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/clientes',
    name: 'Clientes',
    component: () => import('@/views/clientes/ClientesView.vue'),
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
    name: 'DetalleCliente',
    component: () => import('@/views/clientes/ClienteDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/clientes/:id/editar',
    name: 'EditarCliente',
    component: () => import('@/views/clientes/ClienteFormView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/pacientes',
    name: 'Pacientes',
    component: () => import('@/views/pacientes/PacientesView.vue'),
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
    name: 'DetallePaciente',
    component: () => import('@/views/pacientes/PacienteDetailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/citas',
    name: 'Citas',
    component: () => import('@/views/citas/CitasView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/facturas',
    name: 'Facturas',
    component: () => import('@/views/facturas/FacturasView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFoundView.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// Guard de navegación - Verificar autenticación
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const requiresAuth = to.meta.requiresAuth !== false

  if (requiresAuth && !authStore.isAuthenticated) {
    // Redirigir a login si no está autenticado
    next('/login')
  } else if (to.path === '/login' && authStore.isAuthenticated) {
    // Si ya está autenticado y va a login, redirigir al dashboard
    next('/')
  } else {
    next()
  }
})

export default router
