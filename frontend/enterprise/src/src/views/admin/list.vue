<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="admin"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 : <span class="">系统管理</span> > <span class="f_blue">公司管理员</span> </span>
                </nav>
                <div class="con_tab">
                    <button class="inf_btn" v-on:click="add()">添加管理员</button>
                    <el-dialog title="添加管理员" :visible.sync="dialogAdminVisible">
                        <el-form :inline="true" :model="formAdmin" class="demo-form-inline mt20">
                            <el-form-item label="姓名">
                                <el-input v-model="formAdmin.username" placeholder="姓名"></el-input>
                            </el-form-item>
                            <el-form-item class="wrapper">
                                <el-button type="success" @click="searchUser">查询</el-button>
                            </el-form-item>
                        </el-form>
                        <el-table :data="userData" border ref="multipleTable" @selection-change="changeFun">
                            <el-table-column property="user_id" width="100" type="selection" @selection-change="changeFun">
                            </el-table-column>
                            <el-table-column property="full_name" label="姓名" width=""></el-table-column>
                            <el-table-column property="cell_phone" label="手机" width=""></el-table-column>
                            <el-table-column property="department" label="部门"></el-table-column>
                        </el-table>
                        <el-pagination class="tc mt20" small layout="total, prev, pager, next"
                                       @current-change="handleAdminCurrentChange" :current-page="formAdminCurrentPage"
                                       :page-size="formAdminTake" :total="formAdminTotal"></el-pagination>
                        <div class="tc">
                            <button type="button" class="inf_btn mt30 mb20" v-on:click="saveAdmin">保 存
                            </button>
                        </div>
                    </el-dialog>
                    <el-table :data="tableData" border class="mt20" style="width: 100%">
                        <el-table-column prop="full_name" align="center" label="姓名" width="">
                        </el-table-column>
                        <el-table-column prop="cell_phone" align="center" label="手机" width="">
                        </el-table-column>
                        <el-table-column prop="job_number" align="center" label="工号" width="">
                        </el-table-column>
                        <el-table-column prop="email" align="center" label="邮箱" width="">
                        </el-table-column>
                        <el-table-column prop="set" align="center" label="设置" width="">
                            <template scope="scope">
                                <el-button type="text" size="small" v-on:click="setEmployee(scope.row.user_id)">置为普通员工
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="operate" align="center" label="操作" width="80">
                            <template scope="scope">
                                <el-button type="text" size="small" class="red_font" v-on:click="deleteEmployee(scope.row.user_id)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="ds_oq_pageF">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                                       :page-size="take" layout="total, prev, pager, next"
                                       :total="total"></el-pagination>
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
        take: 10,
        currentPage: 1,
        total: 0,
        tableData: [],
        dialogAdminVisible: false,
        formAdmin: {
          username: '',
          department: ''
        },
        formAdminTake: 10,
        formAdminTotal: 0,
        formAdminCurrentPage: 1,
        userData: [],
        // 搜索用户的user_id
        multipleTable: []
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.queryAdminList()
    },
    methods: {
      queryAdminList: function () {
        api.fetch(api.uri.getCompanyAdminList, {
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryAdminList()
      },
      setEmployee: function (userId) {
        this.$confirm('此操作将设置该管理员为普通员工，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.setEmployee, {userid: userId}).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '设置成功!'
              })
              this.queryAdminList()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消设置'
          })
        })
      },
      deleteEmployee: function (userId) {
        this.$confirm('此操作将设删除该员工，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteAdmin, {userid: userId}).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.queryAdminList()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      add: function () {
        this.dialogAdminVisible = true
      },
      searchUser: function () {
        api.fetch(api.uri.getCompanyEmployeeList, {
          fullname: this.formAdmin.username,
          take: this.formAdminTake,
          skip: this.formAdminTake * (this.formAdminCurrentPage - 1)
        }).then(data => {
          if (data.status === 1) {
            this.userData = data.result
            this.formAdminTotal = data.total
          }
        })
      },
      changeFun: function (val) {
        this.multipleTable = val
      },
      handleAdminCurrentChange: function (val) {
        this.formAdminCurrentPage = val
        this.searchUser()
      },
      saveAdmin: function () {
        console.log(this.multipleTable)
        var userids = ''
        for (var i = 0; i < this.multipleTable.length; i++) {
          userids += this.multipleTable[i].user_id + ','
        }
        if (userids === '') {
          this.$message({
            type: 'info',
            message: '请选择员工'
          })
          return
        }
        userids = userids.substring(0, userids.length - 1)
        console.log(userids)
        api.fetch(api.uri.addAdminList, {userids: userids}).then(data => {
          if (data.status === 1) {
            this.queryAdminList()
            this.dialogAdminVisible = false
          }
        })
      }
    }
  }
</script>

<!--<style scoped="scope">-->

<!--</style>-->
