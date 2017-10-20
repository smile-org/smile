<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="registration"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">员工报名记录</span> ></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="form.name" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="讲师" prop="region">
              <el-input v-model="form.user" placeholder="讲师"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
              <button type="button" class="inf_btn ml20">导  出</button>
            </el-form-item>
          </el-form>
          <!--添加报名导出表格-->
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="name" label="课程名称" width="">
            </el-table-column>
            <el-table-column prop="lecturer" label="讲师" width="100">
            </el-table-column>
            <el-table-column prop="timeStart" label="开始时间" width="">
            </el-table-column>
            <el-table-column prop="timeEnd" label="结束时间" width="">
            </el-table-column>
            <el-table-column prop="people" label="人数限制" width="">
            </el-table-column>
            <el-table-column prop="thisR" label="本期报名" width="">
            </el-table-column>
            <el-table-column prop="nextR" label="预约下期" width="">
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
        form: {
          name: '',
          user: '',
          company: {},
          department: '',
          area: '',
          date: '',
          date1: ''
        },
        tableData: [{
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日',
          people: '80',
          thisR: '40',
          nextR: '40'
        }, {
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日',
          people: '80',
          thisR: '40',
          nextR: '40'
        }, {
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日',
          people: '80',
          thisR: '40',
          nextR: '40'
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
      }
    }
  }
</script>

<style>
</style>
