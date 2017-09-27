
<template>
  <div>
    <!--<header>
      <div class="search_c">
        <a class="seach_tit tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <div class="search_input">
          <img src="../../assets/img/seach_icon.png" />
          <img src="../../assets/img/delate.png" />
          <input placeholder="输入关键词搜索相关课程" >
        </div>
        <a class="seach_tit tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>-->
    <search-header v-bind:holder="searchPlaceHolder" v-bind:type="type"></search-header>
    <section>
      <ul class="list_border course_bg" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
        <li class="con_list" v-for="item in data" :key="item.category_id">
          <router-link v-bind:to="{path: '/getCourses', query: {id: item.category_id}}">
            <img :src="item.icon | formatImage" class="course_img" />
            <span class="c_list_font">{{item.category_name}}</span>
          </router-link>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import api from '../../services/api'
// import router from '../../router/index'
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
  created () {
    // api.get(api.uri.getCourseCategories, { skip: this.currentPage * this.take, take: this.take }).then(data => {
    //   if (data.status === 1) {
    //     this.data = data.result
    //   }
    // }).catch(error => {
    //   window.openMessageBox(error.message)
    // })
  },
  methods: {
    // search: function () {
    //   this.currentPage = 0
    //   var params = {
    //     skip: this.currentPage * this.take,
    //     take: this.take,
    //     query: this.query
    //   }
    //   api.fetch(api.uri.getCourseCategories, params).then(data => {
    //     if (data.status === 1) {
    //       this.data = data.result
    //     }
    //   })
    // },
    loadMore: function () {
      this.busy = true
      this.currentPage = this.currentPage + 1
      var params = {
        skip: this.currentPage * this.take,
        take: this.take
      }
      api.fetch(api.uri.getCourseCategories, params).then(data => {
        if (data.status === 1) {
          if (data.result.length === this.take) {
            this.busy = false
          }
          this.data = this.data.concat(data.result)
        } else {
          this.open(data.result)
        }
      }).catch(error => {
        this.openMessageBox(error.message)
      })
    },
    openMessageBox: function (content) {
      this.$alert(content, {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${action}`
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



