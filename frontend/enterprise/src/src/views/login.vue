<template>
    <section class="login_bg">
        <div class="pos_center">
            <h1><img src="../assets/img/logo1.png">企业管理后台</h1>
            <div class="login_con">
                <h3>欢迎登录企业管理后台</h3>
                <hr noshade="noshade">
                <form>
                    <p class="form_warning">{{msg}}</p>
                    <div class="form_list">
                        <span class="img_person"></span>
                        <input type="text" id="UserName" v-model="username" placeholder="请输入用户名" required
                               autocomplete="off">
                    </div>
                    <div class="form_list">
                        <span class="img_loack"></span>
                        <input type="password" id="PassWord" v-model="password" placeholder="请输入密码"
                               errormsg="密码长度为6-16位" autocomplete="off" required/>
                    </div>
                    <ul class="hidden">
                        <li class="fl">
                            <input type="checkbox" name="memberPass" class="vm" v-model="keepAlive"/>
                            <label>保持我的登录状态</label>
                        </li>
                    </ul>
                    <div>
                        <button type="button" v-on:click="login()">登   录</button>
                    </div>
                </form>

            </div>
        </div>
    </section>
</template>

<script>
  import api from '../services/api'
  import router from '../router'
  import md5 from 'js-md5'
  export default {
    data: function () {
      return {
        username: '',
        password: '',
        token: '',
        keepAlive: false,
        msg: ''
      }
    },
    created () {
      var storage = window.localStorage
      var token = storage['token']
      this.username = storage['username']
      this.password = storage['password']
      console.log(token)
    },
    methods: {
      login () {
        this.msg = ''
        var md5Password = md5(this.password)
        console.log(this.keepAlive, this.username, md5Password)
        if (!this.username || !this.password) {
          this.msg = '请输入用户名和密码'
          return
        }
        api.post(api.uri.login, {cellphone: this.username, pwd: md5Password}).then(data => {
          if (data.status === 1) {
            this.token = data.result
            var storage = window.localStorage
            storage['token'] = this.token
            if (this.keepAlive) {
              storage['username'] = this.username
              storage['password'] = md5Password
            }
            router.push({name: 'informationDetail'})
          } else {
            this.msg = '用户名或密码错误'
          }
        }).catch(() => {
          console.error('err')
        })
      }
    }
  }
</script>

<style>

</style>
