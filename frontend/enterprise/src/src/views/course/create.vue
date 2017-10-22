<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
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
          <el-form :rules="formRules" ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden" label-width="80px">
            <el-col :span="8">
              <el-form-item label="课程名称" prop="title">
                <el-input v-model="form.title" placeholder="课程名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程类别" prop="cateid">
                <el-select v-model="form.cateid" placeholder="请选择课程类别">
                  <el-option v-for="item in categoryList" :key="item.category_id" :label="item.category_name" :value="item.category_id">
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
              <el-form-item label="责任人" prop="adminid">
                <el-select v-model="form.adminid" placeholder="请选择责任人">
                  <el-option v-for="item in adminList" :key="item.user_id" :label="item.full_name" :value="item.user_id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="有效期" prop="expdate">
                <el-date-picker
                  v-model="form.expdate"
                  type="date"
                  placeholder="选择日期"
                  style="width: 100%;"
                  :picker-options="pickerOptions0">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="简介" prop="intro">
                <el-input v-model="form.intro" placeholder="简介"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
          <table class="page_m mt30" cellspacing="0" cellpadding="0" border="0">
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

          <div class="mt30">
            <p>课程内容
              <el-button type="text" @click="dialogFormVisible=true">添加课程内容</el-button>
            </p>
            <template>
              <el-dialog title="添加/编辑课程内容" :visible.sync="dialogFormVisible">
                <el-form :rules="contentRules" ref="formInline" :inline="true" :model="formInline" class="demo-form-inline mt20" label-width="50px">
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
                <el-upload class="upload-demo"
                  ref="uploadContent"
                  action="http://192.168.1.111:8888/back/UploadCourseContentAttachment"
                  :on-success="onContentSuccess"
                  :before-upload="beforeContentUpload"
                  :auto-upload="true"
                  :on-change="changeContentUpload"
                  :file-list="fileList"
                  :headers="headers">
                  <el-button slot="trigger"  size="small" class="update_btn" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">支持类型word/ppt/mp4/png/jpg，大小不超过100M</div>
                </el-upload>
                <div v-if="formInline.name">{{formInline.name}}</div>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>

            <template>
              <el-dialog title="添加/编辑课程内容" :visible.sync="editDialogFormVisible">
                <el-form :rules="contentRules" ref="editFormInline" :inline="true" :model="editFormInline" class="demo-form-inline mt20" label-width="50px">
                  <el-form-item label="序号" prop="num">
                    <el-input v-model.number="editFormInline.num" placeholder="序号"></el-input>
                  </el-form-item>
                  <el-form-item label="章节" prop="title">
                    <el-input v-model="editFormInline.title" placeholder="章节"></el-input>
                  </el-form-item>
                  <el-form-item label="标题" prop="content">
                    <el-input v-model="editFormInline.content" placeholder="标题" style="width: 300px;"></el-input>
                  </el-form-item>
                </el-form>
                <el-upload class="upload-demo"
                  ref="uploadContent"
                  action="http://192.168.1.111:8888/back/UploadCourseContentAttachment"
                  :on-success="onContentSuccess"
                  :before-upload="beforeContentUpload"
                  :auto-upload="true"
                  :on-change="changeContentUpload"
                  :file-list="fileList"
                  :headers="headers">
                  <el-button slot="trigger"  size="small" class="update_btn" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">支持类型word/ppt/mp4/png/jpg，大小不超过100M</div>
                </el-upload>
                <div v-if="editFormInline.name">{{editFormInline.name}}</div>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitEditContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>

          </div>
          <div class="mt20">
            <template>
              <el-table :data="contentList" border class="mt20" style="width: 100%">
                <el-table-column prop="num" label="序号" width="100">
                </el-table-column>
                <el-table-column prop="title" label="章节">
                </el-table-column>
                <el-table-column prop="content" label="标题">
                </el-table-column>
                <el-table-column prop="name" label="课件">
                </el-table-column>
                <el-table-column label="操作" class="tc" width="">
                  <template scope="scope">
                    <el-button @click="editContent(scope.row.id)" type="text" size="small">编辑</el-button>
                    <el-button @click="delContent(scope.row.id)" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>

          </div>
          <!--<div class="mt30">
            <p>
              <template>
                <el-checkbox v-model="checked">备选项</el-checkbox>
              </template>
              白名单
              <el-button type="text" @click="dialogTableVisible = true">导入白名单</el-button>
            </p>
            <el-dialog title="导入白名单" :visible.sync="dialogTableVisible">
              <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
                <el-form-item label="姓名">
                  <el-input v-model="formInline.user" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                  <el-input v-model="formInline.department" placeholder="部门"></el-input>
                </el-form-item>
                <el-form-item class="wrapper">
                  <el-button type="success" @click="onSubmit">查询</el-button>
                </el-form-item>
              </el-form>
              <el-table :data="gridData" border>
                <el-table-column property="" label="" width="100">
                  <template scope="scope">
                    <el-checkbox v-model="checked"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column property="date" label="姓名" width=""></el-table-column>
                <el-table-column property="name" label="手机" width=""></el-table-column>
                <el-table-column property="address" label="部门"></el-table-column>
              </el-table>
              <el-pagination class="tc mt20" small layout="prev, pager, next" :total="50"></el-pagination>
              <div class="tc">
                <button type="button" class="inf_btn mt30 mb20" v-on:click="routeByName('informationEdit')">保 存
                </button>
              </div>
            </el-dialog>
            <div class="baiming_list">
              <el-tag v-for="tag in tags" :key="tag.name" :closable="true" :type="tag.type">{{tag.name}}</el-tag>

            </div>
          </div>-->
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " v-on:click="submit">保 存</button>
            <button type="button" class="inf_btn  ml20" v-on:click="routeByName('')">发布/隐藏</button>
          </div>
          <my-upload @input="closeIcon" field="file" @crop-success="cropIconSuccess" @crop-upload-success="cropIconUploadSuccess" @crop-upload-fail="cropIconUploadFail" :url="uploadIconUrl" :width="280" :headers="headers" :height="194" :value.sync="showIcon" :no-circle=true img-format="png">
          </my-upload>

          <my-upload @input="closeBanner" field="file" @crop-success="cropBannerSuccess" @crop-upload-success="cropBannerUploadSuccess" @crop-upload-fail="cropBannerUploadFail" :url="uploadBannerUrl" :width="375" :headers="headers" :height="120" :value.sync="showBanner" :no-circle=true img-format="png">
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
      editDialogFormVisible: false,
      // 是否显示icon上传
      showIcon: false,
      // 是否显示banner 上传
      showBanner: false,
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
      uploadIconUrl: api.uri.uploadCourseIcon,
      uploadBannerUrl: api.uri.uploadCourseBanner,
      // uploadContentFile: api.uri.addCourseContent,
      // 下拉框数据
      adminList: [],
      categoryList: [],
      // 课程内容上传列表， 里面只有一个附件， 第二个会替换第一个
      fileList: [],
      // 新添加的课程内容附件， 后台返回的地址
      newContentUrl: '',
      // 课程内容列表
      contentList: [],
      // 白名单内容列表
      formWriteList: {

      },
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
        num: '',
        title: '',
        content: ''
      },
      editFormInline: {
        num: '',
        title: '',
        content: '',
        name: ''
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
      headers: {}
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
    this.iconSrc = this.iconSrcDefault
    this.bannerSrc = this.bannerSrcDefault
    lang.zh.preview = ''
    api.fetch(api.uri.getSelectList, { courseid: 0 }).then(data => {
      if (data.status === 1) {
        this.categoryList = data.result.CategoryList
        this.adminList = data.result.AdminList
      }
    })
  },
  methods: {
    editContent (id) {
      this.editDialogFormVisible = true
      this.editFormInline = _.find(this.contentList, function (item) {
        return item.id === id
      })
    },
    delContent (id) {
      this.contentList = _.filter(this.contentList, function (item) {
        return item.id !== id
      })
    },
    submitEditContent () {

    },
    submitUploadContent () {
      this.$refs['formInline'].validate((valid) => {
        if (valid) {
          if (!this.newContentUrl) {
            this.$message({
              type: 'info',
              message: '请提供附件'
            })
            return
          }
          api.fetch(api.uri.postCourseContent, {
            num: parseInt(this.formInline.num),
            title: this.formInline.title,
            content: this.formInline.content,
            attachmentUrl: this.newContentUrl
          }).then(data => {
            if (data.status === 1) {
              this.dialogFormVisible = false
              this.contentList.push({
                id: data.result,
                num: this.formInline.num,
                title: this.formInline.title,
                content: this.formInline.content,
                name: this.fileList[0].name,
                url: this.newContentUrl
              })
              this.$refs['formInline'].resetFields()
              this.newContentUrl = ''
              this.fileList = []
            }
          })
        } else {
          return false
        }
      })
    },
    onContentSuccess (response, file, fileList) {
      this.newContentUrl = response.result
      console.log(this.newContentUrl)
    },
    changeContentUpload (file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [file]
      }
    },
    beforeContentUpload (file) {
      // 判断大小
      if (file.size > 100 * 1024 * 1024) {
        this.$message({
          type: 'info',
          message: '附件不能大于100M'
        })
        return false
      }
    },
    submit () {
      this.$refs['form'].validate((valid) => {
        console.log(this.form)
        console.log(this.iconSrc)
        console.log(this.bannerSrc)
        if (valid) {
          this.$message({
            type: 'info',
            message: '通过了'
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
    },
    cropBannerSuccess (data, field) {
      this.bannerData = data
    },
    cropIconUploadSuccess (jsonData, field) {
      if (jsonData.status === 1) {
        this.iconSrc = jsonData.result
      }
    },
    cropBannerUploadSuccess (jsonData, field) {
      if (jsonData.status === 1) {
        this.bannerSrc = jsonData.result
      }
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
        this.iconSrc = this.api.image.course.icon
        this.iconData = this.iconSrcDefault
      } else {
        this.bannerSrc = this.api.image.course.banner
        this.bannerData = this.bannerSrcDefault
      }
    },
    open2 () {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style>
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
  margin-right: 20px;
  height: 34px;
  line-height: 32px;
  padding: 0 5px 0 15px;
  font-size: 14px;
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

.update_btn:hover,
.update_btn:active,
.update_btn:focus {
  color: #fff;
  background-color: #00b553;
  border: 1px solid #00b553;
}
</style>

