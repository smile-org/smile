<template>
  <div>
    <common-header type="course"></common-header>
    <div class="con_main">
      <navigator module="course" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 :
            <span class="">课程管理</span> >
            <span class="">课程信息管理</span> >
            <span class="f_blue">创建课程</span>
          </span>
        </nav>
        <div class="con_tab">
          <el-form :rules="formRules" ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden add_width" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="课程名称" prop="title">
                  <el-input v-model="form.title" placeholder="课程名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="课程类别" prop="cateid">
                  <el-select class="dateTab_width" v-model="form.cateid" placeholder="请选择课程类别">
                    <el-option v-for="item in categoryList" :key="item.category_id" :label="item.category_name" :value="item.category_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="责任人" prop="adminid">
                  <el-select class="dateTab_width" filterable v-model="form.adminid" placeholder="请选择责任人">
                    <el-option v-for="item in adminList" :key="item.user_id" :label="item.full_name" :value="item.user_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部门" prop="depart">
                  <el-input v-model="form.depart" placeholder="部门"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="有效期" prop="expdate">
                  <el-date-picker v-model="form.expdate" type="date" placeholder="选择日期" class="dateTab_width" style="width: 100%;" :picker-options="pickerOptions0">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col>
              <el-form-item label="简介" prop="intro" class="input_textarea">
                <el-input type="textarea" v-model="form.intro" placeholder="简介" style="min-width: 545px;"></el-input>
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
                  <el-form-item label="序号" prop="num">
                    <el-input v-model.number="formInline.num" placeholder="序号"></el-input>
                  </el-form-item>
                  <el-form-item label="章节" prop="title">
                    <el-input v-model="formInline.title" placeholder="章节"></el-input>
                  </el-form-item>
                  <el-form-item label="标题" prop="content">
                    <el-input v-model="formInline.content" placeholder="标题" style="width: 300px;"></el-input>
                  </el-form-item>
                </el-form>
                <el-upload class="upload-demo" accept="video/mp4,video/mov,application/msword,image/jpeg,image/png,application/vnd.ms-powerpoint,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf"
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
                  <div slot="tip" class="el-upload__tip" v-if="isPaid">支持类型pdf/word/ppt/mp4/png/jpg，大小不超过500M</div>
                  <div slot="tip" class="el-upload__tip" v-else>支持类型pdf/word/ppt/png/jpg，大小不超过10M</div>
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
                <el-table-column prop="num" align="center" label="序号" width="100">
                </el-table-column>
                <el-table-column prop="title" align="center" label="章节">
                </el-table-column>
                <el-table-column prop="content" align="center" label="标题">
                </el-table-column>
                <el-table-column prop="name" ali gn="center" label="课件">
                </el-table-column>
                <el-table-column label="操作" align="center"  class="tc" width="100">
                  <template scope="scope">
                    <el-button @click="editContent(scope.row.id)" type="text" size="small">编辑</el-button>
                    <el-button @click="remove(scope.row.id)" type="text" class="red_font" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <hr class="hr_line">
          <div class="mt30">
            <p>
              <template>
                <el-checkbox v-model="useWhiteList">备选项</el-checkbox>
              </template>
              白名单
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
                <el-table-column property="user_id" align="center" width="100" type="selection" @selection-change="changeFun">
                </el-table-column>
                <el-table-column property="full_name" align="center" label="姓名" width=""></el-table-column>
                <el-table-column property="cell_phone" align="center" label="手机" width=""></el-table-column>
                <el-table-column property="department" align="center" label="部门"></el-table-column>
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
            <!--<button type="button" class="inf_btn  ml20" v-on:click="publish">发布</button>-->
          </div>

          <my-upload @input="closeIcon" :no-rotate=false field="file" @crop-success="cropIconSuccess" @crop-upload-success="cropIconUploadSuccess" @crop-upload-fail="cropIconUploadFail" :url="uploadIconUrl" :width="190" :headers="headers" :params="logoFormData" :height="144" :value.sync="showIcon" :no-circle=true img-format="png">
          </my-upload>

          <my-upload @input="closeBanner" :no-rotate=false field="file" @crop-success="cropBannerSuccess" @crop-upload-success="cropBannerUploadSuccess" @crop-upload-fail="cropBannerUploadFail" :url="uploadBannerUrl" :width="375" :headers="headers" :params="bannerFormData" :height="150" :value.sync="showBanner" :no-circle=true img-format="png">
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
// import router from '../../router'
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
        id: '',
        num: '',
        title: '',
        content: '',
        name: '',
        url: ''
      },
      formWhiteList: {
        username: '',
        department: ''
      },
      contentRules: {
        num: [
          { required: true, message: '请输入合法序号' },
          { type: 'number', message: '请输入合法序号' }
        ],
        title: [
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
        cateid: [
          { type: 'number', required: true, message: '请选择课程分类', trigger: 'change' }
        ],
        adminid: [
          { type: 'number', required: true, message: '请选择责任人', trigger: 'change' }
        ],
        depart: [
          { required: true, message: '请选择部门', trigger: 'blur' }
        ],
        expdate: [
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
    this.headers = api.getUploadHeaders()
    this.iconSrc = api.image.course.icon // this.iconSrcDefault
    this.bannerSrc = api.image.course.banner // this.bannerSrcDefault
    lang.zh.preview = ''
    api.fetch(api.uri.getSelectList, { courseid: 0 }).then(data => {
      if (data.status === 1) {
        this.categoryList = data.result.CategoryList
        this.adminList = data.result.AdminList
      }
      api.fetch(api.uri.getPaymentStatus, {}).then(result => {
        if (result.status === 1 && result.result === 1) {
          this.isPaid = true
        }
      })
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
          console.log(data.result)
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
          console.log(data.result)
          this.bannerFormData.OSSAccessKeyId = data.result.accessid
          this.bannerFormData.key = data.result.dir
          this.bannerFormData.policy = data.result.policy
          this.bannerFormData.signature = data.result.signature
          this.bannerFormData.expire = data.result.expire
          this.uploadBannerUrl = data.result.host
        }
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
        num: '',
        id: '',
        title: '',
        content: '',
        name: '',
        url: ''
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
        return item.id === id
      })
      this.fileList.push({
        name: this.formInline.name
      })
    },
    delContent (id) {
      this.$confirm('此操作将删除该课程内容, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contentList = _.filter(this.contentList, function (item) {
          return item.id !== id
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
          if (!this.formInline.url) {
            this.$message({
              type: 'info',
              message: '请提供附件'
            })
            return
          }
          // 编辑
          if (this.formInline.id) {
            api.fetch(api.uri.updateCourseContent, {
              contentid: parseInt(this.formInline.id),
              num: this.formInline.num,
              title: this.formInline.title,
              content: this.formInline.content,
              attachmentUrl: this.formInline.url,
              fileName: this.formInline.name
            }).then(data => {
              this.dialogFormVisible = false
              // 更新contentList
              for (var i = 0; i < this.contentList.length; i++) {
                if (this.contentList[i].id === this.formInline.id) {
                  this.contentList[i].num = this.formInline.num
                  this.contentList[i].title = this.formInline.title
                  this.contentList[i].content = this.formInline.content
                  this.contentList[i].name = this.formInline.name
                  this.contentList[i].url = this.formInline.url
                }
              }
              this.addContentInProgress = false
            })
          } else {
            api.fetch(api.uri.postCourseContent, {
              num: parseInt(this.formInline.num),
              title: this.formInline.title,
              content: this.formInline.content,
              attachmentUrl: this.formInline.url,
              fileName: this.formInline.name
            }).then(data => {
              if (data.status === 1) {
                this.dialogFormVisible = false
                this.contentList.push({
                  id: data.result,
                  num: this.formInline.num,
                  title: this.formInline.title,
                  content: this.formInline.content,
                  name: this.fileList[0].name,
                  url: this.formInline.url
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
      // this.formInline.url = response.result
      this.formInline.name = file.name
      this.formInline.url = this.uploadContentAction + '/' + this.contentFormData.key
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
      this.contentFormData.key = this.contentOrigianlPath + api.guid() + '.' + api.getFileExt(file.name)
      console.log(this.contentFormData.key)
    },
    submitCourse () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var submitObj = {
            cateid: this.form.cateid,
            title: this.form.title,
            adminid: this.form.adminid,
            depart: this.form.depart,
            expdate: moment(this.form.expdate).format('YYYY-MM-DD'),
            intro: this.form.intro,
            iconPath: this.iconSrc,
            picPath: this.bannerSrc
          }
          if (this.keywords && this.keywords.length > 0) {
            submitObj.keywords = this.keywords.join(',')
          }
          var contentIds = []
          for (var ci = 0; ci < this.contentList.length; ci++) {
            contentIds.push(this.contentList[ci].id)
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
          api.post(api.uri.addCourse, submitObj).then(data => {
            if (data.status === 1) {
              router.push({name: 'courseList'})
            }
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
        // this.iconSrc = jsonData.result
      this.iconSrc = this.uploadIconUrl + '/' + this.logoFormData.key
      // }
    },
    cropBannerUploadSuccess (jsonData, field) {
      // if (jsonData.status === 1) {
        // this.bannerSrc = jsonData.result
      this.bannerSrc = this.uploadBannerUrl + '/' + this.bannerFormData.key
      // }
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
  margin: 10px 20px 10px 0px;
  height: 34px;
  line-height: 32px;
  padding: 0 5px 0 15px;
  font-size: 14px;
}

.baiming_list span i {
  margin-left: 10px;
  color: #fff;
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

.update_btn:hover,
.update_btn:active,
.update_btn:focus {
  color: #fff;
  background-color: #00b553;
  border: 1px solid #00b553;
}

</style>

