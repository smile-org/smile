import Vue from 'vue'
import Router from 'vue-router'
import homepage from '@/views/homepage/index'
import me from '@/views/home/me'
import login from '@/views/login'
import list from '@/views/list'
import verification from '@/views/verification'
import resetPassword from '@/views/resetPassword'
import resetDonePassword from '@/views/resetDonePassword'
import getCourseCategories from '@/views/course/list'
import getCourses from '@/views/course/courseList'
import getCourseDetails from '@/views/course/detail'
import commentCourse from '@/views/course/comment'

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
      path: '/getCourseCategories',
      name: 'courseCategories',
      component: getCourseCategories
    },
    {
      path: '/getCourses',
      name: 'courses',
      component: getCourses
    },
    {
      path: '/getCourseDetails',
      name: 'courseDetails',
      component: getCourseDetails
    },
    {
      path: '/commentCourse',
      name: 'courseComment',
      component: commentCourse
    },
    {
      path: '/',
      name: 'homepage',
      component: homepage,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/me',
      name: 'me',
      component: me,
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
