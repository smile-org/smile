<template>
  <div>
    <common-header type="course"></common-header>
    <div class="con_main">
      <navigator module="course"  menu="list" ></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 :
            <span class="">课程管理</span> >
            <span class="">课程信息管理</span> >
            <span class="f_blue">编辑课程</span>
          </span>
        </nav>
        <div class="con_tab">
          <el-form :rules="formRules" ref="form" :inline="true" :model="currentCourse" class="demo-form-inline mt20 hidden add_width" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="课程名称" prop="title">
                  <el-input v-model="currentCourse.title" placeholder="课程名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="课程类别" prop="category_id">
                  <el-select v-model="currentCourse.category_id" placeholder="请选择课程类别">
                    <el-option v-for="item in categoryList" :key="item.category_id" :label="item.category_name" :value="item.category_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部门" prop="department">
                  <el-input v-model="currentCourse.department" placeholder="部门"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="责任人" prop="principal_user_id">
                  <el-select filterable v-model="currentCourse.principal_user_id" placeholder="请选择责任人">
                    <el-option v-for="item in adminList" :key="item.user_id" :label="item.full_name" :value="item.user_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="有效期" prop="expiration_date">
                  <el-date-picker v-model="currentCourse.expiration_date" type="date" placeholder="选择日期" style="width: 100%;"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col :span="24">
              <el-form-item label="简介" prop="intro" class="input_textarea">
                <el-input  type="textarea" v-model="currentCourse.intro" placeholder="简介" style="min-width: 545px;"></el-input>
              </el-form-item>
            </el-col>
             <el-col>
                <el-form-item label="关键字" prop="keywords">
                  <el-tag :key="item" v-for="item in keywords" :closable="true" :close-transition="false" @close="handleClose(item)">
                    {{item}}
                  </el-tag>
                  <el-input class="input-new-tag" v-if="inputVisible" v-model="keyword" ref="saveTagInput" size="mini" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm" maxlength=20 minlength=1>
                  </el-input>
                  <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新增关键字</el-button>
                </el-form-item>
              </el-col>
          </el-form>
          <hr class="hr_line">
          <table class="page_m mt30 mb30" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                课程图标
              </td>
              <td class="page_m_b">
                <img :src="iconData" width="20%" />
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(1)">使用默认</a>
                <a v-on:click="toggleShow(1)">上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                课程主图
              </td>
              <td class="page_m_b">
                <img :src="bannerData" width="100%" />
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(2)">使用默认</a>
                <a v-on:click="toggleShow(2)">上传</a>
              </td>
            </tr>
          </table>
          <hr class="hr_line">
          <div class="mt30">
            <p>课程内容
              <el-button type="text" @click="addContent">添加课程内容</el-button>
            </p>
            <template>
              <el-dialog title="添加/编辑课程内容" :visible.sync="dialogFormVisible">
                <el-form :rules="contentRules" ref="formInline" :inline="true" :model="formInline" class="demo-form-inline mt20" >
                  <el-form-item label="序号" prop="sequnce_num">
                    <el-input v-model.number="formInline.sequnce_num" placeholder="序号"></el-input>
                  </el-form-item>
                  <el-form-item label="章节" prop="sequnce_title">
                    <el-input v-model="formInline.sequnce_title" placeholder="章节"></el-input>
                  </el-form-item>
                  <el-form-item label="标题" prop="content">
                    <el-input v-model="formInline.content" placeholder="标题" style="width: 300px;"></el-input>
                  </el-form-item>
                </el-form>
                <el-upload class="upload-demo" accept="video/mp4,video/mov,application/msword,image/jpeg,image/png,application/vnd.ms-powerpoint,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
                  ref="uploadContent"
                  :action="uploadContentAction"
                  :on-success="onContentSuccess"
                  :before-upload="beforeContentUpload"
                  :auto-upload="true"
                  :on-change="changeContentUpload"
                  :file-list="fileList"
                  :data="contentFormData"
                  :headers="headers">
                  <button slot="trigger"  size="small" class="inf_btn2" type="primary">点击上传</button>
                  <div slot="tip" class="el-upload__tip" v-if="isPaid">支持类型pdf/word/ppt/excel/mp4/png/jpg，大小不超过500M</div>
                  <div slot="tip" class="el-upload__tip" v-else>支持类型pdf/word/ppt/excel/png/jpg，大小不超过10M</div>
                </el-upload>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>

          </div>
          <div class="mt20">
            <template>
              <el-table :data="contentList" border class="mt20 mb30" style="width: 100%">
                <el-table-column prop="sequnce_num" align="center" label="序号" width="100">
                </el-table-column>
                <el-table-column prop="sequnce_title" align="center" label="章节">
                </el-table-column>
                <el-table-column prop="content" align="center" label="标题">
                </el-table-column>
                <el-table-column prop="filename" align="center" label="课件">
                </el-table-column>
                <el-table-column label="操作" align="center" class="tc" width="100">
                  <template scope="scope">
                    <el-button @click="editContent(scope.row.content_id)" type="text" size="small">编辑</el-button>
                    <el-button @click="delContent(scope.row.content_id)" class="red_font" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <hr class="hr_line">
          <div class="mt30">
            <p>
              <template>
                <el-checkbox v-model="useWhiteList">白名单</el-checkbox>
              </template>
              <el-button type="text" :disabled="!useWhiteList"  @click="dialogWhiteListVisible = true">导入白名单</el-button>
            </p>
            <el-dialog title="导入白名单" :visible.sync="dialogWhiteListVisible">
              <el-form :inline="true" :model="formWhiteList" class="demo-form-inline mt20">
                <el-form-item label="姓名">
                  <el-input v-model="formWhiteList.username" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                  <el-input v-model="formWhiteList.department" placeholder="部门"></el-input>
                </el-form-item>
                <el-form-item class="wrapper">
                  <el-button type="success" @click="searchUser">查询</el-button>
                </el-form-item>
              </el-form>
              <el-table :data="userData" border ref="multipleTable"  @selection-change="changeFun">
                <el-table-column property="user_id" width="100" type="selection" @selection-change="changeFun">
                </el-table-column>
                <el-table-column property="full_name" label="姓名" width=""></el-table-column>
                <el-table-column property="cell_phone" label="手机" width=""></el-table-column>
                <el-table-column property="department" label="部门"></el-table-column>
              </el-table>
              <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change="handleWhiteListCurrentChange" :current-page="currentPage"  :page-size="take" :total="total"></el-pagination>
              <div class="tc">
                <button type="button" class="inf_btn mt30 mb20" v-on:click="saveUserToWhiteList">保 存
                </button>
              </div>
            </el-dialog>
            <div class="baiming_list" v-show="useWhiteList">
              <el-tag v-for="tag in whiteList" :key="tag.user_id" :closable="true" @close="handleWhiteListClose(tag)">{{tag.full_name}}</el-tag>
            </div>
          </div>

          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " v-on:click="submitCourse">保 存</button>
            <button type="button" class="inf_btn  ml20" v-on:click="publish">{{currentCourse.ispublished ? "隐藏": "发布"}}</button>
          </div>

          <my-upload @input="closeIcon" field="file" @crop-success="cropIconSuccess" @crop-upload-success="cropIconUploadSuccess" @crop-upload-fail="cropIconUploadFail" :url="uploadIconUrl" :width="190" :headers="headers" :params="logoFormData" :height="144" :value.sync="showIcon" :no-circle=true img-format="png">
          </my-upload>

          <my-upload @input="closeBanner" field="file" @crop-success="cropBannerSuccess" @crop-upload-success="cropBannerUploadSuccess" @crop-upload-fail="cropBannerUploadFail" :url="uploadBannerUrl" :width="375" :headers="headers" :params="bannerFormData" :height="150" :value.sync="showBanner" :no-circle=true img-format="png">
          </my-upload>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import FileUpload from 'vue-upload-component'
