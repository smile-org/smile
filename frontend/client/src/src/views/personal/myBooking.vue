
<template>
  <div id="app">
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.appointment_id">
          <a>
            <div class="hidden">
              <img class="person_header fl" :src="item.avatar | formatImage">
              <div class="bm_con">
                <div class="hidden bm_font">
                  <h3 class="fl ">{{item.appointment_title}}</h3>
                  <p>主讲：{{item.username}}
                    <span class="ml2">{{item.sponsor_date | formatDate}}</span>
                  </p>

                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{item.study_count}}</span>
                    </li>
                  </ul>
                  <span class="see_student">
                    查看同学
                  </span>
                </div>
              </div>
            </div>
            <ul class="keword_num">
              <li>关键字一个</li>
              <li>关键字一个</li>
              <li>关键字一个</li>
              <li>关键字一个</li>
              <li>关键字一个</li>
              <li>关键字一个</li>
            </ul>

          </a>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import { formatDate } from '../../common/date'
export default {
  data: function () {
    return {
      data: [],
      busy: false,
      take: 20,
      currentPage: -1
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.baseURL + uri
    },
    formateDate: function (time) {
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
          this.data = this.data.concat(data.result)
          if (data.result.length === this.take) {
            this.busy = false
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


