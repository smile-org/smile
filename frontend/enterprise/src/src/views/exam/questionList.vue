<template>
  <div>
    <common-header type="exam"></common-header>
    <div class="con_main">
      <navigator module="exam" menu="question"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="f_blue">题库信息管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form :inline="true" :model="formInLine" class="demo-form-inline hidden" label-width="80px">
            <!--<el-row>-->
            <el-form-item label="题目">
              <el-input v-model="formInLine.title" placeholder="题目"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-select v-model="value" placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInLine.start"
                                style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInLine.end"
                                style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="fr">
              <button type="button" class="line-btn " v-on:click="queryQuestionList()">查  询</button>
            </el-form-item>
            <!--</el-row>-->
          </el-form>
          <div class="tr mb20 dc_width">
            <button type="button" v-on:click="addQuestion()" class="inf_btn mr15">添加试题</button>
            <button type="button" class="inf_btn mr15" v-on:click="showUploadDialog()">试题导入</button>
            <a v-bind:href="templateExcelUrl" class="inf_btn mr15 vm dis_in_block">下载导入模板</a>
            <button type="button" v-on:click="exportQuestionList()" :loading="showloading"
                    class="inf_btn vm export_bor">导  出
            </button>
            <el-dialog class="tl" title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
              <div class="tc">
                <!--<p class="exal">电子表格文件生成成功</p>-->
                <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
              </div>
              <div class="tc">
                <a v-bind:href="excelUrl" v-on:click="dialogTableVisible = false"
                   class="inf_btn download" style="display: inline-block;">下  载</a>
                <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消

                </button>
              </div>
            </el-dialog>
            <el-dialog title="试题导入" class="tl" :visible.sync="dialogUploadVisible">
              <el-upload class="upload-demo"
                         ref="uploadContent"
                         :action="uploadContentAction"
                         :on-success="onContentSuccess"
                         :before-upload="beforeContentUpload"
                         :auto-upload="true"
                         :headers="headers">
                <button slot="trigger" size="small" class="inf_btn2" type="primary">点击上传</button>
                <div slot="tip" class="el-upload__tip">支持类型xlsx，大小不超过100M</div>
              </el-upload>
            </el-dialog>
          </div>
          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column align="center" prop="title" label="试题题目" width="">
            </el-table-column>
            <el-table-column align="center" prop="type_id" label="试题类型" width="">
              <template scope="scope">
                <span v-if="scope.row.type_id === 1">单选题</span>
                <span v-else-if="scope.row.type_id === 2">多选题</span>
                <span v-else>是非题</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="created_at" label="创建时间" width="">
              <template scope="scope">
                <span>{{scope.row.created_at | formatDate}} </span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" class="tc" width="">
              <template scope="scope">
                <el-button type="text" size="small" @click="editQuestion(scope.row.question_id)">编辑</el-button>
                <el-button type="text" class="red_font" size="small" @click="deleteQuestion(scope.row.question_id)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="tc mt20">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                           :page-size="take"
                           layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import router from '../../router'
  import axios from 'axios'
  import moment from 'moment'
  export default {
    data: function () {
      return {
        templateExcelUrl: axios.defaults.fileServer + '/import/template/Question.xlsx',
        tableData: [],
        take: 10,
        currentPage: 1,
        total: 0,
        formInLine: {
          title: '',
          // createdat: ''
          start: '',
          end: ''
        },
        options: [{
          value: '1',
          label: '单选题'
        }, {
          value: '2',
          label: '多选题'
        }, {
          value: '3',
          label: '是非题'
        }],
        value: '',
        excelUrl: '',
        dialogTableVisible: false,
        showloading: false,
        dialogUploadVisible: false,
        uploadContentAction: api.uri.uploadQuestionExcel,
        headers: {}
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.queryQuestionList()
      this.headers = api.getUploadHeaders()
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    methods: {
      addQuestion: function () {
        router.push({name: 'examQuestionCreate'})
      },
      queryQuestionList: function () {
        // console.log(this.formInLine.title, this.formInLine.createdat, this.value)
        var typeId = 0
        if (this.value !== '') {
          typeId = parseInt(this.value)
        }
        // var date = ''
        // if (this.formInLine.createdat) {
        //   date = moment(this.formInLine.createdat).format('YYYY-MM-DD')
        // }
        var start = ''
        var end = ''
        if (this.formInLine.start) {
          start = moment(this.formInLine.start).format('YYYY-MM-DD')
        }
        if (this.formInLine.end) {
          end = moment(this.formInLine.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getQuestionList, {
          title: this.formInLine.title,
          typeid: typeId,
          // createdat: date,
          skip: (this.currentPage - 1) * this.take,
          take: this.take,
          start: start,
          end: end
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryQuestionList()
      },
      exportQuestionList: function () {
        this.showloading = true
        var typeId = 0
        if (this.value !== '') {
          typeId = parseInt(this.value)
        }
        // var date = ''
        // if (this.formInLine.createdat) {
        //   date = moment(this.formInLine.createdat).format('YYYY-MM-DD')
        // }
        var start = ''
        var end = ''
        if (this.formInLine.start) {
          start = moment(this.formInLine.start).format('YYYY-MM-DD')
        }
        if (this.formInLine.end) {
          end = moment(this.formInLine.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportQuestionList, {
          title: this.formInLine.title,
          typeid: typeId,
          // createdat: date
          start: start,
          end: end
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.excelUrl = axios.defaults.fileServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      editQuestion: function (id) {
        router.push({name: 'examQuestionEdit', query: {id: id}})
      },
      deleteQuestion: function (id) {
        this.$confirm('此操作将删除该试题，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteQuestion, {
            questionid: id
          }).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              console.log(data.result)
              this.queryQuestionList()
            } else {
              this.$message({
                type: 'info',
                message: data.result
              })
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      showUploadDialog () {
        this.dialogUploadVisible = true
      },
      onContentSuccess (response, file) {
        this.dialogUploadVisible = false
      },
      beforeContentUpload (file) {
        if (file.name.indexOf('.xlsx') < 0) {
          this.$message({
            type: 'info',
            message: '请上传excel文件！'
          })
          return false
        }
        if (file.size > 100 * 1024 * 1024) {
          this.$message({
            type: 'info',
            message: '附件不能大于100M'
          })
          return false
        }
      }
    }
  }
</script>

<style scoped="scope">
  .export_bor i {
    color: #fff !important;
  }

  .export_bor {
    border: none;
    color: #fff;
  }

  .export_bor:hover, .export_bor:active, .export_bor:focus {
    color: #fff;
  }

  .el-icon-loading {
    color: #fff;
  }

  .el-icon-loading {
    animation: rotating 1s linear infinite;
    color: #fff;
  }

  .el-select .el-input__inner:hover, .el-select .el-input__inner:active, .el-select .el-input__inner:focus {
    border-color: #01b554;
  }

  /*导出样式*/
  .download {
    line-height: 38px;
    display: inline-block;
  }

  /*导出样式end*/
</style>
