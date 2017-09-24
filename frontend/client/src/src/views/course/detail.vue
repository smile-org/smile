<template>
  <div>
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <div class="course_banner">
        <img :src="data.pic | formatImage" />
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
            <span id="c_save" v-on:click="favorite" :class="{icon:true,icon4:data.courseCollected,icon5:!data.courseCollected}"></span>
          </li>
          <li class="fr">
            <span class="icon icon3" v-on:click="comment"></span>
          </li>
        </ul>
      </div>

      <el-tabs class=" " v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="课程信息" name="first">
          <ul style="overflow: hidden" class="list_border course_con">
            <li class="">
              <span>责任人 :</span> {{data.principal_user_idName}}
            </li>
            <li>
              <span>部门 :</span> {{data.department}}
            </li>
            <li>
              <span>有效期 :</span> {{data.expiration_date | formatDate}}
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
          <ul style="overflow: hidden" class="list_border course_con a_v">
            <li class="con_list" v-for="item in courseContent" :key="item.content_id">
              <span v-on:click='start(item.content_id, item.content_link)'>
                <span v-bind:class="item.typeImageClass" class="media_img media_img1"></span>
                <span class="c_list_font">{{item.sequnce_title}} {{item.content}}</span>
              </span>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="课程评价" name="third">
          <ul style="overflow: hidden" class="list_border course_con">
            <li v-for="item in courseComment" :key="item.comment_id">
              <el-row style="font-size: .22rem;">
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
      // courseCollected
      courseContent: [],
      courseComment: [],
      id: 0
    }
  },
  filters: {
    formatDate (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    },
    formatImage (uri) {
      return axios.defaults.imageServer + uri
    }
  },
  created () {
    this.id = this.$route.query.id
    api.fetch(api.uri.getCourse, { courseid: this.id }).then(data => {
      if (data.status === 1) {
        this.data = data.result
      } else {
        alert(data.result)
      }
    }).catch(error => {
      alert(error.message)
    })
  },
  methods: {
    handleClick: function (tab, event) {
      if (tab.name === 'second') {
        api.fetch(api.uri.getCourseContent, { courseid: this.id }).then(data => {
          if (data.status === 1) {
            for (var i = 0; i < data.result.length; i++) {
              var item = data.result[i]
              if (item.content_type === 'word') {
                item.typeImageClass = 'media_img media_img3'
              } else if (item.content_type === 'ppt') {
                item.typeImageClass = 'media_img media_img2'
              } else if (item.content_type === 'mp4') {
                item.typeImageClass = 'media_img media_img1'
              } else {
                item.typeImageClass = 'media_img media_img4'
              }
            }
            this.courseContent = data.result
          } else {
            alert(data.result)
          }
        }).catch(error => {
          alert(error.message)
        })
      } else if (tab.name === 'third') {
        api.fetch(api.uri.getCourseComment, { courseid: this.id }).then(data => {
          if (data.status === 1) {
            this.courseComment = data.result
          } else {
            alert(data.result)
          }
        }).catch(error => {
          alert(error.message)
        })
      }
    },
    favorite: function () {
      if (this.data.courseCollected === 1) {
        this.data.courseCollected = 0
        api.fetch(api.uri.cancelFavoriteCourse, { courseid: this.id })
      } else {
        this.data.courseCollected = 1
        api.fetch(api.uri.favoriteCourse, { courseid: this.id })
      }
    },
    comment: function () {
      sessionStorage.setItem('courseTitle', this.data.title)
      router.push({ name: 'courseComment', query: { id: this.id } })
    },
    start: function (contentId, contentLink) {
      api.fetch(api.uri.startCourse, {
        courseid: this.id,
        contentid: contentId
      }).then(data => {
        if (data.status === 1) {
          window.open(axios.defaults.imageServer + contentLink, '_blank')
        }
      })
    }
  }
}
</script>

<style>
.el-tabs__active-bar{
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
      width:50%;
    }
    .el-tabs__item.is-active {
      color: #00b553;
    }
    .el-tabs__nav{
      width:100%;
      text-align: center;
      margin-top: -.3rem;
    }
    .el-tabs__header {
      border-bottom:0;
      margin: 0;
    }
    .el-tabs__nav .el-tabs__item:first-child{
      border-right:1px solid #ededed;
    }
</style>


