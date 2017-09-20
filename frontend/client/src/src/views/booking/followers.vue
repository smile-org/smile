<template>
  <div>
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <ul class="b_header">
        <li v-for="item in data" :key="item.followerId">
          <img class="person_header" :src="item.avatar | formatImage">
          <div>
            <p>{{item.followerName}}</p>
            <span v-if="item.followerType === 'appointment_sponsor'">发起者</span>
            <span v-else-if="item.followerType === 'item_sponsor'">同约者</span>
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
export default {
  data: function () {
    return {
      id: 0,
      data: []
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.baseURL + uri
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
    })
  },
  methods: {

  }
}
</script>
