<template>
  <div id="app">
<header>
  <div class="logo_c">
    <a class="tl" href="../course/course.html"><img src="../../assets/img/back.png" alt="返回"/></a>
    <a class="tc" href="../course/course3.html"><img src="../../assets/img/logo.png" alt="smile"  class="logo1"/></a>
    <a class="tr" href="../course/course5.html"><img src="../../assets/img/home.png" alt="更多" /></a>
  </div>
  </header>
  <section>
     <div class="course_banner">
       <a href=""><img src="../../assets/img/banner_05.png"/></a>
     </div>
    <el-row class="course_tit">
      <el-col :span="16"><h3>{{data.exam.exam_title}}</h3></el-col>
      <el-col :span="4" class="tc" v-on:click="goQuestionList"><a class="exam_green">答题卡</a></el-col>
      <el-col :span="4" class="tr" v-on:click="submitExam"><a class="exam_green">交卷</a></el-col>
    </el-row>
    <div class="course_tit mt3">
        <ul class="exam_circle">
            <li>
               <span class="c_g c_style"></span>
               <span>已答</span>
            </li>
          <li>
            <span class="c_o c_style"></span>
            <span>存疑</span>
          </li>
          <li>
            <span class="c_grey c_style"></span>
            <span>未答</span>
          </li>
          <li>
            剩余时间<span class="big_g">{{countDown}}</span>分钟
          </li>
        </ul>
      <div class="block mt3">
        <el-pagination
          layout="prev, pager, next"
          :total="questionCount"
          :current-page="currentQuestionNo"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>
    <div class="course_tit mt3 subject_list mb3hafe">
      <h4>{{currentQuestionNo}}/{{questionCount}}
        <span v-if="currentQuestion.currentQuestionType===1">单选题</span>
        <span v-else-if="currentQuestion.currentQuestionType===2">多选题</span>
        <span v-else>是非题</span>
        </h4>
      <dl class="">
        <dt class="mt1">{{currentQuestion.title}}</dt>
        <dd v-for="item in currentQuestion.anwsers" :key="item.anwser_id">
          <span v-bind:class="[choose_a, {c_g : item.isSelected}]">{{item.answer_option}}</span>{{item.anwser_content}}
        </dd>
      </dl>
    </div>
  </section>
  <footer>
    <el-row class="exam_b">
      <el-col v-bind:class="[btn, {active: currentQuestionNo === 1}]" v-on:click="goPre" :span="8">上一题</el-col>
      <el-col class="btn" :span="8"><span>标记存疑</span></el-col>
      <el-col v-bind:class="[btn, {active: currentQuestionNo === questionCount}]" v-on:click="goNext" :span="8">下一题</el-col>
    </el-row>
  </footer>

  <div class="popup_box" v-show="showModal">
    <p>考试题尚未答完，确定交卷么？</p>
    <p class="font22 mt1">提示：如中途退出，将按当前的答题情况交卷，并扣除一次考试机会</p>
    <ul class="q_num">
      <!-- orange -->
      <li v-on:click="gotoQuestion(index)" v-for="(item, index) in data.questions" :key="item.question_id" class="c_o">{{index + 1}}</li>
      <!-- green -->
      <li class="c_g">1</li>
    </ul>
    <div class="el-row dio_btn mt3">
      <div class="el-col el-col-12">
        <button type="button" class="c_g btn fr ">确定</button>
      </div>
      <div class="el-col el-col-12">
        <button type="button" class="c_o  btn fl">取消</button>
      </div>
    </div>
  </div>
  <div class="c-modal" v-show="showModal">

  </div>
</div>
</template>

<script>
import api from '../../services/api'
export default {
  data: function () {
    return {
      // 考试实体
      data: [],
      // 考试id
      id: 0,
      // 考试时间倒计时
      countDown: '',
      // 题目总数
      questionCount: 0,
      // 当前题目
      currentQuestionNo: 1,
      // // 当前题目类型（单选， 多选， 对错）
      // currentQuestionType,
      // 当前题目
      currentQuestion: {},

      // 是否弹出答题卡（或者交卷）模态框
      showModal: false,

      timerTask: null,

      autoSubmitCountDonw: 5
    }
  },
  created () {
    this.id = this.$route.query.id
    api.fetch(api.uri.getExamQuestions, {examid: this.id}).then(data => {
      if (data.status === 1){
        this.data = data.exam
        this.questionCount = this.data.questions.length
        this.currentQuestion = this.questions[currentQuestionNo]

        // 开始计时 http://jsfiddle.net/wr1ua0db/17/
        var timer = this.data.time_left * 60, minutes, seconds
        this.timerTask = setInterval(function () {
          minutes = parseInt(timer / 60, 10)
          seconds = parseInt(timer % 60, 10)
          minutes = minutes < 10 ? "0" + minutes : minutes
          seconds = seconds < 10 ? "0" + seconds : seconds
          this.countDown = minutes + ":" + seconds

          if (--timer <= 0) {
            this.showModal = true
            clearInterval(this.timerTask)

            this.timerTask = setInterval(function () {
              this.autoSubmitCountDonw = this.countDown - 1
              if (this.autoSubmitCountDown <= 0) {
                clearInterval(this.timerTask)
                // TODO: 自动交卷API

              }
            }, 1000)
            //timer = this.data.time_limit
          }
        }, 1000)
      }
    }).catch(error => {
      //TODO:
    })
  },
  methods: {
    handleCurrentChange: function (val) {
      console.log(`current page: ${val}`)
      this.currentQuestion = this.questions[val]
    },
    goPre: function () {
      if (this.currentQuestionNo === 1) {
        return
      } else {
        // 把当前提交， 更新currentquestion
        this.currentQuestion = this.data.questions[currentQuestionNo]
      }
    },
    goNext: function () {
      if (this.currentQuestionNo === this.questionCount) {
        return
      } else {
        // 把当前提交， 更新currentquestion
        this.currentQuestion = this.data.questions[currentQuestionNo]
      }
    },
    gotoQuestion: function (index) {
      this.showModal = false
      this.currentQuestion = this.data.questions[index]
      this.currentQuestionNo = index
    },
    goQuestionList: function () {
      this.showModal = true
    },
    submitExam: function () {
      this.showModal = true
    }
  }
}
</script>
