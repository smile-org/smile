<template>
    <div>
        <common-header type="registration"></common-header>
        <div class="con_main">
            <navigator module="registration" menu="list"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 :
                      <span class="">报名管理</span> > <span class=""> 培训报名管理</span> > <span class="f_blue">添加报名</span></span>
                </nav>
                <div class="con_tab">
                    <el-form ref="form" :inline="true"  :model="form" class="demo-form-inline mt20 hidden add_width" label-width="80px">
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="课程名称">
                                    <el-input v-model="form.title" placeholder="课程名称"></el-input>
                                    <div class="el-form-item__error">{{titleErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="讲师">
                                    <el-input v-model="form.teacher" placeholder="讲师"></el-input>
                                    <div class="el-form-item__error">{{teacherErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="人数限制">
                                    <!--<el-input v-model="form.count" placeholder="人数限制"></el-input>-->
                                    <el-input-number v-model="form.count" :min="1" label="人数限制"></el-input-number>
                                    <div class="el-form-item__error">{{countErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="开始时间">
                                    <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="form.startDate"
                                                    style="width: 100%;"></el-date-picker>
                                    <div class="el-form-item__error">{{startDateErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="结束时间">
                                    <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="form.endDate"
                                                    style="width: 100%;"></el-date-picker>
                                    <div class="el-form-item__error">{{endDateErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                          <el-col :span="8">
                            <el-form-item label="地址">
                              <el-input v-model="form.location" placeholder="地址"></el-input>
                              <div class="el-form-item__error">{{locationErrMsg}}</div>
                            </el-form-item>
                          </el-col>
                        </el-row>
                      <el-col>
                        <el-form-item label="课程简介" class="input_textarea">
                          <el-input type="textarea" v-model="form.intro" placeholder="课程简介" ></el-input>
                          <div class="el-form-item__error">{{introErrMsg}}</div>
                        </el-form-item>
                      </el-col>
                    </el-form>
                  <hr class="hr_line">
                    <table class="page_m mt30 mb30" cellspacing="0" cellpadding="0" border="0">
                        <tr>
                            <td class="page_m_a">课程图标</td>
                            <td class="page_m_b">
                                <img :src="iconSrc | formatImage" width="20%"/>
                            </td>
                            <td class="page_m_c">
                                <a v-on:click="setDefaultImage(1)">使用默认</a>
                                <a v-on:click="uploadImage(1)">上传</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="page_m_a">
                                课程主图
                            </td>
                            <td class="page_m_b">
                                <img :src="bannerSrc | formatImage" width="100%"/>
                            </td>
                            <td class="page_m_c">
                                <a v-on:click="setDefaultImage(2)">使用默认</a>
                                <a v-on:click="uploadImage(2)">上传</a>
                            </td>
                        </tr>
                    </table>
                    <hr class="hr_line">
                    <div class="mt30 ">
                        <p class="pos_re">培训内容 <a href="javascript:void(0)" class="green_font" v-on:click="openDialog()" >添加培训内容</a>   <span class="error_font  ml20" style="">{{contentErrMsg}}</span></p>
                        <template class="hidden">
                          <el-dialog title="添加/编辑培训内容" :visible.sync="dialogFormVisible">
                            <el-form :inline="true" :model="formInline" class="demo-form-inline hidden" labelWidth="80px">
                                <!--<el-col :span="12">-->
                                <el-col :span="12">
                                    <el-form-item class="mb10" label="序号">
                                        <!--<el-input v-model="formInline.num" placeholder="序号"></el-input>-->
                                        <el-input-number v-model="formInline.num" :min="1" label="序号"></el-input-number>
                                      <div class="el-form-item__error"> {{numErrMsg}}</div>
                                    </el-form-item>
                                </el-col>
                              <el-col :span="12">
                                <el-form-item label="讲师">
                                  <el-input v-model="formInline.teacher" placeholder="讲师" style="width: 100%;"></el-input>
                                  <div class="el-form-item__error">{{teacherInContentErrMsg}}</div>
                                </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                <el-form-item label="开始时间">
                                  <el-date-picker class="dateTab_width" type="datetime" placeholder="选择日期时间" v-model="formInline.startDate" style="width: 100%;"></el-date-picker>
                                  <div class="el-form-item__error">{{startDateInContentErrMsg}}</div>
                                </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                <el-form-item label="结束时间">
                                  <el-date-picker class="dateTab_width" type="datetime" placeholder="选择日期时间" v-model="formInline.endDate" style="width: 100%;"></el-date-picker>
                                  <div class="el-form-item__error">{{endDateInContentErrMsg}}</div>
                                </el-form-item>
                              </el-col>

                                <!--<el-col :span="12">-->
                                    <!--<el-form-item label="起止时间">-->
                                        <!--<el-date-picker v-model="formInline.dateRange" type="datetimerange"   placeholder="选择时间范围"></el-date-picker>-->
                                        <!--<div class="el-form-item__error">{{dateRangeErrMsg}}</div>-->
                                    <!--</el-form-item>-->
                                <!--</el-col>-->
                                <el-col :span="24 " class="mb20">
                                    <el-form-item label="主题">
                                        <el-input v-model="formInline.topic" placeholder="主题" style="width:365px;"></el-input>
                                        <el-button :plain="true" type="success" class="ml20" style="margin-left: 20px;color: #fff" size="primary" v-on:click="addContent()">添加主题
                                        </el-button>
                                        <div class="el-form-item__error mb20">{{topicErrMsg}}</div>
                                    </el-form-item>
                                </el-col>
                            </el-form>
                          </el-dialog>
                        </template>
                        <template>
                            <el-table :data="tableData" class="mt20" border style="width: 100%">
                                <el-table-column prop="sequnce_num" align="center" label="序号" width="100"></el-table-column>
                                <el-table-column prop="content" label="主题" align="center" width=""></el-table-column>
                                <el-table-column prop="sequnce_title" align="center" label="起止时间"></el-table-column>
                                <el-table-column prop="teacher" align="center" label="讲师"></el-table-column>
                                <el-table-column prop="" label="操作" align="center" width="110">
                                    <template scope="scope">
                                      <el-button @click="editContent(scope.row.sequnce_num)"  type="text" size="small">编辑</el-button>
                                        <el-button @click="deleteContent(scope.row.sequnce_num)"  class="red_font"   type="text" size="small">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                    </div>
                    <div class="tc btn_margin">
                        <button type="button" v-on:click="add()" :loading="showloading" class="inf_btn ml20">保  存</button>
                    </div>
                </div>
            </section>
        </div>
        <!--<my-upload @input="closeMyUpload" field="file"
          @crop-success="cropSuccess"
          @crop-upload-success="cropUploadSuccess"
          @crop-upload-fail="cropUploadFail"
          :width="width"
          :height="height"
          :params="params"
          :headers="headers"
          :value.sync="show"
          :no-circle=true
          :url="uploadUrl"
          img-format="png">
        </my-upload>-->
        <my-upload
          @input="closeIcon"
          field="file"
          :params="logoFormData"
          @crop-success="cropIconSuccess"
          @crop-upload-success="cropIconUploadSuccess"
          @crop-upload-fail="cropIconUploadFail"
          :url="uploadIconUrl"
          :width="190"
          :headers="headers"
          :height="144"
          :value.sync="showIcon"
          :no-circle=true
          img-format="png">
        </my-upload>

        <my-upload
          @input="closeBanner"
          field="file"
          :params="bannerFormData"
          @crop-success="cropBannerSuccess"
          @crop-upload-success="cropBannerUploadSuccess"
          @crop-upload-fail="cropBannerUploadFail"
          :url="uploadBannerUrl"
          :width="375"
          :headers="headers"
          :height="150"
          :value.sync="showBanner"
          :no-circle=true
          img-format="png">
        </my-upload>
    </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import api from '../../services/api'
  import moment from 'moment'
  import axios from 'axios'
  import _ from 'lodash'
  import myUpload from 'vue-image-crop-upload'
  import lang from 'vue-image-crop-upload/utils/language'
  import router from '../../router'
  import ElInput from '../../../node_modules/element-ui/packages/input/src/input'
  export default {
    data: function () {
      return {
        formInline: {
          num: '',
          dateRange: '',
          startDate: '',
          endDate: '',
          topic: '',
          teacher: ''
        },
        form: {
          title: '',
          teacher: '',
          count: '',
          startDate: '',
          endDate: '',
          intro: '',
          location: ''
        },
        tableData: [],
        iconSrc: '',
        bannerSrc: '',
        numErrMsg: '',
        dateRangeErrMsg: '',
        topicErrMsg: '',
        titleErrMsg: '',
        teacherErrMsg: '',
        countErrMsg: '',
        startDateErrMsg: '',
        endDateErrMsg: '',
        introErrMsg: '',
        locationErrMsg: '',
        contentErrMsg: '',
        showloading: false,

        // show: false,
        showIcon: false,
        showBanner: false,
        headers: {},
        // uploadIconUrl: api.uri.uploadEnrollmentIcon,
        // uploadBannerUrl: api.uri.uploadEnrollmentBanner,
        // uploadUrl: '',
        // params: {
        //   pictype: ''
        // },

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

        startDateInContentErrMsg: '',
        endDateInContentErrMsg: '',
        teacherInContentErrMsg: '',
        dialogFormVisible: false
      }
    },
    components: {
      ElInput,
      commonHeader,
      navigator,
      myUpload
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      },
      formatImage (uri) {
        return axios.defaults.imageServer + uri
      }
    },
    created () {
      this.iconSrc = api.image.enrollment.icon
      this.bannerSrc = api.image.enrollment.banner
      lang.zh.preview = ''
      this.headers = api.getUploadHeaders()
      console.log(this.headers.token)
      this.initLogoFormData()
      this.initBannerFormData()
    },
    methods: {
      initLogoFormData () {
        this.headers = api.getUploadHeaders()
        api.fetch(api.uri.ossInfo, {businessType: 'enrollment-icon'}).then(data => {
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
        api.fetch(api.uri.ossInfo, {businessType: 'enrollment-pic'}).then(data => {
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
      openDialog: function () {
        this.numErrMsg = ''
        this.topicErrMsg = ''
        this.startDateInContentErrMsg = ''
        this.endDateInContentErrMsg = ''
        this.teacherInContentErrMsg = ''
        this.formInline.num = 1
        this.formInline.topic = ''
        this.formInline.teacher = ''
        this.formInline.startDate = ''
        this.formInline.endDate = ''
        this.dialogFormVisible = true
      },
      addContent: function () {
        this.numErrMsg = ''
        this.topicErrMsg = ''
        this.startDateInContentErrMsg = ''
        this.endDateInContentErrMsg = ''
        this.teacherInContentErrMsg = ''
        if (!this.formInline.num) {
          this.numErrMsg = '序号不能为空'
        }
//        for (var i in this.tableData) {
//          if (this.tableData[i].sequnce_num === this.formInline.num) {
//            this.numErrMsg = '序号不能重复'
//          }
//        }
        if (!this.formInline.topic) {
          this.topicErrMsg = '主题不能为空'
        }
        if (!this.formInline.startDate) {
          this.startDateInContentErrMsg = '开始日期不能为空'
        }
        if (!this.formInline.endDate) {
          this.endDateInContentErrMsg = '结束日期不能为空'
        }
        if (!this.formInline.teacher) {
          this.teacherInContentErrMsg = '讲师不能为空'
        }
        if (this.numErrMsg !== '' || this.topicErrMsg !== '' || this.startDateInContentErrMsg !== '' || this.endDateInContentErrMsg !== '' || this.teacherInContentErrMsg !== '') {
          return
        }
        this.deleteContent(this.formInline.num)
        var date1 = ''
        if (this.formInline.startDate) {
          date1 = moment(this.formInline.startDate).format('YYYY-MM-DD HH:mm:ss')
        }
        var date2 = ''
        if (this.formInline.endDate) {
          date2 = moment(this.formInline.endDate).format('YYYY-MM-DD HH:mm:ss')
        }

        var dateRangeString = date1 + ' - ' + date2
        var content = {
          sequnce_num: parseInt(this.formInline.num),
          sequnce_title: dateRangeString,
          content: this.formInline.topic,
          teacher: this.formInline.teacher
        }
        this.tableData.push(content)
        this.formInline.num = ''
        this.formInline.topic = ''
        this.formInline.startDate = ''
        this.formInline.endDate = ''
        this.formInline.teacher = ''
        this.dialogFormVisible = false
        console.log(this.tableData)
      },
      deleteContent: function (num) {
        this.tableData = _.remove(this.tableData, function (_item) {
          return _item.sequnce_num !== num
        })
      },
      editContent: function (num) {
        for (var i in this.tableData) {
          if (this.tableData[i].sequnce_num === num) {
            this.dialogFormVisible = true
            this.formInline.num = this.tableData[i].sequnce_num
            this.formInline.teacher = this.tableData[i].teacher
            this.formInline.topic = this.tableData[i].content
            this.formInline.startDate = this.tableData[i].sequnce_title.split(' - ')[0]
            this.formInline.endDate = this.tableData[i].sequnce_title.split(' - ')[1]
          }
        }
      },
      add: function () {
        this.titleErrMsg = ''
        this.teacherErrMsg = ''
        this.countErrMsg = ''
        this.startDateErrMsg = ''
        this.endDateErrMsg = ''
        this.introErrMsg = ''
        this.locationErrMsg = ''
        this.contentErrMsg = ''

        if (!this.form.title) {
          this.titleErrMsg = '课程名称不能为空'
        }
        if (!this.form.teacher) {
          this.teacherErrMsg = '讲师不能为空'
        }
        if (this.form.count < 1) {
          this.countErrMsg = '人数限制不能为空'
        }
        if (!this.form.startDate) {
          this.startDateErrMsg = '开始时间不能为空'
        }
        if (!this.form.endDate) {
          this.endDateErrMsg = '结束时间不能为空'
        }
        if (!this.form.intro) {
          this.introErrMsg = '课程简介不能为空'
        }
        if (!this.form.location) {
          this.locationErrMsg = '地址不能为空'
        }
        if (this.tableData.length === 0) {
          this.contentErrMsg = '培训内容不能为空'
        }
        if (this.titleErrMsg !== '' || this.teacherErrMsg !== '' || this.countErrMsg !== '' || this.startDateErrMsg !== '' || this.endDateErrMsg !== '' || this.introErrMsg !== '' || this.locationErrMsg !== '' || this.contentErrMsg !== '') {
          return
        }
        this.showloading = true
        api.post(api.uri.addEnrollment, {
          title: this.form.title,
          intro: this.form.intro,
          icon: this.iconSrc,
          pic: this.bannerSrc,
          teacher: this.form.teacher,
          count: parseInt(this.form.count),
          start_date: this.form.startDate,
          end_date: this.form.endDate,
          location: this.form.location,
          contentList: this.tableData
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
            router.push({name: 'registrationTrainlist'})
          }
        })
      },
      setDefaultImage: function (number) {
        if (number === 1) {
          this.iconSrc = api.image.enrollment.icon
        } else {
          this.bannerSrc = api.image.enrollment.banner
        }
      },
      // 1: 上传logo; 2: 上传banner
      uploadImage: function (number) {
        if (number === 1) {
          // this.uploadUrl = axios.defaults.baseURL + this.uploadIconUrl
          // this.params.pictype = 'logo'
          this.showIcon = !this.showIcon
        } else {
          // this.uploadUrl = axios.defaults.baseURL + this.uploadBannerUrl
          // this.params.pictype = 'banner'
          this.showBanner = !this.showBanner
        }
      },
      closeIcon: function (value) {
        this.showIcon = value
      },
      closeBanner: function (value) {
        this.showBanner = value
      },
      // cropIconSuccess (data, field) {

      // },
      // cropIconUploadSuccess (jsonData, field) {
      //   this.iconSrc = jsonData.result
      // },
      cropIconUploadFail (status, field) {

      },
      // cropBannerSuccess (data, field) {

      // },
      // cropBannerUploadSuccess (jsonData, field) {
      //   this.bannerSrc = jsonData.result
      // },
      cropIconSuccess (data, field) {
        // this.iconData = data
        this.logoFormData.key = this.logoFormData.key + api.guid() + '.png'
      },
      cropBannerSuccess (data, field) {
        // this.bannerData = data
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
      cropBannerUploadFail (status, field) {

      }
    }
  }
</script>

<style scoped="scope">
    .el-dialog__body {
        padding: 10px 20px;
    }

    .el-button--small {
        font-size: 12px;
        color: #00b553;
    }

    .el-button--small:hover, .el-button--small:active, .el-button--small:focus {
        font-size: 12px;
        color: #1DB513;
    }

    .add_topic li span {
        /*width:100%;*/
        /*padding:10px 0;*/
        /*background: #ededed;*/
        font-size: 12px;
        color: #666;
    }

    .add_topic li i {
        /*width:100%;*/
        /*padding:10px 0;*/
        /*background: #ededed;*/
        font-size: 16px;
        color: #666;
        margin-left: 10px;
        cursor: pointer;
    }

    .add_topic li i:hover {
        color: #f93d1f;
    }

    .el-icon-loading {
        color: #fff;
    }

    .export_bor {
        border: none;
        color: #fff;
    }

    .export_bor:hover, .export_bor:active {
        color: #fff;
    }

    .download {
        line-height: 38px;
        display: inline-block;
    }


    /*文字错误提示*/
    .error_font {
        color: #ff4949;
        font-size: 12px;
        margin-left: 10px;
    }
</style>
<style>
  .el-dialog__headerbtn .el-dialog__close:hover{
    color: #55b761;
  }
  .el-dialog__headerbtn .el-dialog__close:active{
    color: #55b761;
  }
</style>
