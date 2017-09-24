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
      <el-tab-pane label="已通过"  name="first">
        <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_Passed" infinite-scroll-distance="10">
          <li class="course_list  line_only" v-for="item in dataPassed" :key="item.exam_id" >
            <router-link v-bind:to="{name: 'getExamInfo', query: {id: item.exam_id}}">
              <img :src="item.icon | formatImage" class="fl img_bg">
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

              </div>
              <p class="exam_explain">
                {{item.intro}}
              </p>
            </router-link>
          </li>
        </ul>
      </el-tab-pane>
      <el-tab-pane label="未通过" name="second">
        <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_Failed" infinite-scroll-distance="10">
          <li class="course_list  line_only " v-for="item in dataFailed" :key="item.exam_id">
            <router-link v-bind:to="{name: 'getExamInfo', query: {id: item.exam_id}}">
              <img :src="item.icon | formatImage" class="fl img_bg">
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
      dataPassed: [],
      dataFailed: [],
      isBusy_Passed: false,
      isBusy_Failed: false,
      take: 20,
      currentPage_Passed: -1,
      currentPage_Failed: -1
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

    loadMore: function () {
      if (this.activeName === 'first' && this.dataPassed.length === 0 && !this.isBusy_Passed) {
        this.currentPage_Passed = this.currentPage_Passed + 1
        this.isBusy_Passed = true
        api.fetch(api.uri.getMyExamListPassed, { take: this.take, skip: this.currentPage_Passed * this.take }).then(data => {
          if (data.status === 1) {
            this.dataPassed = this.dataPassed.concat(data.result)
            if (data.result.length === this.take) {
              this.isBusy_Passed = false
            }
          } else {
            // todo:
          }
        }).catch(error => {
          console.log(error.message)
        })
      }
      if (this.activeName === 'second' && this.dataFailed.length === 0 && !this.isBusy_Failed) {
        this.currentPage_Failed = this.currentPage_Failed + 1
        this.isBusy_Failed = true
        api.fetch(api.uri.getMyExamListFailed, { take: this.take, skip: this.currentPage_Failed * this.take }).then(data => {
          if (data.status === 1) {
            this.dataFailed = this.dataFailed.concat(data.result)
            if (data.result.length === this.take) {
              this.isBusy_Failed = false
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
<!--.el-tabs__active-bar {-->
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
  <!--width: 50%!important;-->
<!--}-->

<!--.el-tabs__item.is-active {-->
  <!--color: #00b553;-->
<!--}-->

<!--.el-tabs__nav {-->
  <!--width: 100%;-->
  <!--text-align: center;-->
  <!--margin-top: -.3rem;-->
<!--}-->

<!--.el-tabs__header {-->
  <!--border-bottom: 0;-->
  <!--margin: 0;-->
<!--}-->

<!--.el-tabs__nav .el-tabs__item:first-child {-->
  <!--border-right: 1px solid #ededed;-->
<!--}-->
<!--</style>-->
