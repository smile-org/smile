<template>
  <div>
    <!--<header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>-->
    <common-header></common-header>
    <section>
      <div class="course_banner">
        <img :src="data.pic | formatImage"/>
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
            <span id="c_save" v-on:click="favorite"
                  :class="{icon:true,icon4:data.courseCollected,icon5:!data.courseCollected}"></span>
          </li>
          <li class="fr">
            <span class="icon icon3" v-on:click="comment"></span>
          </li>
        </ul>
      </div>

      <el-tabs class=" three_tab" v-model="activeName" @tab-click="handleClick">
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
            <li class="hidden ">
              <span class="fl">课程评分 :</span>
              <el-rate class="fl ml1 show_star2" v-model="data.star" disabled show-text text-color="#ff9900"
                       text-template="{value}"></el-rate>
            </li>
            <li class="">
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
                <span v-on:click='start(item.content_id, item.content_link, item.orignal_path, item.content_type)'>
                  <span v-bind:class="item.typeImageClass" class="media_img media_img1"></span>
                  <span class="c_list_font">{{item.sequnce_title}} {{item.content}}</span>
                </span>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="课程评价" name="third">
          <ul style="overflow: hidden" class="list_border course_con">
            <li v-for="item in courseComment" :key="item.comment_id">
              <p class="time_comment">{{item.created_at | formatDate}}</p>
              <el-row style="font-size: .22rem;">
                <el-col :span="4"><img class="person_header1" :src="item.user_idAvatar | formatImage"></el-col>
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
  import {formatDate} from '../../common/date'
  import axios from 'axios'
  import commonHeader from '../../components/CommonHeader'
  export default {
    data: function () {
      return {
        activeName: 'first',
        data: {},
        // courseCollected
        courseContent: [],
        courseComment: [],
        id: 0,
        userAvatar: ''
      }
    },
    components: {
      commonHeader
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
      this.userAvatar = sessionStorage.getItem('userAvatar')
      var tab = this.$route.query.tab
      api.fetch(api.uri.getCourse, {courseid: this.id}).then(data => {
        if (data.status === 1) {
          this.data = data.result
//          星星评价date
          this.handleStar(this.data)
          if (tab === 'comments') {
            this.activeName = 'third'
            this.handleClick({name: 'third'})
          }
        } else {
          alert(data.result)
        }
      }).catch(error => {
        alert(error.message)
      })
    },
    methods: {
      handleStar: function (data) {
        data.star = data.star.toFixed(1)
//        for (var i = 0; i < data.length; i++) {
//          var current = data[i]
//          if (current && current.star) {
//            current.star = current.star.toFixed(1)
//          }
//        }
      },
      handleClick: function (tab, event) {
        if (tab.name === 'second') {
          api.fetch(api.uri.getCourseContent, {courseid: this.id}).then(data => {
            if (data.status === 1) {
              for (var i = 0; i < data.result.length; i++) {
                var item = data.result[i]
                if (item.content_type.toUpperCase() === 'DOC' || item.content_type.toUpperCase() === 'DOCX') {
                  item.typeImageClass = 'media_img media_img3'
                } else if (item.content_type.toUpperCase() === 'PPTX' || item.content_type.toUpperCase() === 'PPT') {
                  item.typeImageClass = 'media_img media_img2'
                } else if (item.content_type.toUpperCase() === 'MP4' || item.content_type.toUpperCase() === 'MOV') {
                  item.typeImageClass = 'media_img media_img1'
                } else if (item.content_type.toUpperCase() === 'JPG' || item.content_type.toUpperCase() === 'JPEG' || item.content_type.toUpperCase() === 'PNG') {
                  item.typeImageClass = 'media_img media_img4'
                }
              }
              this.courseContent = data.result
              this.handleStar(this.courseContent)
            } else {
              alert(data.result)
            }
          }).catch(error => {
            alert(error.message)
          })
        } else if (tab.name === 'third') {
          api.fetch(api.uri.getCourseComment, {courseid: this.id}).then(data => {
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
          api.fetch(api.uri.cancelFavoriteCourse, {courseid: this.id})
          this.data.collect_count -= 1
        } else {
          this.data.courseCollected = 1
          api.fetch(api.uri.favoriteCourse, {courseid: this.id})
          this.data.collect_count += 1
        }
      },
      comment: function () {
        sessionStorage.setItem('courseTitle', this.data.title)
        router.push({name: 'courseComment', query: {id: this.id}})
      },
      start: function (contentId, contentLink, orignalPath, type) {
        api.fetch(api.uri.startCourse, {
          courseid: this.id,
          contentid: contentId
        }).then(data => {
          if (data.status === 1) {
            if (type === 'ppt' || type === 'pptx' || type === 'doc' || type === 'docx') {
              window.open(axios.defaults.imageServer + contentLink, '_self')
            } else {
              window.open(axios.defaults.imageServer + orignalPath, '_self')
            }
          }
        })
      }
    }
  }
</script>
<style>
  .show_star2 span .el-rate__icon {
    font-size: 0.29rem;
    margin-right: .15rem;
  }

  .show_star2 .el-rate__text {
    font-size: .26rem;
  }
</style>
<style>
  .course_cen h3 {
    max-width: 4.5rem;
  }
</style>