import commonHeader from '../../components/CommonHeader'
import navigator from '../../components/Navigator'
import api from '../../services/api'
import axios from 'axios'
import lang from 'vue-image-crop-upload/utils/language'
import myUpload from 'vue-image-crop-upload'
import _ from 'lodash'
import moment from 'moment'
import router from '../../router'
export default {
  data: function () {
    return {
      pickerOptions0: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      // 是否显示content添加pupup
      dialogFormVisible: false,
      dialogWhiteListVisible: false,
      // 是否启用白名单
      useWhiteList: false,
      // 是否显示icon上传
      showIcon: false,
      // 是否显示banner 上传
      showBanner: false,
      // 添加课程未完成
      addContentInProgress: false,
      // image url 或者 二进制流
      bannerData: '',
      iconData: '',
      // 传给后台存储的路径
      iconSrc: '',
      bannerSrc: '',
      // 默认图片地址
      iconSrcDefault: axios.defaults.imageServer + api.image.course.icon,
      bannerSrcDefault: axios.defaults.imageServer + api.image.course.banner,
      // 图片上传地址
      // uploadIconUrl: api.uri.uploadCourseIcon,
      // uploadBannerUrl: api.uri.uploadCourseBanner,
      // uploadContentAction: api.uri.uploadContentAction,

      uploadIconUrl: '',
      logoFormData: {
        key: '',
        policy: '',
        OSSAccessKeyId: '',
        signature: '',
        expire: 0,
        success_action_status: '200'
      },
      uploadBannerUrl: '',
      bannerFormData: {
        key: '',
        policy: '',
        OSSAccessKeyId: '',
        signature: '',
        expire: 0,
        success_action_status: '200'
      },
      uploadContentAction: '',
      contentFormData: {
        key: '',
        policy: '',
        OSSAccessKeyId: '',
        signature: '',
        expire: 0,
        success_action_status: '200'
      },

      // uploadContentFile: api.uri.addCourseContent,
      // 下拉框数据
      adminList: [],
      categoryList: [],
      // 课程内容上传列表， 里面只有一个附件， 第二个会替换第一个
      fileList: [],
      // 课程内容列表
      contentList: [],
      // 用户搜索结果
      userData: [],
      // 白名单列表
      whiteList: [],
      // 搜索用户的user_id
      multipleTable: [],
      // 课程基本内容
      form: {
        title: '',
        cateid: '',
        depart: '',
        adminid: '',
        expdate: '',
        intro: ''
      },
      // 课程内容obj
      formInline: {
        content_id: '',
        sequnce_num: '',
        sequnce_title: '',
        content: '',
        filename: '',
        orignal_path: ''
      },
      formWhiteList: {
        username: '',
        department: ''
      },
      contentRules: {
        sequnce_num: [
          { required: true, message: '请输入合法序号' },
          { type: 'number', message: '请输入合法序号' }
        ],
        sequnce_title: [
          { required: true, message: '请输入章节', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ]
      },
      formRules: {
        title: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        category_id: [
          { type: 'number', required: true, message: '请选择课程分类', trigger: 'change' }
        ],
        principal_user_id: [
          { type: 'number', required: true, message: '请选择责任人', trigger: 'change' }
        ],
        department: [
          { required: true, message: '请选择部门', trigger: 'blur' }
        ],
        expiration_date: [
          { type: 'date', required: true, message: '请选择有效时间', trigger: 'change' }
        ],
        intro: [
          { required: true, message: '请输入简介', trigger: 'blur' }
        ]
      },
      headers: {},

      // whitelist 分页
      take: 10,
      currentPage: 1,
      total: 0,

      // id
      id: 0,
      currentCourse: {},

      contentOrigianlPath: '',

      isPaid: false,
      maxFileSize: 500,

      keywords: [],
      keyword: '',
      inputVisible: false
    }
  },
  components: {
    commonHeader,
    navigator,
    myUpload,
    FileUpload
  },
  created () {
    this.id = parseInt(this.$route.query.id)
    this.headers = api.getUploadHeaders()
    lang.zh.preview = ''
    api.fetch(api.uri.getSelectList, { courseid: this.id }).then(data => {
      console.log(data)
      if (data.status === 1) {
        this.categoryList = data.result.CategoryList
        this.adminList = data.result.AdminList
        this.currentCourse = data.result.CourseTobeEdit
        this.currentCourse.expiration_date = new Date(data.result.CourseTobeEdit.expiration_date)
        this.contentList = data.result.ContentList
        this.whiteList = data.result.UserWhiteList
        this.useWhiteList = this.whiteList.length > 0
        this.iconSrc = axios.defaults.imageServer + data.result.CourseTobeEdit.icon
        this.iconData = axios.defaults.imageServer + data.result.CourseTobeEdit.icon
        this.bannerSrc = axios.defaults.imageServer + data.result.CourseTobeEdit.pic
        this.bannerData = axios.defaults.imageServer + data.result.CourseTobeEdit.pic
        this.keywords = data.result.CourseTobeEdit.keywords ? data.result.CourseTobeEdit.keywords.split(',') : []
      }
      api.fetch(api.uri.getPaymentStatus).then(result => {
        if (result.status === 1 && result.result === 1) {
          this.isPaid = true
        }
      })
    }).catch(error => {
      this.$message(error.message)
    })
    this.initLogoFormData()
    this.initBannerFormData()
    api.fetch(api.uri.ossInfo, {businessType: 'course-office'}).then(data => {
      if (data.status === 1) {
        console.log('office', data.result)
        this.contentFormData.OSSAccessKeyId = data.result.accessid
        // this.contentFormData.key = data.result.dir
        this.contentFormData.policy = data.result.policy
        this.contentFormData.signature = data.result.signature
        this.contentFormData.expire = data.result.expire
        this.uploadContentAction = data.result.host
        this.contentOrigianlPath = data.result.dir
      }
    })
  },
  methods: {
    handleClose (item) {
      this.keywords.splice(this.keywords.indexOf(item), 1)
    },

    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm () {
      let keyword = this.keyword.trim()
      if (keyword) {
        this.keywords.push(keyword)
      }
      this.inputVisible = false
      this.keyword = ''
    },

    initLogoFormData () {
      this.headers = api.getUploadHeaders()
      api.fetch(api.uri.ossInfo, {businessType: 'course-icon'}).then(data => {
        if (data.status === 1) {
          this.logoFormData.OSSAccessKeyId = data.result.accessid
          this.logoFormData.key = data.result.dir
          this.logoFormData.policy = data.result.policy
          this.logoFormData.signature = data.result.signature
          this.logoFormData.expire = data.result.expire
          this.uploadIconUrl = data.result.host
        }
      })
    },
    initBannerFormData () {
      this.headers = api.getUploadHeaders()
      api.fetch(api.uri.ossInfo, {businessType: 'course-pic'}).then(data => {
        if (data.status === 1) {
          this.bannerFormData.OSSAccessKeyId = data.result.accessid
          this.bannerFormData.key = data.result.dir
          this.bannerFormData.policy = data.result.policy
          this.bannerFormData.signature = data.result.signature
          this.bannerFormData.expire = data.result.expire
          this.uploadBannerUrl = data.result.host
        }
      })
    },
    publish () {
      console.log('ss')
      console.log(this.currentCourse)
      // 隐藏传0， 发布传1
      var _type = 0
      if (this.currentCourse.ispublished === true) {
        _type = 0
      } else {
        _type = 1
      }
      api.fetch(api.uri.publishCourse, {
        courseid: this.id,
        publish: _type
      }).then(data => {
        if (data.status === 1) {
          router.push({name: 'courseList'})
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    changeFun (val) {
      this.multipleTable = val
    },
    handleWhiteListClose (item) {
      this.whiteList = _.remove(this.whiteList, function (_item) {
        return _item.user_id !== item.user_id
      })
    },
    handleWhiteListCurrentChange (val) {
      this.currentPage = val
      this.searchUser()
    },
    searchUser () {
      api.fetch(api.uri.searchWhiteList, {
        cellphone: '',
        area: '',
        fullname: this.formWhiteList.username,
        department: this.formWhiteList.department,
        take: this.take,
        skip: this.take * (this.currentPage - 1)
      }).then(data => {
        if (data.status === 1) {
          // this.multipleTable = []
          this.userData = data.result
          // for (var i = 0; i < this.userData.length; i++) {
          //   if (_.some(this.whiteList, {user_id: this.userData[i].user_id})) {
          //     this.multipleTable.push(this.userData[i])
          //   }
          // }
          // console.log(this.multipleTable)
          this.total = data.total
        }
      })
    },
    saveUserToWhiteList () {
      console.log(this.multipleTable)
      for (var i = 0; i < this.multipleTable.length; i++) {
        if (!_.some(this.whiteList, {user_id: this.multipleTable[i].user_id})) {
          this.whiteList.push(this.multipleTable[i])
        }
      }
      this.dialogWhiteListVisible = false
    },
    resetFormInline () {
      this.formInline = {
        content_id: '',
        sequnce_num: '',
        sequnce_title: '',
        content: '',
        filename: '',
        orignal_path: ''
      }
      this.fileList = []
      this.addContentInProgress = false
    },
    addContent () {
      if (!this.addContentInProgress) {
        this.resetFormInline()
        this.addContentInProgress = true
      }
      this.dialogFormVisible = true
    },
    editContent (id) {
      this.resetFormInline()
      this.dialogFormVisible = true
      this.formInline = _.find(this.contentList, function (item) {
        return item.content_id === id
      })
      this.fileList.push({
        name: this.formInline.filename
      })
    },
    delContent (id) {
      this.$confirm('此操作将删除该课程内容, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contentList = _.filter(this.contentList, function (item) {
          return item.content_id !== id
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    submitUploadContent () {
      this.$refs['formInline'].validate((valid) => {
        if (valid) {
          if (!this.formInline.orignal_path) {
            this.$message({
              type: 'info',
              message: '请提供附件'
            })
            return
          }
          // 编辑
          if (this.formInline.content_id) {
            api.fetch(api.uri.updateCourseContent, {
              contentid: parseInt(this.formInline.content_id),
              num: this.formInline.sequnce_num,
              title: this.formInline.sequnce_title,
              content: this.formInline.content,
              attachmentUrl: this.formInline.orignal_path,
              fileName: this.formInline.filename
            }).then(data => {
              this.dialogFormVisible = false
              // 更新contentList
              for (var i = 0; i < this.contentList.length; i++) {
                if (this.contentList[i].content_id === this.formInline.content_id) {
                  this.contentList[i].sequnce_num = this.formInline.sequnce_num
                  this.contentList[i].sequnce_title = this.formInline.sequnce_title
                  this.contentList[i].content = this.formInline.content
                  this.contentList[i].filename = this.formInline.filename
                  this.contentList[i].orignal_path = this.formInline.orignal_path
                }
              }
              this.addContentInProgress = false
            })
          } else {
            api.fetch(api.uri.postCourseContent, {
              num: parseInt(this.formInline.sequnce_num),
              title: this.formInline.sequnce_title,
              content: this.formInline.content,
              attachmentUrl: this.formInline.orignal_path,
              fileName: this.formInline.filename
            }).then(data => {
              if (data.status === 1) {
                this.dialogFormVisible = false
                this.contentList.push({
                  content_id: data.result,
                  sequnce_num: this.formInline.sequnce_num,
                  sequnce_title: this.formInline.sequnce_title,
                  content: this.formInline.content,
                  filename: this.fileList[0].name,
                  orignal_path: this.formInline.orignal_path
                })
                this.addContentInProgress = false
              }
            })
          }
        } else {
          return false
        }
      })
    },
    onContentSuccess (response, file, fileList) {
      // 上传成功保存两个属性， 保存课程时，判断是否有附件的依据
      // this.formInline.orignal_path = response.result
      this.formInline.filename = file.name
      this.formInline.orignal_path = this.uploadContentAction + '/' + this.contentFormData.key
      console.log('content', this.formInline.orignal_path)
    },
    changeContentUpload (file, fileList) {
      // 保证页面显示一个附件
      if (fileList.length > 0) {
        this.fileList = [file]
      }
    },
    beforeContentUpload (file) {
      // 判断大小与格式
      console.log(file.name.indexOf('.') !== -1)
      if (file.name.indexOf('.') !== -1) {
        var arrLen = file.name.split('.').length - 1
        var extension = file.name.split('.')[arrLen].toUpperCase()
        var targetExtensionArray = api.extension.course
        if (!this.isPaid) {
          targetExtensionArray = api.extension.trial
        }
        if (targetExtensionArray.indexOf(extension) === -1) {
          this.$message({
            type: 'info',
            message: '不支持的上传文件格式'
          })
          this.fileList = []
          return false
        }
      } else {
        this.$message({
          type: 'info',
          message: '不支持的上传文件格式'
        })
        this.fileList = []
        return false
      }
      if (!this.isPaid) {
        this.maxFileSize = 10
      }
      if (file.size > this.maxFileSize * 1024 * 1024) {
        this.$message({
          type: 'info',
          message: '附件不能大于' + this.maxFileSize + 'M'
        })
        this.fileList = []
        return false
      }

      // this.contentFormData.key = this.contentFormData.key + api.guid() + '.' + api.getFileExt(file.name)
      this.contentFormData.key = this.contentOrigianlPath + api.guid() + '.' + api.getFileExt(file.name)
      console.log(this.contentFormData.key)
    },
    submitCourse () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var submitObj = {
            courseid: this.id,
            cateid: this.currentCourse.category_id,
            title: this.currentCourse.title,
            adminid: this.currentCourse.principal_user_id,
            depart: this.currentCourse.department,
            expdate: moment(this.currentCourse.expiration_date).format('YYYY-MM-DD'),
            intro: this.currentCourse.intro,
            iconPath: this.iconSrc,
            picPath: this.bannerSrc
          }
          if (this.keywords && this.keywords.length > 0) {
            submitObj.keywords = this.keywords.join(',')
          }
          var contentIds = []
          for (var ci = 0; ci < this.contentList.length; ci++) {
            contentIds.push(this.contentList[ci].content_id)
          }
          if (contentIds.length === 0) {
            this.$message({
              type: 'info',
              message: '请添加课程内容'
            })
            return
          }
          submitObj.contentids = contentIds.join(',')
          if (this.useWhiteList) {
            submitObj.typeid = 2
            var whiteListIds = []
            for (var i = 0; i < this.whiteList.length; i++) {
              whiteListIds.push(this.whiteList[i].user_id)
            }
            submitObj.whitelsituserids = whiteListIds.join(',')
          } else {
            submitObj.typeid = 1
          }
          console.log(submitObj)
          api.post(api.uri.editCourse, submitObj).then(data => {
            if (data.status === 1) {
              router.push({name: 'courseList'})
            } else {
              console.log(data)
            }
          }).then(error => {
            console.log(error)
          })
        } else {
          return false
        }
      })
    },
    closeIcon () {
      this.showIcon = !this.showIcon
    },
    closeBanner () {
      this.showBanner = !this.showBanner
    },
    cropIconSuccess (data, field) {
      this.iconData = data
      this.logoFormData.key = this.logoFormData.key + api.guid() + '.png'
    },
    cropBannerSuccess (data, field) {
      this.bannerData = data
      this.bannerFormData.key = this.bannerFormData.key + api.guid() + '.png'
    },
    cropIconUploadSuccess (jsonData, field) {
      // if (jsonData.status === 1) {
      //   this.iconSrc = jsonData.result
      // }
      this.iconSrc = this.uploadIconUrl + '/' + this.logoFormData.key
    },
    cropBannerUploadSuccess (jsonData, field) {
      // if (jsonData.status === 1) {
      //   this.bannerSrc = jsonData.result
      // }
      this.bannerSrc = this.uploadBannerUrl + '/' + this.bannerFormData.key
    },
    cropIconUploadFail (status, field) {
      this.$message({
        type: 'info',
        message: status
      })
    },
    cropBannerUploadFail (status, field) {
      this.$message({
        type: 'info',
        message: status
      })
    },
    toggleShow (currentImage) {
      if (currentImage === 1) {
        this.showIcon = !this.showIcon
      } else {
        this.showBanner = !this.showBanner
      }
    },
    setDefault (currentImage) {
      if (currentImage === 1) {
        this.iconSrc = api.image.course.icon
        this.iconData = this.iconSrcDefault
      } else {
        this.bannerSrc = api.image.course.banner
        this.bannerData = this.bannerSrcDefault
      }
    }
  }
}
</script>

<style scoped="scope">
.file-uploads {
  overflow: hidden;
  position: relative;
  text-align: center;
  display: inline-block;
}
.file-uploads.file-uploads-html4 input[type="file"] {
  opacity: 0;
  font-size: 20em;
  z-index: 1;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  width: 100%;
  height: 100%;
}
.file-uploads.file-uploads-html5 input[type="file"] {
  overflow: hidden;
  position: fixed;
  width: 1px;
  height: 1px;
  z-index: -1;
  opacity: 0;
}
.el-dialog__body {
  padding: 10px 20px;
}


/*.el-button--small {*/


/*font-size: 14px;*/


/*color: #00b553;*/


/*}*/


/*.el-button--small:hover, .el-button--small:active, .el-button--small:focus {*/


/*font-size: 14px;*/


/*color: #1DB513;*/


/*}*/

.baiming_list {
  width: 100%;
  min-height: 30px;
  padding: 20px;
  border: 1px solid #dcdcdc;
  margin-top: 20px;
}

.baiming_list span {

  height: 34px;
  line-height: 32px;
  padding: 0 5px 0 15px;
  font-size: 14px;
  margin: 10px 20px 10px 0px;
}

.baiming_list span i {
  margin-left: 10px;
}


/*此处为弹出小分页的颜色*/

.el-pager li.active {
  border-color: #00b553;
  background-color: #00b553;
  color: #fff;
  cursor: default;
}

.el-pager li:hover {
  color: #00b553;
}

.update_btn {

  border-color: #00b553;
  border: 1px solid #00b553;
  background: none;
  color: #00b553;
}
.dateTab_width .el-input__icon+.el-input__inner{
  margin-right: 10px;
}
.update_btn:hover,
.update_btn:active,
.update_btn:focus {
  color: #fff;
  background-color: #00b553;
  border: 1px solid #00b553;
}
</style>

