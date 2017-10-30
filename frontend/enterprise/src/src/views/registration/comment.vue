<template>
  <div>
    <common-header type="registration"></common-header>
    <div class="con_main">
      <navigator module="registration" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">报名管理</span> > <span class="">培训报名管理</span> > <span class="f_blue">课程评价</span></span>
        </nav>
        <div class="con_tab">
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="user_idName" label="员工姓名" align="center" width="180"></el-table-column>
              <el-table-column prop="star" label="星级"  align="center" width="">
                <template scope="scope">
                  <el-rate v-model="scope.row.star" disabled show-text text-color="#ff9900" text-template="{value}"></el-rate>
                </template>
              </el-table-column>
              <el-table-column prop="comments"  align="center" label="评价"></el-table-column>
              <el-table-column prop="created_at"  align="center" label="评价日期" width="180">
                <template scope="scope" >
                  <span >{{scope.row.created_at | formatDate}} </span>
                </template>
              </el-table-column>
            </el-table>
            <div class="ds_oq_pageF" style="margin:10px 38%">
              <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
            </div>
          </template>
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
  export default {
    data: function () {
      return {
        id: '',
        tableData: [],
        take: 10,
        currentPage: 1,
        total: 0
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
      }
    },
    created () {
      this.id = parseInt(this.$route.query.id)
      api.fetch(api.uri.getEnrollmentCommentList, {enrollmentid: this.id, skip: (this.currentPage - 1) * this.take, take: this.take}).then(data => {
        if (data.status === 1) {
          this.tableData = data.result
          this.total = data.total
        }
      })
    },
    methods: {
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        api.fetch(api.uri.getEnrollmentCommentList, {enrollmentid: this.id, skip: (this.currentPage - 1) * this.take, take: this.take}).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      }
    }
  }
</script>

<style scoped="scope">

</style>
