// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuelidate from 'vuelidate'
import App from './App'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import vueInfiniteScroll from 'vue-infinite-scroll'

Vue.config.productionTip = false

// router.beforeEach((to, from, next) => {
//   console.log('to: ', to)
//   console.log('from: ', from)
//   next()
// })

Vue.use(Vuelidate)
Vue.use(Element)
Vue.use(vueInfiniteScroll)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})

