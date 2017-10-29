<template>
  <div>
    <!--<header>-->
      <!--<div class="logo_c">-->
        <!--<a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>-->
        <!--<a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>-->
        <!--<a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>-->
      <!--</div>-->
    <!--</header>-->
    <common-header></common-header>
    <section>
      <ul class="b_header hidden">
        <li v-for="item in data" :key="item.followerId">
          <img class="person_header" :src="item.avatar | formatImage">
          <div>
            <p>{{item.followerName}}</p>
            <span v-if="item.followType === 'appointment_sponsor'">发起者</span>
            <span v-else-if="item.followType === 'item_sponsor'">同约者</span>
            <span v-else>响应者</span>
          </div>
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
      data: []
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
    api.fetch(api.uri.getBookingFollowers, { appointmentId: this.id }).then(data => {
      if (data.status === 1) {
        this.data = data.result
      } else {
        // TODO: 统一处理
      }
    }).catch(error => {
      // TODO: 统一处理
      console.log(error.message)
    })
  },
  methods: {

  }
}
</script>
