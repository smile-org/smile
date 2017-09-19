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
      <div class="course_banner">
        <img :src="data.pic | formatImage" />
      </div>
      <div class="course_tit">
        <h3>{{data.exam_title}}</h3>
        <div class="exam_peo">
         <span class="icon icon1"></span>
         <span class="green00b">{{data.study_count}}</span>
        </div>
        <ul class="c_icon">
          <li class="fl">
            <span class="icon icon1"></span>
            <span class="green00b">{{data.study_count}}</span>
          </li>
          <li class="fl">
            <span class="icon icon2"></span>
            <span class="redff7">{{data.collect_count}}}</span>
          </li>
          <li class="fr">
            <span id="c_save" v-on:click="favorite" :class="{icon:true,icon4:data.examCollected,icon5:!data.examCollected}"></span>
          </li>
        </ul>

      </div>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="考试信息" name="first">
          <ul class="mb3hafe list_border course_con">
            <li class="tc">
              <el-row>
                <el-col :span="8" class="exam_inf">
                  <p>
                    <span class="o_s">{{data.tries_limit}}</span>
                    <span>次</span>
                  </p>
                  <div>
                    <img class="exam_time" src="../../assets/img/icon6.png" />
                    <span>次数限制</span>
                  </div>
                </el-col>
                <el-col :span="8" class="exam_inf">
                  <p>
                    <span class="o_s">{{data.pass_score}}</span>
                    <span>分</span>
                  </p>
                  <div>
                    <img class="exam_time" src="../../assets/img/icon7.png" />
                    <span>通过分数</span>
                  </div>
                </el-col>
                <el-col :span="8" class="exam_inf">
                  <p>
                    <span class="o_s">{{time_limit}}</span>
                    <span>分钟</span>
                  </p>
                  <div>
                    <img class="exam_time" src="../../assets/img/icon8.png" />
                    <span>时间限制</span>
                  </div>
                </el-col>
              </el-row>
            </li>
            <li>
              <span>考试编码 :</span> {{data.exam_num}}
            </li>
            <li>
              <span>有效期 :</span> {{data.start_data | formatDate}} ~ {{data.end_data | formatDate}}
            </li>
            <li>
              <span>管理员 :</span> {{data.manager_idName}}
            </li>
            <li>
              <span>考试简介 :</span>
              <p>{{data.intro}}</p>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="学习资料" name="second">
          <ul class="mb3hafe list_border course_line">
            <li class="course_list  line_only" v-for="item in examCourses" :key="item.course_id">
              <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
                <img src="../../assets/img/img1.png" class="fl img_bg">
                <div class="course_cen">
                  <div class="hidden">
                    <h3 class="fl">{{item.exam_title}}</h3>
                  </div>
                  <p class="exam_explain">
                    {{item.intro}}
                  </p>
                </div>
              </router-link>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="考试记录" name="third">
          <ul class="mb3hafe list_border course_con">
            <li class="exam_hight" v-for="item in examRecords" :key="item.history_id">
              <el-row style="font-size: .22rem;">
                <el-col :span="10">
                  {{item.start_date|formatTime}} ~ {{item.end_date|formatTime}}
                </el-col>
                <el-col :span="6" class="tc">
                  {{item.score}}分
                </el-col>
                <el-col :span="8" class="tc">
                  <img class="repeat_pic" src="../../assets/img/repeat-pic1.png" v-show="item.status === 'pass'">
                  <img class="repeat_pic" src="../../assets/img/repeat-pic2.png" v-show="item.status === 'failure'">
                </el-col>
              </el-row>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>

    </section>
    <footer>
      <button class="login_btn btn_position" type="button" v-show="data.status === 'started'">我要考试</button>
      <button class="login_btn btn_position" type="button" v-show="data.status === 'inProgress'">继续答题</button>
      <button class="login_btn btn_position disabled" disabled="disabled" type="button" v-show="data.status === 'notStarted'">考试暂未开始</button>
    </footer>
  </div>
</template>

<script>
import api from '../../services/api'
import router from '../../router'
import { formatDate } from '../../common/date'
import axios from 'axios'
export default {
  data: function () {
    return {
      activeName: 'first',
      data: {},
      id: 0,
      examCourses: [],
      examRecords: []
    }
  },
  methods: {
    favorite: function () {
      if (this.data.examCollected === 1) {
        this.data.examCollected = 0
        api.fetch(api.uri.cancelFavoriteExam, { examid: this.id })
      } else {
        this.data.examCollected = 1
        api.fetch(api.uri.favoriteExam, { examid: this.id })
      }
    },
    handleClick: function () {
      if (tab.name === 'second') {
        api.fetch(api.uri.getExamCourses, { examid: this.id }).then(data => {
          if (data.status === 1) {
            this.examCourses = data.result
          }
        }).catch(error => {
          // TODO:
        })
      } else if (tab.name === 'third') {
        api.fetch(api.uri.getExamRecords, {examid: this.id}).then(data => {
          if (data.status === 1) {
            this.examRecords = data.result
          }
        }).catch(error => {
          // TODO:
        })
      }
    }
  },
  created () {
    this.id = this.$route.query.id
    api.fetch(api.uri.getExamInfo, { examid: this.id}).then(data => {
      if (data.status === 1) {
        this.data = data.result
      }
    }).catch(error => {
      // TODO
    })
  },
  filters: {
    formatDate (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    },
    formatImage (uri) {
      return axios.defaults.baseURL + uri
    },
    formatTime (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd HH:mm:ss')
    }
  }
}
</script>
