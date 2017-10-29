import Vue from 'vue'
import Router from 'vue-router'
// 登录
import login from '@/views/login'
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
import courseCategoryEdit from '@/views/course/category/edit'

import courseList from '@/views/course/list'
import courseCreate from '@/views/course/create'
import courseEdit from '@/views/course/edit'
import courseComment from '@/views/course/comment'
import learningList from '@/views/course/learningList'

// 报名管理
import registrationEnrollmentRecord from '@/views/registration/enrollmentRecord'
import registrationTrainlist from '@/views/registration/trainList'
import registrationEdit from '@/views/registration/edit'
import registrationThisPeriod from '@/views/registration/thisPeriod'
import registrationNextPeriod from '@/views/registration/nextPeriod'
import registrationComment from '@/views/registration/comment'
import registrationAdd from '@/views/registration/add'
import registrationAddPeriod from '@/views/registration/addPeriod'

// 约课管理
import bookingList from '@/views/booking/list'
import bookingDetail from '@/views/booking/detail'

// 修改密码
import adminList from '@/views/admin/list'
import adminChangePWD from '@/views/admin/changePWD'

// 考试管理
import examList from '@/views/exam/list'
import examQuestionList from '@/views/exam/questionList'
import examUserList from '@/views/exam/userList'
import examEdit from '@/views/exam/edit'
import examCreate from '@/views/exam/create'
import examQuestionCreate from '@/views/exam/questionCreate'
import examQuestionEdit from '@/views/exam/questionEdit'
import examDetail from '@/views/exam/detail'

Vue.use(Router)
var requiresAuth = false

export default new Router({
  mode: 'history',
  routes: [
    // 登录
    {
      path: '/login',
      name: 'login',
      component: login,
      meta: {requiresAuth: requiresAuth}
    },
    // 公司信息管理
    {
      path: '/',
      name: 'informationDetail',
      component: informationDetail,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/information/edit',
      name: 'informationEdit',
      component: informationEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/information/show',
      name: 'informationShow',
      component: informationShow,
      meta: {requiresAuth: requiresAuth}
    },
    // 员工管理
    {
      path: '/user/list',
      name: 'userList',
      component: userList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/user/create',
      name: 'userCreate',
      component: userCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/user/edit',
      name: 'userEdit',
      component: userEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/user/log',
      name: 'userLog',
      component: userLog,
      meta: {requiresAuth: requiresAuth}
    },
    // 课程管理
    {
      path: '/course/category/list',
      name: 'courseCategoryList',
      component: courseCategoryList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/category/create',
      name: 'courseCategoryCreate',
      component: courseCategoryCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/category/edit',
      name: 'courseCategoryEdit',
      component: courseCategoryEdit,
      meta: { requiresAuth: requiresAuth }
    },
    {
      path: '/course/create',
      name: 'courseCreate',
      component: courseCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/edit',
      name: 'courseEdit',
      component: courseEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/comment',
      name: 'courseComment',
      component: courseComment,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/list',
      name: 'courseList',
      component: courseList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/course/learningList',
      name: 'learningList',
      component: learningList,
      meta: {requiresAuth: requiresAuth}
    },
    // 报名管理
    {
      path: '/registration/trainList',
      name: 'registrationTrainlist',
      component: registrationTrainlist,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/edit',
      name: 'registrationEdit',
      component: registrationEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/comment',
      name: 'registrationComment',
      component: registrationComment,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/add',
      name: 'registrationAdd',
      component: registrationAdd,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/addPeriod',
      name: 'registrationAddPeriod',
      component: registrationAddPeriod,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/EnrollmentRecord',
      name: 'registrationEnrollmentRecord',
      component: registrationEnrollmentRecord,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/thisPeriod',
      name: 'registrationThisPeriod',
      component: registrationThisPeriod,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/registration/nextPeriod',
      name: 'registrationNextPeriod',
      component: registrationNextPeriod,
      meta: {requiresAuth: requiresAuth}
    },
    // 约课
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
    // 系统管理
    {
      path: '/admin/changePWD',
      name: 'adminChangePWD',
      component: adminChangePWD,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/admin/list',
      name: 'adminList',
      component: adminList,
      meta: {requiresAuth: requiresAuth}
    },
    // 考试管理
    {
      path: '/exam/list',
      name: 'examList',
      component: examList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/questionList',
      name: 'examQuestionList',
      component: examQuestionList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/userList',
      name: 'examUserList',
      component: examUserList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/edit',
      name: 'examEdit',
      component: examEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/create',
      name: 'examCreate',
      component: examCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/question/create',
      name: 'examQuestionCreate',
      component: examQuestionCreate,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/question/edit',
      name: 'examQuestionEdit',
      component: examQuestionEdit,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/exam/detail',
      name: 'examDetail',
      component: examDetail,
      meta: {requiresAuth: requiresAuth}
    }
  ]
})
