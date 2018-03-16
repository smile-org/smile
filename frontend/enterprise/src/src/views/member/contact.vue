<template>
  <div>
    <common-header type="membering"></common-header>
    <div class="con_main">
      <navigator module="membering" menu="contact"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="f_blue">学习任务管理</span></span>
        </nav>
        <div class="con_tab member_bg">
          <ul class="member_contact">
            <li class="contact">联系“玖点e学”客服咨询会员升级</li>
            <li class="contact">客服QQ：<span>321398164</span></li>
            <li class="contact">客服邮箱：<span>kefu@xueland.com</span></li>
          </ul>
          <img src="../../assets/img/member_plane.png" class="member_plane">
        </div>
      </section>
    </div>

  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
//  import api from '../../services/api'
//  import moment from 'moment'
//  import router from '../../router'
//  import axios from 'axios'
  export default {
    components: {
      commonHeader,
      navigator
    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
