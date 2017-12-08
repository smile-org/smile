<template>
  <div>
    <common-header type="course"></common-header>
    <div class="con_main">
      <navigator menu="membership"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">企业会员管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="企业名称">
              <el-input v-model="formInline.name" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="省份">
              <el-select class="dateTab_width" v-model="formInline.provinceId" placeholder="请选择省份">
                <el-option
                  v-for="item in provinceList"
                  :key="item.province_id"
                  :label="item.province_name"
                  :value="item.province_id">
                </el-option>
              </el-select>
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
            <el-form-item label="代理商">
              <el-select class="dateTab_width" v-model="formInline.agencyId" placeholder="请选择主营行业">
                <el-option
                  v-for="item in agencyList"
                  :key="item.agency_id"
                  :label="item.agency_name"
                  :value="item.agency_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="入驻时间">
              <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.start" style="width: 100%;"></el-date-picker>
              </el-col>
              <el-col class="line ml_10style" :span="2">—</el-col>
              <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.end" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="fr hidden mb20 dc_width">
              <button type="button" class="line-btn ml20" @click="search">查  询</button>
              <button type="button" v-on:click="membershipCreate"  class="inf_btn ml20 export_bor">企业入驻</button>
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
          <el-table :data="tableData" border style="width: 100%" >
            <el-table-column prop="company_name" align="center" label="企业名称" min-width="180">
            </el-table-column>
            <el-table-column prop="contact_person" align="center" label="联系人" min-width="140">
            </el-table-column>
            <el-table-column prop="phone_number" align="center" label="联系电话" width="120">
            </el-table-column>
            <el-table-column prop="user_limit" label="授权用户" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="expiration_date" label="服务截止" align="center" min-width="120">
              <template scope="scope">
                {{scope.row.expiration_date | formatExpirationDate}}
              </template>
            </el-table-column>
            <el-table-column prop="province" label="省份" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="business" label="主营行业" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="agency" label="代理商" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="start" label="入驻时间" align="center" width="120">
              <template scope="scope">
                {{scope.row.start | formatDate}}
              </template>
            </el-table-column>
            <el-table-column prop="indicator" label="状态" align="center" min-width="100">
              <template scope="scope">
                {{scope.row.indicator ? "生效" : "禁用"}}
              </template>
            </el-table-column>
            <el-table-column label="操作" class="tc" width="140" align="center">
              <template scope="scope">
                <el-button @click="edit(scope.row.company_id)" type="text" size="small">编辑</el-button>
                <el-button @click="updateState(scope.row)"  class="red_font"  type="text" size="small">{{scope.row.indicator ? '禁用' : '生效'}}</el-button>
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
  import axios from 'axios'
  export default {
    data: function () {
      return {
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          agencyId: 0,
          start: '',
          end: '',
          businessId: 0,
          provinceId: 0
        },
        businessList: [{
          business_id: 0,
          business_name: '请选择'
        }],
        provinceList: [{
          province_id: 0,
          province_name: '请选择'
        }],
        agencyList: [{
          agency_id: 0,
          agency_name: '请选择'
        }],
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
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      },
      formatExpirationDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD')
      }
    },
    created () {
      api.fetch(api.uri.getProvinceAndBusiness, {companyid: 0}).then(data => {
        if (data.status === 1) {
          this.businessList = this.businessList.concat(data.result.BusinessList)
          this.provinceList = this.provinceList.concat(data.result.ProvinceList)
          this.agencyList = this.agencyList.concat(data.result.AgencyList)
          this.search()
        }
      })
    },
    methods: {
      membershipCreate: function () {
        router.push({name: 'membershipCreate'})
      },
      addCourse: function () {
        router.push({name: 'courseCreate'})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
      edit: function (id) {
        router.push({name: 'membershipEdit', query: {id: id}})
      },
      // 查询列表
      search: function () {
        var start = ''
        var end = ''
        if (this.formInline.start) {
          start = moment(this.formInline.start).format('YYYY-MM-DD')
        }
        if (this.formInline.end) {
          end = moment(this.formInline.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getCompanyList, {
          companyname: this.formInline.name,
          agencyid: this.formInline.agencyId,
          businessid: this.formInline.businessId,
          provinceid: this.formInline.provinceId,
          start: start,
          end: end,
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
      },
      updateState: function (item) {
        // 隐藏传0， 发布传1
        var _type = 0
        if (item.indicator === true) {
          _type = 0
        } else {
          _type = 1
        }
        api.fetch(api.uri.updateCompanyStatus, {
          companyid: item.company_id,
          status: _type
        }).then(data => {
          if (data.status === 1) {
            this.$message('操作成功')
            item.indicator = !item.indicator
          }
        })
      },
      exportList () {
        var start = ''
        var end = ''
        if (this.formInline.start) {
          start = moment(this.formInline.start).format('YYYY-MM-DD')
        }
        if (this.formInline.end) {
          end = moment(this.formInline.end).format('YYYY-MM-DD')
        }
        this.showloading = true
        api.fetch(api.uri.exportCompany, {
          companyname: this.formInline.name,
          agencyid: this.formInline.agencyId,
          businessid: this.formInline.businessId,
          provinceid: this.formInline.provinceId,
          start: start,
          end: end
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
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
  .el-input {
    width: 100%;
  }
</style>
