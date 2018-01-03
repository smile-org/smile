<template>
  <div id="app">
    <common-header></common-header>
    <section>
      <el-tabs class="f_con2" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="进行中" name="first">
          <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_inProgress" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataInProgress" :key="item.course_id">
              <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                <img :src="item.icon | formatImage" class="fl img_bg">
                <div class="course_cen show_star">
                  <div class="hidden effect_right ">
                    <h3 class="fl">{{item.title}}</h3>
                    <el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900" text-template="{value}"></el-rate>
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
                <p class="exam_explain">
                  {{item.intro}}
                </p>
              </router-link>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="已完成" name="second">
          <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_finish" infinite-scroll-distance="10">
            <li class="course_list  line_only"  v-for="item in dataFinish" :key="item.course_id">
              <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                <img :src="item.icon | formatImage" class="fl img_bg">
                <div class="course_cen show_star">
                  <div class="hidden effect_right ">
                    <h3 class="fl">{{item.title}}</h3>
                    <el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900" text-template="{value}"></el-rate>
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
                <p class="exam_explain">
                  {{item.intro}}
                </p>
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
    }
  },
  methods: {
    handleClick: function (tab, event) {
      this.loadMore()
    },
    handleStar: function (data) {
      for (var i = 0; i < data.length; i++) {
        var current = data[i]
        if (current && current.star) {
          current.star = current.star.toFixed(1)
        }
      }
    },
    loadMore: function () {
      if (this.activeName === 'first' && this.dataInProgress.length === 0 && !this.isBusy_inProgress) {
        this.currentPage_inProgress = this.currentPage_inProgress + 1
        this.isBusy_inProgress = true
        api.fetch(api.uri.getMyCourseListInProgress, {take: this.take, skip: this.currentPage_inProgress * this.take}).then(data => {
          if (data.status === 1) {
            this.dataInProgress = this.dataInProgress.concat(data.result)
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
      if (this.activeName === 'second' && this.dataFinish.length === 0 && !this.isBusy_finish) {
        this.currentPage_finish = this.currentPage_finish + 1
        this.isBusy_finish = true
        api.fetch(api.uri.getMyCourseListFinished, {take: this.take, skip: this.currentPage_finish * this.take}).then(data => {
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
    }
  }
}
</script>

<!--<style>-->
  <!--.el-tabs__active-bar{-->
    <!--width: 0!important;-->
    <!--transform: translateX(0rem)!important;-->
  <!--}-->
  <!--.el-tabs__item.is-active:before {-->
    <!--content: "";-->
    <!--position: absolute;-->
    <!--bottom: 0;-->
    <!--left: .7rem;-->
    <!--right: .7rem;-->
    <!--height: 3px;-->
    <!--background: #00b553;-->
    <!--z-index: 2;-->
  <!--}-->
  <!--.el-tabs__item {-->
    <!--padding: 0 .5rem;-->
    <!--height: .7rem;-->
    <!--box-sizing: border-box;-->
    <!--line-height: .5rem;-->
    <!--display: inline-block;-->
    <!--list-style: none;-->
    <!--font-size: .28rem;-->
    <!--color: #666;-->
    <!--position: relative;-->
    <!--width:50%!important;-->
  <!--}-->
  <!--.el-tabs__item.is-active {-->
    <!--color: #00b553;-->
  <!--}-->
  <!--.el-tabs__nav{-->
    <!--width:100%;-->
    <!--text-align: center;-->
    <!--margin-top: -.3rem;-->
  <!--}-->
  <!--.el-tabs__header {-->
    <!--border-bottom:0;-->
    <!--margin: 0;-->
  <!--}-->
  <!--.el-tabs__nav .el-tabs__item:first-child{-->
    <!--border-right:1px solid #ededed;-->
  <!--}-->
  <!--</style>-->
