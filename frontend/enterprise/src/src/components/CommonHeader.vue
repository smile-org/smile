<template>
  <header>
    <div class="logo">
      <img class="vm" :src="companyLogo" />
      <span class="vm">企业管理后台</span>
    </div>
    <ul class="fr">
      <li v-on:click="routeByName('informationDetail')">
        <span class="h_icon h_icon01"></span>
        <p>基本信息</p>
      </li>
      <li v-on:click="routeByName('userList')">
        <span class="h_icon h_icon02"></span>
        <p>员工管理</p>
      </li>
      <li v-on:click="routeByName('courseCategoryList')">
        <span class="h_icon h_icon03"></span>
        <p>课程类别</p>
      </li>
      <li>
        <span class="h_icon h_icon04"></span>
        <p>考试管理</p>
      </li>
      <li v-on:click="routeByName('registrationDetail')">
        <span class="h_icon h_icon05"></span>
        <p>报名管理</p>
      </li>
      <li v-on:click="routeByName('bookingDetail')">
        <span class="h_icon h_icon06"></span>
        <p>约课管理</p>
      </li>
      <li v-on:click="routeByName('adminList')">
        <span class="h_icon h_icon07"></span>
        <p class="name_e">公司管理员</p>
      </li>
      <li>
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
  created () {
    // retrieve logo from session storage
    this.companyLogo = 'http://ww2.sinaimg.cn/thumb180/8892b46egw1f319hyl0juj20iz0sgq4v.jpg'
  },
  methods: {
    routeByName: function (name) {
      router.push({ name: name })
    }
  }
}
</script>

<style>

</style>
