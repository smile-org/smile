
<template>
  <div>
    <header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <!--<el-input icon="el-icon-search" />-->
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关约课">
        </div>
        <a class="seach_tit tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <ul class=" list_border course_line reg_nohover mb3hafe" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.appointmentId">
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
              <li v-for="(key, index) in item.keywordArray" :key="index">{{key}}</li>
            </ul>
          </router-link>
        </li>
      </ul>
    </section>
    <footer>
      <button class="login_btn btn_position" type="button" v-on:click="newBooking">我要发起约课</button>
    </footer>
  </div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import router from '../../router'
import { formatDate } from '../../common/date'
export default {
  data: function () {
    return {
      currentPage: -1,
      take: 20,
      data: [],
      busy: false
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    },
    formatDate (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    }
  },
  methods: {
    newBooking: function () {
      router.push({name: 'newBooking'})
    },
    loadMore: function () {
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take
      }
      api.fetch(api.uri.getBookingList, params).then(data => {
        if (data.status === 1) {
          // 只有每次拿回来的数据数量和take的数量相等时， 证明有可能有下一页， 否则直接认为没有下一页了。
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
          // TODO:
        }
      }).catch(error => {
        // TODO:
        console.log(error.message)
      })
    }
  }
}
</script>
<style>
.el-tabs__active-bar{
      width: 0!important;
      transform: translateX(0rem)!important;
    }
    .el-tabs__item.is-active:before {
      content: "";
      position: absolute;
      bottom: 0;
      left: .7rem;
      right: .7rem;
      height: 3px;
      background: #00b553;
      z-index: 2;
    }
    .el-tabs__item {
      padding: 0 .5rem;
      height: .7rem;
      box-sizing: border-box;
      line-height: .5rem;
      display: inline-block;
      list-style: none;
      font-size: .28rem;
      color: #666;
      position: relative;
      width:50%;
    }
    .el-tabs__item.is-active {
      color: #00b553;
    }
    .el-tabs__nav{
      width:100%;
      text-align: center;
      margin-top: -.3rem;
    }
    .el-tabs__header {
      border-bottom:0;
      margin: 0;
    }
    .el-tabs__nav .el-tabs__item:first-child{
      border-right:1px solid #ededed;
    }
    .bm_font p {
      margin-top: .45rem;
    }
    .bm_font .small_icon {
      top: .3rem;
    }
</style>

