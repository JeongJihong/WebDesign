import Vue from 'vue'
import App from './App.vue'
// import VueRouter from 'vue-router';
// import routes from './routes'
import router from './routes'
import store from './vuex/store'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
// Optionally install the BootstrapVue icon components plugin
Vue.config.productionTip = false

// Vue.use(VueRouter)

// const router = new VueRouter({
//     routes,
// });

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


