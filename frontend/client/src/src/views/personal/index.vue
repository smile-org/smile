<style>
  #asd{
    background-color: lightblue;
    position: fixed;
    z-index: 1111;
    height: 100%;
    width: 100%;
    margin-top: -1.2rem;
  }
  #asd canvas{
    border:1px solid #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    width: 300px!important;
    height: 300px!important;
    margin-top: 1rem;
  }
  #asd svg{
    position: static;
    margin-left: -20px;
    margin-bottom: 280px;
    background: #fff;
    border-radius: 50%;
    -webkit-filter: drop-shadow(-2px 2px 2px rgba(0,0,0,.7));
    filter: drop-shadow(-2px 2px 2px rgba(0, 0, 0, .7));
    z-index: 10;
    cursor: pointer;
    border: 2px solid #fff;
    width:20px;
    height: 20px;
    right: .1rem!important;
  }
  #asd button{
    margin:0 auto;
    width: 2rem;
  }
  button{
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4;
  }
</style>
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
    <croppa v-model="myCroppa" v-if="seen" id="asd" style='text-align: center;'  placeholder="请选择图片"  :zoom-speed="5" canvas-color="transparent">
      <div>
        <button @click="upload">保存</button>
        <button @click="homeClick">取消</button>
      </div>
    </croppa >
    <div class="personal_bg">
      <div class="person_tit">
        <img :src="data.avatar|formatImage" class="person_img" @click="homeClick()" id="myImg">
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
         <router-link v-bind:to="{name: 'myLearning'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img4.png" />
          <span class="">我的学习记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myTask'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img5.png" />
          <span class="">我的学习任务</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myExam'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img6.png" />
          <span class="">我的考试记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myEnrollment'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img7.png" />
          <span class="">面授报名记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myBooking'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img8.png" />
          <span class="">我的约课记录</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'myFavorites'}" class="quyu_click">
          <img class="icon_per" src="../../assets/img/logo_img9.png" />
          <span class="">我的收藏</span>
          <img class="personal_go" src="../../assets/img/go02.png" />
        </router-link>
      </li>
      <li class="">
        <router-link v-bind:to="{name: 'changePWD'}" class="quyu_click">
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
import Croppa from '../../../node_modules/vue-croppa'
import Vue from 'vue'
Vue.use(Croppa)
export default {
  data: function () {
    return {
      seen: false, // 是否显示更换头像界面
      myCroppa: {},
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
  methods: {
//    是否显示更换头像界面
    homeClick: function () {
      this.seen = !this.seen
      console.log(this.data.avatar)
    },
    // 上传头像
    upload: function () {
      if (!this.myCroppa.hasImage()) {
        alert('no image to upload')
        return
      }
      var imgUrl = this.myCroppa.generateDataUrl()
      this.myCroppa.generateBlob(function (blob) {
        var fd = new FormData()
        fd.append('file', blob)
        axios({
          url: axios.defaults.baseURL + '/UploadUserPic',
          method: 'post',
          data: fd,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          if (res.status === 1) {
            document.getElementById('myImg').src = imgUrl
            this.seen = !this.seen
          } else {
            alert('上传失败！')
          }
        })
      }.bind(this))
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    }
  }
}

</script>



