
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
      <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.appointment_id">
          <router-link v-bind:to="{name: 'getBooking', query: {id: item.appointmentId}}">
            <div class="hidden">
              <img class="person_header fl" :src="item.avatar | formatImage">
              <div class="bm_con">
                <div class="hidden bm_font ml2">
                  <h3 class="fl ">{{item.appointmentTitle}}</h3>
                  <p>主讲：{{item.sponsorName}}
                    <span class="ml2">{{item.sponsorDate | formatDate}}</span>
                  </p>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{item.followerCount}}</span>
                    </li>
                  </ul>
                  <span class="see_student">
                    <router-link v-bind:to="{name: 'getBookingFollowers', query: {id: item.appointmentId}}">
                    查看同学
                    </router-link>
                  </span>
                </div>
              </div>
            </div>
            <ul class="keword_num">
              <li v-for="(keyword, index) in item.keywords" :key="index">{{keyword}}</li>
            </ul>
          </router-link>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import { formatDate } from '../../common/date'
import commonHeader from '../../components/CommonHeader'
export default {
  data: function () {
    return {
      data: [],
      busy: false,
      take: 20,
      currentPage: -1
    }
  },
  components: {
    commonHeader
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    },
    formatDate: function (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    }
  },
  methods: {
    loadMore: function () {
      this.currentPage = this.currentPage + 1
      this.busy = true
      api.fetch(api.uri.getMyAppointmentList, { take: this.take, skip: this.currentPage * this.take }).then(data => {
        if (data.status === 1) {
          if (data.result.length === this.take) {
            this.busy = false
          }
          // 只有实际拿到数据后， 才附加到data属性上
          if (data.result.length > 0) {
            for (var i = 0; i < data.result.length; i++) {
              data.result[i].keywordArray = data.result[i].keyword.split(',')
            }
            this.data = this.data.concat(data.result)
          }
        } else {
          // todo:
        }
      }).catch(error => {
        console.log(error.message)
      })
    }
  }
}
</script>
<style scoped="scope">
  .bm_font .small_icon {
    top: .3rem;
  }
</style>


