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
                    <el-table :data="tableData" border class="mt20" style="width: 100%">
                        <el-table-column prop="full_name" label="姓名" width="">
                        </el-table-column>
                        <el-table-column prop="cell_phone" label="手机" width="">
                        </el-table-column>
                        <el-table-column prop="job_number" label="工号" width="">
                        </el-table-column>
                        <el-table-column prop="email" label="邮箱" width="">
                        </el-table-column>
                        <el-table-column prop="set" label="设置" width="">
                            <template scope="scope">
                                <el-button type="text" size="small" v-on:click="setEmployee(scope.row.user_id)">置为普通员工

                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="operate" label="操作" width="">
                            <template scope="scope">
                                <el-button type="text" size="small" v-on:click="deleteEmployee(scope.row.user_id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="ds_oq_pageF" style="margin:10px 38%">
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
        tableData: []
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
        alert('popup')
      }
    }
  }
</script>

<style scoped="scope">

</style>
