import Vue from 'vue'
import Router from 'vue-router'

// 公司信息管理
import informationDetail from '@/views/information/detail'
import informationEdit from '@/views/information/edit'
import informationShow from '@/views/information/show'

// 员工管理
import userList from '@/views/user/list'
import userCreate from '@/views/user/create'

// 课程管理
import courseCategoryList from '@/views/course/category/list'

import courseList from '@/views/course/list'

Vue.use(Router)
var requiresAuth = false

export default new Router({
  mode: 'history',
  routes: [

    // 公司信息管理
    {
      path: '/',
      name: 'informationDetail',
      component: informationDetail,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/information/edit',
      name: 'informationEdit',
      component: informationEdit,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/information/show',
      name: 'informationShow',
      component: informationShow,
      meta: { requiresAuth: requiresAuth }
    },
    // 员工管理
    {
      path: '/user/list',
      name: 'userList',
      component: userList,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/user/create',
      name: 'userCreate',
      component: userCreate,
      meta: { requiresAuth: requiresAuth }
    },
    // 课程管理
    {
      path: '/course/category/list',
      name: 'courseCategoryList',
      component: courseCategoryList,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/course/list',
      name: 'courseList',
      component: courseList,
      meta: { requiresAuth: requiresAuth }
    }
  ]
})
