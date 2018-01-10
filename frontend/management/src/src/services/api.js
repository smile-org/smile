import axios from 'axios'
// import conf from '../config/index.js'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true

// axios.defaults.baseURL = 'http://192.168.1.106:8888' // 'http://192.168.0.108:3000'
axios.defaults.baseURL = 'http://localhost:8888'
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
    login: '/admin/logon',

    // *** 修改密码 ***
    updatePassword: 'updatePassword',

    // *** 获取运营人员  ***
    getOperatorList: '/admin/GetPlatformUserList',

    // *** 删除运营人员 ***
    deleteUser: '/back/DisableUserByID',

    // *** 编辑运营人员 ***
    editOperator: '/admin/UpdatePlatformUser',

    // *** 添加运营人员 ***
    addOperator: '/admin/AddPlatformUser',

    // *** 驾驶舱 ***
    getRepository: '/admin/GetResourceStatistics',
    getIndustry: '/admin/GetIndustryStatistics',
    getEmployee: '/admin/GetEmployeeStatistics',
    getIncreasing: '/admin/GetCompanyStatistics  ',
    getGetCompanyArea: '/admin/GetCompanyAreaStatistics',
    getGetAgency: '/admin/GetAgencyStatistics',

    // *** 代理商管理 ***
    getAgencyList: '/admin/GetAgencyList',
    addAgency: '/admin/AddAgency',
    getAgency: '/admin/GetAgencyByID',
    updateAgency: '/admin/Updateagency',
    updateAgencyStatus: '/admin/UpdateAgencyStatusByID',
    exportAgency: '/admin/ExportAgencyList',

    // *** 企业会员  ***
    getCompanyList: '/admin/GetCompanyList',
    getProvinceAndBusiness: '/admin/GetCompanyEidtPageInfo',
    uploadLicense: '/admin/UploadCompanyBusinessLicence',
    updateCompanyStatus: '/admin/UpdateCompanyStatusByID',
    getCityList: '/admin/GetCityList',
    createCompany: '/admin/AddCompany',
    getCompanyInfo: '/admin/GetCompanyEidtPageInfo',
    updateCompanyInfo: '/admin/UpdateCompanyByID',
    exportCompany: '/admin/ExportCompanyList',

    // *** 约课 ***
    getAppointments: '/appointment/adminGetAppointmentList',
    getAppointment: '/appointment/adminGetAppointment',
    exportAppointment: '/appointment/adminExportAppointment',

    // *** Aliyun OSS ***
    ossInfo: '/ossInfo'
  },

  getUploadHeaders: function () {
    var token = window.localStorage.getItem('token')
    return {
      token: token
    }
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
