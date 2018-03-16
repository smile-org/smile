import Vue from 'vue'
import Router from 'vue-router'
import homepage from '@/views/homepage/index'
import search from '@/views/homepage/search'
import searchResult from '@/views/homepage/searchResult'
import discover from '@/views/homepage/discover'

// 登录
import login from '@/views/login'
import list from '@/views/list'
import verification from '@/views/verification'
import resetPassword from '@/views/resetPassword'

// 课程
import resetDonePassword from '@/views/resetDonePassword'
import getCourseCategories from '@/views/course/list'
import getCourses from '@/views/course/courseList'
import getCourseDetails from '@/views/course/detail'
import commentCourse from '@/views/course/comment'
import media from '@/views/course/media'
import video from '@/views/course/video'

// 考试
import getExamList from '@/views/exam/list'
import getExamInfo from '@/views/exam/detail'
import startExam from '@/views/exam/exam'
import examSuccess from '@/views/exam/success'
import examFailed from '@/views/exam/failed'

// 约课
import getBookingList from '@/views/booking/list'
import getBookingFollowers from '@/views/booking/followers'
import getBooking from '@/views/booking/detail'
import newBooking from '@/views/booking/new'

// 报名
import getEnrollList from '@/views/registration/list'
import getEnroll from '@/views/registration/detail'
import getEnrollComment from '@/views/registration/comment'

// 个人中心
import me from '@/views/personal/index'
import myLearning from '@/views/personal/myLearning'
import myTask from '@/views/personal/myTask'
import myExam from '@/views/personal/myExam'
import myEnrollment from '@/views/personal/myEnrollment'
import myBooking from '@/views/personal/myBooking'
import myFavorites from '@/views/personal/myFavorites'
import changePWD from '@/views/personal/changePWD'

// 学习任务
import myTaskDetail from '@/views/personal/myTaskDetail'
import myTaskSee from '@/views/personal/myTaskSee'

Vue.use(Router)

var requiresAuth = true

export default new Router({
  mode: 'history',
  routes: [
    // 登录
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
    // 课程
    {
      path: '/getCourseCategories',
      name: 'courseCategories',
      component: getCourseCategories,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getCourses',
      name: 'courses',
      component: getCourses,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getCourseDetails',
      name: 'courseDetails',
      component: getCourseDetails,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/commentCourse',
      name: 'courseComment',
      component: commentCourse,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/media',
      name: 'courseMedia',
      component: media,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/video',
      name: 'courseVideo',
      component: video,
      meta: {requiresAuth: requiresAuth}
    },
    // 考试
    {
      path: '/getExamList',
      name: 'getExamList',
      component: getExamList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getExamInfo',
      name: 'getExamInfo',
      component: getExamInfo,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/startExam',
      name: 'startExam',
      component: startExam,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/examSuccess',
      name: 'examSuccess',
      component: examSuccess,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/examFailed',
      name: 'examFailed',
      component: examFailed,
      meta: {requiresAuth: requiresAuth}
    },

    // 约课
    {
      path: '/getBookingList',
      name: 'getBookingList',
      component: getBookingList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getBookingFollowers',
      name: 'getBookingFollowers',
      component: getBookingFollowers,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getBooking',
      name: 'getBooking',
      component: getBooking,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/newBooking',
      name: 'newBooking',
      component: newBooking,
      meta: {requiresAuth: requiresAuth}
    },
    // 报名
    {
      path: '/getEnrollList',
      name: 'getEnrollList',
      component: getEnrollList,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getEnroll',
      name: 'getEnroll',
      component: getEnroll,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/getEnrollComment',
      name: 'getEnrollComment',
      component: getEnrollComment,
      meta: {requiresAuth: requiresAuth}
    },
    // 个人中心
    {
      path: '/me',
      name: 'me',
      component: me,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myLearning',
      name: 'myLearning',
      component: myLearning,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myTask',
      name: 'myTask',
      component: myTask,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myExam',
      name: 'myExam',
      component: myExam,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myEnrollment',
      name: 'myEnrollment',
      component: myEnrollment,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myBooking',
      name: 'myBooking',
      component: myBooking,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myFavorites',
      name: 'myFavorites',
      component: myFavorites,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/changePWD',
      name: 'changePWD',
      component: changePWD,
      meta: {requiresAuth: requiresAuth}
    },

    // 首页
    {
      path: '/',
      name: 'homepage',
      component: homepage,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/search',
      name: 'search',
      component: search,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/searchResult',
      name: 'searchResult',
      component: searchResult,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/discover',
      name: 'discover',
      component: discover,
      meta: {requiresAuth: requiresAuth}
    },

    // 其他

    {
      path: '/list',
      name: 'list',
      component: list,
      meta: {requiresAuth: requiresAuth}
    },
    // 学习任务
    {
      path: '/myTaskDetail',
      name: 'myTaskDetail',
      component: myTaskDetail,
      meta: {requiresAuth: requiresAuth}
    },
    {
      path: '/myTaskSee',
      name: 'myTaskSee',
      component: myTaskSee,
      meta: {requiresAuth: requiresAuth}
    }
  ]
})
