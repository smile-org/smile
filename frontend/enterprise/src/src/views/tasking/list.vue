<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置a : <span class="">学习任务</span> > <span class="f_blue">学习任务管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="任务名称">
              <el-input v-model="formInline.name" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="截止日期">
              <el-col :span="11">
                <el-date-picker type="date" class="dateTab_width"  placeholder="开始时间" v-model="formInline.start" style="width: 100%;"></el-date-picker>
              </el-col>
              <el-col class="line tc ml_10style" :span="2"> —</el-col>
              <el-col :span="11">
                <el-date-picker type="date"  class="dateTab_width"  placeholder="结束时间" v-model="formInline.end" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
          </el-form>
          <div class="fr hidden mb20 dc_width">
            <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
            <button type="button" class="inf_btn ml20" v-on:click="addTasking">添加学习任务</button>
          </div>
          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%" >
            <el-table-column prop="task_title" align="center" label="任务名称" min-width="180">
            </el-table-column>
            <el-table-column prop="task_scope" align="center" label="目标学员范围" min-width="140">
            </el-table-column>
            <el-table-column prop="expiration_date | formatDate" align="center" label="截止日期" width="120">
              <template scope="scope">
                <span>{{scope.row.expiration_date | formatDate}} </span>
              </template>
            </el-table-column>
            <el-table-column prop="ispublished" label="课程状态" align="center" min-width="100">
              <template scope="scope">
                {{scope.row.ispublished ? "已发布" : "未发布"}}
              </template>
            </el-table-column>
            <el-table-column prop="finish_status" label="完成进度"  align="center" min-width="120">
              <template scope="scope">
                  <el-button v-on:click="getComments(scope.row.task_id)" type="text" size="small">
                      {{scope.row.finish_status}}
                  </el-button>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="start_date" label="发布日期" align="center" width="120">
              <template scope="scope" v-if="scope.row.start_date">
                <span>{{scope.row.start_date | formatDate}} </span>
              </template>
            </el-table-column> -->
            <el-table-column label="操作" class="tc" width="140" align="center">
              <template scope="scope">
                <el-button @click="taskingEdit(scope.row.task_id)" type="text" size="small">编辑</el-button>
                <el-button @click="updateState(scope.row)" type="text" size="small">{{scope.row.ispublished ? '隐藏' : '发布'}}</el-button>
                <el-button @click="del(scope.row.task_id)" class="red_font" type="text" size="small">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
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
  import router from '../../router'
//  import axios from 'axios'
  export default {
    data: function () {
      return {
        currentPage: 0,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          start: '',
          end: ''
        },
        tableData: [],
        categoryList: [{
          category_id: -1,
          category_name: '请选择'
        }],
        exportExcelUrl: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getTaskList, {
        title: this.formInline.name,
        start: this.formInline.start,
        end: this.formInline.end,
        skip: 0,
        take: this.take
      }).then(data => {
        console.log(data)
        if (data.status === 1) {
          this.tableData = data.result
          this.total = data.total
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD')
      }
    },
    methods: {
      addTasking: function () {
        router.push({name: 'taskingCreate'})
      },
      taskingProgress: function () {
        router.push({name: 'taskingProgress'})
      },
      taskingEdit: function (id) {
        router.push({name: 'taskingEdit', query: {id: id}})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
      getComments: function (id) {
        router.push({name: 'taskingProgress', query: {id: id}})
      },
      del: function (id) {
        this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.DisableTask, {taskid: id}).then(data => {
            this.search()
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      search: function () {
        var start = ''
        var end = ''
        if (this.formInline.start) {
          start = moment(this.formInline.start).format('YYYY-MM-DD')
        }
        if (this.formInline.end) {
          end = moment(this.formInline.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getTaskList, {
          tasktitle: this.formInline.name,
          start: start,
          end: end,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          console.log(111)
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      updateState (current) {
        // 隐藏传0， 发布传1
        var _type = 0
        if (current.ispublished === true) {
          _type = 0
        } else {
          _type = 1
        }
        api.fetch(api.uri.UpdateTaskPublishStatus, {
          taskid: current.task_id,
          publish: _type
        }).then(data => {
          if (data.status === 1) {
            this.$message('操作成功')
            current.ispublished = !current.ispublished
            if (current.ispublished) {
              console.log(moment())
              current.publish_date = moment().format('YYYY-MM-DD')
            } else {
              current.publish_date = ''
            }
          }
        }).catch(error => {
          this.$message(error.message)
        })
      }
    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
