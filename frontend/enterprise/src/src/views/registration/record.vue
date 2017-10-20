<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="registration"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">员工报名记录</span> ></span>
        </nav>
        <div class="con_tab">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="name" label="姓名" width="">
            </el-table-column>
            <el-table-column prop="department" label="部门" width="100">
            </el-table-column>
            <el-table-column prop="address" label="区域" width="">
            </el-table-column>
            <el-table-column prop="tel" label="手机" width="">
            </el-table-column>
            <el-table-column prop="number" label="工号" width="">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="">
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
        form: {
          name: '',
          user: '',
          company: {},
          department: '',
          area: '',
          date: '',
          date1: ''
        },
        tableData: [{
          name: '王重阳',
          department: '技术部',
          address: '北京',
          tel: '136852636956',
          number: '125452655',
          email: '4dddd0dddd.qq.com'
        }, {
          name: '王重阳',
          department: '技术部',
          address: '北京',
          tel: '136852636956',
          number: '125452655',
          email: '4dddd0dddd.qq.com'
        }, {
          name: '王重阳',
          department: '技术部',
          address: '北京',
          tel: '136852636956',
          number: '125452655',
          email: '4dddd0dddd.qq.com'
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
        router.push({ name: name })
      }
    }
  }
</script>

<style>
  .el-input__inner:focus{
    border-color: #01b554;
  }
</style>
