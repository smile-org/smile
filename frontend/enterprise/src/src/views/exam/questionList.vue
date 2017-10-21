<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="f_blue">考试信息管理</span></span>
        </nav>
        <div class="con_tab">

          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="考试编号">
              <el-input v-model="formInline.user" placeholder="考试编号"></el-input>
            </el-form-item>
            <el-form-item label="考试名称">
              <el-input v-model="formInline.user" placeholder="考试名称"></el-input>
            </el-form-item>
            <el-form-item label="管理员">
              <el-input v-model="formInline.address" placeholder="管理员"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date1" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
            </el-form-item>
          </el-form>
          <div class="fr hidden mb20">
            <button type="button" class="inf_btn mr20" v-on:click="routeByName('courseEdit')" >添加课程</button>
            <el-button type="button" v-on:click="click" :loading="showloading" class="inf_btn ml20 export_bor">导  出</el-button>
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
            <el-table-column label="操作" class="tc" width="180">
              <template scope="scope">
                <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                <el-button @click="open2" type="text" size="small">删除</el-button>
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
        showloading: '',
        formInline: {
          user: '',
          company: {},
          apartment: '',
          address: '',
          date: '',
          date1: ''
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
      },
      open2 () {
        this.$confirm('此课程将要被删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      click: function () {
        this.showloading = true
      }
    }
  }
</script>

<style scoped>
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
