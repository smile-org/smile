<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="4"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">运营人员管理</span></span>
        </nav>
        <div class="con_tab">
          <button class="inf_btn" v-on:click="add()">添加运营人员</button>
          <el-dialog title="添加/编辑运营人员" :visible.sync="dialogAdminVisible">
            <el-form :inline="true" :model="formAdmin" :rules="rules" class="demo-form-inline mt20">
              <el-form-item label="人员姓名" prop="username">
                <el-input v-model="formAdmin.username" placeholder="人员姓名"></el-input>
              </el-form-item>
              <el-form-item label="手机号码" prop="tel">
                <el-input v-model="formAdmin.tel" placeholder="手机号码"></el-input>
              </el-form-item>
              <el-form-item style="margin-left:10px" label="电子邮箱" prop="email">
                <el-input v-model="formAdmin.email" placeholder="电子邮箱"></el-input>
              </el-form-item>
            </el-form>
            <div class="tc">
              <button type="button" class="inf_btn mt30 mb20" v-on:click="save">保 存

              </button>
            </div>
          </el-dialog>
          <el-table :data="tableData" border class="mt20" style="width: 100%">
            <el-table-column prop="full_name" align="center" label="姓名" width="">
            </el-table-column>
            <el-table-column prop="cell_phone" align="center" label="手机" width="">
            </el-table-column>
            <el-table-column prop="email" align="center" label="邮箱" width="">
            </el-table-column>
            <el-table-column label="操作" class="tc" width="140" align="center">
              <template scope="scope">
                <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
                <el-button @click="deleteUser(scope.row.user_id)" class="red_font" type="text" size="small">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="take"
                           layout="total, prev, pager, next" :total="total"></el-pagination>
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
          email: '',
          tel: '',
          userId: 0
        },
        saveMode: '',
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          tel: [
            { required: true, message: '请输入手机号', trigger: 'change' },
            { len: 11, message: '请输入正确格式的手机号码', trigger: 'blur' },
            { validator: (rule, value, callback) => {
              if (/^1[34578]\d{9}$/.test(value) === false) {
                callback(new Error('请输入正确格式的手机号码'))
              } else {
                callback()
              }
            },
              trigger: 'blur'}
          ],
          email: [
            { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
          ]
        }
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
        api.fetch(api.uri.getOperatorList, {
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
      add: function () {
        this.formAdmin.username = ''
        this.formAdmin.tel = ''
        this.formAdmin.email = ''
        this.dialogAdminVisible = true
        this.saveMode = 'add'
      },
      edit: function (row) {
        this.formAdmin.username = row.full_name
        this.formAdmin.tel = row.cell_phone
        this.formAdmin.email = row.email
        this.dialogAdminVisible = true
        this.formAdmin.userId = row.user_id
        this.saveMode = 'edit'
      },
      deleteUser: function (id) {
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteUser, {userid: id}).then(data => {
            this.queryAdminList()
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
      save: function () {
        if (this.saveMode === 'add') {
          console.log('add')
          api.fetch(api.uri.addOperator, {
            fullname: this.formAdmin.username,
            cellphone: this.formAdmin.tel,
            email: this.formAdmin.email
          }).then(data => {
            if (data.status === 1) {
              this.queryAdminList()
              this.dialogAdminVisible = false
            }
          }).catch(error => {
            this.$message(error.message)
          })
        } else {
          console.log('edit')
          api.fetch(api.uri.editOperator, {
            userid: this.formAdmin.userId,
            fullname: this.formAdmin.username,
            cellphone: this.formAdmin.tel,
            email: this.formAdmin.email
          }).then(data => {
            if (data.status === 1) {
              this.queryAdminList()
              this.dialogAdminVisible = false
            }
          }).catch(error => {
            this.$message(error.message)
          })
        }
      }
    }
  }
</script>

<style scoped="scope">
  .info_tab {
    /*padding: 5px;*/
  }

  .info_tab tr td {
    padding: 10px;
    font-size: 14px;
  }

  .info_tab tr td:first-child {
    /*font-weight:600;*/
    background: #f2f7f1;
  }

</style>
