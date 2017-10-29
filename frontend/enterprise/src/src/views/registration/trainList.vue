<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="registration"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 : <span class="">报名管理</span> > <span class="f_blue">培训报名管理</span> </span>
                </nav>
                <div class="con_tab">
                    <el-form ref="form" :inline="true" :model="form" class="demo-form-inline" label-width="80px">
                        <el-form-item label="课程名称">
                            <el-input v-model="form.title" placeholder="课程名称"></el-input>
                        </el-form-item>
                        <el-form-item label="讲师" prop="region">
                            <el-input v-model="form.teacher" placeholder="讲师"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间" >
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="form.startDate" style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="form.endDate" style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item class="fr">
                          <button type="button" class="line-btn ml20" v-on:click="queryEnrollment">查  询</button>
                          <el-button type="button" v-on:click="exportEnrollment" :loading="showloading" class="inf_btn ml10  mr10 export_bor">导  出</el-button>
                          <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                            <div class="tc">
                              <!--<p class="exal">电子表格文件生成成功</p>-->
                              <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                            </div>
                            <div class="tc">
                              <a v-bind:href="excelUrl" class="inf_btn download" style="display: inline-block;">下  载</a>
                              <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消</button>
                            </div>
                          </el-dialog>
                          <button type="button" class="inf_btn " v-on:click="addEnrollment">添加报名</button>
                        </el-form-item>
                    </el-form>
                   <hr class="hr_line">
                    <!--添加报名导出表格-->
                    <el-table :data="tableData" border class="table_minheight" style="width: 100%">
                        <el-table-column prop="title"  align="center" label="课程名称" width="">
                        </el-table-column>
                        <el-table-column prop="teacher"  align="center" label="讲师" width="100">
                        </el-table-column>
                        <el-table-column prop="start"   align="center"label="开始时间" width="140">
                        </el-table-column>
                        <el-table-column prop="end"  align="center" label="结束时间" width="140">
                        </el-table-column>
                        <el-table-column prop="count"  align="center" label="人数限制" width="100">
                        </el-table-column>
                        <el-table-column prop="appraise"  width="100" align="center" label="查看评价" >
                            <template scope="scope">
                                <el-button v-on:click="enrollmentComments(scope.row)" type="text" size="small">查看评价
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="appraise"  align="center" label="再开一期" width="100">
                            <template scope="scope">
                                <el-button v-on:click="addPeriod(scope.row)" class="green_font" type="text" size="small">再开一期</el-button>
                            </template>
                        </el-table-column>

                        <el-table-column label="操作"  align="center" class="tc" width="100">
                            <template scope="scope">
                                <el-button @click="editEnrollment(scope.row)" type="text" size="small">编辑</el-button>
                                <el-button @click="deleteEnrollment(scope.row)" class="red_font" type="text" size="small">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="ds_oq_pageF">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        form: {
          title: '',
          teacher: '',
          startDate: '',
          endDate: ''
        },
        dialogTableVisible: false,
        tableData: [],
        take: 10,
        currentPage: 1,
        total: 0,
        showloading: false,
        excelUrl: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.queryEnrollment()
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    methods: {
      queryEnrollment: function () {
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.form.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.form.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getEnrollmentList, {
          title: this.form.title,
          teacher: this.form.teacher,
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
        this.queryEnrollment()
      },
      exportEnrollment: function () {
        this.showloading = true
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.form.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.form.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportEnrollmentList, {
          title: this.form.title,
          teacher: this.form.teacher,
          start: date1,
          end: date2
        }).then(data => {
          if (data.status === 1) {
            this.excelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      addEnrollment: function () {
        router.push({name: 'registrationAdd'})
      },
      enrollmentComments: function (row) {
        router.push({name: 'registrationComment', query: {id: row.enrollment_id}})
      },
      addPeriod: function (row) {
        router.push({
          name: 'registrationAddPeriod',
          query: {period_id: row.period_id}
        })
      },
      editEnrollment: function (row) {
        router.push({name: 'registrationEdit', query: {period_id: row.period_id}})
      },
      deleteEnrollment: function (row) {
        console.log('delete.')
        this.$confirm('此操作将删除该报名培训，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteEnrollmentPeriod, {periodid: row.period_id}).then(data => {
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

<style scoped="scope">
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
</style>
