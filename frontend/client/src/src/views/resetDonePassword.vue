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
        <div class=" face_img1"></div>
        <p class="face_font">恭喜您，密码修改成功</p>
        <button class="login_btn" type="button" v-on:click="login" >立即登录</button>
      </div>
    </section>
  </div>
</template>

<script>
import router from '../router/index'

export default {
  data: function () {
    return {

    }
  },

  methods: {
    goBack: function () {
      router.push({name: 'passwordReset'})
    },
    login: function () {
      router.push({name: 'login'})
    }
  }
}
</script>


