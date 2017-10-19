<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="f_blue">课程信息管理</span></span>
        </nav>
        <div class="con_tab">

          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="formInline.user" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="课程类别" prop="region">
              <el-select v-model="formInline.region" placeholder="请选择课程类别">
                <el-option label="类别一" value="销售"></el-option>
                <el-option label="类别一" value="企划"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="责任人">
              <el-input v-model="formInline.address" placeholder="责任人"></el-input>
            </el-form-item>
            <el-form-item label="发布时间">
              <el-input v-model="formInline.address" placeholder="发布时间"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
            </el-form-item>
          </el-form>
          <div class="fr hidden mb20">
            <button type="button" class="inf_btn mr20" v-on:click="routeByName('courseEdit')" >添加课程</button>
            <button type="button" class="inf_btn">导  入</button>
          </div>
          <el-table  :data="tableData" border style="width: 100%">
            <el-table-column prop="" label="课程名称" width="180">
            </el-table-column>
            <el-table-column prop="number" label="课程类别" width="180">
            </el-table-column>
            <el-table-column prop="workNum" label="责任人" width="180">
            </el-table-column>
            <el-table-column prop="email" label="部门" width="180">
            </el-table-column>
            <el-table-column prop="department" label="有效期" width="180">
            </el-table-column>
            <el-table-column prop="address" label="课程类型" width="180">
            </el-table-column>
            <el-table-column prop="timeStart" label="课程状态" width="180">
            </el-table-column>
            <el-table-column prop="timeEnd" label="发布日期" width="180">
            </el-table-column>
            <el-table-column  prop="appraise" label="查看评价" width="180">
              <template scope="scope">
                <el-button  v-on:click="routeByName('courseComment')" type="text" size="small">查看课程评价</el-button>
              </template>
            </el-table-column>
            <el-table-column label="操作" class="tc" width="100">
              <template scope="scope">
                <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                <el-button @click="checkFail(scope.row.id)" type="text" size="small">删除</el-button>
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
        formInline: {
          user: '',
          company: {},
          apartment: '',
          address: ''
        },
        tableData: [{
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
        }, {
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
        }, {
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
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
        console.log(name)
        router.push({ name: name })
      }
    }
  }
</script>

<style scoped>
  /*.el-button--small {*/
    /*font-size: 14px;*/
    /*color: #00b553;*/
  /*}*/
</style>
