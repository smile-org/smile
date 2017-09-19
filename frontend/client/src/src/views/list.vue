
<template>
  <div>
    <header>
      <div class="search_c">

        <div class="search_input">
          <input placeholder="输入关键词搜索相关考试" v-model.trim="query" @keyup.13="search">
        </div>

        <!--<el-col :span="4" class="tr" id=""  @click="dialogVisible = true"><a class="exam_green">交卷</a></el-col>-->
        <el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>
      </div>
    </header>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      size="tiny"
      :before-close="handleClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
    </el-dialog>

    <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
      <div v-for="item in data" :key="item.category_id">
        {{ item.category_name }}
      </div>
    </div>

    <span v-on:click="newWindow">
      lalalalalallalalaal
    </span>
  </div>
</template>

<script>
import md5 from 'js-md5'
import api from '../services/api'
// var counter = 0
export default {
  data: function () {
    return {
      currentPage: -1,
      take: 20,
      data: [],
      busy: false,
      query: '',

      // exam
      dialogVisible: false,

      link: 'http://www.baidu.com'
    }
  },
  created () {
    console.log(md5('123456'))
  },
  methods: {
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => { })
    },
    search: function () {
      // this.currentPage = 0
      // api.fetch(api.uri.getCourseCategories, {skip: 0, take: 20, query: this.query}).then(data => {
      //   this.data = data.result
      // })

      this.$message('aaaaaa')
    },
    loadMore: function () {
      if (!this.busy) {
        this.busy = true
        this.currentPage = this.currentPage + 1
        api.fetch(api.uri.getCourseCategories, { skip: this.currentPage * this.take, take: this.take }).then(data => {
          if (data.status === 1) {
            // this.data = data.result
            this.data = this.data.concat(data.result)
          } else {
            this.$message.error(data.result)
          }
          this.busy = false
        }).catch(error => {
          this.$message.error(error.message)
        })
      }
    },
    start: function () {
      console.log('aaaaaaaaaaaaaaaaaaaaaa')
      alert('aaaaaaaaaaaaaaaaaa')
    },
    newWindow: function () {
      console.log('--------------')
      window.open('https://www.baidu.com', '_blank')
    }
  }
}
</script>

<style>

</style>

