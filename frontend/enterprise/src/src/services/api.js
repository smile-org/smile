import axios from 'axios'
// import conf from '../config/index.js'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true

// axios.defaults.baseURL = 'http://192.168.1.106:8888' // 'http://192.168.0.108:3000'
axios.defaults.baseURL = 'http://123.207.154.226:8888'
// axios.defaults.imageServer = 'http://192.168.1.106:4001'
axios.defaults.imageServer = 'http://123.207.154.226:4001'

/**
 * 拦截器， 对所有的请求。
 * 作用： 可以为每个请求加上额外参数
 */
axios.interceptors.request.use(config => {
  var token = window.localStorage.getItem('token')
  if (token) {
    // token放到header中
    config.headers.common.token = token
  }
  // config.headers.common.token = '666666'
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
    login: '/back/logon',

    // *** 公司信息管理 ***
    // 获取公司信息
    getCompanyInfo: '/back/GetCompanyByID',

    // 使用默认图片
    setDefaultImage: '/back/SetDefaultCompanyPic',
    uploadCompanyPic: axios.defaults.baseURL + '/back/UploadCompanyPic',

    // *** 用户 ***
    // 搜索日志
    searchUserLog: '/back/GetLogonHistoryList',

    // 获取用户列表
    searchUserList: '/back/GetUserList',

    // 创建用户
    createUser: '/back/AddEmployee',

    // 编辑用户
    editUser: '/back/UpdateEmployee',

    // 获取用户
    getUser: '/back/getUserByID',

    deleteUser: '/back/DisableUserByID',

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
    editCourse: '/back/UpdateCourse',
    editCourseContent: '',
    publishCourse: '/back/UpdateCoursePublishStatus',

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
    deleteEnrollmentPeriod: '/back/DisableEnrollmentPeriod',
    getEnrollmentResultList: '/back/GetEnrollmentResultList',
    exportEnrollmentResultList: '/back/ExportEnrollmentResultList',
    getUserListThisPeriod: '/back/GetUserListThisEnrollmentPeriod',
    getUserListNextPeriod: '/back/GetUserListNextEnrollmentPeriod',
    getEnrollmentEditPageInfo: '/back/GetEnrollmentEditPageInfoByPeriodID',
    addEnrollment: '/back/AddEnrollment',
    updateEnrollment: '/back/UpdateEnrollment',
    openEnrollment: '/back/OpenEnrollment',
    publishEnrollment: '/back/UpdateEnrollmentPublishStatus',

    // 公司管理员
    updatePassword: 'updatePassword',
    getCompanyAdminList: '/back/GetCompanyAdminList',
    setEmployee: '/back/SetAdminToEmployee',
    deleteAdmin: '/back/DisableUserByID',
    addAdminList: '/back/AddAdminList',
    getCompanyEmployeeList: '/back/GetCompanyEmployeeList',

    // ********* 考试管理 ********
    // 考试管理
    getQuestionList: '/back/GetQuestionList',
    getExamList: '/back/GetExamList',
    exportExamList: '/back/ExportExamList',
    deleteExam: '/back/DisableExam',
    exportQuestionList: '/back/ExportQuestionList',
    getExamHistoryList: '/back/GetExamHistoryList',
    exportExamHistoryList: '/back/ExportExamHistoryList',
    getExamHistoryDetail: '/back/GetExamHistoryDetail',
    getExamDetailQuestionList: '/back/GetExamDetailQuestionList',
    publishExam: '/back/UpdateExamPublishStatus',
    // 添加试题
    addQuestion: '/back/AddQuestion',
    // 编辑试题
    editQuestion: '/back/UpdateQuestion',
    // 删除试题
    deleteQuestion: '/back/DisableQuestion',
    // 获取试题
    getQuestion: '/back/GetQuestionEditPageInfo',
    uploadExamIcon: axios.defaults.baseURL + '/back/UploadExamIcon',
    uploadExamBanner: axios.defaults.baseURL + '/back/UploadExamPic',
    addExam: '/back/AddExam',
    getExam: '/back/GetExamEditPageInfo',
    updateExam: '/back/UpdateExam',
    DisableTask: '/back/DisableTask',
    // ********** 学习任务 **********
    getTaskList: '/back/GetTaskList',
    AddTask: '/back/AddTask',
    UpdateTask: '/back/UpdateTask',
    UpdateTaskPublishStatus: '/back/UpdateTaskPublishStatus',
    GetUserTaskListByTaskID: '/back/GetUserTaskListByTaskID',
    GetUserTaskStatus: '/back/GetUserTaskStatus',
    GetTaskEditPageInfo: '/back/GetTaskEditPageInfo',
    GetUserTaskList: '/back/GetUserTaskList',
    // 员工导入
    uploadEmployeeExcel: axios.defaults.baseURL + '/back/ImportUserList',
    // 试题导入
    uploadQuestionExcel: axios.defaults.baseURL + '/back/ImportQuestionList',
    // 员工导出
    exportUserList: '/back/ExportUserList',
    // 课程导出
    exportCourseList: '/back/ExportCourseList',
    // 学习记录导出
    exportUserLearnProgressList: '/back/ExportUserLearnProgressList',
    // 上传报名Icon和Banner
    uploadEnrollmentIcon: '/back/UploadEnrollmentIcon',
    uploadEnrollmentBanner: '/back/UploadEnrollmentPic'
  },
  extension: {
    course: [
      'PDF',
      'JPG',
      'PNG',
      'MP4',
      'MOV',
      'DOC',
      'DOCX',
      'PPT',
      'PPTX'
    ]
  },
  image: {
    category: '/default/cateicon.png',
    course: {
      icon: '/default/course-icon.png',
      banner: '/default/course-pic.png'
    },
    enrollment: {
      icon: '/default/enrollment-icon.png',
      banner: '/default/enrollment-pic.png'
    },
    exam: {
      icon: '/default/exam-icon.png',
      banner: '/default/exam-pic.png'
    }
  },

  getUploadHeaders: function () {
    var token = window.localStorage.getItem('token')
    return {
      token: token
    }
  }
}
