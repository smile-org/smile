<template>
  <div>
    <header>
      <div class="logo">
        <img class="vm" src="../../assets/img/small_logo.png"/>
        <span class="vm">企业管理后台</span>
      </div>
      <ul class="fr">
        <li>
          <span class="h_icon h_icon01"></span>
          <p>基本信息</p>
        </li>
        <li>
          <span class="h_icon h_icon02"></span>
          <p>员工管理</p>
        </li>
        <li>
          <span class="h_icon h_icon03"></span>
          <p>课程类别</p>
        </li>
        <li>
          <span class="h_icon h_icon04"></span>
          <p>考试管理</p>
        </li>
        <li>
          <span class="h_icon h_icon05"></span>
          <p>报名管理</p>
        </li>
        <li>
          <span class="h_icon h_icon06"></span>
          <p>约课管理</p>
        </li>
        <li>
          <span class="h_icon h_icon07"></span>
          <p class="name_e">公司管理员</p>
        </li>
        <li>
          <span class="h_icon h_icon08"></span>
          <p>退出</p>
        </li>
      </ul>
    </header>
    <div class="con_main">
      <aside>
        <div class="a_tit">
          <span class="h_icon aside_icon01 vm"></span>
          <span class="vm">基本信息</span>
        </div>
        <ul class="tc">
          <li>
            <span class="vm">公司基本信息</span>
          </li>
          <li>
            <span class="vm">页面定置管理</span>
          </li>
        </ul>
      </aside>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">
          <div>
            <button class="inf_btn mr15">添加员工</button>
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
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="number" label="手机" width="180">
              </el-table-column>
              <el-table-column prop="workNum" label="工号">
              </el-table-column>
              <el-table-column prop="email" label="邮箱" width="180">
              </el-table-column>
              <el-table-column prop="department" label="部门" width="180">
              </el-table-column>
              <el-table-column prop="address" label="区域">
              </el-table-column>
              <el-table-column prop="date" label="创建时间" width="180">
              </el-table-column>
              <el-table-column prop="name" label="最后修改时间" width="180">
              </el-table-column>
              <el-table-column prop="address" label="">
              </el-table-column>
            </el-table>
          </template>
        </div>
      </section>
    </div>

  </div>
</template>

<script>
  export default {
    data: function () {
      return {
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
          timeEnd: '12323243222',
          workNumssss: '12345644'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    methods: {
      onSubmit: function () {
        console.log('submit!')
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
</style>
