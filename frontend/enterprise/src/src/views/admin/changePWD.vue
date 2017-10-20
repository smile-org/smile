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
          <form class="repeat_pwd">
            <div class="form_list">
              <div class="el-input" errormsg="密码长度为6-16位">
                <input autocomplete="off" placeholder="输入旧密码" type="password" rows="2" validateevent="true" class="el-input__inner">
              </div>
            </div>
            <div class="form_list">
              <div class="el-input" errormsg="密码长度为6-16位">
                <input autocomplete="off" placeholder="输入新密码" type="password" rows="2" validateevent="true" class="el-input__inner">
              </div>
            </div>
            <div class="form_list">
              <span class="login_icon2"></span>
              <div class="el-input" errormsg="密码长度为6-16位">
                <input autocomplete="off" placeholder="确认新密码" type="password" rows="2" validateevent="true" class="el-input__inner">
              </div>
            </div>
            <p class="form_warning" style="display: block;">
              <img src="" class="warnning_img">密码输入不一致
            </p>
            <button type="button" class="inf_btn btn_margin" v-on:click="routeByName('')">修  改</button>
          </form>
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
        company: {}
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
  .el-input__inner:hover {
    border-color: #01b554;
  }
  .el-input__inner:active {
    border-color: #01b554;
  }
  .el-input__inner:focus {
    border-color: #01b554;
  }
.el-input__inner{
  width:400px;
  margin-bottom:20px;
}
  .form_warning{
    margin-top:0px;
  }
</style>
