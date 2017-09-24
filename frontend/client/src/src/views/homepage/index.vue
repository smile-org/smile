<template>
  <div id="app" @click="homeClick(false)">
    <header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <!--<el-input icon="el-icon-search" />-->
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关培训报名">
        </div>
        <a class="seach_tit tr" href="javaScript:;" @click.stop.prevent="homeClick(true)"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <img class="banner_bg" src="../../assets/img/banner_bg.png" />
      <el-row class="home_sec tc">
        <el-col :span="6" class="home_icon">
          <router-link v-bind:to="{name: 'myTask'}">
            <img src="../../assets/img/home_icon1.png">
            <p class="">学习任务</p>
          </router-link>
        </el-col>
        <el-col :span="6" class="home_icon">
          <router-link v-bind:to="{name: 'myLearning'}">
            <img src="../../assets/img/home_icon2.png">
            <p class="">学习记录</p>
          </router-link>
        </el-col>
        <el-col :span="6" class="home_icon">
          <router-link v-bind:to="{name: 'myExam'}">
            <img src="../../assets/img/home_icon3.png">
            <p class="">考试记录</p>
          </router-link>
        </el-col>
        <el-col :span="6" class="home_icon">
          <router-link v-bind:to="{name: 'myBooking'}">
            <img src="../../assets/img/home_icon4.png">
            <p class="">约课记录</p>
          </router-link>
        </el-col>
      </el-row>
      <div class="home_third">
        <div class="hidden">
          <h3 class="list_need fl">热门约课</h3>
          <router-link v-bind:to="{name: 'getBookingList'}" class="home_more">
            更多 >
          </router-link>
        </div>
        <ul class=" list_border course_line reg_nohover">
          <li class="course_list  line_only" v-for="item in booking" :key="item.appointmentId">
            <router-link v-bind:to="{name: 'getBooking', query: {id: item.appointmentId}}">
              <div class="p3_line wid100 hidden">
                <img class="person_header fl" :src="item.avatar | formatImage">
                <div class="bm_con">
                  <div class="hidden bm_font">
                    <h3 class="fl ">{{item.appointmentTitle}}</h3>
                    <div class="hidden wid100">
                      <p class="fl">主讲：{{item.sponsorName}}</p>
                      <p class="fr">{{item.sponsorDate | formatDate}}</p>
                    </div>
                    <div class="home_person">
                      <span class="icon icon1 vm"></span>
                      <span class="vm">{{item.followerCount}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </router-link>
          </li>
        </ul>
      </div>
      <div class="home_four">
        <div class="hidden">
          <h3 class="list_need fl">课程排行</h3>
          <router-link v-bind:to="{name: 'courseCategories'}" class="home_more">
            更多 >
          </router-link>
        </div>
        <div class="f_con">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="本周" name="first">
              <ul class="list_border course_line">
                <li class="course_list  line_only" v-for="item in courseWeek" :key="item.course_id">
                  <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                    <img :src="item.icon | formatImage" class="fl img_bg">
                    <div class="course_cen">
                      <div class="hidden">
                        <h3 class="fl">{{item.title}}</h3>
                        <ul class="small_icon fr">
                          <li class="fl">
                            <span class="icon icon1"></span>
                            <span class="green00b">{{item.study_count}}</span>
                          </li>
                          <li class="fl">
                            <span class="icon icon2"></span>
                            <span class="redff7">{{item.collect_count}}</span>
                          </li>
                        </ul>
                      </div>
                      <p class="exam_explain">
                        {{item.intro}}
                      </p>
                    </div>
                  </router-link>
                </li>
              </ul>
            </el-tab-pane>
            <el-tab-pane label="本月" name="second">
              <ul class="list_border course_line">
                <li class="course_list  line_only" v-for="item in courseMonth" :key="item.course_id">
                  <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                    <img :src="item.icon | formatImage" class="fl img_bg">
                    <div class="course_cen">
                      <div class="hidden">
                        <h3 class="fl">{{item.title}}</h3>
                        <ul class="small_icon fr">
                          <li class="fl">
                            <span class="icon icon1"></span>
                            <span class="green00b">{{item.study_count}}</span>
                          </li>
                          <li class="fl">
                            <span class="icon icon2"></span>
                            <span class="redff7">{{item.collect_count}}</span>
                          </li>
                        </ul>
                      </div>
                      <p class="exam_explain">
                        {{item.intro}}
                      </p>
                    </div>
                  </router-link>
                </li>
              </ul>
            </el-tab-pane>
            <el-tab-pane label="累计" name="third">
              <ul class="list_border course_line">
                <li class="course_list  line_only" v-for="item in courseTotal" :key="item.course_id">
                  <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                    <img :src="item.icon | formatImage" class="fl img_bg">
                    <div class="course_cen">
                      <div class="hidden">
                        <h3 class="fl">{{item.title}}</h3>
                        <ul class="small_icon fr">
                          <li class="fl">
                            <span class="icon icon1"></span>
                            <span class="green00b">{{item.study_count}}</span>
                          </li>
                          <li class="fl">
                            <span class="icon icon2"></span>
                            <span class="redff7">{{item.collect_count}}</span>
                          </li>
                        </ul>
                      </div>
                      <p class="exam_explain">
                        {{item.intro}}
                      </p>
                    </div>
                  </router-link>
                </li>
              </ul>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <div class="home_five">
        <div class="hidden">
          <h3 class="list_need fl">近期考试</h3>
          <router-link v-bind:to="{name: 'getExamList'}" class="home_more">
            更多 >
          </router-link>
        </div>
        <ul class="list_border course_line">
          <li class="course_list  line_only" v-for="item in exam" :key="item.exam_id">
            <router-link v-bind:to="{path: '/getExamInfo', query: {id: item.exam_id}}">
              <img :src="item.icon|formatImage" class="fl img_bg">
              <div class="course_cen">
                <div class="hidden">
                  <h3 class="fl">{{item.exam_title}}</h3>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{item.study_count}}</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">{{item.collect_count}}</span>
                    </li>
                  </ul>
                </div>
                <p class="exam_explain">
                  {{item.intro}}
                </p>
              </div>
            </router-link>
          </li>

        </ul>
      </div>
      <div class="home_five">
        <div class="hidden">
          <h3 class="list_need fl">培训报名</h3>
          <router-link v-bind:to="{name: 'getEnrollList'}" class="home_more">
            更多 >
          </router-link>
        </div>
        <ul class=" list_border course_line reg_nohover">
          <li class="course_list  line_only"  v-for="item in enroll" :key="item.period_id">
            <router-link v-bind:to="{name: 'getEnroll', query: {id: item.period_id}}">
              <img class="person_header fl" :src="item.icon | formatImage">
              <div class="bm_con">
                <div class="hidden bm_font">
                  <h3 class="fl mb15">{{item.title}}</h3>
                  <p class="">主讲：{{item.teacher}}</p>
                  <p>{{item.start_date | formatDate}}--{{item.end_date | formatDate}}</p>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{item.study_count}}</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">{{item.collect_count}}</span>
                    </li>
                  </ul>
                  <span class="surplus_num s_num" v-if="item.left_count > 0">
                    剩余{{item.left_count}}人
                  </span>
                  <!--<span class="surplus_num red_full" v-else-if="item.left_count === 0">
                    已 满
                  </span>
                  <img class="end_png" src="../../assets/img/end.png" />-->
                </div>
              </div>
            </router-link>
          </li>
        </ul>
      </div>
    </section>
    <nav @click="homeClick(false,$event)" id="slide_menu" :class="nav1 ?'active' : ''">
      <div class="user_info">
        <img :src="userAvatar | formatImage" width="100%" alt="default_img">
      </div>
      <ul class="nav" style="height: 581px;">
        <li class="">
          <router-link v-bind:to="{name: 'homepage'}">首页</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'courseCategories'}">我要学课</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getExamList'}">我要考试</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getEnrollList'}">我要报名</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getBookingList'}">我要约课</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'me'}">个人中心</router-link>
        </li>
      </ul>
    </nav>
    <div class="c-modal" :style="{display:nav1 ? 'block':'none'}">
    </div>
  </div>
