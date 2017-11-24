<template>
  <header>
    <div class="logo">
      <!--<img class="vm" :src="companyLogo" />-->
      <img class="vm" src="../assets/img/small_logo.png"/>
      <span class="vm" v-on:click="exit()">企业管理后台</span>
    </div>
    <el-dropdown @command="commandClick" trigger="click">
      <span class="el-dropdown-link vm">
         <img class="header_rimg"
              src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=547138142,3998729701&fm=27&gp=0.jpg">
         <span>{{fullName}}</span>
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown" style="right: 20px;left: auto">
        <el-dropdown-item command="changePWD">修改密码</el-dropdown-item>
        <el-dropdown-item command="exit">退出系统</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </header>
</template>
<script>
  import router from '../router'
  export default {
    data: function () {
      return {
        companyLogo: '',
        fullName: ''
      }
    },
    props: ['type'],
    created () {
      this.fullName = window.localStorage['fullname']
    },
    methods: {
      commandClick (name) {
        if (name === 'exit') {
          this.exit()
        } else if (name === 'changePWD') {
          this.changePWD()
        }
      },
      routeByName: function (name) {
        router.push({name: name})
      },
      changePWD: function () {
        router.push({name: 'changePWD'})
      },
      exit: function () {
        console.log('111')
        var storage = window.localStorage
        storage.removeItem('token')
        storage.removeItem('username')
        storage.removeItem('password')
        storage.removeItem('fullname')
        this.routeByName('login')
      },
      handleCommand (command) {
        this.$message('click on item ' + command)
      }
    }
  }
</script>

<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #fff;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  .el-dropdown {
    color: #fff;
    font-size: 14px;
    height: 60px;
    line-height: 60px;
    margin-right: 30px;
  }

  .el-icon-arrow-down {
    font-size: 12px;
    color: #fff;
    margin-left: 5px;
  }

  .header_rimg {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    overflow: hidden;
    vertical-align: middle;
    margin-right: 10px;
  }

  .el-dropdown-menu {
    text-align: center;
    font-size: 14px;
    right: 20px;
    left: auto
  }
</style>
