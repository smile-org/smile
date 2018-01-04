<template>
  <div id="app">
    <common-header></common-header>
    <section>
      <el-tabs class="f_con2" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="进行中" name="first">
          <ul class="tasking_ul "  v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_inProgress" infinite-scroll-distance="10">
            <li class="tasking_li" v-for="item in dataInProgress" :key="item.task_id">
               <router-link v-bind:to="{path: '/myTaskDetail', query: {id: item.task_id}}">
                <p class="tasking_font">
                  <span class="">任务名称 ：</span>
                  {{item.task_title}}
                </p>
                <p class="tasking_font">
                  <span class="">截止日期 ：</span>
                  {{item.expiration_date | formatDate}}
                </p>
                <p class="tasking_font">
                  <span class="">目标学员范围 ：</span>
                  {{item.task_scope}}
                </p>
                <p class="tasking_font ">
                  <span class="">任务完成进度 ：</span>
                  {{item.learn_percentage}}
                </p>
                <div class="surplus_num t_bor1 " style="">
                  倒计时：<span>{{item.count_down}}</span>天
                </div>
               </router-link>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="已完成" name="second">
          <ul class="tasking_ul " v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_finish" infinite-scroll-distance="10">
            <li class="tasking_li" v-for="item in dataFinish" :key="item.task_id">
              <router-link v-bind:to="{path: '/myTaskDetail', query: {id: item.task_id}}">
                <p class="tasking_font">
                  <span class="">任务名称 ：</span>
                  {{item.task_title}}
                </p>
                <p class="tasking_font">
                  <span class="">截止日期 ：</span>
                  {{item.expiration_date | formatDate}}
                </p>
                <p class="tasking_font">
                  <span class="">目标学员范围 ：</span>
                  {{item.task_scope}}
                </p>
                <p class="tasking_font">
                  <span class="">任务完成进度 ：</span>
                  {{item.learn_percentage}}
                </p>
                <div class="surplus_num  t_bor2" style="">
                  {{item.learn_status}}
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
  import commonHeader from '../../components/CommonHeader'
  import {formatDate} from '../../common/date'
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
      handleClick: function (tab, event) {
        this.loadMore()
      },
      loadMore: function () {
        if (this.activeName === 'first' && this.dataInProgress.length === 0 && !this.isBusy_inProgress) {
          this.currentPage_inProgress = this.currentPage_inProgress + 1
          this.isBusy_inProgress = true
          api.fetch(api.uri.getTaskInProgress, {take: this.take, skip: this.currentPage_inProgress * this.take}).then(data => {
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
          api.fetch(api.uri.getTaskFinished, {take: this.take, skip: this.currentPage_finish * this.take}).then(data => {
            if (data.status === 1) {
              this.dataFinish = this.dataFinish.concat(data.result)
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

<style scoped="scope">
  /*.el-tabs__nav .el-tabs__item:first-child {*/
    /*border-right: 1px solid #ededed;*/
  /*}*/
</style>
