
<template>
  <div>
    <!--<header>
      <div class="logo_c">
        <a class="tl" href="../course/course.html"><img src="../img/back.png" alt="返回" /></a>
        <a class="tc" href="../course/course3.html"><img src="../img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="../course/course5.html"><img src="../img/home.png" alt="更多" /></a>
      </div>
    </header>-->
    <common-header></common-header>
    <section>
      <div class="search_tit">
        <ul class="fl_tab">
          <li :class="{active: type === 1}" v-on:click="alert(1111)">
            课程
          </li>
          <li :class="{active: type === 2}" v-on:click="alert(1111)">
            考试
          </li>
          <li :class="{active: type === 3}" v-on:click="alert(1111)">
            报名
          </li>
          <li :class="{active: type === 4}" v-on:click="alert(1111)">
            约课
          </li>
        </ul>
        <div class="b_search_input">
          <img class="s_seach" src="../../assets/img/seach_icon.png" />
          <img class="s_delate" src="../../assets/img/delate.png" v-on:click="searchText = ''" />
          <input placeholder="选择资源类型搜索更精准" v-model.trim="searchText" @keyup.13="search">
        </div>
      </div>
      <ul class="list_border course_con s_div">
        <li>
          <img class="s_ing" src="../../assets/img/s_ing.png" />
          <span>历史查询</span>
          <img class="s_delate" src="../../assets/img/s_delate.png" v-on:click="searchText=''" />
        </li>
        <li class="s_tag">
          <span class="history_tag" v-on:click="clickHistory(item)" v-for="(item, index) in historySearchItems" :key="index">{{item.keyword}}</span>
        </li>
      </ul>
      <ul class="list_border course_con  s_div mt3">
        <li>
          <img class="s_ing" src="../../assets/img/s_fire.png" />
          <span>热门推荐</span>
        </li>
        <li class="s_tag">
          <span class="history_tag" v-on:click="clickHot(item)" v-for="(item, index) in hotSearchItems" :key="index">{{item}}</span>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import commonHeader from '../../components/CommonHeader'
import api from '../../services/api'
import axios from 'axios'
import router from '../../router'
export default {
  data: function () {
    return {
      type: 0, // 1: 课程, 2: 考试, 3: 报名, 4: 约课
      searchText: '',
      historySearchItems: [],
      hotSearchItems: []
    }
  },
  components: {
    commonHeader
  },
  methods: {
    clickHot: function (item) {
      this.searchText = item.keyword
      this.search()
    },
    clickHistory: function (item) {
      this.searchText = item.keyword
      this.search()
    },
    search: function () {
      api.fetch(api.uri.addSearchHistory, {typeid: this.type, keyword: this.searchText}).then(data => {
        if (data.status === 1) {
          router.push({name: 'searchResult', query: {type: this.type, search: this.searchText}})
        }
      })
    }
  },
  created () {
    this.type = parseInt(this.$route.query.type)
    axios.all([
      api.fetch(api.uri.getSearchHistoryList, {typeid: this.type}),
      api.fetch(api.uri.getRecommanedKeyWords, {typeid: this.type})
    ]).then(axios.spread((historyList, recommandList) => {
      if (historyList.status === 1) {
        this.historySearchItems = historyList.result
      }
      if (recommandList.status === 1) {
        this.hotSearchItems = recommandList.result
      }
    }))
  }
}
</script>

<style>

</style>
