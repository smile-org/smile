<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="f_blue">员工学习记录</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="formInline.name" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名" prop="region">
              <el-input v-model="formInline.user" placeholder="员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
              <button type="button" class="inf_btn ml20">导  出</button>
            </el-form-item>
          </el-form>
          <el-table :data="tableData"  border style="width: 100%">
            <el-table-column prop="user" label="员工姓名">
            </el-table-column>
            <el-table-column prop="name" label="课程名称">
            </el-table-column>
            <el-table-column prop="department" label="部门">
            </el-table-column>
            <el-table-column prop="area" label="区域">
            </el-table-column>
            <el-table-column prop="content" label="课程内容">
            </el-table-column>
            <el-table-column prop="study" label="学习时间">
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
  export default {
    data: function () {
      return {
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          user: '',
          date: '',
          categoryId: ''
        },
        tableData: []
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.searchCourse, {
        title: this.formInline.name,
        priName: this.formInline.user,
        typeid: this.formInline.categoryId,
        pubdate: this.formInline.date,
        skip: 0,
        take: this.take
      }).then(data => {
        if (data.status === 1) {
          this.tableData = data.result
          api.fetch(api.uri.getCategory).then(data1 => {
            if (data1.status === 1) {
              this.categoryList = data1.result
            }
          }).catch(err => {
            this.$message(err.message)
          })
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    methods: {}
  }
</script>

<style scoped="scope">

</style>

