<template>
  <header>
    <div class="logo_c">
      <a class="tl">
        <img src="../assets/img/back.png" alt="返回" v-show="routeName!=='startExam' && routeName!=='examFailed' && routeName !== 'examSuccess'" v-on:click="goBack" />
      </a>
      <!--<router-link v-bind:to="{name: 'homepage'}">-->
      <a><img :src="logo | formatImage"  class="logo1" v-on:click="goHome" /></a>
      <!--</router-link>-->
      <a class="seach_tit tr" href="javaScript:;" @click.stop.prevent="homeClick(true)">
        <!--<img src="../assets/img/home.png" alt="更多" v-show="routeName!=='startExam'" />-->
      </a>
    </div>
    <nav @click="homeClick(false,$event)" id="slide_menu" :class="nav1 ?'active' : ''">
      <div class="user_info">
        <img :src="userAvatar" width="100%" alt="default_img">
      </div>
      <ul class="nav" style="height: 581px;">
        <li class="">
          <router-link v-bind:to="{name: 'homepage'}">首页</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'courseCategories'}">我要学课</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getExamList'}">我要考试</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getEnrollList'}">我要报名</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'getBookingList'}">我要约课</router-link>
        </li>
        <li class="">
          <router-link v-bind:to="{name: 'me'}">个人中心</router-link>
        </li>
      </ul>
    </nav>
    <div class="c-modal" :style="{display:nav1 ? 'block':'none'}">
    </div>
  </header>
</template>

<script>
import axios from 'axios'
import router from '../router'
import api from '../services/api'
export default {
  data: function () {
    return {
      nav1: false,
      userAvatar: '',
      routeName: '',
      logo: '',
      banner: ''
    }
  },
  created () {
    api.fetch(api.uri.getBannerAndLogo).then(data => {
      if (data.status === 1) {
        this.logo = data.logo
        this.banner = data.banner
      }
    })
    this.routeName = this.$route.name
    this.userAvatar = axios.defaults.imageServer + sessionStorage.getItem('userAvatar')
  },
  methods: {
    homeClick: function (status, event) {
      this.nav1 = status
    },
    goBack () {
      router.go(-1)
    },
    goHome () {
      if (this.routeName === 'startExam') {
        return
      } else {
        router.push({name: 'homepage'})
      }
    }
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
//      return !uri ? '' : uri.includes('http://') ? uri : axios.defaults.imageServer + uri
    }
  }

}
</script>

<style>

</style>
