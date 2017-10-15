<template>
  <div>
    <header>
      <div class="logo">
        <img class="vm" src="../../assets/img/small_logo.png"/>
        <span class="vm">企业管理后台</span>
      </div>
      <ul class="fr">
        <li>
          <span class="h_icon h_icon01"></span>
          <p>基本信息</p>
        </li>
        <li>
          <span class="h_icon h_icon02"></span>
          <p>员工管理</p>
        </li>
        <li>
          <span class="h_icon h_icon03"></span>
          <p>课程类别</p>
        </li>
        <li>
          <span class="h_icon h_icon04"></span>
          <p>考试管理</p>
        </li>
        <li>
          <span class="h_icon h_icon05"></span>
          <p>报名管理</p>
        </li>
        <li>
          <span class="h_icon h_icon06"></span>
          <p>约课管理</p>
        </li>
        <li>
          <span class="h_icon h_icon07"></span>
          <p class="name_e">公司管理员</p>
        </li>
        <li>
          <span class="h_icon h_icon08"></span>
          <p>退出</p>
        </li>
      </ul>
    </header>
    <div class="con_main">
      <aside>
        <div class="a_tit">
          <span class="h_icon aside_icon01 vm"></span>
          <span class="vm">基本信息</span>
        </div>
        <ul class="tc">
          <li v-on:click="routeByName('informationDetail')">
            <span class="vm">公司基本信息</span>
          </li>
          <li v-on:click="routeByName('informationEdit')">
            <span class="vm">页面定置管理</span>
          </li>
        </ul>
      </aside>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">

          <!--此部分为信息列表-->
          <ul class="basic_inf">
            <li>
              <span>公司名称：</span> <span>北京某某科技股份有限公司</span>
            </li>
            <li>
              <span>联系人：</span> <span>张某某</span>
            </li>
            <li>
              <span>联系电话：</span> <span>13801109876</span>
            </li>
            <li>
              <span>所在省份：</span> <span>北京市</span>
            </li>
            <li>
              <span>所在城市：</span> <span>朝阳区</span>
            </li>
            <li>
              <span>公司地址：</span> <span>望京soho</span>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import router from '../../router'
  // var counter = 0
  export default {
    data: function () {
      return { a: 1 }
    },

    created () {
      console.log('created')
    },
    methods: {
      routeByName: function (name) {
        console.log(name)
        router.push({ name: name })
      }
    }
  }
</script>

<style>

</style>
