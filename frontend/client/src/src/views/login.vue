<template>
  <div>
    <header>
      <div class="logo_c">
        <a class="tl" href="##"></a>
        <a class="tc" href="##"><img src="../assets/img/logo.png" alt="smile"  class="logo1"/></a>
        <a class="tr" href="##"><img src="../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <form>
        <div class="login_a sologan1"></div>
        <div class="form_list">
          <span class=" login_icon1"></span>
          <el-input type="text"  placeholder="请输入用户名"></el-input>
        </div>
        <div class="form_list">
          <span class="login_icon2"></span>
          <el-input type="password"  placeholder="请输入密码" errormsg="密码长度为6-16位" ></el-input>
        </div>
        <p class="form_warning">
          <img class="warnning_img" src="../assets/img/warnning.png"/>手机号码或密码错误，请重新输入
        </p>
        <a href="#" class="unable_login">无法登陆，点击找回密码</a>
        <button class="login_btn" type="button">登 录</button>
      </form>
    </section>
  </div>
</template>

<script>
// import {required, between} from 'vuelidate/lib/validators'
// import api from '../services/api'
// import router from '../router/index'
// export default {
//   data: function () {
//     return {
//       // isDisabled: false,
//       username: '',
//       password: '',
//       age: 0
//     }
//   },
//   validations: {
//     username: {
//       required
//       // email,
//       // minLength: minLength(4)
//     },
//     age: {
//       between: between(20, 30)
//     },
//     password: {
//       required
//     }
//   },
//   methods: {
//     login: function () {
//       var uri = api.uri.login
//       api.post(uri, {username: this.username, password: this.password}).then(data => {
//         if (data.status) {
//           sessionStorage.setItem('smile', 'lalalal')
//           router.push({name: 'home'})
//         }
//       }).catch(error => {
//         alert(error.message)
//       })
//     }
//   }

// }
</script>


