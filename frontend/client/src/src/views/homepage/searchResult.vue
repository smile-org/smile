<template>
  <div id="app">
    <common-header></common-header>
    <section v-show="!noResult">
      <div class="search_tit">
        <ul class="fl_tab" style="position: relative;z-index:9;">
          <li :class="{active: type === 1}" v-on:click="selectModule(1)">
            课程
          </li>
          <li :class="{active: type === 2}" v-on:click="selectModule(2)">
            考试
          </li>
          <li :class="{active: type === 3}" v-on:click="selectModule(3)">
            报名
          </li>
          <li :class="{active: type === 4}" v-on:click="selectModule(4)">
            约课
          </li>
        </ul>
        <div class="b_search_input">
          <img class="s_seach" src="../../assets/img/seach_icon.png" />
          <img class="s_delate" src="../../assets/img/delate.png" v-on:click="goBack"  style="top:.06rem;right: .2rem;"/>
          <input placeholder="选择资源类型搜索更精准" v-model.trim="search" @focus="goBack">
          <!--<button type="text" class="search_font" v-on:click="search">搜索</button>-->
        </div>
      </div>
      <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.id">
          <a  v-if="type===4" v-on:click="goDetail(item.id)" style="width: 100%;">
          <!--<router-link v-bind:to="{name: 'getBooking', query: {id: item.id}}">-->
            <div class="p3_line wid100 hidden">
              <img class="person_header fl" :src="item.icon | formatImage">
              <div class="bm_con">
                <div class="hidden bm_font ml2">
                  <h3 class="">{{item.title}}</h3>
                  <!--<div class="hidden wid100">-->
                    <!--<p class="fl">主讲：{{item.sponsorName}}</p>-->
                    <!--<p class="fr">{{item.sponsorDate | formatDate}}</p>-->
                  <!--</div>-->
                  <!--<div class="home_person">-->
                    <!--<span class="icon icon1 vm"></span>-->
                    <!--<span class="vm">{{item.followerCount}}</span>-->
                  <!--</div>-->
                  <div class="hidden wid100">
                    <p class="fl">主讲：{{item.person}}</p>
                    <!--<p class="fr">{{item.sponsorDate | formatDate}}</p>-->
                    <p class="fr">
                      {{item.start | formatDate}}<span v-if="item.end">&nbsp;至&nbsp;{{item.end | formatDate}}</span>
                    </p>
                  </div>
                  <div class="home_person">
                    <span class="icon icon1 vm"></span>
                    <span class="vm">{{item.count}}</span>
                  </div>
                  <!--<ul class="small_icon fr font22">-->
                    <!--{{item.person}}{{item.count}}-->
                  <!--</ul>-->
                </div>

              </div>
            </div>
          </a>
          <a  v-else v-on:click="goDetail(item.id)" style="width: 100%;">
            <!--<router-link v-bind:to="{name: 'getBooking', query: {id: item.id}}">-->
            <img  :src="item.icon | formatImage" class="fl img_bg">
            <!--<img v-else :src="item.icon | formatImage" class="fl img_bg">-->
            <div class="course_cen">
              <div class="hidden person_search">
                <h3 class="">{{item.title}}</h3>
                <p class="person_search">主讲：{{item.person}} </p>
                <p class="exam_explains">
                  {{item.intro}}
                </p>
                <ul class="small_icon fr font22" style="top: .3rem">
                  <li class="fl">
                    <span class="icon icon1"></span>
                    <span class="green00b">{{item.count}}</span>
                  </li>
                </ul>
              </div>
            </div>

            <div class="small_icon exam_explain " style="color: #999;">
              {{item.start | formatDate}}<span v-if="item.end">&nbsp;至&nbsp;{{item.end | formatDate}}</span>
            </div>
          </a>
          <!--</router-link>-->
        </li>
      </ul>
    </section>
    <section v-show="noResult">
      <div class="appraise_w">
        <div class="face_img3 ">

        </div>
        <div class="face_font">
          <span>没有找到与 <span>“ {{this.search}} ”</span> 相关课程？</span></br>
          <span>赶紧去发起约课吧。</span>
        </div>
        <el-row>
          <el-col :span="12"> <button class="exam_btn orange_b fr" type="button" v-on:click="goBooking">发起约课</button></el-col>
          <el-col :span="12"> <button class="exam_btn green_b fl" type="button" v-on:click="goHome">返回首页</button></el-col>
        </el-row>
      </div>
    </section>
  </div>
</template>

<script>
import commonHeader from '../../components/CommonHeader'
import api from '../../services/api'
import axios from 'axios'
import { formatDate } from '../../common/date'
import router from '../../router'
export default {
  data: function () {
    return {
      type: 0, // 1: 课程, 2: 考试, 3: 报名, 4: 约课
      take: 20,
      currentPage: -1,
      busy: false,
      search: '',
      data: [],
      noResult: false
    }
  },
  components: {
    commonHeader
  },
  created () {
    this.type = parseInt(this.$route.query.type)
    this.search = this.$route.query.search
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
    loadMore: function () {
      this.noResult = false
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take,
        query: this.search,
        typeid: this.type
      }
      api.fetch(api.uri.search, params).then(data => {
        if (data.status === 1) {
          if (data.result.length === this.take) {
            this.busy = false
          }
          if (data.result.length > 0) {
            this.data = this.data.concat(data.result)
          }
        }
        if (this.data.length === 0) {
          this.noResult = true
        }
      })
    },
    goBack: function () {
      router.go(-1)
    },
    selectModule: function (type) {
      sessionStorage.removeItem('searchType')
      this.type = type
      router.push({name: 'search', query: {type: this.type}})
    },
    goBooking: function () {
      router.push({name: 'newBooking'})
    },
    goHome: function () {
      router.push({name: 'homepage'})
    },
    goDetail: function (itemId) {
      if (this.type === 1) {
        router.push({path: '/getCourseDetails', query: {id: itemId}})
      } else if (this.type === 2) {
        router.push({name: 'getExamInfo', query: {id: itemId}})
      } else if (this.type === 3) {
        router.push({name: 'getEnroll', query: {id: itemId}})
      } else {
        router.push({name: 'getBooking', query: {id: itemId}})
      }
    }
  }
}
</script>
<style>
  .person_search p{
    font-size: .22rem;
    color: #999;
    /*margin-top: .1rem;*/
  }
  .person_search .exam_explains{
    font-size: .22rem;
    color: #999;
    margin-top: .01rem;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    /*max-width: 70%;*/
    min-height:.4rem;
    margin-left: 0rem;
    padding-right: .03rem;
  }
</style>
