<template>
  <header>
    <div class="logo">
      <!--<img class="vm" :src="companyLogo" />-->
      <img class="vm" src="../assets/img/jd_logo.png" style="width: 50px"/>
      <span class="vm">企业管理后台</span>
    </div>
    <ul class="fr">
      <li v-on:click="routeByName('informationDetail')" v-bind:class="{active: type==='information'}">
        <span class="h_icon h_icon01"></span>
        <p>基本信息</p>
      </li>
      <li v-on:click="routeByName('userList')" v-bind:class="{active: type==='user'}">
        <span class="h_icon h_icon02"></span>
        <p>员工管理</p>
      </li>
      <li v-on:click="routeByName('courseCategoryList')" v-bind:class="{active: type==='course'}">
        <span class="h_icon h_icon03"></span>
        <p>课程管理</p>
      </li>
      <li  v-on:click="routeByName('examList')" v-bind:class="{active: type==='exam'}">
        <span class="h_icon h_icon04"></span>
        <p>考试管理</p>
      </li>
      <li v-on:click="routeByName('registrationTrainlist')" v-bind:class="{active: type==='registration'}">
        <span class="h_icon h_icon05"></span>
        <p>报名管理</p>
      </li>
      <li v-on:click="routeByName('bookingList')" v-bind:class="{active: type==='booking'}">
        <span class="h_icon h_icon06"></span>
        <p>约课管理</p>
      </li>
      <li v-on:click="routeByName('taskingList')" v-bind:class="{active: type==='tasking'}">
        <span class="h_icon h_icon10"></span>
        <p>学习任务</p>
      </li>
      <li v-on:click="routeByName('memberList')" v-bind:class="{active: type==='membering'}">
        <span class="h_icon h_icon11"></span>
        <p>会员升级</p>
      </li>
      <li v-on:click="routeByName('adminList')" v-bind:class="{active: type==='admin'}">
        <span class="h_icon h_icon07"></span>
        <p class="name_e">公司管理员</p>
      </li>
      <li v-on:click="exit()">
        <span class="h_icon h_icon08"></span>
        <p>退出</p>
      </li>
    </ul>
  </header>
</template>

<script>
import router from '../router'
export default {
  data: function () {
    return {
      companyLogo: ''
    }
  },
  props: ['type'],
  created () {
    // retrieve logo from session storage
//    this.companyLogo = 'http://ww2.sinaimg.cn/thumb180/8892b46egw1f319hyl0juj20iz0sgq4v.jpg'
  },
  methods: {
    routeByName: function (name) {
      router.push({ name: name })
    },
    exit: function () {
      var storage = window.localStorage
      storage.removeItem('token')
      storage.removeItem('username')
      storage.removeItem('password')
      this.routeByName('login')
    }
  }
}
</script>

<style>

</style>
