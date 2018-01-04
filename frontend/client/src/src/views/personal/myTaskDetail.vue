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
              {{data.task_title}}
            </p>
            <p class="tasking_font">
              <span class="">截止日期 ：</span>
              {{data.expiration_date | formatDate}}
            </p>
            <p class="tasking_font">
              <span class="">目标学员范围 ：</span>
              {{data.task_scope}}
            </p>
            <p class="tasking_font">
              <span class="">学习任务描述 ：</span>
              {{data.task_description}}
            </p>
            <p class="tasking_font">
              <span class="">任务完成进度 ：</span>
              {{data.learn_percentage}}
            </p>
            <div class="surplus_num t_bor1 " style="">
              <div class="o_f" v-show="data.count_down >= 0">倒计时：<span>{{data.count_down}}</span>天</div>
              <div class="o_f" v-show="data.count_down == -1">已结束</div>
            </div>
            <router-link class="tasking_see" v-bind:to="{path: '/myTaskSee', query: {id: data.task_id}}">查看同学</router-link>
          </a>
        </li>
      </ul>
      <div class="hidden">
        <h3 class="list_need">课程</h3>
        <ul class="list_border course_line" v-show="data.CourseList.length > 0">
          <li class="course_list  line_only" v-for="course in data.CourseList" :key="course.content.course_id">
            <router-link v-bind:to="{path: '/getCourseDetails', query: {id: course.content.course_id}}">
              <img :src="course.content.icon | formatImage" class="fl img_bg">
              <div class="course_cen show_star">
                <div class="hidden effect_right ">
                  <h3 class="fl" style="max-width: 2.85rem;">{{course.content.title}}</h3>
                  <el-rate class="star_time" v-model="course.content.star" disabled text-color="#ff9900" ></el-rate>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{course.content.study_count}}</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">{{course.content.collect_count}}</span>
                    </li>
                  </ul>
                </div>
              </div>
              <p class="exam_explain">
                {{course.content.intro}}
              </p>
              <div  class="surplus_num" :class="course.learn_status=='未完成'?'t_bor3':'t_bor2'" style="">
                {{course.learn_status}}
              </div>
            </router-link>
          </li>
        </ul>
        <div class="null_date" v-show="data.CourseList.length === 0">
          <img class="vm mr1" src="../../assets/img/sade_null.png"> <span class="vm">暂无数据</span>
        </div>
      </div>
      <div class="hidden">
        <h3 class="list_need" >考试</h3>
        <ul class="list_border course_line" v-show="data.ExamList.length > 0">
          <li class="course_list  line_only" v-for="exam in data.ExamList" :key="exam.content.exam_id">
            <router-link v-bind:to="{path: '/getExamInfo', query: {id: exam.content.exam_id}}">
              <img :src="exam.content.icon|formatImage" class="fl img_bg">
              <div class="course_cen">
                <div class="hidden effect_right ">
                  <h3 class="fl" style="max-width: 2.85rem;">{{exam.content.exam_title}}</h3>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{exam.content.study_count}}</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">{{exam.content.collect_count}}</span>
                    </li>
                  </ul>
                </div>
              </div>
              <p class="exam_explain">
                {{exam.content.intro}}
              </p>
              <div  class="surplus_num" :class="exam.learn_status=='未完成'?'t_bor3':'t_bor2'" style="">
                {{exam.learn_status}}
              </div>
            </router-link>
          </li>
        </ul>
        <div class="null_date" v-show="data.ExamList.length === 0">
          <img class="vm mr1" src="../../assets/img/sade_null.png"> <span class="vm">暂无数据</span>
        </div>
      </div>
      <div  class="hidden">
        <h3 class="list_need">报名</h3>
        <ul class=" list_border course_line reg_nohover" v-show="data.EnorllmentList.length > 0">
          <li class="course_list  line_only" v-for="enroll in data.EnorllmentList" :key="enroll.content.period_id">
            <router-link v-bind:to="{name: 'getEnroll', query: {id: enroll.content.period_id}}">
              <img class="person_header2 fl" :src="enroll.content.icon | formatImage">
              <div class="bm_con_bm show_star">
                <div class="hidden bm_font ml6">
                  <h3 class=" mb10" style="max-width: 2.85rem;">{{enroll.content.title}}</h3>
                  <p class="ellipsis" style="width: 73%;">主讲：{{enroll.content.teacher}}</p>
                  <p class="" style="">{{enroll.content.start_date | formatDate}}--{{enroll.content.end_date | formatDate}}</p>
                  <el-rate class="star_time" v-model="enroll.content.star" disabled  text-color="#ff9900"></el-rate>
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{enroll.content.study_count}}</span>
                    </li>
                    <li class="fl">
                      <span class="icon icon2"></span>
                      <span class="redff7">{{enroll.content.collect_count}}</span>
                    </li>
                  </ul>
                  <!-- <span class="surplus_num s_num" style="right:0.05rem;top:.65rem;" v-if="item.left_count > 0">
                    剩余{{item.left_count}}人
                  </span> -->
                  <!--<span class="surplus_num red_full" v-else-if="item.left_count === 0">
                    已 满
                  </span>
                  <img class="end_png" src="../../assets/img/end.png" />-->
                </div>
              </div>
              <div class="surplus_num" :class="enroll.learn_status=='未完成'?'t_bor3':'t_bor2'" style="">
                {{enroll.learn_status}}
              </div>
            </router-link>
          </li>
        </ul>
        <div class="null_date" v-show="data.EnorllmentList.length === 0">
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
  import {formatDate} from '../../common/date'
  export default {
    data: function () {
      return {
        id: 0,
        data: {}
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
    created () {
      this.id = this.$route.query.id
      api.fetch(api.uri.getTaskDetail, {taskid: this.id}).then(data => {
        if (data.status === 1) {
          this.data = data.result
        }
      })
    }
  }
</script>

<style scoped="scope">

</style>
