<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="booking"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">约课管理</span> > <span class="f_blue">约课信息管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline" label-width="80px">
            <el-form-item label="约课主题">
              <el-input v-model="form.name" placeholder="约课主题"></el-input>
            </el-form-item>
            <el-form-item label="发起时间">
              <el-col>
                <el-date-picker type="date" placeholder="发起时间" v-model="form.date1" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="fr">
              <button type="button" class="inf_btn ml20" v-on:click="queryAppointment">查  询</button>
              <el-button type="button" v-on:click="exportAppointment" :loading="showloading" class="inf_btn ml20 export_bor">导  出</el-button>
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
            </el-form-item>
          </el-form>
          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="appointmentTitle" align="center" label="约课主题" width=""></el-table-column>
            <el-table-column prop="sponsorName" align="center" label="发起者" width=""></el-table-column>
            <el-table-column prop="sponsorDate" align="center" label="发起时间" width="">
              <template scope="scope" >
                <span >{{scope.row.sponsorDate | formatDate}} </span>
              </template>
            </el-table-column>
            <el-table-column prop="thisR" align="center" label="操作" width="" >
              <template scope="scope" >
                <el-button v-on:click="routeByName(scope.row)" type="text" size="small">查看</el-button>
                <el-button  type="text" size="small" @click="closeAppointment(scope.row)">关闭约课</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF" >
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
        showloading: false,
        form: {
          name: '',
          date1: ''
        },
        tableData: [],
        take: 10,
        currentPage: 1,
        total: 0,
        excelUrl: '',
        dialogTableVisible: false
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.queryAppointment()
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    methods: {
      routeByName: function (row) {
        console.log(row.appointmentId)
        router.push({ name: 'bookingDetail', query: {id: row.appointmentId} })
      },
      exportAppointment: function () {
        console.log('start export appointment.')
        this.showloading = true
        var date = ''
        if (this.form.date1) {
          date = moment(this.form.date1).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportAppointment, {title: this.form.name, sponsorDate: date}).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.excelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      queryAppointment: function () {
        console.log(this.form.date1)
        var date = ''
        if (this.form.date1) {
          date = moment(this.form.date1).format('YYYY-MM-DD')
        }
        console.log(this.currentPage)
        api.fetch(api.uri.getBackAppointmentList, {title: this.form.name, sponsorDate: date, skip: (this.currentPage - 1) * this.take, take: this.take}).then(data => {
          if (data.status === 1) {
            this.total = data.total
            this.tableData = data.result
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryAppointment()
      },
      closeAppointment (row) {
        console.log(row.appointmentId)
        this.$confirm('此操作将关闭该约课需求，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.closeAppointment, {appointmentId: row.appointmentId}).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '关闭成功!'
              })
              this.total = data.total
              this.tableData = data.result
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消关闭'
          })
        })
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
