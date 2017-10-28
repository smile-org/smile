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
          <div>
            <button v-on:click="routeByName('userCreate')" class="inf_btn mr15 vm">添加员工</button>
            <button class="inf_btn mr15 vm dis_in_block">批量导入</button>
            <a v-bind:href="excelUrl" style="display: inline-block"  class="inf_btn mr15 vm">下载导入模板</a>
            <button class="inf_btn mr15 vm">导  出</button>
          </div>
          <el-form :inline="true" :model="formInLine" class="demo-form-inline mt20">
            <!--<el-row>-->
            <el-form-item label="姓名">
              <el-input v-model="formInLine.user" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="formInLine.mobile" placeholder="手机号"></el-input>
            </el-form-item>
            <!--</el-row>-->
            <!--<el-row>-->
            <el-form-item label="部门">
              <el-input v-model="formInLine.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInLine.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            <!--</el-row>-->
          </el-form>
          <template>
            <el-table :data="data" border style="width: 100%">
              <el-table-column prop="name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="mobile" label="手机" width="180">
              </el-table-column>
              <el-table-column prop="employeeNo" label="工号"  width="180">
              </el-table-column>
              <el-table-column prop="email" label="邮箱" width="180">
              </el-table-column>
              <el-table-column prop="department" label="部门" width="180">
              </el-table-column>
              <el-table-column prop="area" label="区域"  width="180" >
              </el-table-column>
              <el-table-column prop="createdTime | formatDate" label="创建时间" width="180">
              </el-table-column>
              <el-table-column prop="updatedTime | formatDate" label="最后修改时间" width="180">
              </el-table-column>
              <el-table-column  label="操作" class="tc" width="150">
                <template scope="scope"  >
                  <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                  <el-button @click="checkFail(scope.row.id)" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="1" layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import router from '../../router'
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        formInLine: {
          department: '',
          area: '',
          name: '',
          // 去掉， 还要写验证， 麻烦
          mobile: ''
        },
        data: [],
        take: 20,
        currentPage: 0,
        total: 0,
        excelUrl: axios.defaults.imageServer + '/import/template/User.xlsx'
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.searchUserList, {
        name: this.formInLine.name,
        area: this.formInLine.area,
        department: this.formInLine.department,
        mobile: this.formInLine.mobile,
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
    filter: {
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    methods: {
      handleCurrentChange: function (value) {
        api.fetch(api.uri.searchUserList, {
          name: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          mobile: this.formInLine.mobile,
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
      onSubmit: function () {
        api.fetch(api.uri.searchUserList, {
          name: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          mobile: this.formInLine.mobile,
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
      routeByName: function (name) {
        router.push({ name: name })
      }
    }
  }
</script>

<style scoped>
  .el-input {
    width: 150px;
  }

  .el-button--primary {
    color: #fff;
    background-color: #00b553;
    border-color: #00b553;
    width: 80px;
  }

  .el-button--primary:hover, .el-button--primary:active {
    background: rgba(0, 181, 83, 0.75);
  }
  .el-button--small {
    font-size: 14px;
    color: #00b553;
  }
</style>
