import Vue from 'vue'
import Router from 'vue-router'
import home from '@/views/home/home'
import me from '@/views/home/me'
import login from '@/views/login'
import validation from '@/views/validation'
import element from '@/views/element'
import list from '@/views/list'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: login
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
    },
    {
      path: '/validation',
      name: 'validation',
      component: validation
    },
    {
      path: '/element',
      name: 'element',
      component: element
    },
    {
      path: '/list',
      name: 'list',
      component: list
    }
  ]
})
