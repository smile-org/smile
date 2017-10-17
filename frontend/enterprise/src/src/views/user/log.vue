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
          <el-form ref="form"  :inline="true" :model="formInline"  class="demo-form-inline" label-width="80px">
            <el-form-item label="姓名">
              <el-input v-model="formInline.user" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="登录时间">
              <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date1" style="width: 100%;"></el-date-picker>
              </el-col>
              <el-col class="line tc" :span="2">—</el-col>
              <el-col :span="11">
                <el-time-picker type="fixed-time" placeholder="选择时间" v-model="formInline.date2" style="width: 100%;"></el-time-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.apartment" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.address" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <button class="inf_btn ml20"  @click="onSubmit">查  询</button>
            </el-form-item>
          </el-form>
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="" label="员工姓名" width="180"></el-table-column>
              <el-table-column prop="" label="部门" width="180"></el-table-column>
              <el-table-column prop="" label="区域"></el-table-column>
              <el-table-column prop="time" label="登陆时间"></el-table-column>
            </el-table>
          </template>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="ds_oq_handleCurrentChange" :current-page="currentPage"  :page-size="10" layout="total, prev, pager, next" :total="totalRow"></el-pagination>
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
        formInline: {
          user: '',
          company: {},
          date1: '',
          date2: '',
          apartment: '',
          address: ''
        }
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.company = data
        }
      })
    },
    methods: {
      onSubmit () {
        console.log('error submit!!')
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
