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
      path: '/login',
      name: 'Login',
      component: login
    },
    {
      path: '/',
      name: 'home',
      component: home,
      meta: { requiresAuth: true }
    },
    {
      path: '/me',
      name: 'me',
      component: me,
      meta: { requiresAuth: true }
    },
    {
      path: '/validation',
      name: 'validation',
      component: validation,
      meta: { requiresAuth: true }
    },
    {
      path: '/element',
      name: 'element',
      component: element,
      meta: { requiresAuth: true }
    },
    {
      path: '/list',
      name: 'list',
      component: list,
      meta: { requiresAuth: true }
    }
  ]
})
