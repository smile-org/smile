<template>
  <div>
    <common-header type="registration"></common-header>
    <div class="con_main" >
      <navigator module="registration" menu="record"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 :  <span class="">报名管理</span> > <span class="f_blue">员工报名记录</span> </span>
        </nav>
        <div class="con_tab">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="full_name" align="center" label="姓名" width="">
            </el-table-column>
            <el-table-column prop="department" align="center" label="部门" width="100">
            </el-table-column>
            <el-table-column prop="area" align="center" label="区域" width="">
            </el-table-column>
            <el-table-column prop="cell_phone" align="center" label="手机" width="">
            </el-table-column>
            <el-table-column prop="job_number" align="center" label="工号" width="">
            </el-table-column>
            <el-table-column prop="email" align="center" label="邮箱" width="">
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
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
  export default {
    data: function () {
      return {
        id: 0,
        tableData: [],
        total: 0,
        take: 10,
        currentPage: 1
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.id = parseInt(this.$route.query.id)
      this.queryPeriodResult()
    },
    methods: {
      queryPeriodResult () {
        api.fetch(api.uri.getUserListThisPeriod, {periodid: this.id, skip: (this.currentPage - 1) * this.take, take: this.take}).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryPeriodResult()
      }
    }
  }
</script>

<style scoped="scope">
</style>
