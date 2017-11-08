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
      <el-tabs class="f_con2" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="已报名" name="first">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore"
              infinite-scroll-disabled="isBusy_finish" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataFinish" :key="item.enrollment_id">
              <router-link v-bind:to="{name: 'getEnroll', query: {id: item.period_id}}">
                <img class="person_header2 fl" :src="item.icon | formatImage">
                <div class="bm_con_bm show_star">
                  <div class="hidden bm_font ml6">
                    <h3 class="mb15">{{item.title}}</h3>
                    <p class="">主讲：{{item.teacher}}</p>
                    <p>{{item.start_date | formatDate}} ~ {{item.end_date | formatDate}}</p>
                    <el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900"
                             text-template="{value}"></el-rate>
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
                </div>
              </router-link>
              <span @click.prevent="cancelCourse(item.period_id)" class="surplus_num red_full " style="right: 0.05rem; top: 0.65rem;cursor: pointer">结束报名</span>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="预约报名" name="second">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore"
              infinite-scroll-disabled="isBusy_inProgress" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataInProgress" :key="item.enrollment_id">
              <router-link v-bind:to="{name: 'getEnroll', query: {id: item.period_id}}">
                <img class="person_header2 fl" :src="item.icon | formatImage">
                <div class="bm_con_bm show_star">
                  <div class="hidden bm_font ml6">
                    <h3 class="mb15">{{item.title}}</h3>
                    <p class="">主讲：{{item.teacher}}</p>
                    <p>{{item.start_date | formatDate}} ~ {{item.end_date | formatDate}}</p>
                    <el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900"
                             text-template="{value}"></el-rate>
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
                </div>
              </router-link>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>
    </section>
  </div>
</template>

<script>
  import api from '../../services/api'
  import axios from 'axios'
  import {formatDate} from '../../common/date'
  import commonHeader from '../../components/CommonHeader'
  export default {
    data: function () {
      return {
        activeName: 'first',
        dataInProgress: [],
        dataFinish: [],
        isBusy_inProgress: false,
        isBusy_finish: false,
        take: 20,
        currentPage_inProgress: -1,
        currentPage_finish: -1
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
      handleStar: function (data) {
        for (var i = 0; i < data.length; i++) {
          var current = data[i]
          if (current && current.star) {
            current.star = current.star.toFixed(1)
          }
        }
      },
      cancelCourse: function (courseId) {
        if (courseId) {
          api.fetch(api.uri.cancelCourse, {periodid: courseId}).then(data => {
            if (data.status === 1) {
              this.removeCancelCourseItem(courseId)
//              this.loadMore()
            } else {
              // todo:
            }
          }).catch(error => {
            console.log(error.message)
          })
        }
      },
      removeCancelCourseItem: function (courseId) {
        if (courseId) {
          for (let i = 0; i < this.dataFinish.length; i++) {
            let item = this.dataFinish[i]
            if (item.period_id === courseId) {
              this.dataFinish.splice(i, 1)
            }
          }
        }
      },
      handleClick: function (tab, event) {
        this.loadMore()
      },

      loadMore: function () {
        if (this.activeName === 'first' && this.dataFinish.length === 0 && !this.isBusy_finish) {
          this.currentPage_finish = this.currentPage_finish + 1
          this.isBusy_finish = true
          api.fetch(api.uri.getMyEnrollFinish, {
            take: this.take,
            skip: this.currentPage_finish * this.take
          }).then(data => {
            if (data.status === 1) {
              this.dataFinish = this.dataFinish.concat(data.result)
              this.handleStar(this.dataFinish)
              if (data.result.length === this.take) {
                this.isBusy_finish = false
              }
            } else {
              // todo:
            }
          }).catch(error => {
            console.log(error.message)
          })
        }
        if (this.activeName === 'second' && this.dataInProgress.length === 0 && !this.isBusy_inProgress) {
          this.currentPage_inProgress = this.currentPage_inProgress + 1
          this.isBusy_inProgress = true
          api.fetch(api.uri.getMyEnrollInProgress, {
            take: this.take,
            skip: this.currentPage_inProgress * this.take
          }).then(data => {
            if (data.status === 1) {
              this.dataInProgress = this.dataInProgress.concat(data.result)
              this.handleStar(this.dataInProgress)
              if (data.result.length === this.take) {
                this.isBusy_inProgress = false
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
  }
</script>
