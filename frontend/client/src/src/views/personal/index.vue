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
      <croppa v-model="myCroppa" v-if="seen" id="asd" style='text-align: center;color: #ffffff;' placeholder="请选择图片"
              :zoom-speed="5" canvas-color="transparent">
        <div style="margin-top: .7rem;">
          <button @click="upload" class="btn_save">保  存</button>
          <!--<button @click="homeClick" style="background: #dcdcdc;border-color: #dcdcdc;color: #fff;font-size: .3rem">取  消</button>-->
        </div>
        <div style="">
          <button @click="homeClick" class="btn_pqx" style="">取  消</button>
        </div>
      </croppa>
      <div class="personal_bg">
        <div class="person_tit">
          <img :src="data.avatar|formatImage" class="person_img" @click="homeClick()" id="myImg">
          <p class="personal_name">{{data.full_name}}</p>
        </div>
        <el-row class="person_black">
          <el-col :span="8" class="per_num">
            <p>上次学习</p>
            <p class="sec_p"><span>{{data.lastStudy < 0 ? 0 : data.lastStudy}}</span>天前</p>
          </el-col>
          <el-col :span="8" class="per_num">
            <p>本月学习</p>
            <p class="sec_p"><span>{{data.courseCountThisMonth}}</span>门课程</p>
          </el-col>
          <el-col :span="8" class="per_num">
            <p>累计学习</p>
            <p class="sec_p"><span>{{data.coursecountTotal}}</span>门课程</p>
          </el-col>
        </el-row>
      </div>
      <ul class="list_border course_con mb3hafe v_active">
        <li class="">
          <router-link v-bind:to="{name: 'myLearning'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img4.png"/>
            <span class="">我的学习记录</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'myTask'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img5.png"/>
            <span class="">我的学习任务</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'myExam'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img6.png"/>
            <span class="">我的考试记录</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'myEnrollment'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img7.png"/>
            <span class="">面授报名记录</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'myBooking'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img8.png"/>
            <span class="">我的约课记录</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'myFavorites'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img9.png"/>
            <span class="">我的收藏</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'changePWD'}" class="quyu_click">
            <img class="icon_per" src="../../assets/img/logo_img10.png"/>
            <span class="">修改密码</span>
            <img class="personal_go" src="../../assets/img/go02.png"/>
          </router-link>
        </li>
        <!--<li class="" @click="logout()">-->
        <li class=""  @click="open">
          <!-- <router-link v-bind:to="{name: 'changePWD'}" class="quyu_click"> -->
          <!--<img class="icon_per" src="../../assets/img/logo_img10.png"/>-->
          <span class="text_iconfont">&#xe60f;</span>
          <span class="">退出</span>
          <img class="personal_go" src="../../assets/img/go02.png"/>
          <!-- </router-link> -->
        </li>
      </ul>
    </section>
    <div class="exit_fade" v-show="showModal" v-on:click="cancel">
    </div>
    <!--<div  class="c-modal" v-show="showModal"></div>-->
    <div class="exit_con" v-show="showModal">
      <div class="exit_box">
        <p class="exit_font1">
          是否退出登录
        </p>
        <ul class="exit_button">
          <li class="fl">
            <button type="button" v-on:click="cancel">取消</button>
          </li>
          <li class="fl">
            <button type="button" v-on:click="confirmButtonText" >确定</button>
          </li>
        </ul>
      </div>
    </div>
    <v-footer currentTag="4"></v-footer>
  </div>
</template>

