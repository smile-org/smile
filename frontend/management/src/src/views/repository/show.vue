<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="4"></navigator>
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
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  // var counter = 0
  export default {
    data: function () {
      return { a: 1 }
    },
    components: {
      navigator,
      commonHeader
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
