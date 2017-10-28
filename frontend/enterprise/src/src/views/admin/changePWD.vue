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
                                <input autocomplete="off" placeholder="输入旧密码" type="password" rows="2"
                                       validateevent="true" class="el-input__inner" v-model="oldPass">
                            </div>
                        </div>
                        <div class="form_list">
                            <div class="el-input" errormsg="密码长度为6-16位">
                                <input autocomplete="off" placeholder="输入新密码" type="password" rows="2"
                                       validateevent="true" class="el-input__inner" v-model="newPass">
                            </div>
                        </div>
                        <div class="form_list">
                            <span class="login_icon2"></span>
                            <div class="el-input" errormsg="密码长度为6-16位">
                                <input autocomplete="off" placeholder="确认新密码" type="password" rows="2"
                                       validateevent="true" class="el-input__inner" v-model="confirmPass">
                            </div>
                        </div>
                        <p class="form_warning" style="display: block;">
                            <img src="" class="warnning_img">{{msg}}
                        </p>
                        <button type="button" class="inf_btn btn_margin" v-on:click="updatePassword()">修  改</button>
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

  export default {
    data: function () {
      return {
        oldPass: '',
        newPass: '',
        confirmPass: '',
        msg: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {

    },
    methods: {
      updatePassword: function () {
        this.msg = ''
        if (this.oldPass === '') {
          this.msg = '旧密码不能为空'
          return
        }
        if (this.newPass === '') {
          this.msg = '新密码不能为空'
          return
        }
        if (this.confirmPass === '') {
          this.msg = '确认密码不能为空'
          return
        }
        if (this.newPass !== this.confirmPass) {
          this.msg = '两次输入的密码不一致'
          return
        }
        api.post(api.uri.updatePassword, {password: this.newPass}).then(data => {
          if (data.status === 1) {
            this.$message({
              type: 'success',
              message: '密码修改成功!'
            })
            this.oldPass = ''
            this.newPass = ''
            this.confirmPass = ''
          }
        })
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

    .el-input__inner {
        width: 400px;
        margin-bottom: 20px;
    }

    .form_warning {
        margin-top: 0px;
    }
</style>