<script>
  import api from '../../services/api'
  import router from '../../router/index'
  import axios from 'axios'
  import commonHeader from '../../components/CommonHeader'
  import Croppa from '../../../node_modules/vue-croppa'
  import VFooter from '../../components/footer'
  import Vue from 'vue'
  Vue.use(Croppa)
  export default {
    data: function () {
      return {
        showModal: false,
        currentPage: -1,
        seen: false, // 是否显示更换头像界面
        myCroppa: {},
        data: {}
      }
    },
    components: {
      commonHeader,
      VFooter
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
//      logout: function () {
//        this.$confirm('是否退出登录', {
//          confirmButtonText: '确定',
//          cancelButtonText: '取消',
//          type: 'warning'
//        }).then(() => {
//          sessionStorage.clear()
//          localStorage.clear()
//          router.push({name: 'login'})
//        }).catch(() => {
//
//        })
//      },
      cancel: function () {
        this.showModal = false
      },
      open: function () {
        this.showModal = true
      },
      confirmButtonText: function () {
        sessionStorage.clear()
        localStorage.clear()
        router.push({name: 'login'})
      },
      // 上传头像
      upload: function () {
        if (!this.myCroppa.hasImage()) {
          alert('no image to upload')
          return
        }
        // var imgUrl = this.myCroppa.generateDataUrl()
        api.fetch(axios.defaults.baseURL + '/ossInfo', {businessType: 'header'}).then(data => {
          if (data.status === 1) {
            console.log(data)
            var uploadUrl = data.result.host
            var relatedPath = data.result.dir + api.guid() + '.png'
            var imageUrl = uploadUrl + '/' + relatedPath
            var fd = new FormData()
            fd.append('OSSAccessKeyId', data.result.accessid)
            fd.append('key', relatedPath)
            fd.append('policy', data.result.policy)
            fd.append('signature', data.result.signature)
            fd.append('expire', data.result.expire)
            fd.append('host', data.result.host)
            var that = this
            this.myCroppa.generateBlob(function (blob) {
              fd.append('file', blob)
              api.post(uploadUrl, fd).then(d => {
                document.getElementById('myImg').src = imageUrl
                api.post(api.uri.uploadUserAvatar, {avatar: imageUrl}).then(av => {
                  that.seen = !that.seen
                })
              })
            })
          }
        })
      }
    },

    filters: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri
      }
    }
  }

</script>
<!--<style scope="scoped">-->
  <!--.el-message-box{-->
    <!--border-radius: .1rem!important;-->
    <!--width: 80%!important;-->
    <!--/*padding:0 .3rem;*/-->
  <!--}-->
  <!--.el-message-box__header,.el-message-box__status.el-icon-warning,.el-message-box__headerbtn{-->
    <!--display: none!important;-->
  <!--}-->
  <!--.el-message-box__btns {-->
    <!--padding: 0 .1rem!important;-->
    <!--text-align: right!important;-->
  <!--}-->
  <!--.el-message-box__content{-->
    <!--font-size: .3rem!important;-->
    <!--color: #666!important;-->
    <!--text-align: center!important;-->
    <!--/*height:1.49rem;*/-->
    <!--/*line-height:1.49rem;*/-->
  <!--}-->
  <!--.el-message-box__message {-->
    <!--margin-left: 0!important;-->
  <!--}-->
  <!--.el-button{-->
    <!--border:none!important;-->
    <!--font-weight: normal!important;-->
    <!--font-size: .3rem!important;-->
    <!--background: #fff!important;-->
    <!--color: #00b553!important;-->
    <!--/*padding: .1rem .3rem;*/-->
    <!--flex: 1!important;-->
    <!--border-radius: 0!important;-->
  <!--}-->
  <!--.el-button:hover,.el-button:active,.el-button:focus{-->
    <!--border:none!important;-->
    <!--background: #fff!important;-->
  <!--}-->
  <!--.el-button&#45;&#45;primary.is-active:hover,.el-button&#45;&#45;primary.is-active, .el-button&#45;&#45;primary:focus{-->
    <!--border:none!important;-->
    <!--background: #fff!important;-->
    <!--color: #00b553!important;-->
  <!--}-->
  <!--.el-button&#45;&#45;primary.is-active, .el-button&#45;&#45;primary:active{-->
    <!--border:none!important;-->
    <!--background: #fff!important;-->
    <!--color: #00b553!important;-->
  <!--}-->
  <!--.el-button:focus, .el-button:hover{-->
    <!--color: #00b553!important;-->
  <!--}-->
  <!--.el-message-box__btns{-->
    <!--display: flex!important; /*设为伸缩容器*/-->
    <!--flex-flow: row!important; /*伸缩项目单行排列*/-->
    <!--align-items: center!important;-->
    <!--justify-content: center!important;-->
    <!--height: 1rem!important;-->
    <!--line-height: 0rem!important;-->
    <!--position: relative!important;-->
  <!--}-->
  <!--.el-message-box__btns:before{-->
    <!--content: '';-->
    <!--border-top: 1px solid #e8e8e8!important;-->
    <!--width: 90%!important;-->
    <!--top: 0!important;-->
    <!--right: 5%!important;-->
    <!--position: absolute!important;-->

  <!--}-->
  <!--.el-message-box__btns button:nth-child(2) {-->
    <!--margin-left: .1rem!important;-->
    <!--border-left: 1px solid #e8e8e8!important;-->
  <!--}-->
