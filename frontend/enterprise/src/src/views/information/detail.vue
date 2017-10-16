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
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  export default {
    data: function () {
      return { a: 1 }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
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
