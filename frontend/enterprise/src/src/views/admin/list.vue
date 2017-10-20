<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="admin"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">系统管理</span> > <span class="f_blue">修改密码</span> </span>
        </nav>
        <div class="con_tab">
          <button class="inf_btn" v-on:click="routeByName('')">添加管理员</button>
          <el-table :data="tableData" border class="mt20" style="width: 100%">
            <el-table-column prop="user" label="姓名" width="">
            </el-table-column>
            <el-table-column prop="tel" label="手机" width="">
            </el-table-column>
            <el-table-column prop="number" label="工号" width="">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="">
            </el-table-column>
            <el-table-column prop="set" label="设置" width="">
              <template scope="scope">
                <el-button  type="text" size="small">置为普通员工</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="operate" label="操作" width="">
              <template scope="scope">
                <el-button  type="text" size="small">删除</el-button>
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
        company: {},
        tableData: [{
          user: '张三',
          tel: '手机',
          number: '工号',
          email: '工号',
          set: '工号',
          operate: '工号'
        }, {
          user: '张三',
          tel: '手机',
          number: '工号',
          email: '工号',
          set: '工号',
          operate: '工号'
        }, {
          user: '张三',
          tel: '手机',
          number: '工号',
          email: '工号',
          set: '工号',
          operate: '工号'
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
        router.push({name: name})
      }
    }
  }
</script>

<style scoped="scope">

</style>
