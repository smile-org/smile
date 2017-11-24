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
          <el-dialog title="添加运营人员" :visible.sync="dialogAdminVisible">
            <el-form :inline="true" :model="formAdmin" class="demo-form-inline mt20">
              <el-form-item label="人员姓名">
                <el-input v-model="formAdmin.username" placeholder="人员姓名"></el-input>
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input v-model="formAdmin.tel" placeholder="手机号码"></el-input>
              </el-form-item>
              <el-form-item label="电子邮箱">
                <el-input v-model="formAdmin.email" placeholder="电子邮箱"></el-input>
              </el-form-item>
            </el-form>
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
            <el-table-column prop="email" align="center" label="邮箱" width="">
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
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
          department: ''
        },
        formAdminTake: 10,
        formAdminTotal: 0,
        formAdminCurrentPage: 1
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
      add: function () {
        this.dialogAdminVisible = true
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

<style scoped="scope">
  .info_tab {
    /*padding: 5px;*/
  }

  .info_tab tr td {
    padding: 10px;
    font-size: 14px;
  }
  .info_tab tr td:first-child{
    /*font-weight:600;*/
    background: #f2f7f1;
  }

</style>
