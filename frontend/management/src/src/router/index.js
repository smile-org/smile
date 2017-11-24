import Vue from 'vue'
import Router from 'vue-router'
// 登录
import login from '@/views/login'
// 修改密码
import changePWD from '@/views/changePWD'

// 管理驾驶舱
import repositoryList from '@/views/repository/list'
// 企业会员管理
import membershipList from '@/views/membership/list'
import membershipCreate from '@/views/membership/create'
// 约课信息管理
import bookingList from '@/views/booking/list'
import bookingDetail from '@/views/booking/detail'
// 代理商管理
import agencyList from '@/views/agency/list'
import agencyCreate from '@/views/agency/create'
import agencyEdit from '@/views/agency/edit'
// 运营人员管理
import maintainenceList from '@/views/maintainence/list'

Vue.use(Router)
var requiresAuth = true

export default new Router({
  mode: 'history',
  routes: [
    // 登录
    {
      path: '/login',
      name: 'login',
      component: login,
      meta: {requiresAuth: false}
    },
    // 修改密码
    {
      path: '/changePWD',
      name: 'changePWD',
      component: changePWD,
      meta: {requiresAuth: requiresAuth}
    },
    // 管理驾驶舱
    {
      path: '/',
      name: 'repositoryList',
      component: repositoryList,
      meta: {requiresAuth: requiresAuth}
    },
    // 约课信息管理
    {
      path: '/booking/list',
      name: 'bookingList',
      component: bookingList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/booking/detail',
      name: 'bookingDetail',
      component: bookingDetail,
      meta: {requiresAuth: requiresAuth}
    },
    // 代理商管理
    {
      path: '/agency/list',
      name: 'agencyList',
      component: agencyList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/agency/create',
      name: 'agencyCreate',
      component: agencyCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/agency/edit',
      name: 'agencyEdit',
      component: agencyEdit,
      meta: {requiresAuth: requiresAuth}
    },

    // 运营人员管理
    {
      path: '/maintainence/list',
      name: 'maintainenceList',
      component: maintainenceList,
      meta: {requiresAuth: requiresAuth}
    },
    // 企业会员管理
    {
      path: '/membership/list',
      name: 'membershipList',
      component: membershipList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/membership/create',
      name: 'membershipCreate',
      component: membershipCreate,
      meta: {requiresAuth: requiresAuth}
    }
  ]
})
