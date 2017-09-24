<template>
  <div id="app">
    <common-header></common-header>
    <section>
      <div class="search_tit">
        <ul class="fl_tab">
          <li :class="{active: type === 1}" v-on:click="selectModule(1)">
            课程
          </li>
          <li :class="{active: type === 2}" v-on:click="selectModule(2)">
            考试
          </li>
          <li :class="{active: type === 3}" v-on:click="selectModule(3)">
            报名
          </li>
          <li :class="{active: type === 4}" v-on:click="selectModule(4)">
            约课
          </li>
        </ul>
        <div class="b_search_input">
          <img class="s_seach" src="../../assets/img/seach_icon.png" />
          <img class="s_delate" src="../../assets/img/delate.png" v-on:click="goBack" />
          <input placeholder="选择资源类型搜索更精准" v-model.trim="search" @focus="goBack">
        </div>
      </div>
      <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.id">
          <a>
            <img :src="item.icon | formatImage" class="fl img_bg">
            <div class="course_cen">
              <div class="hidden">
                <h3 class="fl">{{item.title}}</h3>
                <!--<ul class="small_icon fr">
                  <li class="fl">
                    <span class="icon icon1"></span>
                    <span class="green00b">80</span>
                  </li>
                  <li class="fl">
                    <span class="icon icon2"></span>
                    <span class="redff7">80</span>
                  </li>
                </ul>-->
              </div>
          </div>
          <p class="exam_explain">
            {{item.intro}}
          </p>
          </a>
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
      take: 20,
      currentPage: -1,
      busy: false,
      search: '',
      data: []
    }
  },
  components: {
    commonHeader
  },
  created () {
    this.type = this.$route.query.type
    this.search = this.$route.query.search
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    }
  },
  methods: {
    loadMore: function () {
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take,
        query: this.search,
        typeid: this.type
      }
      api.fetch(api.uri.search, params).then(data => {
        if (data.status === 1) {
          if (data.result.length === this.take) {
            this.busy = false
          }
          if (data.result.length > 0) {
            this.data = this.data.concat(data.result)
          }
        }
      })
    },
    goBack: function () {
      router.go(-1)
    },
    selectModule: function (type) {
      this.type = type
      router.push({name: 'search'}, {query: { type: type }})
    }
  }
}
</script>

<style>

</style>
