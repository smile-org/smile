eee
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
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="发起时间" v-model="form.date1" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
              <el-button type="button" v-on:click="click" :loading="showloading" class="inf_btn ml20 export_bor">导  出</el-button>
            </el-form-item>
          </el-form>
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="topic" label="约课主题" width="">
            </el-table-column>
            <el-table-column prop="lecturer" label="发起者" width="">
            </el-table-column>
            <el-table-column prop="timeStart" label="发起时间" width="">
            </el-table-column>
            <el-table-column prop="thisR" label="操作" width="" >
              <template scope="scope" >
                <el-button v-on:click="routeByName('bookingDetail')" type="text" size="small">查看</el-button>
                <el-button  type="text" size="small">关闭约课</el-button>
              </template>
            </el-table-column>
          </el-table>
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
  export default {
    data: function () {
      return {
        showloading: false,
        form: {
          name: '',
          company: {},
          date: '',
          date1: ''
        },
        tableData: [{
          topic: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日'
        }, {
          topic: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日'
        }, {
          topic: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日'
        }]
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
      routeByName: function (name) {
        router.push({ name: name })
      },
      click: function () {
        this.showloading = true
      }
    }
  }
</script>

<style>
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
</style>
