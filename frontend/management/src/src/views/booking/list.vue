<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="booking"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">约课信息管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="企业名称">
              <el-input v-model="formInline.name" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="主营行业">
              <el-select class="dateTab_width" v-model="formInline.businessId" placeholder="请选择主营行业">
                <el-option
                  v-for="item in businessList"
                  :key="item.business_id"
                  :label="item.business_name"
                  :value="item.business_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="约课主题">
              <el-input v-model="formInline.title" placeholder="约课主题"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-date-picker class="dateTab_width" type="date" placeholder="开始时间"
                              v-model="formInline.start" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-date-picker class="dateTab_width" type="date" placeholder="结束时间"
                              v-model="formInline.end" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <button type="button" class="line-btn ml20" v-on:click="search" >查  询</button>
              <button type="button" v-on:click="exportList" :loading="showloading" class="inf_btn ml20 export_bor">导  出</button>
              <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                <div class="tc">
                  <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;" />
                </div>
                <div class="tc">
                  <a v-bind:href="exportExcelUrl" v-on:click="dialogTableVisible = false" class="inf_btn download" style="display: inline-block;">下 载</a>
                  <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消
                  </button>
                </div>
              </el-dialog>
            </el-form-item>
          </el-form>
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="appointment_title" label="约课主题"  align="center" min-width="180"></el-table-column>
              <el-table-column prop="company_name" label="企业名称"  align="center" width="180"></el-table-column>
              <el-table-column prop="business_name" label="主营行业"  align="center" width="180"></el-table-column>
              <el-table-column prop="sponsor_idfull_name" label="发起者"  align="center" width="180"></el-table-column>
              <el-table-column prop="sponsor_idcell_phone" label="发起人联系方式"  align="center" width="180"></el-table-column>
              <el-table-column prop="contact_person" label="企业联系人"  align="center" width="180"></el-table-column>
              <el-table-column prop="sponsor_date" label="发起时间"  align="center" width="180">
                <template scope="scope">
                  {{scope.row.sponsor_date | formatDate}}
                </template>
              </el-table-column>
              <el-table-column label="操作" class="tc" width="100" align="center">
                <template scope="scope">
                  <el-button  v-on:click="goDetail(scope.row.appointment_id)" type="text" size="small">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="ds_oq_pageF">
              <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                             :page-size="take" layout="total, prev, pager, next"
                             :total="total"></el-pagination>
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
  import router from '../../router'
  import api from '../../services/api'
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          businessId: 0,
          start: '',
          end: '',
          title: ''
        },
        tableData: [],
        businessList: [{
          business_id: 0,
          business_name: '请选择'
        }],
        showloading: false,
        exportExcelUrl: '',
        dialogTableVisible: false
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getProvinceAndBusiness, {companyid: 0}).then(data => {
        if (data.status === 1) {
          this.businessList = this.businessList.concat(data.result.BusinessList)
          this.search()
        }
      })
    },
    filters: {
      formatDate: function (time) {
        var date = new Date(time)
        console.log(date)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    methods: {
      goDetail: function (id) {
        router.push({name: 'bookingDetail', query: {id: id}})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
      search: function () {
        api.fetch(api.uri.getAppointments, {
          companyname: this.formInline.name,
          businessid: this.formInline.businessId,
          appointmenttitle: this.formInline.title,
          start: this.formInline.start,
          end: this.formInline.end,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      exportList () {
        this.showloading = true
        api.fetch(api.uri.exportAppointment, {
          companyname: this.formInline.name,
          businessid: this.formInline.businessId,
          appointmenttitle: this.formInline.title,
          start: this.formInline.start,
          end: this.formInline.end
        }).then(data => {
          if (data.status === 1) {
            this.exportExcelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      }
    }
  }
</script>

<style scoped="scope">
  .info_tab {
    /*padding: 5px;*/
  }

  .info_tab tr td {
    padding: 10px;
    font-size: 14px;
  }

  .info_tab tr td:first-child {
    /*font-weight:600;*/
    background: #f2f7f1;
  }

</style>
