<template>
  <section class="login_bg">
    <div class="pos_center">
      <h1><img src="../assets/img/logo1.png">企业管理后台</h1>
      <div class="login_con">
        <h3>欢迎登录企业管理后台</h3>
        <hr   noshade="noshade">
        <form>
          <p class="form_warning">请输入正确密码 </p>
          <div class="form_list">
            <span class="img_person"></span>
            <input type="text" id="UserName"  placeholder="请输入用户名"  required autocomplete="off">
          </div>
          <div class="form_list">
            <span class="img_loack"></span>
            <input type="password" id="PassWord"   placeholder="请输入密码"  errormsg="密码长度为6-16位" autocomplete="off" required/>
          </div>
          <ul class="hidden">
            <li class="fl">
              <input type="checkbox" name="memberPass" class="" checked="checked"/>
              <label>保持我的登录状态</label>
            </li>
            <!--<li class="fr">-->
              <!--<span class="">忘记密码</span>-->
            <!--</li>-->
          </ul>
          <div>
            <button>登  录</button>
          </div>
        </form>

      </div>
    </div>
  </section>
  <!--<div>-->
    <!--im index-->
    <!--<router-link :to="{name: 'infoDetail'}">-->
      <!--link to info-->
    <!--</router-link>-->
  <!--</div>-->
</template>

<script>
export default {

}
</script>

<style>

</style>
