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
            <a v-bind:href="excelUrl"   class="inf_btn mr15 vm dis_in_block">下载导入模板</a>
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
              <el-table-column prop="full_name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="cell_phone" label="手机" width="180">
              </el-table-column>
              <el-table-column prop="job_number" label="工号"  width="180">
              </el-table-column>
              <el-table-column prop="email" label="邮箱" width="180">
              </el-table-column>
              <el-table-column prop="department" label="部门" width="180">
              </el-table-column>
              <el-table-column prop="area" label="区域"  width="180" >
              </el-table-column>
              <el-table-column prop="created_at" label="创建时间" width="180">
                <template scope="scope">
                  {{scope.row.created_at | formatDate}}
                </template>
              </el-table-column>
              <el-table-column prop="updated_at" label="最后修改时间" width="180">
                <template scope="scope">
                  {{scope.row.updated_at | formatDate}}
                </template>
              </el-table-column>
              <el-table-column  label="操作" class="tc" width="150">
                <template scope="scope"  >
                  <el-button @click="editUser(scope.row.user_id)" type="text" size="small">编辑</el-button>
                  <el-button @click="delUser(scope.row.user_id)" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  layout="total, prev, pager, next" :total="total"></el-pagination>
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
        take: 10,
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
      console.log(this.formInLine)
      api.fetch(api.uri.searchUserList, {
        fullname: this.formInLine.name,
        cellphone: this.formInLine.mobile,
        department: this.formInLine.department,
        area: this.formInLine.area,
        skip: 0,
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
    filters: {
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    methods: {
      editUser: function (id) {
        router.push({name: 'userEdit', query: {id: id}})
      },
      delUser: function (id) {
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteUser, {userid: id}).then(data => {
            this.onSubmit()
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      handleCurrentChange: function (value) {
        this.currentPage = value
        api.fetch(api.uri.searchUserList, {
          fullname: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          cellphone: this.formInLine.mobile,
          skip: (this.currentPage - 1) * this.take,
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
          fullname: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          cellphone: this.formInLine.mobile,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      routeByName: function (name) {
        router.push({name: name})
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
