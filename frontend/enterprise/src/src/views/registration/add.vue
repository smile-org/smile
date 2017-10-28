<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="registration"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 :<span class="">报名管理</span> > <span class=""> 培训报名管理</span> > <span
                            class="f_blue">添加报名</span></span>
                </nav>
                <div class="con_tab">
                    <el-form ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden"
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
                                    <el-date-picker type="date" placeholder="选择日期" v-model="form.startDate"
                                                    style="width: 100%;"></el-date-picker>
                                    <div class="el-form-item__error">{{startDateErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="结束时间">
                                    <el-date-picker type="date" placeholder="选择日期" v-model="form.endDate"
                                                    style="width: 100%;"></el-date-picker>
                                    <div class="el-form-item__error">{{endDateErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="课程简介">
                                    <el-input v-model="form.intro" placeholder="课程简介"></el-input>
                                    <div class="el-form-item__error">{{introErrMsg}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-col :span="8">
                            <el-form-item label="地址">
                                <el-input v-model="form.location" placeholder="地址"></el-input>
                                <div class="el-form-item__error">{{locationErrMsg}}</div>
                            </el-form-item>
                        </el-col>

                    </el-form>
                    <table class="page_m mt30" cellspacing="0" cellpadding="0" border="0">
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
                    <div class="mt30 ">
                        <p class="pos_re">培训内容   <span class="error_font  ml20" style="">{{contentErrMsg}}</span></p>

                        <template class="hidden">
                            <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
                                <!--<el-col :span="12">-->
                                <el-col :span="12">
                                    <el-form-item class="mb10" label="序号">
                                        <!--<el-input v-model="formInline.num" placeholder="序号"></el-input>-->
                                        <el-input-number v-model="formInline.num" :min="1" label="序号"></el-input-number>
                                    </el-form-item>

                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="起止时间">
                                        <el-date-picker v-model="formInline.dateRange" type="datetimerange"
                                                        placeholder="选择时间范围"></el-date-picker>
                                        <div class="el-form-item__error">{{dateRangeErrMsg}}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item label="主题">
                                        <el-input v-model="formInline.topic" placeholder="主题"
                                                  style="width:400px;"></el-input>
                                        <el-button :plain="true" type="success" class="ml20" size="primary"
                                                   v-on:click="addContent()">添加主题

                                        </el-button>
                                        <div class="el-form-item__error mb20">{{topicErrMsg}}</div>

                                    </el-form-item>
                                </el-col>
                            </el-form>
                        </template>
                        <template>
                            <el-table :data="tableData" class="mt20" border style="width: 100%">
                                <el-table-column prop="sequnce_num" label="序号" width="100"></el-table-column>
                                <el-table-column prop="content" label="主题" width=""></el-table-column>
                                <el-table-column prop="sequnce_title" label="起止时间"></el-table-column>
                                <el-table-column prop="" label="操作" width="100">
                                    <template scope="scope">
                                        <el-button @click="deleteContent(scope.row.num)" type="text" size="small">删除

                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                    </div>
                    <div class="tc btn_margin">
                        <el-button type="button" v-on:click="add()" :loading="showloading"
                                   class="inf_btn ml20 export_bor">保  存
                        </el-button>
                    </div>
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
          endDate: ''
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
        showloading: false
      }
    },
    components: {
      commonHeader,
      navigator
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
          if (this.tableData[i].num === this.formInline.num) {
            this.numErrMsg = '序号不能重复'
          }
        }
        if (!this.formInline.topic) {
          this.topicErrMsg = '主题不能为空'
        }
        if (!this.formInline.dateRange) {
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
          return _item.num !== num
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
          if (data.status === 1) {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.form.title = ''
            this.form.teacher = ''
            this.form.count = 1
            this.form.startDate = ''
            this.form.endDate = ''
            this.form.intro = ''
            this.form.location = ''
            this.tableData = []
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
      uploadImage: function (number) {
        alert('ddd')
      }
    }
  }
</script>

<style scoped="scope">
    .el-dialog__body {
        padding: 10px 20px;
    }

    .el-button--small {
        font-size: 14px;
        color: #00b553;
    }

    .el-button--small:hover, .el-button--small:active, .el-button--small:focus {
        font-size: 14px;
        color: #1DB513;
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

    .qx_btn {
        min-width: 120px;
        height: 38px;
        text-align: center;
        color: #fff;
        background: #a4a4a4;
        border-radius: 4px;
        font-size: 16px;
        letter-spacing: 2px;
        cursor: pointer;
        padding: 0 20px;
    }

    .qx_btn:hover, .qx_btn:active, .qx_btn:focus {
        color: #fff;
        background: #c3c3c3;
        outline: none;
    }

    /*文字错误提示*/
    .error_font {
        color: #ff4949;
        font-size: 12px;
        margin-left: 10px;
    }
</style>
