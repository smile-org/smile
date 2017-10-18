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
            <button v-on:click="routeByName('userCreate')" class="inf_btn mr15">添加员工</button>
            <button class="inf_btn mr15">批量导入</button>
            <button class="inf_btn mr15">下载导入模板</button>
            <button class="inf_btn mr15">导  出</button>
          </div>
          <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
            <!--<el-row>-->
            <el-form-item label="姓名">
              <el-input v-model="formInline.user" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="formInline.num" placeholder="手机号"></el-input>
            </el-form-item>
            <!--</el-row>-->
            <!--<el-row>-->
            <el-form-item label="部门">
              <el-input v-model="formInline.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            <!--</el-row>-->
          </el-form>
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="number" label="手机" width="180">
              </el-table-column>
              <el-table-column prop="workNum" label="工号"  width="180">
              </el-table-column>
              <el-table-column prop="email" label="邮箱" width="180">
              </el-table-column>
              <el-table-column prop="department" label="部门" width="180">
              </el-table-column>
              <el-table-column prop="address" label="区域"  width="180" >
              </el-table-column>
              <el-table-column prop="timeStart" label="创建时间" width="180">
              </el-table-column>
              <el-table-column prop="timeEnd" label="最后修改时间" width="180">
              </el-table-column>
              <el-table-column  label="操作" class="tc" width="">
                <template scope="scope"  >
                  <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                  <el-button @click="checkFail(scope.row.id)" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="ds_oq_handleCurrentChange" :current-page="currentPage"  :page-size="10" layout="total, prev, pager, next" :total="totalRow"></el-pagination>
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
  export default {
    data: function () {
      return {
        company: {},
        formInline: {
          user: '',
          region: ''
        },
        tableData: [{
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
        }, {
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
        }, {
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222'
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
      onSubmit: function () {
        console.log('submit!')
      },
      routeByName: function (name) {
        console.log(name)
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