<!--</style>-->
<style>
  .text_iconfont{
    font-family: jiudian;
    font-size: .24rem;
    color: #ff9925;
    vertical-align: middle;
    margin-right: .1rem;
  }
  #asd {
    background-color: #333;
    position: fixed;
    z-index: 1111;
    height: 100%;
    width: 100%;
    margin-top: -1.2rem;
    padding: .3rem;
  }

  #asd canvas {
    border: 1px solid #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    width: 4rem !important;
    height: 4rem !important;
    margin-top: 1rem;
  }

  #asd svg {
    position: static;
    margin-left: -20px;
    margin-bottom: 280px;
    background: #fff;
    border-radius: 50%;
    -webkit-filter: drop-shadow(-2px 2px 2px rgba(0, 0, 0, .7));
    filter: drop-shadow(-2px 2px 2px rgba(0, 0, 0, .7));
    z-index: 10;
    cursor: pointer;
    border: 2px solid #fff;
    width: 20px;
    height: 20px;
    right: .1rem !important;
  }

  /*!*#asd button {*!*/
  /*margin: 0 auto;*/
  /*width: 100%;*/
  /*}*/
  #asd .btn_save {
    width: 100%;
    height: .8rem;
    background: #00b553;
    outline: 0;
    border-radius: 0.1rem;
    font-size: 0.32rem;
    color: #fff;
    letter-spacing: 0.1rem;
    display: block;
    margin-top: .5rem;
  }

  .btn_pqx {
    width: 100%;
    height: .8rem;
    outline: 0;
    border-radius: 0.1rem;
    font-size: 0.32rem;
    color: #fff;
    letter-spacing: 0.1rem;
    display: block;
    margin-top: .5rem;
    background: #cccccc;
    border-color: #cccccc;
  }

  .el-message-box__wrapper {
    position: fixed;
    top: 0%;
    width: 100%;
    bottom: 0;
    left: 0;
    right: 0;
    padding: .3rem;
    text-align: center;
  }

  .el-message-box {
    width: 100%;
  }
  .exit_fade{
    position: fixed;
    top:0;
    left:0;
    bottom:0;
    width:100%;
    background: rgba(51, 51, 51, 0.48);
    z-index: 10000;
  }
  .exit_con{
    position: absolute;
    /*top: 50%;*/
    /*right: .5rem;*/
    /*left: .5rem;*/
    /*margin-top: -1.5rem;*/
    z-index: 10001;
  }
  .exit_box{
    /*width:100%;*/
    background: #fff;
    height:2.5rem;
    left: .8rem;
    right: .8rem;
    text-align: center;
    border-radius: .1rem;
    top: 50%;
    margin-top: -1.5rem;
    position: fixed;
    padding: 0 .3rem;
  }
  .exit_font1{
    font-size: .3rem;
    color: #666;
    height:1.49rem;
    line-height:1.49rem;
  }
  .exit_button{
    width: 100%;
    display: flex; /*设为伸缩容器*/
    flex-flow: row; /*伸缩项目单行排列*/
    align-items: center;
    justify-content: center;
    height: 1rem;
    line-height: 0rem;
    border-top: 1px solid #e8e8e8;
    /*margin: .15rem 0;*/
  }
  .exit_button li{
    flex: 1;
    /*padding: .2rem 0;*/

  }
  .exit_button li:first-child{
    border-right: 1px solid #e8e8e8;
  }
  .exit_button li button{
    font-size: .3rem;
    background: #fff;
    color: #00b553;
    padding: .1rem .3rem;

  }
</style>


