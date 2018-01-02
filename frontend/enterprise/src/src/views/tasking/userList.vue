<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="userList"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">员工学习任务</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="员工姓名">
              <el-input v-model="formInline.fullname" placeholder="员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="任务名称">
              <el-input v-model="formInline.tasktitle" placeholder="任务名称"></el-input>
            </el-form-item>
            <el-form-item label="工号">
              <el-input v-model="formInline.jobnumber" placeholder="工号"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
            </el-form-item>
          </el-form>
          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%" >
            <el-table-column prop="full_name" align="center" label="姓名" min-width="180">
            </el-table-column>
            <el-table-column prop="job_number" align="center" label="工号" min-width="140">
            </el-table-column>
            <el-table-column prop="department" label="部门" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="area" label="区域" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="task_title" align="center" label="任务名称" width="120">
            </el-table-column>
            <el-table-column prop="" align="center" label="截止日期" width="120">
              <template scope="scope">
                <span>{{scope.row.expiration_date | formatDate}} </span>
              </template>
            </el-table-column>
            <el-table-column prop="ispublished" label="状态" align="center" min-width="100">
              <template scope="scope">
                {{scope.row.ispublished ? "已发布" : "未发布"}}
              </template>
            </el-table-column>
            <el-table-column prop="percentage" label="完成进度" align="center"  min-width="120" sortable>
              <template scope="scope">
                {{scope.row.percentage | formatString}}
              </template>
            </el-table-column>
            <el-table-column label="操作" class="tc" width="100" align="center">
              <template scope="scope">
                <el-button v-on:click="taskingDetail(scope.row.task_id,scope.row.user_id)" type="text" size="small">查看</el-button>
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
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          fullname: '',
          tasktitle: '',
          jobnumber: '',
          area: '',
          department: ''
        },
        tableData: [
          {
            title: '啊董',
            number: '252522'
          }
        ]
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
      },
      formatString (str) {
        return str + '%'
      }
    },
    created () {
      api.fetch(api.uri.GetUserTaskList, {
        fullname: this.formInline.fullname,
        tasktitle: this.formInline.tasktitle,
        jobnumber: this.formInline.jobnumber,
        area: this.formInline.area,
        department: this.formInline.department,
        skip: 0,
        take: this.take
      }).then(data => {
        if (data.status === 1) {
          this.tableData = data.result
          for (var i = 0; i < this.tableData.length; i++) {
            this.tableData[i].percentage = parseFloat(this.tableData[i].learn_percentage)
          }
          console.log(this.tableData)
          this.total = data.total
          this.tableData.expiration_date = new Date(this.tableData.expiration_date)
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    methods: {
      taskingDetail: function (id, userid) {
        router.push({name: 'taskingDetail', query: {id: id, userid: userid}})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
      search: function () {
        api.fetch(api.uri.GetUserTaskList, {
          fullname: this.formInline.fullname,
          tasktitle: this.formInline.tasktitle,
          jobnumber: this.formInline.jobnumber,
          area: this.formInline.area,
          department: this.formInline.department,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
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
