<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
     <navigator module="information"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">
          <!--&lt;!&ndash;此部分为上传页面&ndash;&gt;-->
          <table class="page_m" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                logo
              </td>
              <td class="page_m_b">
                <img src="../../assets/img/logo1.png" width="20%"/>
              </td>
              <td class="page_m_c">
                <a>使用默认</a>
                <a>上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                banner
              </td>
              <td class="page_m_b">
                <img src="../../assets/img/banner_05.png" width="100%"/>
              </td>
              <td class="page_m_c">
                <a>使用默认</a>
                <a>上传</a>
              </td>
            </tr>
          </table>
          <div class="tc">
            <button class="inf_btn btn_margin" v-on:click="routeByName('informationShow')">预  览</button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  // import router from '../../router'
  // import commonHeader from '../../components/CommonHeader'
  // import navigator from '../../components/Navigator'
  // import api from '../../services/api'
  export default {
    data: function () {
    },

    created () {
    },
    methods: {

    }
  }
</script>

<style>

</style>
