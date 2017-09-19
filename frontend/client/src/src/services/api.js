import axios from 'axios'
import conf from '../config/index.js'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true

axios.defaults.baseURL = 'http://123.207.154.226:8888' // 'http://192.168.0.108:3000'

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
    getExamList: '',
    // 获取考试
    getExamInfo: '',
    // 取消收藏考试
    cancelFavoriteExam: '',
    // 收藏考试
    favoriteExam: '',
    // 获取考试相关学习资料（课程）
    getExamCourses: '',
    // 获取考试记录
    getExamRecords: '',
    // 获取考试信息与试题列表
    getExamQuestions: ''
  }
}
