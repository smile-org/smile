<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="f_blue">员工学习记录</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="formInline.title" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="formInline.user" placeholder="员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20" v-on:click="search">查  询</button>
              <button type="button" class="inf_btn ml20" v-on:click="exportList">导  出</button>
            </el-form-item>
          </el-form>
          <el-table :data="tableData"  border style="width: 100%">
            <el-table-column prop="full_name" label="员工姓名">
            </el-table-column>
            <el-table-column prop="title" label="课程名称">
            </el-table-column>
            <el-table-column prop="department" label="部门">
            </el-table-column>
            <el-table-column prop="area" label="区域">
            </el-table-column>
            <el-table-column prop="content" label="课程内容">
            </el-table-column>
            <el-table-column label="学习时间">
              <template scope="scope">
                {{scope.row.learn_at|formatDate}}
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF" style="margin:10px 38%">
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
  import moment from 'moment'
  export default {
    data: function () {
      return {
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          title: '',
          department: '',
          area: ''
        },
        tableData: []
      }
    },
    components: {
      commonHeader,
      navigator
    },
    filters: {
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    created () {
      this.search()
    },
    methods: {
      exportList () {
        this.$message('coming soon by 大拿')
      },
      search () {
        api.fetch(api.uri.getCourseLearningRecords, {
          title: this.formInline.title,
          fullname: this.formInline.name,
          department: this.formInline.department,
          area: this.formInline.area,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      handleCurrentChange (val) {
        this.currentPage = val
        this.search()
      }
    }
  }
</script>

<style scoped="scope">

</style>

