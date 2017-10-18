<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="user"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="criteria"  class="demo-form-inline" label-width="80px">
            <el-form-item label="姓名">
              <el-input v-model="criteria.name" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="登录时间">
              <el-col :span="11">
                <el-date-picker type="date" @change="changeDateFrom" placeholder="选择日期" v-model="dateFrom" style="width: 100%;"></el-date-picker>
              </el-col>
              <el-col class="line tc" :span="2">—</el-col>
              <el-col :span="11">
                <el-date-picker type="date" @change="changeDateTo" placeholder="选择日期" v-model="dateTo" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="criteria.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="criteria.address" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <button class="inf_btn ml20" type="button"  @click="onSubmit">查  询</button>
            </el-form-item>
          </el-form>
          <template>
            <el-table :data="data" border style="width: 100%">
              <el-table-column prop="name" label="员工姓名" width="180"></el-table-column>
              <el-table-column prop="department" label="部门" width="180"></el-table-column>
              <el-table-column prop="area" label="区域"></el-table-column>
              <el-table-column prop="loginTime | formatDate" label="登陆时间"></el-table-column>
            </el-table>
          </template>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="10" layout="total, prev, pager, next" :total="total"></el-pagination>
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
        dateFrom: '',
        dateTo: '',
        criteria: {
          name: '',
          dateFrom: '',
          dateTo: '',
          department: '',
          area: ''
        },
        data: [],
        take: 20,
        currentPage: 0,
        total: 0
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
      api.fetch(api.uri.searchUserLog, {
        area: this.criteria.area,
        skip: this.currentPage * this.take
      }).then(data => {
        if (data.status === 1) {
          this.data = data.result
        } else {
          // alert()
        }
      }).catch(error => {
        alert(error.message)
      })
    },
    methods: {
      changeDateFrom (dateStr) {
        this.criteria.dateFrom = dateStr
        if (this.criteria.dateTo && this.criteria.dateTo < dateStr) {
          this.dateTo = dateStr
        }
      },
      changeDateTo (dateStr) {
        this.criteria.dateTo = dateStr
        if (this.criteria.dateFrom && this.criteria.dateFrom > dateStr) {
          this.dateFrom = dateStr
        }
      },
      onSubmit () {
        this.currentPage = 0
        var _dateFrom = moment(this.criteria.dateFrom).format('YYYY-MM-DD')
        var _dateTo = moment(this.criteria.dateTo).format('YYYY-MM-DD')
        api.fetch(api.uri.searchUserLog, {
          username: this.criteria.name,
          dateFrom: _dateFrom,
          dateTo: _dateTo,
          department: this.criteria.department,
          area: this.criteria.area,
          skip: this.currentPage * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          alert(error.message)
        })
      },
      handleCurrentChange (val) {
        api.fetch(api.uri.searchUserLog, {
          username: this.criteria.name,
          dateFrom: this.criteria.dateFrom,
          dateTo: this.criteria.dateTo,
          department: this.criteria.department,
          area: this.criteria.area,
          skip: this.currentPage * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          alert(error.message)
        })
      }
    }
  }
</script>

<style scoped>
  element.style {
    width: 146.063px;
    transform-origin: center top 0px;
    z-index: 2007;
    position: absolute;
    top: 177px;
    left: 580px;
  }
</style>
