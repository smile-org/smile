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
              <el-input v-model="formInline.name" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="课程类别" prop="formInline.categoryId">
              <el-select v-model="formInline.categoryId" placeholder="请选择课程类别">
                 <el-option
                  v-for="item in categoryList"
                  :key="item.category_id"
                  :label="item.category_name"
                  :value="item.category_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="责任人">
              <el-input v-model="formInline.user" placeholder="责任人"></el-input>
            </el-form-item>
            <el-form-item label="发布时间">
              <el-input v-model="formInline.date" placeholder="发布时间"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
            </el-form-item>
          </el-form>
          <div class="fr hidden mb20">
            <button type="button" class="inf_btn mr20" v-on:click="routeByName('courseEdit')" >添加课程</button>
            <button type="button"  class="inf_btn">导  出</button>
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
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
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
        currentPage: 0,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          user: '',
          date: '',
          categoryId: ''
        },
        tableData: [],
        categoryList: []
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
    methods: {
      handleCurrentChange: function (val) {

      }
    }
  }
</script>

<style scoped>

</style>
