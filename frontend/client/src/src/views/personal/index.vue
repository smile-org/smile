<template>
  <div id="app">
<!--<header>-->
  <!--<div class="logo_c">-->
    <!--<a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回"/></a>-->
    <!--<a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile"  class="logo1"/></a>-->
    <!--<a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>-->
  <!--</div>-->
  <!--</header>-->
    <common-header></common-header>
  <section>
    <div class="personal_bg">
      <div class="person_tit">
        <img :src="data.avatar|formatImage" class="person_img">
        <p class="personal_name">{{data.full_name}}</p>
      </div>

      <el-row class="person_black">
        <el-col :span="8" class="per_num">
           <p>上次学习</p>
          <p class="sec_p"><span>{{data.lastStudy}}</span>天前</p>
        </el-col>
        <el-col :span="8"  class="per_num">
          <p>本月学习</p>
          <p class="sec_p"><span>{{data.courseCountThisMonth}}</span>门课程</p>
        </el-col>
        <el-col :span="8"  class="per_num">
          <p>累计学习</p>
          <p class="sec_p"><span>{{data.coursecountTotal}}</span>门课程</p>
        </el-col>
      </el-row>
    </div>
    <ul class="list_border course_con mb3hafe v_active">
      <li class="">
         <router-link v-bind:to="{name: 'myLearning'}">
          <img class="icon_per" src="../../assets/img/logo_img4.png" />
          <span class="">我的学习记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myTask'}">
          <img class="icon_per" src="../../assets/img/logo_img5.png" />
          <span class="">我的学习任务</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myExam'}">
          <img class="icon_per" src="../../assets/img/logo_img6.png" />
          <span class="">我的考试记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myEnrollment'}">
          <img class="icon_per" src="../../assets/img/logo_img7.png" />
          <span class="">面授报名记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myBooking'}">
          <img class="icon_per" src="../../assets/img/logo_img8.png" />
          <span class="">我的约课记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myFavorites'}">
          <img class="icon_per" src="../../assets/img/logo_img9.png" />
          <span class="">我的收藏</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'changePWD'}">
          <img class="icon_per" src="../../assets/img/logo_img10.png" />
          <span class="">修改密码</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
    </ul>
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
      data: {}
    }
  },
  components: {
    commonHeader
  },
  created () {
    api.fetch(api.uri.getPersonal).then(data => {
      if (data.status === 1) {
        this.data = data.result
      }
    })
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    }
  }
}
</script>

