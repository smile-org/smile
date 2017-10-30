<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="exam" menu="list"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">编辑考试</span> > <span
                            class="f_blue">考试信息管理</span></span>
                </nav>
                <div class="con_tab">

                    <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
                        <el-form-item label="考试编号">
                            <el-input v-model="formInline.exam_num" placeholder="考试编号" ></el-input>
                        </el-form-item>
                        <el-form-item label="考试名称">
                            <el-input v-model="formInline.exam_title" placeholder="考试名称"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员">
                            <el-input v-model="formInline.manager_idName" placeholder="管理员"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.start_date"
                                                style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.end_date"
                                                style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item>
                            <button type="button" class="line-btn ml20" v-on:click="queryExamList()">查  询</button>
                        </el-form-item>
                    </el-form>
                    <div class="fr hidden mb20">
                        <button type="button" v-on:click="addExam" class="inf_btn mr20" icon="search">添加考试</button>
                        <el-button type="button" v-on:click="exportExamList()" :loading="showloading" class="inf_btn ml20 export_bor">导  出</el-button>
                        <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">

                            <div class="tc">
                                <!--<p class="exal">电子表格文件生成成功</p>-->
                                <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                            </div>
                            <div class="tc">
                                <a v-bind:href="excelUrl" v-on:click="dialogTableVisible = false" class="inf_btn download" style="display: inline-block;">下  载</a>
                                <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消</button>
                            </div>
                        </el-dialog>
                    </div>
                   <hr class="hr_line">
                    <el-table :data="tableData" border style="width: 100%">
                        <el-table-column prop="exam_num" align="center" label="考试编号" width="180">
                        </el-table-column>
                        <el-table-column prop="exam_title" align="center" label="考试名称" width="200">
                        </el-table-column>
                        <el-table-column prop="manager_idName" align="center" label="管理员" width="180">
                        </el-table-column>
                        <el-table-column prop="start_date" align="center" label="开始日期" width="180">
                            <template scope="scope" >
                                <span >{{scope.row.start_date | formatDate}} </span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="end_date | formatDate" label="结束日期" width="180">
                            <template scope="scope" >
                                <span >{{scope.row.end_date | formatDate}} </span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="tries_limit" label="次数限制" width="">
                        </el-table-column>
                        <el-table-column align="center" prop="pass_score" label="通过分数" width="100">
                        </el-table-column>
                        <el-table-column align="center" prop="time_limit" label="时间限制" width="100">
                        </el-table-column>
                        <el-table-column align="center" label="操作" class="tc" width="100">
                            <template scope="scope">
                                <el-button @click="editExam(scope.row.exam_id)" type="text" size="small">编辑</el-button>
                                <el-button @click="deleteExam(scope.row.exam_id)" class="red_font" type="text" size="small">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="tc mt20">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                                       :page-size="take" layout="total, prev, pager, next"
                                       :total="total"></el-pagination>
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
  import router from '../../router'
  export default {
    data: function () {
      return {
        data: [],
        take: 10,
        currentPage: 1,
        total: 0,
        formInline: {
          exam_num: '',
          exam_title: '',
          manager_idName: '',
          start_date: '',
          end_date: ''
        },
        tableData: [],
        showloading: false,
        excelUrl: '',
        dialogTableVisible: false
      }
    },
    components: {
      commonHeader,
      navigator
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD')
      }
    },
    created () {
      this.queryExamList()
    },
    methods: {
      addExam: function () {
        router.push({name: 'examCreate'})
      },
      queryExamList: function () {
        var date1 = ''
        if (this.formInline.start_date) {
          date1 = moment(this.formInline.start_date).format('YYYY-MM-DD')
        }

        var date2 = ''
        if (this.formInline.end_date) {
          date2 = moment(this.formInline.end_date).format('YYYY-MM-DD')
        }

        api.fetch(api.uri.getExamList, {
          num: this.formInline.exam_num,
          title: this.formInline.exam_title,
          manager: this.formInline.manager_idName,
          start: date1,
          end: date2,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryExamList()
      },
      exportExamList: function () {
        this.showloading = true
        var date1 = ''
        if (this.formInline.start_date) {
          date1 = moment(this.formInline.start_date).format('YYYY-MM-DD')
        }

        var date2 = ''
        if (this.formInline.end_date) {
          date2 = moment(this.formInline.end_date).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportExamList, {
          num: this.formInline.exam_num,
          title: this.formInline.exam_title,
          manager: this.formInline.manager_idName,
          start: date1,
          end: date2
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.excelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      editExam: function (examId) {
        router.push({name: 'examEdit', query: {id: examId}})
      },
      deleteExam: function (examId) {
        console.log(examId)
        this.$confirm('此操作将删除该考试，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteExam, {examid: examId}).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.total = data.total
              this.tableData = data.result
            }
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

    /*导出样式*/
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

    /*导出样式end*/
</style>
