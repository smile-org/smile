<template>
  <div>
    <header>
      <div class="logo">
        <img class="vm" src="img/small_logo.png"/>
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
          <img src="img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">
          <div>
            <button class="inf_btn mr15">添加员工</button>
            <button class="inf_btn mr15">批量导入</button>
            <button class="inf_btn mr15">下载导入模板</button>
            <button class="inf_btn mr15">导  出</button>
          </div>
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input v-model="formInline.user" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="formInline.user" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.user" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.user" placeholder="区域"></el-input>
            </el-form-item>
            </el-form-item><el-form-item>
            <el-button class="inf_btn" type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
          </el-form>

        </div>
      </section>
    </div>

  </div>
</template>

<script>
  export default {

  }
</script>

<style>

</style>
