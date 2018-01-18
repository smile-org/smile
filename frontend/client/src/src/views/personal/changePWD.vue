<template>
<div id="app">
  <common-header></common-header>
  <section>
    <form>
      <!--<div class="login_a sologan1"></div>-->
      <div class="form_list">
        <span class=" login_icon2"></span>
        <el-input type="password"  placeholder="输入旧密码" v-model="oldPassword" errormsg="密码长度为6-16位" ></el-input>
      </div>
      <div class="form_list">
        <span class=" login_icon2"></span>
        <el-input type="password"  placeholder="输入新密码" v-model="newPassword" errormsg="密码长度为6-16位" ></el-input>
      </div>
      <div class="form_list">
        <span class="login_icon2"></span>
        <el-input type="password"  placeholder="确认新密码" v-model="confirmPassword" errormsg="密码长度为6-16位" ></el-input>
      </div>
      <p class="form_warning" v-show="showError">
        <img class="warnning_img" src="../../assets/img/warnning.png" />密码输入不一致
      </p>
      <button class="login_btn" v-on:click="changePWD" type="button">确认</button>
    </form>
  </section>
</div>
</template>
<script>
  import api from '../../services/api'
  import commonHeader from '../../components/CommonHeader'
  import router from '../../router'
  import md5 from 'js-md5'
  export default {
    data: function () {
      return {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
        showError: false
      }
    },
    components: {
      commonHeader
    },
    methods: {
      changePWD: function () {
        if (this.newPassword !== this.confirmPassword) {
          this.showError = true
          return
        }
        api.post(api.uri.updatePassword, {password: md5(this.confirmPassword)}).then(data => {
          if (data.status === 1) {
            // TODO: element ui 弹出框后再跳转
            router.push({name: 'me'})
          } else {
            this.showError = true
            this.errorMessage = data.result
          }
        }).catch(error => {
          this.showError = true
          this.errorMessage = error.message
        })
      }
    }
  }
</script>
