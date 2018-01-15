<template>
  <header @click="homeClick(false)">
    <div class="search_c">
      <a class="seach_tit tl"><img src="../assets/img/back.png" alt="返回" v-on:click="goBack"/></a>
      <div class="search_input" style="margin-right: .35rem;">
        <img src="../assets/img/seach_icon.png" alt="search"/>
        <!--<span class="search_img">&#xe60f;</span>-->
        <!--<img src="../assets/img/delate.png" />-->
        <input :placeholder="holder" @focus="goSearch">
      </div>
      <!--<a class="seach_tit tr" href="javaScript:;" @click.stop.prevent="homeClick(true)">-->
        <!--<img src="../assets/img/home.png" alt="更多" />-->
      <!--</a>-->
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
export default {
  props: ['holder', 'type'],
  data: function () {
    return {
      nav1: false,
      userAvatar: ''
    }
  },
  created () {
    this.userAvatar = axios.defaults.imageServer + sessionStorage.getItem('userAvatar')
  },
  methods: {
    homeClick: function (status, event) {
      this.nav1 = status
    },
    goBack () {
      router.go(-1)
    },
    goSearch () {
      sessionStorage.removeItem('searchType')
      router.push({name: 'search', query: {type: this.type}})
    }
  }
}
</script>

<style>

</style>
