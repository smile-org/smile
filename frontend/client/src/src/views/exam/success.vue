
<template>
  <div id="app">
    <header>
      <div class="logo_c">
        <a class="tl" href="../course/course.html"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="../course/course3.html"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="../course/course5.html"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <div class="appraise_w">
        <div class="face_img1"></div>
        <div class="face_font">
          <span>恭喜您通过了本次考试，</span>
          </br>
          <span>{{score}}分，</span>
          </br>
          <span>还可以继续挑战高分哦。</span>
        </div>
        <el-row>
          <el-col :span="12">
            <button class="exam_btn orange_b fr" type="button" v-on:click="goExam">重新考试</button>
          </el-col>
          <el-col :span="12">
            <button class="exam_btn green_b fl" type="button" v-on:click="goHomepage">返回首页</button>
          </el-col>
        </el-row>
      </div>
    </section>
  </div>
</template>

<script>
import router from '../../router'
export default {
  data: function () {
    return {
      score: 0,
      id: 0,
      historyId: 0
    }
  },
  created () {
    this.score = this.$route.query.score
    this.historyId = this.$route.query.historyId
    this.id = this.$route.query.id
  },
  methods: {
    goExam: function () {
      router.push({name: 'startExam', query: {id: this.id, historyId: this.historyId}})
    },
    goHomepage: function () {
      router.push({name: 'homepage'})
    }
  }
}
</script>

<style>

</style>


