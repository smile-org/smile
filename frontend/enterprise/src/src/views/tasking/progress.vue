<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="">学习任务管理</span> > <span class="f_blue">学习任务详情</span></span>
        </nav>
        <div class="con_tab">
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="full_name" label="姓名" align="center" width=""></el-table-column>
              <el-table-column prop="job_number" label="工号" align="center" width=""></el-table-column>
              <el-table-column prop="department" label="部门" align="center" width=""></el-table-column>
              <el-table-column prop="area" label="区域" align="center"></el-table-column>
              <el-table-column prop="task_title" label="任务名称" align="center"></el-table-column>
              <el-table-column prop="expiration_date" label="截止日期" align="center" width="">
                <template scope="scope">
                  <span>{{scope.row.expiration_date | formatDate}} </span>
                </template>
              </el-table-column>
              <el-table-column prop="learn_status" label="状态" align="center"></el-table-column>
              <el-table-column prop="learn_percentage" label="完成进度" align="center">
                <!--<template scope="scope">
                  <el-button @click="taskingProgress" type="text" size="small">{{scope.row.learn_percentage}}</el-button>
                </template>-->
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template scope="scope">
                  <el-button v-on:click="taskingDetail(scope.row.task_id)" type="text" size="small">查看</el-button>
                </template>
              </el-table-column>

            </el-table>
            <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change="handleCurrentChange" :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
          </template>
        </div>

      </section>
    </div>

  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  //  import ElRow from "element-ui/packages/row/src/row";
  import api from '../../services/api'
  import moment from 'moment'
  import router from '../../router'
  export default {
    data: function () {
      return {
        take: 10,
        currentPage: 1,
        total: 0,
        tableData: []
      }
    },
    created () {
      api.fetch(api.uri.GetUserTaskListByTaskID, {
        taskid: this.$route.query.id,
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
    methods: {
      search: function () {
        api.fetch(api.uri.GetUserTaskListByTaskID, {
          taskid: this.$route.query.id,
          skip: this.take * (this.currentPage - 1),
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
      taskingDetail: function (id) {
        router.push({name: 'taskingDetail', query: {id: id}})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      }
      // taskingProgress: function () {
      //   console.log('1')
      // }
    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
