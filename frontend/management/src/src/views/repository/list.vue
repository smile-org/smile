<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="repository"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">管理驾驶舱</span></span>
        </nav>
        <div class="con_tab">

        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  //  import api from '../../servi_withTaskces/api'
  export default {
    data: function () {
      return {
        company: {},
        formInline: {
          name: '',
          user: '',
          // date: '',
          categoryId: '',
          start: '',
          end: ''
        }
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {

    },
    methods: {}
  }
</script>

<style scoped="scope">
  .info_tab {
    /*padding: 5px;*/
  }

  .info_tab tr td {
    padding: 10px;
    font-size: 14px;
  }

  .info_tab tr td:first-child {
    /*font-weight:600;*/
    background: #f2f7f1;
  }

</style>
