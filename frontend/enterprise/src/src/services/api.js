import axios from 'axios'
// import conf from '../config/index.js'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true

axios.defaults.baseURL = 'http://192.168.1.102:8888' // 'http://192.168.0.108:3000'
// axios.defaults.baseURL = 'http://123.207.154.226:8888'
axios.defaults.imageServer = 'http://192.168.1.102:4001'
// axios.defaults.imageServer = 'http://123.207.154.226:4001'

/**
 * 拦截器， 对所有的请求。
 * 作用： 可以为每个请求加上额外参数
 */
axios.interceptors.request.use(config => {
  // var token = sessionStorage.getItem(conf.cookie.key)
  // if (token) {
  //   // token放到header中
  //   config.headers.common.token = token
  // }
  config.headers.common.token = '666666'
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

    // *** 公司信息管理 ***
    // 获取公司信息
    getCompanyInfo: '',

    // 使用默认图片
    setDefaultImage: '',

    // 上传公司图片
    uploadCompanyImage: '',

    // *** 用户 ***
    // 搜索日志
    searchUserLog: '',

    // 获取用户列表
    searchUserList: '',

    // 创建用户
    createUser: '',

    // 编辑用户
    editUser: '',

    // 获取用户
    getUser: '',

    // *** 课程管理 ***
    // 课程分类
    getCategory: '/getCategoryList',
    createCategory: '/back/AddCourseCategory',
    editCategory: '/back/UpdateCourseCategory',
    deleteCategory: '/back/DisableCourseCategory',
    getCategoryByID: '/back/GetCourseCategoryByID',

    // 课程信息管理
    searchCourse: '/back/GetCourseList',
    addCourse: '/back/AddCourse',
    editCourseContent: '',

    getComments: '/back/GetCourseCommentList',
    deleteCourse: '/back/DisableCourse',

    // 添加课程内容 + 上传附件
    // addCourseContent: axios.defaults.baseURL + '/back/AddCourseContent',
    // 提交课程obj
    postCourseContent: '/back/AddCourseContent',
    updateCourseContent: '/back/UpdateCourseContent',
    getCourseContents: '',
    searchWhiteList: '/back/GetUserList',

    // 获取admin列表 和 课程分类列表
    getSelectList: '/back/GetCourseEditPageInfo',

    uploadCategoryImage: axios.defaults.baseURL + '/back/UploadCourseCategoryIcon',
    uploadCourseIcon: axios.defaults.baseURL + '/back/UploadCourseIcon',
    uploadCourseBanner: axios.defaults.baseURL + '/back/UploadCoursePic',
    uploadContentAction: axios.defaults.baseURL + '/back/UploadCourseContentAttachment',

    // 学习记录
    getCourseLearningRecords: '/back/GetUserLearnProgressList',

    // 约课管理
    getBackAppointmentList: '/appointment/backAppointmentList',
    closeAppointment: '/appointment/closeAppointment',
    backAppointment: '/appointment/backAppointment',
    backAppointmentFollowers: '/appointment/backAppointmentFollowers',
    exportAppointment: '/appointment/exportAppointment',

    // 报名管理
    getEnrollmentList: '/back/GetEnrollmentList',
    exportEnrollmentList: '/back/ExportEnrollmentList',
    getEnrollmentCommentList: '/back/GetEnrollmentCommentList',
    deleteEnrollmentPeriod: '/back/DisableEnrollment',
    getEnrollmentResultList: '/back/GetEnrollmentResultList',
    exportEnrollmentResultList: '/back/ExportEnrollmentResultList',
    getUserListThisPeriod: '/back/GetUserListThisEnrollmentPeriod',
    getUserListNextPeriod: '/back/GetUserListNextEnrollmentPeriod'
  },
  image: {
    category: '/default/cateicon.png',
    course: {
      icon: '/default/course-icon.png',
      banner: '/default/course-pic.png'
    }
  },

  getUploadHeaders: function () {
    return {
      token: '666666'
    }
  }
}
