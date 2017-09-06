import Vue from 'vue'
import Router from 'vue-router'
import home from '@/components/business/home'
import me from '@/components/business/me'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'default',
      component: home
    },
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/me',
      name: 'me',
      component: me
    }
  ]
})
