<template>
  <div>
    <!--<header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关考试">
        </div>
        <a class="seach_tit tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>-->
    <search-header v-bind:holder="searchPlaceHolder" v-bind:type="2"></search-header>
    <section>
      <ul class="mt3 list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.exam_id">
          <router-link v-bind:to="{name: 'getExamInfo', query: {id: item.exam_id}}">
            <img :src="item.icon | formatImage" class="fl img_bg">
            <div class="course_cen">
              <div class="hidden effect_right">
                <h3 class="fl">{{item.exam_title}}</h3>
                <ul class="small_icon fr">
                  <li class="fl">
                    <span class="icon icon1"></span>
                    <span class="green00b">{{item.study_count}}</span>
                  </li>
                  <li class="fl">
                    <span class="icon icon2"></span>
                    <span class="redff7">{{item.collect_count}}</span>
                  </li>
                </ul>
              </div>

            </div>
            <p class="exam_explain">
              {{item.intro}}
            </p>
          </router-link>
        </li>
      </ul>
    </section>
    <v-footer currentTag="2"></v-footer>
  </div>
</template>

<script>
import axios from 'axios'
import api from '../../services/api'
import VFooter from '../../components/footer'
import searchHeader from '../../components/SearchHeader'
export default {
  data: function () {
    return {
      currentPage: -1,
      take: 20,
      data: [],
      busy: false,
      searchPlaceHolder: '输入关键词搜索相关考试',
      type: 2
    }
  },
  components: {
    searchHeader,
    VFooter
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
        take: this.take
      }
      api.fetch(api.uri.getExamList, params).then(data => {
        if (data.status === 1) {
          this.data = this.data.concat(data.result)
          if (data.result.length === this.take) {
            this.busy = false
          }
        } else {
          this.open(data.result)
        }
      }).catch(error => {
        // this.openMessageBox(error.message)
        console.log(error.message)
      })
    }
  }
}
</script>
