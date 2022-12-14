import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import VueCookies from "vue-cookies"

import '@/assets/css/globle.css'


import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import './assets/css/style.css'



createApp(App).use(store).use(router).use(ElementPlus, {size: 'small' }).mount('#app')
