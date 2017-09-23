<template>
  <div id="app">
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="已报名" name="first">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_finish" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataFinish" :key="item.enrollment_id">
              <a>
                <img class="person_header fl" :src="item.icon | formatImage">
                <div class="bm_con">
                  <div class="hidden bm_font">
                    <h3 class="fl mb15">{{item.title}}</h3>
                    <p class="">主讲：{{item.user_idName}}</p>
                    <p>{{item.start_date | formatDate}} ~ {{item.end_date | formatDate}}</p>
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
              </a>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="预约报名" name="second">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_inProgress" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataFinish" :key="item.enrollment_id">
              <a>
                <img class="person_header fl" :src="item.icon | formatImage">
                <div class="bm_con">
                  <div class="hidden bm_font">
                     <h3 class="fl mb15">{{item.title}}</h3>
                    <p class="">主讲：{{item.user_idName}}</p>
                    <p>{{item.start_date | formatDate}} ~ {{item.end_date | formatDate}}</p>
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
import {formatDate} from '../../common/date'
export default {
  data: function() {
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
  filters: {
    formatImage: function (uri) {
      return axios.defaults.baseURL + uri
    },
    formatDate: function (date) {
      var date = new Date(date)
      return formatDate(date, 'yyyy-MM-dd')
    }
  },
  methods: {
    handleClick: function(tab, event) {

    },

    loadMore: function() {
      if (this.activeName === 'first' && dataFinish.length === 0) {
        this.currentPage_finish = this.currentPage_finish + 1
        this.isBusy_finish = true
        api.fetch(api.uri.getMyEnrollFinish, { take: this.take, skip: this.currentPage_finish * this.take }).then(data => {
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
      if (this.activeName === 'second' && dataInProgress.length === 0) {
        this.currentPage_inProgress = this.currentPage_inProgress + 1
        this.isBusy_inProgress = true
        api.fetch(api.uri.getMyEnrollInProgress, { take: this.take, skip: this.currentPage_inProgress * this.take }).then(data => {
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
  width: 50%;
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
