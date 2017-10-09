
<template>
  <div>
    <common-header></common-header>
    <section>

      <div class="search_tit">
        <ul class="fl_tab" style="position: relative;z-index:9;">
          <li :class="{active: type === 1}" v-on:click="changeModule(1)">
            课程
          </li>
          <li :class="{active: type === 2}" v-on:click="changeModule(2)">
            考试
          </li>
          <li :class="{active: type === 3}" v-on:click="changeModule(3)">
            报名
          </li>
          <li :class="{active: type === 4}" v-on:click="changeModule(4)">
            约课
          </li>
        </ul>
        <div class="b_search_input">
          <img class="s_seach" src="../../assets/img/seach_icon.png" />
          <img class="" src="../../assets/img/delate.png" v-on:click="searchText = ''" />
          <input placeholder="选择资源类型搜索更精准" v-model.trim="searchText" @keyup.13="search">
        </div>
      </div>

      <ul class="list_border course_con s_div">
        <li>
          <img class="s_ing vm" src="../../assets/img/s_ing.png" />
          <span>历史查询</span>
          <img class="s_delate" src="../../assets/img/s_delate.png" v-on:click="deleteSearchHistory" />
        </li>
        <li class="s_tag">
          <span class="history_tag" v-on:click="clickHistory(item)" v-for="(item, index) in historySearchItems" :key="index">{{item.keyword}}</span>
        </li>
      </ul>
      <ul class="list_border course_con  s_div mt3">
        <li>
          <img class="s_ing vm" src="../../assets/img/s_fire.png" />
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
      type: 1, // 1: 课程, 2: 考试, 3: 报名, 4: 约课
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
      this.searchText = item
      this.search()
    },
    clickHistory: function (item) {
      this.searchText = item.keyword
      this.search()
    },
    search: function () {
      if (this.searchText.trim().length > 0) {
        api.fetch(api.uri.addSearchHistory, { typeid: this.type, keyword: this.searchText }).then(data => {
          if (data.status === 1) {
            router.push({ name: 'searchResult', query: { type: this.type, search: this.searchText } })
          }
        })
      }
    },
    changeModule: function (index) {
      this.type = index
      axios.all([
        api.fetch(api.uri.getSearchHistoryList, { typeid: this.type }),
        api.fetch(api.uri.getRecommanedKeyWords, { typeid: this.type })
      ]).then(axios.spread((historyList, recommandList) => {
        if (historyList.status === 1) {
          this.historySearchItems = historyList.result
        }
        if (recommandList.status === 1) {
          this.hotSearchItems = recommandList.result
        }
      }))
      console.log(this.type)
    },
    deleteSearchHistory: function () {
      this.searchText = ''
      this.historySearchItems = []
      api.fetch(api.uri.deleteSearchHistory, { typeid: this.type }).then(data => {
        if (data.status === 1) {
          this.historySearchItems = []
        }
      })
    }
  },
  created () {
    var queryType = parseInt(this.$route.query.type)
    if (queryType) {
      this.type = queryType
    }
    axios.all([
      api.fetch(api.uri.getSearchHistoryList, { typeid: this.type }),
      api.fetch(api.uri.getRecommanedKeyWords, { typeid: this.type })
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
