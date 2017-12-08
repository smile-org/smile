<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="agency"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">代理商管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="120px">
            <el-form-item label="代理商名称">
              <el-input v-model="formInline.name" placeholder="代理商名称"></el-input>
            </el-form-item>
            <el-form-item label="代理区域">
              <el-input v-model="formInline.area" placeholder="代理区域"></el-input>
            </el-form-item>
            <el-form-item label="代理权截止日期">
              <el-date-picker class="dateTab_width" type="date" placeholder="代理权截至日期"
                              v-model="formInline.date" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item class="fr hidden mb20 dc_width">
              <button type="button" v-on:click="queryAgencyList()" class="line-btn ml20">查  询</button>
              <button type="button" v-on:click="agencyCreate"  class="inf_btn ml20 export_bor">代理商入驻</button>
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
          <hr class="hr_line">
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="agency_name" label="代理商名称" align="center" min-width="180"></el-table-column>
              <el-table-column prop="legal_person" label="法人" align="center" width="180"></el-table-column>
              <el-table-column prop="contact_person" label="联系人" align="center" width="180"></el-table-column>
              <el-table-column prop="contact_phone" label="联系方式" align="center" width="180"></el-table-column>
              <el-table-column prop="agent_area" label="代理区域" align="center" width="180"></el-table-column>
              <el-table-column label="代理权开始日期" sortable align="center" width="180">
                <template scope="scope">
                  {{scope.row.agent_start_date | formatDate}}
                </template>
              </el-table-column>
              <el-table-column prop="agent_end_date" label="代理权截止日期" sortable align="center" width="180">
                <template scope="scope">
                  {{scope.row.agent_end_date | formatDate}}
                </template>
              </el-table-column>
              <el-table-column prop="company_count" label="服务企业数量" sortable align="center" width="180"></el-table-column>
              <el-table-column label="操作" class="tc" width="100" align="center">
                <template scope="scope">
                  <el-button v-on:click="agencyEdit(scope.row.agency_id)" type="text" size="small">编辑</el-button>
                  <el-button v-on:click="agencyDelete(scope.row.agency_id)" class="red_font" type="text" size="small">禁止</el-button>
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
          area: '',
          date: ''
        },
        tableData: [],
        showloading: false,
        exportExcelUrl: '',
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
        return moment(date).format('YYYY-MM-DD')
      }
    },
    created () {
      this.queryAgencyList()
    },
    methods: {
      queryAgencyList: function () {
        var date1 = ''
        if (this.formInline.date) {
          date1 = moment(this.formInline.date).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getAgencyList, {
          agencyname: this.formInline.name,
          agentarea: this.formInline.area,
          end: date1,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      agencyCreate: function () {
        router.push({name: 'agencyCreate'})
      },
      agencyEdit: function (id) {
        router.push({name: 'agencyEdit', query: {id: id}})
      },
      agencyDelete: function (id) {
        console.log(id)
        this.$confirm('此操作将永久禁用该代理商, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.updateAgencyStatus, {agencyid: id, status: 0}).then(data => {
            this.queryAgencyList()
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消禁用'
          })
        })
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.queryAgencyList()
      },
      exportList () {
        this.showloading = true
        var date1 = ''
        if (this.formInline.date) {
          date1 = moment(this.formInline.date).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportAgency, {
          agencyname: this.formInline.name,
          agentarea: this.formInline.area,
          end: date1
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

  .el-form--inline .el-form-item__label {
    text-align: left !important;
  }

</style>
