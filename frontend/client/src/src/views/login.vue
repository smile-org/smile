<template>
  <div>
    <header>
      <div class="logo_c">
        <a class="tl" href="##"></a>
        <a class="tc" href="##"><img src="../assets/img/logo.png" alt="smile" class="logo1"/></a>
        <a class="tr" href="##"></a>
      </div>
    </header>
    <section>
      <div class="p3">
        <div class="login_a sologan1"></div>
        <div class="form_list">
          <span class=" login_icon0"></span>
          <el-input type="text" placeholder="请输入用户名" v-model.trim="username" @input="$v.username.$touch()"></el-input>
        </div>
        <div class="form_list">
          <span class="login_icon2"></span>
          <el-input type="password" placeholder="请输入密码" errormsg="密码长度为6-16位" v-model.trim="password"
                    @input="$v.password.$touch()"></el-input>
        </div>
        <p class="form_warning" v-show="showError">
          <img class="warnning_img" src="../assets/img/warnning.png"/>{{errorMessage}}
        </p>
        <!--<a href="#" class="unable_login">无法登陆，点击找回密码</a>-->
        <div class="rember_pws">
          <input type="checkbox" class=" vm" name="memberPass" v-model="keepAlive"/>
          <label class="vm" style="font-size: 0.26rem;  color: #666;margin-left: -.1rem;">保持我的登录状态</label>
        </div>

        <router-link :to="{name: 'codeVerification'}" class="unable_login">无法登陆，点击找回密码</router-link>
        <button class="login_btn" :disabled="$v.username.$invalid || $v.password.$invalid" v-on:click='login'>登 录
        </button>
      </div>
    </section>
  </div>
</template>

<script>
  import {required, minLength, maxLength} from 'vuelidate/lib/validators'

  // ajax请求封装对象
  import api from '../services/api'

  // 路由管理对象
  import router from '../router/index'
  import md5 from 'js-md5'

  export default {
    data: function () {
      return {
        username: '',
        password: '',
        // isDisabled: false,
        showError: false,
        errorMessage: ''
      }
    },
    validations: {
      username: {
        required
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(16)
      }
    },
    methods: {
      login: function () {
        var uri = api.uri.login
        var md5PWD = md5(this.password)
        // md5PWD = this.password
        api.post(uri, {cellphone: this.username, pwd: md5PWD}).then(data => {
          if (data.status === 1) {
            this.showError = false
            this.errorMessage = ''
            sessionStorage.setItem('smile', data.result)
            sessionStorage.setItem('username', data.userInfo.full_name)
            sessionStorage.setItem('userAvatar', data.userInfo.avatar)
            router.push({name: 'homepage'})
          } else {
            this.showError = true
            this.errorMessage = '手机号码或密码错误，请重新输入'
          }
        }).catch(error => {
          this.showError = true
          this.errorMessage = error.message
        })
      }
    }
  }
</script>

<style>
  .rember_pws {
    height: 40px;
  }

  .rember_pws input {
    border: 1px solid #dcdcdc;
    background: #fff;
    width: 16px;
    height: 16px;
  }
</style>
