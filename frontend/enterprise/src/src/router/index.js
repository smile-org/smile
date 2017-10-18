import Vue from 'vue'
import Router from 'vue-router'

// 公司信息管理
import informationDetail from '@/views/information/detail'
import informationEdit from '@/views/information/edit'
import informationShow from '@/views/information/show'

// 员工管理
import userList from '@/views/user/list'
import userCreate from '@/views/user/create'
import userEdit from '@/views/user/edit'
import userLog from '@/views/user/log'

// 课程管理
import courseCategoryList from '@/views/course/category/list'
import courseCategoryCreate from '@/views/course/category/create'

import courseList from '@/views/course/list'
import courseDetail from '@/views/course/detail'
import courseEdit from '@/views/course/edit'
import courseComment from '@/views/course/comment'

// test
import testCorp from '@/views/test/corp-ori'

Vue.use(Router)
var requiresAuth = false

export default new Router({
  mode: 'history',
  routes: [
    // test corp & upload
    {
      path: '/testcorp',
      name: 'testCorp',
      component: testCorp,
      meta: { requiresAuth: requiresAuth }
    },
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
    {
      path: '/user/edit',
      name: 'userEdit',
      component: userEdit,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/user/log',
      name: 'userLog',
      component: userLog,
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
      path: '/course/category/create',
      name: 'courseCategoryCreate',
      component: courseCategoryCreate,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/course/detail',
      name: 'courseDetail',
      component: courseDetail,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/course/edit',
      name: 'courseEdit',
      component: courseEdit,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/course/comment',
      name: 'courseComment',
      component: courseComment,
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
