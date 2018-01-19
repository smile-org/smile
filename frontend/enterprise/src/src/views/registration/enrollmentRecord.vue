<template>
  <div>
    <common-header type="registration"></common-header>
    <div class="con_main">
      <navigator module="registration" menu="record"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">报名管理</span> > <span class="f_blue">员工报名记录</span> </span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="form.title" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="讲师" prop="region">
              <el-input v-model="form.teacher" placeholder="讲师"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" class="dateTab_width" placeholder="选择日期" v-model="form.startDate" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" class="dateTab_width"  placeholder="选择日期" v-model="form.endDate" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="dc_width">
              <button type="button" class="line-btn ml20" v-on:click="queryEnrollmentResult">查  询</button>
              <button type="button" v-on:click="exportEnrollmentResult" :loading="showloading" class="inf_btn ml20 export_bor">导  出</button>
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
            </el-form-item>
          </el-form>

          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="title" align="center" label="课程名称" width="">
            </el-table-column>
            <el-table-column prop="teacher" align="center" label="讲师" width="100">
            </el-table-column>
            <el-table-column prop="start" align="center" label="开始时间" width="">
            </el-table-column>
            <el-table-column prop="end" align="center" label="结束时间" width="">
            </el-table-column>
            <el-table-column prop="count" align="center" label="人数限制" width="">
            </el-table-column>
            <el-table-column prop="countThisPeriod" align="center" label="本期报名" width="">
              <template scope="scope">
                <el-button v-on:click="thisPeriod(scope.row)" class="green_font" type="text" size="small">{{scope.row.countThisPeriod}}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="dateCountNextPeriod" align="center" label="预约下期" width="">
              <template scope="scope">
                <el-button v-on:click="nextPeriod(scope.row)" class="green_font" type="text" size="small">{{scope.row.dateCountNextPeriod}}</el-button>
              </template>
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
        tableData: [],
        total: 0,
        take: 10,
        currentPage: 1,
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
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    created () {
      this.queryEnrollmentResult()
    },
    methods: {
      queryEnrollmentResult: function () {
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.form.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.form.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getEnrollmentResultList, {title: this.form.title, teacher: this.form.teacher, start: date1, end: date2, skip: (this.currentPage - 1) * this.take, take: this.take}).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryEnrollmentResult()
      },
      exportEnrollmentResult: function () {
        this.showloading = true
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.form.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.form.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportEnrollmentResultList, {title: this.form.title, teacher: this.form.teacher, start: date1, end: date2}).then(data => {
          if (data.status === 1) {
            this.excelUrl = axios.defaults.fileServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      thisPeriod: function (row) {
        router.push({name: 'registrationThisPeriod', query: {id: row.period_id}})
      },
      nextPeriod: function (row) {
        router.push({name: 'registrationNextPeriod', query: {id: row.period_id}})
      }
    }
  }
</script>

<style scoped="scope">
  .el-icon-loading{
    color: #fff;
  }
  .export_bor{
    border:none;
    color: #fff;
  }
  .export_bor:hover, .export_bor:active{
    color: #fff;
  }

  .download {
    line-height: 38px;
    display: inline-block;
  }


</style>

