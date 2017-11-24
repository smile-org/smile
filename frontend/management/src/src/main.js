// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
// import Vuelidate from 'vuelidate'
import App from './App'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
// import MintUI from 'mint-ui'
// import 'mint-ui/lib/style.css'
// import vueInfiniteScroll from 'vue-infinite-scroll'
import VueCookie from 'vue-cookie'
import conf from './config/'
import 'babel-polyfill'

Vue.config.productionTip = false

Vue.use(VueCookie)
// Vue.use(Vuelidate)
Vue.use(Element)
// Vue.use(MintUI)
// Vue.use(vueInfiniteScroll)

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    var token = localStorage.getItem(conf.cookie.key)
    if (token) {
      next()
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})

