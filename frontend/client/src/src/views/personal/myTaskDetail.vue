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
      <ul class="tasking_ul ">
        <li class="tasking_li">
          <a>
            <p class="tasking_font">
              <span class="">任务名称 ：</span>
              学习任务1
            </p>
            <p class="tasking_font">
              <span class="">截止日期 ：</span>
              20162325
            </p>
            <p class="tasking_font">
              <span class="">目标学员范围 ：</span>
              学习任务学习任务务学习任务务学习任务
            </p>
            <p class="tasking_font">
              <span class="">学习任务描述 ：</span>
              学习任务学习任g务jj 学习任务学习任务学习务学习任务学习任务学习任务学习任务学习任务
            </p>
            <p class="tasking_font">
              <span class="">任务完成进度 ：</span>
              <span class="g_f">45%</span>
            </p>
            <div class="surplus_num t_bor1 " style="">
              倒计时：<span>2</span>天
            </div>
            <router-link class="tasking_see" v-bind:to="{path: '/myTaskSee'}">查看同学</router-link>
          </a>
        </li>
      </ul>
      <div class="hidden">
        <h3 class="list_need">课程</h3>
        <ul class="list_border course_line">
          <li class="course_list  line_only">
            <a>
              <img src="../../assets/img/img1.png" class="fl img_bg">
              <div class="course_cen show_star">
                <div class="hidden effect_right ">
                  <h3 class="fl">销售的哭昏</h3>
                  <!--<el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900" text-template="value5"></el-rate>-->
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">2</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">4</span>
                    </li>
                  </ul>
                </div>
              </div>
              <p class="exam_explain">
                这杀手焦点科技撒
              </p>
              <div class="surplus_num  t_bor2" style="">
                已完成
              </div>
            </a>
          </li>
        </ul>
      </div>
      <div class="hidden">
        <h3 class="list_need">考试</h3>
        <div class="null_date">
          <img class="vm mr1" src="../../assets/img/sade_null.png"> <span class="vm">暂无数据</span>
        </div>

      </div>
      <div  class="hidden">
        <h3 class="list_need">报名</h3>
        <div class="null_date">
          <img class="vm mr1" src="../../assets/img/sade_null.png"> <span class="vm">暂无数据</span>
        </div>

      </div>
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
        dataNotStart: [],
        dataFinish: [],
        isBusy_inProgress: false,
        isBusy_notStart: false,
        take: 20,
        currentPage_inProgress: -1,
        currentPage_notStart: -1,
        value5: 3.7
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
      // handleClick: function (tab, event) {
      //   this.loadMore()
      // },
      handleStar: function (data) {
        for (var i = 0; i < data.length; i++) {
          var current = data[i]
          if (current && current.star) {
            current.star = current.star.toFixed(1)
          }
        }
      },
      loadMore: function () {
        this.busy = true
        this.currentPage_inProgress = this.currentPage_inProgress + 1
        api.fetch(api.uri.getMyTaskListInProgress, {
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
</script>

<style scoped="scope">

</style>
