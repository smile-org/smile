import Vue from 'vue'
import Router from 'vue-router'
import home from '@/views/home/home'
import me from '@/views/home/me'
import login from '@/views/login'
import validation from '@/views/validation'
import element from '@/views/element'
import list from '@/views/list'
import verification from '@/views/verification'
import resetPassword from '@/views/resetPassword'
import resetDonePassword from '@/views/resetDonePassword'

Vue.use(Router)

var requiresAuth = true

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/verification',
      name: 'codeVerification',
      component: verification
    },
    {
      path: '/resetPassword',
      name: 'passwordReset',
      component: resetPassword
    },
    {
      path: '/resetPassword/done',
      name: 'passwordResetDone',
      component: resetDonePassword
    },
    {
      path: '/',
      name: 'home',
      component: home,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/me',
      name: 'me',
      component: me,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/validation',
      name: 'validation',
      component: validation,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/element',
      name: 'element',
      component: element,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/list',
      name: 'list',
      component: list,
      meta: { requiresAuth: requiresAuth }
    }
  ]
})
