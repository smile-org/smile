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
              <span>公司名称：</span> <span>{{company.company_name}}</span>
            </li>
            <li>
              <span>联系人：</span> <span>{{company.contact_person}}</span>
            </li>
            <li>
              <span>联系电话：</span> <span>{{company.phone_number}}</span>
            </li>
            <li>
              <span>所在省份：</span> <span>{{company.province}}</span>
            </li>
            <li>
              <span>所在城市：</span> <span>{{company.city}}</span>
            </li>
            <li>
              <span>公司地址：</span> <span>{{company.address}}</span>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import api from '../../services/api'
  export default {
    data: function () {
      return {
        company: {}
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.company = data.result
        }
      })
    },
    methods: {
    }
  }
</script>

<style>

</style>
