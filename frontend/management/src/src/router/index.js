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
    // ,
    // // 员工管理
    // {
    //   path: '/user/list',
    //   name: 'userList',
    //   component: userList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/user/create',
    //   name: 'userCreate',
    //   component: userCreate,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/user/log',
    //   name: 'userLog',
    //   component: userLog,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // // 课程管理
    // {
    //   path: '/course/category/list',
    //   name: 'courseCategoryList',
    //   component: courseCategoryList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/course/comment',
    //   name: 'courseComment',
    //   component: courseComment,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/course/list',
    //   name: 'courseList',
    //   component: courseList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/course/learningList',
    //   name: 'learningList',
    //   component: learningList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // // 报名管理
    // {
    //   path: '/registration/trainList',
    //   name: 'registrationTrainlist',
    //   component: registrationTrainlist,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/registration/comment',
    //   name: 'registrationComment',
    //   component: registrationComment,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/registration/addPeriod',
    //   name: 'registrationAddPeriod',
    //   component: registrationAddPeriod,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/registration/EnrollmentRecord',
    //   name: 'registrationEnrollmentRecord',
    //   component: registrationEnrollmentRecord,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/registration/thisPeriod',
    //   name: 'registrationThisPeriod',
    //   component: registrationThisPeriod,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/registration/nextPeriod',
    //   name: 'registrationNextPeriod',
    //   component: registrationNextPeriod,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // // 约课
    // {
    //   path: '/booking/list',
    //   name: 'bookingList',
    //   component: bookingList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/booking/detail',
    //   name: 'bookingDetail',
    //   component: bookingDetail,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // // 系统管理
    // {
    //   path: '/admin/changePWD',
    //   name: 'adminChangePWD',
    //   component: adminChangePWD,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/admin/list',
    //   name: 'adminList',
    //   component: adminList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // // 考试管理
    // {
    //   path: '/exam/list',
    //   name: 'examList',
    //   component: examList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/exam/questionList',
    //   name: 'examQuestionList',
    //   component: examQuestionList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    // {
    //   path: '/exam/userList',
    //   name: 'examUserList',
    //   component: examUserList,
    //   meta: {requiresAuth: requiresAuth}
    // },
    //
    // {
    //   path: '/exam/detail',
    //   name: 'examDetail',
    //   component: examDetail,
    //   meta: {requiresAuth: requiresAuth}
    // }
  ]
})
