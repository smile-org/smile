<template>
  <div>
    <common-header type="exam"></common-header>
    <div class="con_main">
      <navigator module="exam" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">考试信息管理</span> > <span class="f_blue">添加考试</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :rules="formRules" :inline="true" :model="form" class="demo-form-inline mt20 hidden add_width" label-width="80px">
            <el-col :span="8">
              <el-form-item label="考试名称" prop="name">
                <el-input v-model="form.name" placeholder="考试名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="次数限制" prop="times">
                <el-input-number v-model="form.times" :min="1" label="次数限制"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="责任人" prop="admin">
                <el-select  class="dateTab_width" v-model="form.admin" placeholder="请选择责任人">
                  <el-option v-for="item in adminList" :key="item.user_id" :label="item.full_name" :value="item.user_id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="时间限制" prop="minutes" >
                <el-input-number v-model="form.minutes" :min="1" label="通过分数"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开始时间" prop="dateStart">
                <el-col>
                  <el-date-picker class="dateTab_width" type="date" :picker-options="pickerOptions0" @change="changeDateStart" placeholder="选择日期" v-model="form.dateStart" style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="结束时间" prop="dateEnd">
                <el-col>
                  <el-date-picker class="dateTab_width" type="date" :picker-options="pickerOptions0" @change="changeDateEnd" placeholder="选择日期" v-model="form.dateEnd" style="width: 100%;">
                  </el-date-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="单选分值" prop="single">
                <el-input-number v-model="form.single" @change="changeSingle" :min="1" label="单选分值"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="多选分值" prop="multi">
                <el-input-number v-model="form.multi" @change="changeMulti" :min="1" label="多选分值"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="是非分值" prop="truefalse">
                <el-input-number v-model="form.truefalse" @change="changeTruefalse" :min="1" label="是非分值"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="总分">
                <el-input v-model="form.totalScore" placeholder="总分" ></el-input>
                <!--{{form.totalScore}}-->
              </el-form-item>
            </el-col>
            <el-col :span="8" >
              <el-form-item label="通过分数" prop="score">
                <el-input-number v-model="form.score" :min="1" label="通过分数"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="考试简介" prop="intro"  class="input_textarea">
                <el-input  type="textarea" v-model="form.intro" placeholder="考试简介" style="min-width: 545px;"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
          <hr class="hr_line">
          <table class="page_m mb30 " cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                课程图标
              </td>
              <td class="page_m_b">
                <img :src="iconSrc | formatImage" width="20%"/>
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
                <img :src="bannerSrc | formatImage" width="100%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(2)">使用默认</a>
                <a v-on:click="toggleShow(2)">上传</a>
              </td>
            </tr>
          </table>
          <hr class="hr_line">
          <div>
            <p>复习资料
              <el-button type="text" @click="dialogMaterialVisible = true">添加复习资料</el-button>
            </p>
            <template>
              <el-dialog title="添加复习资料" :visible.sync="dialogMaterialVisible">
                <el-form :inline="true" :model="formMaterial" class="demo-form-inline mt20">
                  <el-form-item label="课程名称">
                    <el-input v-model="formMaterial.name"  placeholder="课程名称"></el-input>
                  </el-form-item>
                  <el-form-item label="开始时间">
                    <el-date-picker class="dateTab_width" type="date" placeholder="开始时间"
                      v-model="formMaterial.start" style="width: 100%;"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="结束时间">
                      <el-date-picker class="dateTab_width" type="date" placeholder="结束时间"
                      v-model="formMaterial.end" style="width: 100%;"></el-date-picker>
                  </el-form-item>
                  <el-form-item class="wrapper">
                    <el-button class="update_btn" @click="searchMaterial">查询</el-button>
                  </el-form-item>
                </el-form>
                <el-table :data="materialList" border ref="materialListChecked" @selection-change="materialListCheckedChange">
                  <el-table-column property="id" align="center" width="100" type="selection" @selection-change="materialListCheckedChange">
                  </el-table-column>
                  <el-table-column prop="name" align="center" label="课程名称" width=""></el-table-column>
                </el-table>
                <el-pagination class="tc mt20" small @current-change="handleMaterialCurrentChange" :current-page="materialCurrentPage"
                    :page-size="materialTake" layout="total, prev, pager, next"
                    :total="materialTotal"></el-pagination>
                <div class="tc">
                  <button type="button" class="inf_btn mt30 mb20" v-on:click="saveMaterial">保  存
                  </button>
                </div>
              </el-dialog>
            </template>
          </div>
          <div class="mt20 mb30">
            <template>
              <el-table :data="materialSelected" border class="mt20" style="width: 100%">
                <el-table-column prop="name" align="center" label="课程名称" width="">
                </el-table-column>
                <el-table-column label="操作"  align="center"  class="tc" width="">
                  <template scope="scope">
                    <el-button @click="deleteSelectedMaterial(scope.row.id)" class="red_font" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <hr class="hr_line">
          <div>
            <p>
              试题
              <el-button type="text" @click="dialogExamVisible = true">添加试题</el-button>
            </p>
            <el-dialog title="添加试题" :visible.sync="dialogExamVisible">
              <el-form :inline="true" :model="formExam" class="demo-form-inline mt20">
                <!--<el-col :span="12">-->
                  <el-form-item label="试题名称">
                    <el-input v-model="formExam.name" placeholder="试题名称"></el-input>
                  </el-form-item>
                <!--</el-col>-->
                <!--<el-col :span="12">-->
                  <el-form-item label="开始时间">
                    <el-col>
                      <el-date-picker type="date" placeholder="选择日期" v-model="formExam.start" class="date_input" style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item label="结束时间">
                    <el-col>
                      <el-date-picker type="date" placeholder="选择日期" v-model="formExam.end" class="date_input" style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                <!--</el-col>-->
                <!--<el-col :span="4">-->
                  <el-form-item class="wrapper">
                  <el-button class="update_btn" @click="searchExam">查询</el-button>
                  </el-form-item>
                <!--</el-col>-->
              </el-form>
              <el-table :data="examList" border ref="examListChecked" @selection-change="examListCheckedChange">
                <el-table-column property="id" align="center" width="100" type="selection" @selection-change="examListCheckedChange">
                </el-table-column>
                <!--<el-table-column property="num" align="center" label="序号" width="80"></el-table-column>-->
                <el-table-column property="name" align="center" label="试题名称" width=""></el-table-column>
                <el-table-column property="type" align="center" label="试题类型" width="">
                  <template scope="scope">
                    {{scope.row.type | formatExamType}}
                  </template>
                </el-table-column>
                <el-table-column property="date" align="center" label="创建时间" width=""></el-table-column>
              </el-table>
              <el-pagination class="tc mt20" small @current-change="handleExamCurrentChange" :current-page="examCurrentPage"
                                       :page-size="examTake" layout="total, prev, pager, next"
                                       :total="examTotal"></el-pagination>

              <div class="tc">
                <button type="button" class="inf_btn mt30 mb20" v-on:click="saveExam">保  存
                </button>
              </div>
            </el-dialog>
            <template>
              <el-table :data="examSelected" border class="mt20" style="width: 100%">
                <el-table-column type="index" width="50">
                </el-table-column>
                <el-table-column  align="center"  prop="name" label="试题名称" width="">
                </el-table-column>
                <el-table-column   align="center"  label="操作" class="tc" width="">
                  <template scope="scope">
                    <el-button @click="deleteSelectedExam(scope.row.id)" class="red_font" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " v-on:click="save">保  存</button>
          </div>
          <my-upload @input="closeIcon" field="file" @crop-success="cropIconSuccess" @crop-upload-success="cropIconUploadSuccess" @crop-upload-fail="cropIconUploadFail" :url="uploadIconUrl" :width="190" :headers="headers" :height="144" :value.sync="showIcon" :no-circle=true img-format="png">
          </my-upload>

          <my-upload @input="closeBanner" field="file" @crop-success="cropBannerSuccess" @crop-upload-success="cropBannerUploadSuccess" @crop-upload-fail="cropBannerUploadFail" :url="uploadBannerUrl" :width="375" :headers="headers" :height="150" :value.sync="showBanner" :no-circle=true img-format="png">
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
  import axios from 'axios'
  import myUpload from 'vue-image-crop-upload'
  import _ from 'lodash'
  import moment from 'moment'
  import lang from 'vue-image-crop-upload/utils/language'
  import router from '../../router'
  export default {
    filters: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri
      },
      formatExamType: function (type) {
        if (type === 1) {
          return '单选题'
        } else if (type === 2) {
          return '多选题'
        } else {
          return '是非题'
        }
      }
    },
    data: function () {
      return {
        pickerOptions0: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        uploadIconUrl: api.uri.uploadExamIcon,
        uploadBannerUrl: api.uri.uploadExamBanner,
        // 是否显示icon上传
        showIcon: false,
        // 是否显示banner 上传
        showBanner: false,
        headers: {},

        // 基本信息
        form: {
          name: '',
          // 次数限制
          times: 10,
          // 通过分数
          score: 0,
          // 责任人
          admin: '',
          // 时间限制
          minutes: 60,
          dateStart: '',
          dateEnd: '',
          intro: '',
          single: 0,
          multi: 0,
          truefalse: 0,
          totalScore: 0
        },
        formRules: {
          name: [
            { required: true, message: '请输入考试名称', trigger: 'blur' }
          ],
          times: [
            { type: 'number', required: true, message: '请输入考试次数', trigger: 'blur' }
          ],
          score: [
            { type: 'number', required: true, message: '请输入分数', trigger: 'blur' }
          ],
          minutes: [
            { type: 'number', required: true, message: '请输入时间限制', trigger: 'blur' }
          ],
          admin: [
            { type: 'number', required: true, message: '请选择责任人', trigger: 'change' }
          ],
          dateStart: [
            { required: true, message: '请选择开始时间', trigger: 'change' }
          ],
          dateEnd: [
            { required: true, message: '请选择结束时间', trigger: 'change' }
          ],
          intro: [
            { required: true, message: '请添加考试简介', trigger: 'blur' }
          ]
        },
        // 复习资料
        formMaterial: {
          name: '',
          start: '',
          end: ''
        },
        // 复习资料查询列表 ---- id, name
        materialList: [],
        // 复习资料选择的列表 ---- id, name
        materialSelected: [],
        // 选中的
        materialListChecked: [],

        // 试题
        formExam: {
          name: '',
          // date: ''
          start: '',
          end: ''
        },

        // 试题查询列表 --- num, name, type, date
        examList: [],
        // 试题选择列表  --- name
        examSelected: [],
        // 选中的
        examListChecked: [],

        // 责任人列表
        adminList: [],

        iconSrc: '',
        iconDefault: api.image.exam.icon,
        bannerSrc: '',
        bannerDefault: api.image.exam.banner,

        // pagination material
        materialTake: 10,
        materialCurrentPage: 1,
        materialTotal: 0,

        // pagination exam
        examTake: 10,
        examCurrentPage: 1,
        examTotal: 0,

        dialogMaterialVisible: false,
        dialogExamVisible: false
      }
    },
    components: {
      commonHeader,
      navigator,
      myUpload
    },
    created () {
      // 给默认图标
      this.iconSrc = this.iconDefault
      this.bannerSrc = this.bannerDefault
      this.headers = api.getUploadHeaders()
      lang.zh.preview = ''
      api.fetch(api.uri.getSelectList, { courseid: 0 }).then(data => {
        if (data.status === 1) {
          this.adminList = data.result.AdminList
        }
      })
    },
    methods: {
      materialListCheckedChange (val) {
        this.materialListChecked = val
      },
      examListCheckedChange (val) {
        this.examListChecked = val
      },
      changeDateStart (dateStr) {
        this.form.dateStart = dateStr
        if (this.form.dateEnd && this.form.dateEnd < dateStr) {
          this.form.dateEnd = dateStr
        }
      },
      changeDateEnd (dateStr) {
        this.form.dateEnd = dateStr
        if (this.form.dateStart && this.form.dateStart > dateStr) {
          this.form.dateStart = dateStr
        }
      },
      searchMaterial: function () {
        var start = ''
        var end = ''
        if (this.formMaterial.start) {
          start = moment(this.formMaterial.start).format('YYYY-MM-DD')
        }
        if (this.formMaterial.end) {
          end = moment(this.formMaterial.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.searchCourse, {
          title: this.formMaterial.name,
          priName: '',
          categoryid: 0,
          // pubdate: '',
          start: start,
          end: end,
          take: this.materialTake,
          skip: this.materialTake * (this.materialCurrentPage - 1)
        }).then(data => {
          if (data.status === 1) {
            var result = data.result
            this.materialList = []
            for (var i = 0; i < result.length; i++) {
              this.materialList.push({
                id: result[i].course_id,
                name: result[i].title
              })
            }
            this.materialTotal = data.total
          }
        })
      },
      handleMaterialCurrentChange: function (val) {
        this.materialCurrentPage = val
        this.searchMaterial()
      },
      saveMaterial: function () {
        for (var i = 0; i < this.materialListChecked.length; i++) {
          var spec = this.materialListChecked[i]
          if (!_.some(this.materialSelected, {id: spec.id})) {
            this.materialSelected.push({
              id: spec.id,
              name: spec.name
            })
          }
        }
        this.dialogMaterialVisible = false
      },
      deleteSelectedMaterial (id) {
        this.$confirm('此操作将删除该复习资料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.materialSelected = _.filter(this.materialSelected, function (item) {
            return item.id !== id
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      searchExam: function () {
        var start = ''
        var end = ''
        if (this.formExam.start) {
          start = moment(this.formExam.start).format('YYYY-MM-DD')
        }
        if (this.formExam.end) {
          end = moment(this.formExam.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getQuestionList, {
          title: this.formExam.name,
          typeid: 0,
          // createdat: this.formExam.date ? moment(this.formExam.date).format('YYYY-MM-DD') : '',
          take: this.examTake,
          skip: this.examTake * (this.examCurrentPage - 1),
          start: start,
          end: end
        }).then(data => {
          if (data.status === 1) {
            var result = data.result
            this.examList = []
            for (var i = 0; i < result.length; i++) {
              this.examList.push({
                id: result[i].question_id,
                name: result[i].title,
                type: result[i].type_id,
                date: moment(result[i].created_at).format('YYYY-MM-DD'),
                num: result[i].question_num
              })
            }
            this.examTotal = data.total
          }
        })
      },
      handleExamCurrentChange: function (val) {
        this.examCurrentPage = val
        this.searchExam()
      },
      saveExam: function () {
        for (var i = 0; i < this.examListChecked.length; i++) {
          var spec = this.examListChecked[i]
          if (!_.some(this.examSelected, {id: spec.id})) {
            this.examSelected.push({
              id: spec.id,
              name: spec.name,
              num: spec.num,
              type: spec.type
            })
          }
        }
        this.changeQuestionScore()
        this.dialogExamVisible = false
      },
      deleteSelectedExam (id) {
        this.$confirm('此操作将删除该试题, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.examSelected = _.filter(this.examSelected, function (item) {
            return item.id !== id
          })
          this.changeQuestionScore()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },

      // 保存考试
      save () {
        this.$refs['form'].validate((valid) => {
          console.log(this.form)
          if (valid) {
            var obj = {
              managerid: this.form.admin,
              title: this.form.name,
              start: this.form.dateStart,
              end: this.form.dateEnd,
              trieslimit: this.form.times,
              passscore: this.form.score,
              timelimit: this.form.minutes,
              intro: this.form.intro,
              icon: this.iconSrc,
              pic: this.bannerSrc,
              courseids: '',
              questionList: [],
              single_choice_score: this.form.single,
              multi_choice_score: this.form.multi,
              true_false_score: this.form.truefalse
            }
            var courseArray = []
            for (var i = 0; i < this.materialSelected.length; i++) {
              var spec = this.materialSelected[i]
              courseArray.push(spec.id)
            }
            obj.courseids = courseArray.join(',')
            for (var j = 0; j < this.examSelected.length; j++) {
              var spec1 = this.examSelected[j]
              obj.questionList.push({
                question_id: spec1.id,
                question_num: j + 1
              })
            }
            api.post(api.uri.addExam, obj).then(data => {
              if (data.status === 1) {
                router.push({name: 'examList'})
              }
            })
          } else {
            this.$message('invalid')
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
        // this.iconData = data
      },
      cropBannerSuccess (data, field) {
        // this.bannerData = data
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
          this.iconSrc = api.image.exam.icon
        } else {
          this.bannerSrc = api.image.exam.banner
        }
      },

      changeSingle (val) {
        this.form.single = val
        this.changeQuestionScore()
      },

      changeMulti (val) {
        this.form.multi = val
        this.changeQuestionScore()
      },

      changeTruefalse (val) {
        this.form.truefalse = val
        this.changeQuestionScore()
      },
      changeQuestionScore () {
        this.form.totalScore = 0
        for (var i = 0; i < this.examSelected.length; i++) {
          var current = this.examSelected[i]
          if (current.type === 1) {
            this.form.totalScore += this.form.single
          } else if (current.type === 2) {
            this.form.totalScore += this.form.multi
          } else {
            this.form.totalScore += this.form.truefalse
          }
        }
      }
    }
  }
</script>

<style scoped="scope">
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

  .update_btn:hover, .update_btn:active, .update_btn:focus {
    color: #fff;
    background-color: #00b553;
    border: 1px solid #00b553;
  }
/*考试新加*/
  .date_input .el-input{
    width:80%;
  }
  .el-dialog--small {
    width: 56%;
  }
</style>

