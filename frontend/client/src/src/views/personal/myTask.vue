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
      <el-tabs class="f_con2" v-model="activeName">
        <el-tab-pane label="进行中" name="first">
          <ul class="tasking_ul ">
            <li class="tasking_li">
               <router-link v-bind:to="{path: '/myTaskDetail'}">
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
                  学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务
                </p>
                <p class="tasking_font">
                  <span class="">任务完成进度 ：</span>
                  <span class="g_f">45%</span>
                </p>
                <div class="surplus_num t_bor1 " style="">
                  倒计时：<span>2</span>天
                </div>
               </router-link>
            </li>
            <li class="tasking_li ">
              <router-link v-bind:to="{path: '/myTaskDetail'}">
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
                  学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务
                </p>
                <p class="tasking_font">
                  <span class="">任务完成进度 ：</span>
                  <span class="g_f">45%</span>
                </p>
                <div class="surplus_num t_bor1 " style="">
                  倒计时：<span>2</span>天
                </div>
              </router-link>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="已完成" name="second">
          <ul class="tasking_ul ">
            <li class="tasking_li">
              <router-link v-bind:to="{path: '/myTaskDetail'}">
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
                  学习任务学习任务学习任务学习任yy务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务
                </p>
                <p class="tasking_font">
                  <span class="">任务完成进度 ：</span>
                   <span class="g_f">45%</span>
                </p>
                <div class="surplus_num  t_bor2" style="">
                  已完成
                </div>
              </router-link>
            </li>
            <li class="tasking_li">
              <a href="myTaskDetail.vue">
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
                  学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务学习任务
                </p>
                <p class="tasking_font">
                  <span class="">任务完成进度 ：</span>
                  <span class="g_f">45%</span>
                </p>
                <div class="surplus_num  t_bor3" style="">
                  未完成
                </div>
              </a>
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
        dataNotStart: [],
        isBusy_inProgress: false,
        isBusy_notStart: false,
        take: 20,
        currentPage_inProgress: -1,
        currentPage_notStart: -1
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
  /*.el-tabs__nav .el-tabs__item:first-child {*/
    /*border-right: 1px solid #ededed;*/
  /*}*/
</style>
