import axios from 'axios'
import conf from './../config/index.js'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true

// axios.defaults.baseURL = 'http://192.168.0.106:8888' // 'http://192.168.0.108:3000'
axios.defaults.baseURL = 'http://123.207.154.226:8888'
// axios.defaults.baseURL = 'http://api.jxueyuan.com'
axios.defaults.imageServer = '' // 'http://123.207.154.226:4001'

/**
 * 拦截器， 对所有的请求。
 * 作用： 可以为每个请求加上额外参数
 */
axios.interceptors.request.use(config => {
  var token = sessionStorage.getItem(conf.cookie.key)
  if (token) {
    // token放到header中
    config.headers.common.token = token
  }

  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 拦截器， 对所有的响应
 * 返回到vue前可以先做一层过滤
 */
axios.interceptors.response.use(response => {
  return response.data
}, error => {
  return Promise.reject(error)
})

export default {

  /**
   * 获取数据
   * @param {* @/users/list or @/users/userId} url
   * @param {* {type: 1}} params
   */
  fetch (url, params = {}) {
    return axios.get(url, {
      params: params
    })
  },

  /**
   * 提交数据
   * @param {* @/users } url
   * @param {* {firstName: 'cui', lastName: 'lin', age: 18} } params
   */
  post (url, params = {}) {
    return axios.post(url, params)
  },

  uri: {
    // *** 登录 ***
    // 登录
    login: '/logon',
    // 获取验证码
    getCode: '/getVerificationCode',
    // 验证验证码
    verifyCode: '/checkVerificationCode',
    // 重设密码
    resetPassword: '/updatePassword',
    // 重置密码
    resetPasswordByCellPhone: 'updatePasswordByCellphone',

    // *** 课程 ***
    // 获取课程分类
    getCourseCategories: '/getCategoryList',
    // 根据课程分类获取课程列表
    getCourseByCategoryId: '/getCoursesList',
    // 获取特定课程
    getCourse: '/getCourseByID',
    // 获取特定课程目录
    getCourseContent: '/getCourseContentListByID',
    // 获取特定课程评论
    getCourseComment: '/getCourseCommentList',
    // 提交课程评论
    submitCourseComments: '/addCourseComment',
    // 点开具体课程目录开始学习
    startCourse: '/clickCourseContent',
    // 收藏课程
    favoriteCourse: '/addCourseCollection',
    // 取消收藏课程
    cancelFavoriteCourse: '/cancelCourseCollection',

    // *** 考试 ***
    // 获取所有考试
    getExamList: '/getExamList',
    // 获取考试
    getExamInfo: '/getExambyID',
    // 取消收藏考试
    cancelFavoriteExam: '/cancelExamCollection',
    // 收藏考试
    favoriteExam: '/addExamCollection',
    // 获取考试相关学习资料（课程）
    getExamCourses: '/getCourseListByExamid',
    // 获取考试记录
    getExamRecords: '/getExamHistoryList',
    // 获取考试信息与试题列表
    getExamQuestions: '/getExamQuestionAnswerCombineEntity',
    // 开始考试
    startExam: '/addHistory',
    // 更新每道题
    saveQuestionAnwser: '/updateHistory',
    // 完成考试
    finishExam: '/finishHistory',

    // *** 约课 ***
    // 获取约课列表
    getBookingList: '/appointment/appointmentList',
    // 查看约课同学
    getBookingFollowers: '/appointment/followers',
    // 约课详细页面
    getBooking: '/appointment/appointment',
    // 提交约课
    submitBooking: '/appointment/addAppointment',
    // 点赞
    likeBooking: '/appointment/follow',
    // cancel dian zan
    unlikeBooking: '/appointment/cancelFollow',
    // 添加新需求
    submitNewRequirement: '/appointment/addItem',

    // *** 报名 ***
    // 获取报名列表
    getEnrollList: '/getEnrollmentPeriodListInprocess',
    // 获取结束报名列表
    getEnrollListFinish: '/getEnrollmentPeriodListFinished',
    // 获取课程信息
    getEnrollCourse: '/getEnrollmentPeriodByID',
    // 获取课程目录
    getEnrollCourseCategory: '/getEnrollmentContentListByID',
    // 获取课程评价
    getEnrollCourseComment: '/getEnrollmentCommentList',
    // 报名
    enroll: '/addEnrollmentMapping',
    // 收藏报名
    favoriteEnrollment: '/addEnrollmentCollection',
    // 取消报名
    cancelFavoriteEnrollment: '/cancelEnrollmentCollection',
    // 添加评论
    addEnrollmentComment: '/addEnrollmentComment',
    // 添加开班提醒
    addEnrollReminder: '/addReminderMapping',
    // 列表页面取消报名
    cancelEnrollment: '/deleteEnrollmentmapping',

    // *** 个人中心  ***
    // 首页
    getPersonal: '/getMyStudyStatus',
    // 获取我的课程 - 进行中
    getMyCourseListInProgress: '/getMyCourseListInprocess',
    // 获取我的课程 - 已完成
    getMyCourseListFinished: '/getMyCourseListFinished',
    // 获取我的任务 -   ---- 只有 一个 api, 返回为开始和进行中的
    getMyTaskListInProgress: '/getMyCourseListInTask',
   // 取消课程
    cancelCourse: '/deleteEnrollmentMapping',
    // 获取我的任务 - 未开始
    // getMyTaskListNotStart: '',

    // 获取我的通过考试
    getMyExamListPassed: '/getMyExamlistPassed',
    // 获取我的失败考试:
    getMyExamListFailed: '/getMyExamlistNotPassed',
    // 获取我的约课列表
    getMyAppointmentList: '/getMyAppointmentList',
    // 获取我的报名 - 已经报名
    getMyEnrollFinish: '/getMyEnrollmentListEnrolled',
    // 获取我的报名 - 预约报名
    getMyEnrollInProgress: '/getMyEnrollmentListInReminder',

    getMyFavorite: '/getMyCollectionList',
    // // 我的收藏 - 课程
    // getMyFavoriteCourse: '',
    // // 我的收藏 - 报名
    // getMyFavoriteEnroll: '',
    // // 我的收藏 - 考试
    // getMyFavoriteExam: '',
    updatePassword: 'updatePassword',

    // 新的 我的任务 api
    getTaskInProgress: 'GetMyTaskListInProcess',
    getTaskFinished: 'GetMyTaskListFinished',
    getTaskDetail: 'GetTaskAndLearnStatusDetail',
    getTaskUserRanking: 'GetTaskClassMate',

    // 首页
    getHomepageCourse: '/getFirstPageCourseList',
    getSearchHistoryList: '/getsearchHistoryList',
    getRecommanedKeyWords: '/getRecommanedKeyWords',
    addSearchHistory: '/addSearchHistory',
    deleteSearchHistory: '/deleteSearchHistory',
    search: '/search',

    getBannerAndLogo: '/GetCompanyPic',

    uploadUserAvatar: '/UpdateUserPicPath'
  },

  // 用于生成uuid
  S4: function () {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
  },
  guid: function () {
    return (this.S4() + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + '-' + this.S4() + this.S4() + this.S4())
  },

  // 获取文件扩展名
  getFileExt: function (fileName) {
    var arrLen = fileName.split('.').length - 1
    var extension = fileName.split('.')[arrLen].toUpperCase()
    return extension
  }
}