</template>

<script>
import api from '../../services/api'
import {formatDate} from '../../common/date'
import axios from 'axios'
export default {
  data: function () {
    return {
      activeName: 'first',
      booking: [],
      courseWeek: [],
      courseMonth: [],
      courseTotal: [],
      exam: [],
      enroll: [],
      visible: false,
      nav1: false,
      username: '',
      userAvatar: ''
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.uri
    },
    formatDate: function (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    }
  },
  created () {
    this.username = sessionStorage.getItem('username')
    this.userAvatar = sessionStorage.getItem('userAvatar')
    axios.all([
      api.fetch(api.uri.getBookingList, {skip: 0, take: 3}),
      api.fetch(api.uri.getHomepageCourse, {scopeid: 1, skip: 0, take: 3}),
      api.fetch(api.uri.getHomepageCourse, {scopeid: 2, skip: 0, take: 3}),
      api.fetch(api.uri.getHomepageCourse, {scopeid: 3, skip: 0, take: 3}),
      api.fetch(api.uri.getExamList, {skip: 0, take: 3}),
      api.fetch(api.uri.getEnrollList, {skip: 0, take: 3})
    ]).then(axios.spread((bookingData, courseWeekData, courseMonthData, courseTotalData, examData, enrollData) => {
      if (bookingData.status === 1) {
        this.booking = bookingData.result
      }
      if (courseWeekData.status === 1) {
        this.courseWeek = courseWeekData.result
      }
      if (courseMonthData.status === 1) {
        this.courseMonth = courseMonthData.result
      }
      if (courseTotalData.status === 1) {
        this.courseTotal = courseTotalData.result
      }
      if (examData.status === 1) {
        this.exam = examData.result
      }
      if (enrollData.status === 1) {
        this.enroll = enrollData.result
      }
    }))
  },
  methods: {
    handleClick: function (tab, event) {
      console.log(tab, event)
    },
    homeClick: function (status, event) {
      this.nav1 = status
    }
  }
}
</script>

<style>
.el-rate__icon {
  font-size: .16rem;
  margin-right: .006rem;
}

.el-tabs__active-bar {
  width: 0!important;
  transform: translateX(0rem)!important;
}

.el-tabs__item.is-active:before {
  content: "";
  position: absolute;
  bottom: 0;
  left: .3rem;
  right: .3rem;
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
  width: 33.33%;
}

.el-tabs__item.is-active {
  color: #00b553;
}

.el-tabs__nav {
  width: 100%;
  text-align: center;
  margin-top: -.3rem;
}

.el-tabs__header {
  border-bottom: 0;
  margin: 0;
}

.el-tabs__nav .el-tabs__item:first-child {
  border-right: 1px solid #ededed;
}
</style>
