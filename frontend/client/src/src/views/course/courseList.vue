<template>
  <div>
    <search-header v-bind:holder="searchPlaceHolder" v-bind:type="type"></search-header>
    <section>
      <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.course_id">
          <router-link v-bind:to="{path: '/getCourseDetails', query: {id: item.course_id}}">
            <img :src="item.icon | formatImage" class="fl img_bg">
            <div class="course_cen show_star">
              <div class="hidden effect_right">
                <h3 class="fl">{{item.title}}</h3>
                <el-rate class="star_time" v-model="item.star" disabled show-text text-color="#ff9900" text-template="{value}"></el-rate>
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
  </div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import searchHeader from '../../components/SearchHeader'
export default {
  data: function () {
    return {
      currentPage: -1,
      take: 20,
      data: [],
      busy: false,
      searchPlaceHolder: '输入关键词搜索相关课程',
      type: 1
    }
  },
  components: {
    searchHeader
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    }
  },
  methods: {
    handleStar: function (data) {
      for (var i = 0; i < data.length; i++) {
        var current = data[i]
        if (current && current.star) {
          current.star = current.star.toFixed(1)
        }
      }
    },
    loadMore: function () {
      this.busy = true
      var id = this.$route.query.id

      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take,
        cateid: id
      }
      api.fetch(api.uri.getCourseByCategoryId, params).then(data => {
        if (data.status === 1) {
          if (data.result.length === this.take) {
            this.busy = false
          }
          this.data = this.data.concat(data.result)
          this.handleStar(this.data)
        } else {
          // TODO:统一处理弹框
        }
      })
    }
  }
}
</script>


