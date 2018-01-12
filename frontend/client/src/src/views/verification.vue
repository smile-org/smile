<template>
  <div>
    <header class="login_header">
      <div class="logo_c">
        <a class="tl" href="##"><img src="../assets/img/back.png" alt="返回" v-on:click="goBack" /></a>
        <a class="tc" href="##"><img src="../assets/img/jd_logo.png" alt="smile" class="logo1"/></a>
        <a class="tr" href="##"></a>
      </div>
    </header>
    <section>
      <div class="p3">
        <div class="login_a sologan2"></div>
        <div class="form_list">
          <span class=" login_icon1"></span>
          <el-input type="text" placeholder="请输入您的手机号" v-model="username" @input="$v.username.$touch()"></el-input>
        </div>
        <el-row class="form_list">
          <el-col :span="14">
            <span class="login_icon3"></span>
            <el-input placeholder="验证码" v-model="code" @input="$v.code.$touch()"></el-input>
          </el-col>
          <el-col class="form_num " :span="8" :offset="2">
            <div class="form_num_s " v-on:click="getCode" v-show="showCode" :disabled="$v.username.$invalid">
              获取验证码
            </div>
            <div class="form_num_s " v-show="!showCode">
              {{countDown}} s
            </div>
          </el-col>
        </el-row>
        <p class="form_warning" v-show="showError">
          <img class="warnning_img" src="../assets/img/warnning.png" />{{errorMessage}}
        </p>
        <button class="login_btn" type="button" v-on:click="verifyCode" :disabled="$v.code.$invalid || $v.username.$invalid">下一步</button>

      </div>
      </section>
  </div>
</template>

<script>
import router from '../router/index'
import api from '../services/api'
import { required } from 'vuelidate/lib/validators'

export default {
  data: function () {
    return {
      username: '',
      code: '',
      showError: false,
      errorMessage: '',

      // 显示获取验证码
      showCode: true,

      // 倒计时
      countDown: 0,

      // 定时器
      timer: null
    }
  },
  created () {
    sessionStorage.removeItem('smile_username')
    sessionStorage.removeItem('smile_code')
  },
  validations: {
    username: {
      required
    },
    code: {
      required
    }
  },
  methods: {
    goBack: function () {
      router.push({ name: 'login' })
    },

    // 通过手机号获取验证码
    getCode: function () {
      var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/
      if (!reg.test(this.username)) {
        this.showError = true
        this.errorMessage = '请输入正确格式的手机号码'
        return
      }
      const TIME_COUNT = 59
      var uri = api.uri.getCode
      api.fetch(uri, { cellphone: this.username }).then(data => {
        if (data.status === 1) {
          this.showError = false
          this.errorMessage = ''
          if (!this.timer) {
            this.countDown = TIME_COUNT
            this.showCode = false
            this.timer = setInterval(() => {
              if (this.countDown > 0 && this.countDown <= TIME_COUNT) {
                this.countDown--
              } else {
                this.showCode = true
                clearInterval(this.timer)
                this.timer = null
              }
            }, 1000)
          }
        } else {
          this.showError = true
          this.errorMessage = data.result
        }
      }).catch(error => {
        this.showError = true
        this.errorMessage = error.message
      })
    },

    // 提交验证码!
    verifyCode: function () {
      var uri = api.uri.verifyCode
      api.post(uri, { cellphone: this.username, vcode: this.code }).then(data => {
        if (data.status === 1) {
          sessionStorage.setItem('smile_username', this.username)
          sessionStorage.setItem('smile_code', this.code)
          router.push({ name: 'passwordReset' })
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


