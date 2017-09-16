<template>
  <div id="app">
    <header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <!--<el-input icon="el-icon-search" />-->
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关课程" >
        </div>
        <a class="seach_tit tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <ul class="list_border course_line" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="course_list  line_only" v-for="item in data" :key="item.course_id">
          <a>
            <img :src="item.icon" class="fl img_bg">
            <div class="course_cen">
              <div class="hidden">
                <h3 class="fl">{{item.title}}</h3>
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
              <p class="exam_explain">
                {{item.intro}}
              </p>
            </div>
          </a>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import api from '../../services/api'

export default {
  data () {
    return {
      currentPage: -1,
      take: 20,
      data: [],
      busy: false
    }
  },
  created () {
    // var id = this.$route.query.id
    // api.fetch(api.uri.getCourseByCategoryId, {category_id: id}).then(data => {
    //   if (data.status === 1) {
    //     this.data = data.result
    //   }
    // })
  },
  methods: {
    // search: function () {
    //   var id = this.$route.query.id
    //   this.currentPage = 0
    //   api.fetch(api.uri.getCourseByCategoryId, {take: this.take, skip: this.currentPage * this.take, category_id: id, query: this.query}).then(data => {
    //     if (data.status === 1) {
    //       this.data = data.result
    //     }
    //   })
    // },
    loadMore: function () {
      var id = this.$route.query.id
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take,
        cateid: id
      }
      api.fetch(api.uri.getCourseByCategoryId, params).then(data => {
        if (data.status === 1) {
          this.data = this.data.concat(data.result)
        } else {
          // TODO:统一处理弹框
        }
        this.busy = false
      })
    }
  }
}
</script>
