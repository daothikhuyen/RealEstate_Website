
import { createApp } from 'vue';
import { CkeditorPlugin } from '@ckeditor/ckeditor5-vue';
import app from './App.vue'
import router from './router/index.js';
import store from './store/index.js'

const vueApp = createApp(app)

vueApp.use(router)
vueApp.use(store)
vueApp.use( CkeditorPlugin )
vueApp.mount('#app')

export default vueApp
