<template>
  <div id="app">
    <header>
      <div class="logo_c">
        <a class="tl" href="../course/course.html"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="../course/course3.html"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="../course/course5.html"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="我的课程" name="first">
          <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_course" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataCourse" :key="item.course_id">
              <a>
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
              </a>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="我的报名" name="second">
          <ul class=" list_border course_line reg_nohover" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_enroll" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataEnroll" :key="item.enrollment_id">
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
        <el-tab-pane label="我的考试" name="third">
          <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="isBusy_exam" infinite-scroll-distance="10">
            <li class="course_list  line_only" v-for="item in dataExam" :key="item.exam_id">
              <a>
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
                  <p class="exam_explain">
                    {{item.intro}}
                  </p>
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
import { formatDate } from '../../common/date'
export default {
  data: function () {
    return {
      activeName: 'first',
      dataCourse: [],
      dataEnroll: [],
      dataExam: [],
      isBusy_course: false,
      isBusy_enroll: false,
      isBusy_exam: false,
      currentPage_course: -1,
      currentPage_enroll: -1,
      currentPage_exam: -1,
      take: 20
    }
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
      if (this.activeName === 'first' && this.dataCourse.length === 0 && !this.isBusy_course) {
        this.currentPage_course = this.currentPage_course + 1
        this.isBusy_course = true
        api.fetch(api.uri.getMyFavorite, { typeid: 1, take: this.take, skip: this.currentPage_course * this.take }).then(data => {
          if (data.status === 1) {
            this.dataCourse = this.dataCourse.concat(data.result)
            if (data.result.length === this.take) {
              this.isBusy_course = false
            }
          } else {
            // todo:
          }
        }).catch(error => {
          console.log(error.message)
        })
      }
      if (this.activeName === 'second' && this.dataEnroll.length === 0 && !this.isBusy_enroll) {
        this.currentPage_enroll = this.currentPage_enroll + 1
        this.isBusy_enroll = true
        api.fetch(api.uri.getMyFavorite, { typeid: 3, take: this.take, skip: this.currentPage_enroll * this.take }).then(data => {
          if (data.status === 1) {
            this.dataEnroll = this.dataEnroll.concat(data.result)
            if (data.result.length === this.take) {
              this.isBusy_enroll = false
            }
          } else {
            // todo:
          }
        }).catch(error => {
          console.log(error.message)
        })
      }
      if (this.activeName === 'third' && this.dataExam.length === 0 && !this.isBusy_exam) {
        this.currentPage_exam = this.currentPage_exam + 1
        this.isBusy_exam = true
        api.fetch(api.uri.getMyFavorite, { typeid: 2, take: this.take, skip: this.currentPage_exam * this.take }).then(data => {
          if (data.status === 1) {
            this.dataExam = this.dataExam.concat(data.result)
            if (data.result.length === this.take) {
              this.isBusy_exam = false
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
