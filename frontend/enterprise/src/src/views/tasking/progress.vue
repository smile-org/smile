<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="">学习任务管理</span> > <span class="f_blue">添加学习任务</span></span>
        </nav>
        <div class="con_tab">
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="name" label="姓名" align="center" width=""></el-table-column>
              <el-table-column prop="number" label="工号" align="center" width=""></el-table-column>
              <el-table-column prop="apartment" label="部门" align="center" width=""></el-table-column>
              <el-table-column prop="address" label="区域" align="center"></el-table-column>
              <el-table-column prop="mission" label="任务名称" align="center"></el-table-column>
              <el-table-column prop="name" label="截止日期" align="center" width="">
                <template scope="scope">
                  <span>{{scope.row.start_date | formatDate}} </span>
                </template>
              </el-table-column>
              <el-table-column prop="address" label="状态" align="center"></el-table-column>
              <el-table-column prop="address" label="完成进度" align="center">
                <template scope="scope">
                  <el-button @click="taskingProgress" type="text" size="small">20/50</el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template scope="scope">
                  <el-button v-on:click="taskingDetail" type="text" size="small">查看</el-button>
                </template>
              </el-table-column>

            </el-table>
            <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change=""
                           :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
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
  //  import api from '../../services/api'
  import moment from 'moment'
  import router from '../../router'
  export default {
    data: function () {
      return {
        take: 10,
        currentPage: 1,
        total: 0,
        tableData: [{
          date: '2016-05-02',
          name: '王小公共关系虎',
          address: '上海 1518 弄'
        }]
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
    methods: {
      taskingDetail: function () {
        router.push({name: 'taskingDetail'})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      }

    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
