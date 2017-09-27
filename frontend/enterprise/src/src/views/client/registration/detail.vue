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
      <div class="course_banner">
        <a href="##"><img :src="data.pic | formatImage" /></a>
      </div>
      <div class="course_tit">
        <h3>{{data.title}}</h3>
        <ul class="c_icon">
          <li class="fl">
            <span class="icon icon1"></span>
            <span class="green00b">{{data.study_count}}</span>
          </li>
          <li class="fl">
            <span class="icon icon2"></span>
            <span class="redff7">{{data.collect_count}}</span>
          </li>
          <li class="fr">
            <span id="c_save" v-on:click="favorite" :class="{icon:true,icon4:data.enrollmentCollected,icon5:!data.enrollmentCollected}"></span>
          </li>
          <li class="fr">
            <span class="icon icon3" v-on:click="comment"></span>
          </li>
        </ul>

      </div>
      <el-tabs class="three_tab" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="课程信息" name="first">
          <ul class="list_border course_con mb3hafe">
            <li class="">
              <span>讲师 :</span> {{data.teacher}}
            </li>
            <li>
              <span>开班地点 :</span> {{data.location}}
            </li>
            <li>
              <span>有效期 :</span> {{data.start_date | formatDate}} ~ {{data.end_date | formatDate}}
            </li>
            <li class="position_r">
              <span>人数 :</span> {{data.count}}
              <em class="surplus_num red_full" style="bottom: .2rem;" v-if="data.left_count === 0">已  满</em>
              <em class="surplus_num s_num" style="bottom: .2rem;" v-if="data.left_count > 0">剩 余 {{data.left_count}} 人</em>
            </li>
            <li>
              <span>课程简介 :</span>
              <p>
                {{data.intro}}
              </p>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="课程目录" name="second">
          <ul class="list_border course_con mb3hafe">
            <li v-for="item in courseContent" :key="item.content_id">
              {{item.sequnce_title}} {{item.content}}
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="课程评价" name="third">
          <ul class="list_border course_con mb3hafe">
            <li v-for="item in courseComment" :key="item.comment_id">
              <el-row class="font22">
                <el-col :span="4"><img class="person_header" :src="item.user_idAvatar | formatImage"></el-col>
                <el-col :span="6" class="">
                  <p>{{item.user_idName}}</p>
                  <el-rate v-model="item.star" disabled text-color="#ff9900" text-template="{value}">
                  </el-rate>
                </el-col>
                <el-col :span="14">{{item.comments}}</el-col>
              </el-row>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>
    </section>
    <footer>
      <button class="login_btn btn_position" :class="{ b_active: data.isEnrollemntAdded }" type="button" v-on:click="enroll">我要报名</button>
    </footer>
  </div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import { formatDate } from '../../common/date'
import router from '../../router'
import commonHeader from '../../components/CommonHeader'
export default {
  data: function () {
    return {
      activeName: 'first',
      data: {},
      courseContent: [],
      courseComment: [],
      id: 0,
      enrollmentId: 0
    }
  },
  components: {
    commonHeader
  },
  created () {
    this.id = this.$route.query.id
    api.fetch(api.uri.getEnrollCourse, { periodid: this.id }).then(data => {
      if (data.status === 1) {
        this.enrollmentId = data.result.enrollment_id
        this.data = data.result
      } else {
        // alert(data.result)
      }
    }).catch(error => {
      alert(error.message)
    })
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
      if (tab.name === 'second') {
        api.fetch(api.uri.getEnrollCourseCategory, { periodid: this.id }).then(data => {
          if (data.status === 1) {
            this.courseContent = data.result
          } else {
            // alert(data.result)
          }
        }).catch(error => {
          console.log(error.message)
        })
      } else if (tab.name === 'third') {
        api.fetch(api.uri.getEnrollCourseComment, { enrollmentid: this.enrollmentId }).then(data => {
          if (data.status === 1) {
            this.courseComment = data.result
          } else {
            // alert(data.result)
          }
        }).catch(error => {
          console.log(error.message)
        })
      }
    },
    enroll: function () {
      api.fetch(api.uri.enroll, { periodid: this.id }).then(data => {
        if (data.status === 1) {
          this.data.isEnrollemntAdded = true
        } else {
          alert(data.message)
        }
      }).catch(error => {
        console.log(error.message)
      })
    },
    favorite: function () {
      if (this.data.enrollmentCollected === 1) {
        this.data.enrollmentCollected = 0
        this.data.collect_count = this.data.collect_count - 1
        api.fetch(api.uri.cancelFavoriteEnrollment, { periodid: this.id })
      } else {
        this.data.enrollmentCollected = 1
        this.data.collect_count = this.data.collect_count + 1
        api.fetch(api.uri.favoriteEnrollment, { periodid: this.id })
      }
    },
    comment: function () {
      sessionStorage.setItem('enrollCourseTitle', this.data.title)
      router.push({ name: 'getEnrollComment', query: { id: this.enrollmentId } })
    }
  }
}
</script>

<style>

</style>
