<template>
  <div>
    <header>
      <div class="logo">
        <img class="vm" src="../../assets/img/small_logo.png"/>
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
          <!--&lt;!&ndash;此部分为上传页面&ndash;&gt;-->
          <div class="m_show">
            <div class="m_show_header">
              <img src="../../assets/img/small_logo.png"/>
            </div>
            <!--<img src="img/p_header.jpg"/>-->
            <img class="m_show_banner" src="../../assets/img/banner_bg.png" width="100%"/>
          </div>
          <div class="tc">
            <button class="inf_btn btn_margin" v-on:click="routeByName('informationEdit')">返  回</button>
          </div>
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
