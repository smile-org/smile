<template>
  <div>
    <header class="login_header">
      <div class="logo_c">
        <a class="tl" href="##"><img src="../assets/img/back.png" alt="返回" v-on:click="goBack" /></a>
        <a class="tc" href="##"><img src="../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"></a>
      </div>
    </header>
    <section>
      <div class="p3">
        <div class="login_a sologan1"></div>
        <div class="form_list">
          <span class=" login_icon3"></span>
          <el-input type="password" placeholder="输入新密码" errormsg="密码长度为6-16位" v-model="password" @input="$v.password.$touch()"></el-input>
        </div>
        <div class="form_list">
          <span class="login_icon3"></span>
          <el-input type="password" placeholder="确认新密码" errormsg="密码长度为6-16位" v-model="repeatPassword" @input="$v.repeatPassword.$touch()"></el-input>
        </div>
        <p class="form_warning" v-show="showError">
          <img class="warnning_img" src="../assets/img/warnning.png" />{{errorMessage}}
        </p>
        <button class="login_btn" type="button" :disabled="$v.password.$invalid || $v.repeatPassword.$invalid" v-on:click="resetPassword" >下一步</button>
      </div>

    </section>
  </div>
</template>

<script>
import router from '../router/index'
import api from '../services/api'
import md5 from 'js-md5'
import { required, minLength, maxLength } from 'vuelidate/lib/validators'

export default {
  data: function () {
    return {
      password: '',
      repeatPassword: '',
      showError: false,
      errorMessage: ''
    }
  },
  validations: {
    password: {
      required,
      minLength: minLength(6),
      maxLength: maxLength(16)
    },
    repeatPassword: {
      required,
      minLength: minLength(6),
      maxLength: maxLength(16)
    }
  },
  methods: {
    goBack: function () {
      router.push({name: 'codeVerification'})
    },
    resetPassword: function () {
      if (this.password !== this.repeatPassword) {
        this.showError = true
        this.errorMessage = '密码输入不一致'
        return
      }

      // 检查 sessionStorage 中的电话与验证码
      var usernameInSS = sessionStorage.getItem('smile_username')
      var codeInSS = sessionStorage.getItem('smile_code')

      if (!usernameInSS || !codeInSS) {
        this.showError = true
        this.errorMessage = '请重新获取验证码'
        return
      }

      var md5PWD = md5(this.password)
      md5PWD = this.password
      api.post(api.uri.resetPassword, {cellphone: usernameInSS, vcode: codeInSS, pwd: md5PWD}).then(data => {
        if (data.status === 1) {
          // 设置成功后清楚 SS
          sessionStorage.removeItem('smile_username')
          sessionStorage.removeItem('smile_code')
          router.push({name: 'passwordResetDone'})
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


