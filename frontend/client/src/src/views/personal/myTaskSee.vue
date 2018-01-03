<template>
  <div id="app">
    <!--<header>-->
    <!--<div class="logo_c">-->
    <!--<a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>-->
    <!--<a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>-->
    <!--<a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>-->
    <!--</div>-->
    <!--</header>-->
    <common-header></common-header>
    <section>
      <p class="tasking_member">
        <img src="../../assets/img/progress.png" class="vm mr1" />您打败了<span>{{data.percentage}}</span>的同学
      </p>
      <ul class="course_con list_border ">
        <li class="tasking_memberFont" v-for="item in data.UserList" :key="item.avatar">
          <img :src="item.avatar|formatImage" class="vm mr1">
          <span class="vm mr1">{{item.user_name}} </span>
          <span class="vm">当前完成进度：{{item.percentage}}</span>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
  import api from '../../services/api'
  import axios from 'axios'
  import commonHeader from '../../components/CommonHeader'
  export default {
    data: function () {
      return {
        id: 0,
        data: {}
      }
    },
    components: {
      commonHeader
    },
    filters: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri
      }
    },
    created () {
      this.id = this.$route.query.id
      api.fetch(api.uri.getTaskUserRanking, {taskid: this.id}).then(data => {
        if (data.status === 1) {
          console.log(data.result)
          this.data = data.result
        }
      })
    }
  }
</script>

<style scoped="scope">

</style>
