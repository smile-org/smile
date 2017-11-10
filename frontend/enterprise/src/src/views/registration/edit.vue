<template>
  <div>
    <common-header type="registration"></common-header>
    <div class="con_main">
      <navigator module="registration" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 :<span class="">报名管理</span> > <span class=""> 培训报名管理</span> > <span
                  class="f_blue">编辑报名</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden add_width"
                   label-width="80px">
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
            <el-col :span="24">
              <el-form-item label="课程简介">
                <el-input v-model="form.intro" placeholder="课程简介" style="min-width: 545px;"></el-input>
                <div class="el-form-item__error">{{introErrMsg}}</div>
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
            <p class="pos_re">培训内容   <span class="error_font  ml20" style="">{{contentErrMsg}}</span></p>
            <template class="hidden">
              <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
                <!--<el-col :span="12">-->
                <el-col :span="12">
                  <el-form-item class="mb10" label="序号">
                    <!--<el-input v-model="formInline.num" placeholder="序号"></el-input>-->
                    <el-input-number v-model="formInline.num" :min="1" label="序号"></el-input-number>
                    <div class="el-form-item__error"> {{numErrMsg}}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="起止时间">
                    <el-date-picker v-model="formInline.dateRange" type="datetimerange" placeholder="选择时间范围"></el-date-picker>
                    <div class="el-form-item__error">{{dateRangeErrMsg}}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="主题">
                    <el-input v-model="formInline.topic" placeholder="主题"
                              style="width:400px;"></el-input>
                    <el-button :plain="true" type="success" class="ml20" size="primary" v-on:click="addContent()">添加主题</el-button>
                    <div class="el-form-item__error mb20">{{topicErrMsg}}</div>
                  </el-form-item>
                </el-col>
              </el-form>
            </template>
            <template>
              <el-table :data="tableData" class="mt20" border style="width: 100%">
                <el-table-column prop="sequnce_num" align="center" label="序号" width="100"></el-table-column>
                <el-table-column prop="content"  align="center" label="主题" width=""></el-table-column>
                <el-table-column prop="sequnce_title"  align="center" label="起止时间"></el-table-column>
                <el-table-column prop="" label="操作"  align="center" width="100">
                  <template scope="scope">
                    <el-button @click="deleteContent(scope.row.sequnce_num)" class="red_font" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <div class="tc btn_margin">
            <button type="button" v-on:click="add()" :loading="showloading" class="inf_btn  mb20">保  存</button>
          </div>
          <my-upload
            @input="closeIcon"
            field="file"
            :params="params"
            @crop-success="cropIconSuccess"
            @crop-upload-success="cropIconUploadSuccess"
            @crop-upload-fail="cropIconUploadFail"
            :url="uploadUrl"
            :width="280"
            :headers="headers"
            :height="194"
            :value.sync="showIcon"
            :no-circle=true
            img-format="png">
          </my-upload>

          <my-upload
            @input="closeBanner"
            field="file"
            :params="params"
            @crop-success="cropBannerSuccess"
            @crop-upload-success="cropBannerUploadSuccess"
            @crop-upload-fail="cropBannerUploadFail"
            :url="uploadUrl"
            :width="375"
            :headers="headers"
            :height="120"
            :value.sync="showBanner"
            :no-circle=true
            img-format="png">
          </my-upload>
        </div>
      </section>
    </div>
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
  export default {
    data: function () {
      return {
        formInline: {
          num: '',
          dateRange: '',
          topic: ''
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
        period_id: '',
        enrollment_id: '',

        showIcon: false,
        showBanner: false,
        headers: {},
        uploadIconUrl: api.uri.uploadEnrollmentIcon,
        uploadBannerUrl: api.uri.uploadEnrollmentBanner,
        uploadUrl: '',
        params: {
          pictype: ''
        }
      }
    },
    components: {
      commonHeader,
      navigator,
      myUpload
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD')
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
      this.period_id = parseInt(this.$route.query.period_id)
      api.fetch(api.uri.getEnrollmentEditPageInfo, {
        periodid: this.period_id
      }).then(data => {
        if (data.status === 1) {
          console.log(data)
          this.iconSrc = data.result.EnrollmentToBeEidt.icon
          this.bannerSrc = data.result.EnrollmentToBeEidt.pic
          this.form.title = data.result.EnrollmentToBeEidt.title
          this.form.intro = data.result.EnrollmentToBeEidt.intro
          this.form.teacher = data.result.EnrollmentPeriodToBeEdit.teacher
          this.form.count = data.result.EnrollmentPeriodToBeEdit.count
          this.form.location = data.result.EnrollmentPeriodToBeEdit.location
          this.form.startDate = new Date(data.result.EnrollmentPeriodToBeEdit.start_date)
          this.form.endDate = new Date(data.result.EnrollmentPeriodToBeEdit.end_date)
          this.tableData = data.result.ContentList
          this.enrollment_id = data.result.EnrollmentToBeEidt.enrollment_id
        }
      })
    },
    methods: {
      addContent: function () {
        this.numErrMsg = ''
        this.topicErrMsg = ''
        this.dateRangeErrMsg = ''
        if (!this.formInline.num) {
          this.numErrMsg = '序号不能为空'
        }
        for (var i in this.tableData) {
          if (this.tableData[i].sequnce_num === this.formInline.num) {
            this.numErrMsg = '序号不能重复'
          }
        }
        if (!this.formInline.topic) {
          this.topicErrMsg = '主题不能为空'
        }
        if (!this.formInline.dateRange[0]) {
          this.dateRangeErrMsg = '日期范围不能为空'
        }
        if (this.numErrMsg !== '' || this.topicErrMsg !== '' || this.dateRangeErrMsg !== '') {
          return
        }
        var date1 = ''
        if (this.formInline.dateRange[0]) {
          date1 = moment(this.formInline.dateRange[0]).format('YYYY-MM-DD HH:mm:ss')
        }
        var date2 = ''
        if (this.formInline.dateRange[1]) {
          date2 = moment(this.formInline.dateRange[1]).format('YYYY-MM-DD HH:mm:ss')
        }

        var dateRangeString = date1 + ' - ' + date2
        var content = {
          content_id: 0,
          sequnce_num: parseInt(this.formInline.num),
          sequnce_title: dateRangeString,
          content: this.formInline.topic
        }
        this.tableData.push(content)
        this.formInline.num = ''
        this.formInline.topic = ''
        this.formInline.dateRange = ''
        console.log(this.tableData)
      },
      deleteContent: function (num) {
        this.tableData = _.remove(this.tableData, function (_item) {
          return _item.sequnce_num !== num
        })
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
        api.post(api.uri.updateEnrollment, {
          enrollmentid: parseInt(this.enrollment_id),
          periodid: parseInt(this.period_id),
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
          if (data.status === 1) {
            router.push({name: 'registrationTrainlist'})
//            this.form.title = ''
//            this.form.teacher = ''
//            this.form.count = 1
//            this.form.startDate = ''
//            this.form.endDate = ''
//            this.form.intro = ''
//            this.form.location = ''
//            this.tableData = []
            this.showloading = false
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
          this.uploadUrl = axios.defaults.baseURL + this.uploadIconUrl
          this.params.pictype = 'logo'
          this.showIcon = !this.showIcon
        } else {
          this.uploadUrl = axios.defaults.baseURL + this.uploadBannerUrl
          this.params.pictype = 'banner'
          this.showBanner = !this.showBanner
        }
      },
      closeIcon: function (value) {
        this.showIcon = value
      },
      closeBanner: function (value) {
        this.showBanner = value
      },
      cropIconSuccess (data, field) {

      },
      cropIconUploadSuccess (jsonData, field) {
        this.iconSrc = jsonData.result
      },
      cropIconUploadFail (status, field) {

      },
      cropBannerSuccess (data, field) {

      },
      cropBannerUploadSuccess (jsonData, field) {
        this.bannerSrc = jsonData.result
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
  .add_topic li span {
    /*width:100%;*/
    /*padding:10px 0;*/
    /*background: #ededed;*/
    font-size: 14px;
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
