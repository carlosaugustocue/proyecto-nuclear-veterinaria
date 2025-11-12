import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'

// Crear aplicación
const app = createApp(App)

// Usar plugins
app.use(createPinia())
app.use(router)
app.use(vuetify)

// Montar aplicación
app.mount('#app')
