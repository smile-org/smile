<template>
  <div id="app">
    <!--<header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关培训报名">
        </div>
        <a class="seach_tit tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>-->
    <search-header v-bind:holder="searchPlaceHolder"></search-header>
    <section>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="正在报名" name="first">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in data" :key="item.period_id">
              <router-link v-bind:to="{name: 'getEnroll', query: {id: item.period_id}}">
                <img class="person_header2 fl" :src="item.icon | formatImage">
                <div class="bm_con">
                  <div class="hidden bm_font ml6">
                    <h3 class="fl mb15">{{item.title}}</h3>
                    <p class="">主讲：{{item.teacher}}</p>
                    <p class="new_p">{{item.start_date | formatDate}}--{{item.end_date | formatDate}}</p>
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
                    <span class="surplus_num s_num">
                      剩余{{item.left_count}}人
                    </span>
                  </div>
                </div>
              </router-link>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="结束报名" name="second">
          <ul class=" list_border course_line reg_nohover"  v-infinite-scroll="loadMore_sec" infinite-scroll-disabled="busy_sec" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in data_sec" :key="item.period_id">
              <router-link v-bind:to="{name: 'getEnroll', query: {id: item.period_id}}">
                <img class="person_header2 fl" :src="item.icon | formatImage">
                <div class="bm_con">
                  <div class="hidden bm_font ml6">
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
                    <span>
                    </span>
                    <span class="surplus_num s_num" v-if="!item.isFinished && item.left_count > 0">
                      剩余{{item.left_count}}人
                    </span>
                    <span class="surplus_num red_full" v-if="!item.isFinished && item.left_count === 0">
                      已 满
                    </span>
                    <button class="surplus_num g_remind" v-if="item.isFinished && !item.isReminderAdded" v-on:click="remind(item.period_id)" >
                    开班提醒我
                    </button>
                    <span class="surplus_num end_grey" v-if="item.isFinished && item.isReminderAdded">
                      开班提醒我
                    </span>
                    <span v-if="item.isFinished">
                      <img class="end_png" src="../../assets/img/end.png" />
                    </span>
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
import { formatDate } from '../../common/date'
import searchHeader from '../../components/SearchHeader'
export default {
  data: function () {
    return {
      activeName: 'first',
      currentPage: -1,
      take: 20,
      data: [],
      busy: false,

      data_sec: [],
      // data_finish_loaded: false,
      currentPage_sec: -1,
      busy_sec: false,

      searchPlaceHolder: '输入关键词搜索相关课程'
    }
  },
  components: {
    searchHeader
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
    handleClick: function (tab, event) {
      console.log(tab, event)
    },
    loadMore: function () {
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take
      }
      api.fetch(api.uri.getEnrollList, params).then(data => {
        if (data.status === 1) {
          // 只有每次拿回来的数据数量和take的数量相等时， 证明有可能有下一页， 否则直接认为没有下一页了。
          if (data.result.length === this.take) {
            this.busy = false
          }
          // 只有实际拿到数据后， 才附加到data属性上
          if (data.result.length > 0) {
            this.data = this.data.concat(data.result)
          }
        } else {
          // TODO:
        }
      }).catch(error => {
        // TODO:
        console.log(error.message)
      })
    },
    loadMore_sec: function () {
      this.busy_sec = true
      this.currentPage_sec = this.currentPage_sec + 1
      var params = {
        skip: this.currentPage_sec * this.take,
        take: this.take
      }
      api.fetch(api.uri.getEnrollListFinish, params).then(data => {
        if (data.status === 1) {
          // 只有每次拿回来的数据数量和take的数量相等时， 证明有可能有下一页， 否则直接认为没有下一页了。
          if (data.result.length === this.take) {
            this.busy_sec = false
          }
          // 只有实际拿到数据后， 才附加到data属性上
          if (data.result.length > 0) {
            this.data_sec = this.data_sec.concat(data.result)
          }
        } else {
          // TODO:
        }
      }).catch(error => {
        // TODO:
        console.log(error.message)
      })
    },
    remind: function (id) {
      console.log(id)
      api.fetch(api.uri.addEnrollReminder, {periodid: id}).then(data => {
        if (data.status === 1) {
          for (var i = 0; i < this.data_sec.length; i++) {
            if (this.data_sec[i].period_id === id) {
              this.data_sec[i].isReminderAdded = true
              break
            }
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
.el-tabs__active-bar {
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
  width: 50%!important;
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
