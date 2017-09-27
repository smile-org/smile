<template>
  <div id="app">
<!--<header>-->
  <!--<div class="logo_c">-->
    <!--<a class="tl" href="../course/course.html"><img src="../../assets/img/back.png" alt="返回"/></a>-->
    <!--<a class="tc" href="../course/course3.html"><img src="../../assets/img/logo.png" alt="smile"  class="logo1"/></a>-->
    <!--<a class="tr" href="../course/course5.html"><img src="../../assets/img/home.png" alt="更多" /></a>-->
  <!--</div>-->
  <!--</header>-->
    <common-header></common-header>
  <section>
     <div class="course_banner">
       <a href=""><img :src="data.exam.pic|formatImage"/></a>
     </div>
    <el-row class="course_tit">
      <el-col :span="16"><h3>{{data.exam.exam_title}}</h3></el-col>
      <el-col :span="4" class="tc" ><a class="exam_green" v-on:click="goQuestionList">答题卡</a></el-col>
      <el-col :span="4" class="tr" ><a class="exam_green" v-on:click="goSubmitModal">交卷</a></el-col>
    </el-row>
    <div class="course_tit mt3">
        <ul class="exam_circle">
            <li class="">
               <span class="c_g c_style"></span>
               <span>已答</span>
            </li>
          <li class="ml1">
            <span class="c_o c_style"></span>
            <span>存疑</span>
          </li>
          <li class="ml1">
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
          :page-size='1'
          :current-page="currentQuestionNo"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>
    <div class="course_tit mt3 subject_list mb3hafe">
      <h4>{{currentQuestionNo}}/{{questionCount}}
        <span v-if="currentQuestion.type_id===1">单选题</span>
        <span v-else-if="currentQuestion.type_id===2">多选题</span>
        <span v-else>是非题</span>
        </h4>
      <dl class="">
        <dt class="mt1">{{currentQuestion.title}}</dt>
        <dd v-for="item in currentQuestion.answers" :key="item.anwser_id">
          <span class="choose_a" v-on:click="choose(item)" v-bind:class="{c_g : item.selected}">{{item.answer_option}}</span> {{item.answer_content}}
        </dd>
      </dl>
    </div>
  </section>
  <footer>
    <el-row class="exam_b">
      <el-col class="btn" v-bind:class="{active: currentQuestionNo === 1}" v-on:click="goPre" :span="8">
        <span :disabled="currentQuestionNo === 1"  v-bind:class="{active: currentQuestionNo === 1}" v-on:click="goPre">上一题</span>
      </el-col>
      <el-col class="btn" :span="8">
        <span v-on:click="suspect">标记存疑</span>
      </el-col>
      <el-col class="btn" v-bind:class="{active: currentQuestionNo === questionCount}" v-on:click="goNext" :span="8">
        <span :disabled="currentQuestionNo === questionCount"  v-bind:class="{active: currentQuestionNo === questionCount}" v-on:click="goNext">下一题</span>
      </el-col>
    </el-row>

  </footer>
  <div class="popup_box" v-show="showModal">
    <div v-if="popupType === 0">
      <p>考试题尚未答完，确定交卷吗？</p>
      <p class="font22 mt1">提示：如中途退出，将按当前的答题情况交卷，并扣除一次考试机会</p>
      <ul class="q_num">
        <li v-on:click="gotoQuestion(index)" v-for="(item, index) in data.questions" :key="item.question_id" :class="{c_o: item.lastStatus === 'indoubt', c_g: item.lastStatus === 'finished'}">{{index + 1}}</li>
      </ul>
      <div class="el-row dio_btn mt3">
        <div class="el-col el-col-12">
          <span class="c_g btn fr " v-on:click="submitExam">交卷</span>
        </div>
        <div class="el-col el-col-12">
          <button type="button" class="c_o  btn fl" v-on:click='showModal=false'>取消</button>
        </div>
      </div>
    </div>
    <div v-else-if="popupType === 1">
      <p>确定交卷吗？</p>
      <ul class="q_num">
        <li v-on:click="gotoQuestion(index)" v-for="(item, index) in data.questions" :key="item.question_id" :class="{c_o: item.lastStatus === 'indoubt', c_g: item.lastStatus === 'finished'}">{{index + 1}}</li>
      </ul>
      <div class="el-row dio_btn mt3">
        <div class="el-col el-col-12">
          <button type="button" class="c_g btn fr " v-on:click="submitExam">确定</button>
        </div>
        <div class="el-col el-col-12">
          <button type="button" class="c_o  btn fl" v-on:click='showModal=false'>取消</button>
        </div>
      </div>
    </div>
    <div v-else>
      <p>考试时间到</p>
      <p class="font22 mt1"><span class="font28 redff7">{{autoSubmitCountDown}}s</span>后自动交卷</p>
      <ul class="q_num">
        <li v-for="(item, index) in data.questions" :key="item.question_id" :class="{c_o: item.lastStatus === 'indoubt', c_g: item.lastStatus === 'finished'}">{{index + 1}}</li>
      </ul>
      <div class="el-row dio_btn mt3">
        <div class="el-col el-col-24 tc">
          <button type="button" class="c_g btn " v-on:click="submitExam">确定</button>
        </div>
      </div>
    </div>
  </div>
  <div class="c-modal" v-show="showModal">

  </div>
</div>
</template>

<script>
import api from '../../services/api'
import axios from 'axios'
import router from '../../router'
import commonHeader from '../../components/CommonHeader'
export default {
  data: function () {
    return {
      // 考试实体
      data: {},
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

      autoSubmitCountDown: 5,

      // 考试记录ID
      historyId: 0,

      // 模态对话框类型， 0：答题卡； 1: 交卷；2：时间到交卷
      popupType: 0
    }
  },
  components: {
    commonHeader
  },
  created () {
    this.id = parseInt(this.$route.query.id)
    this.historyId = parseInt(this.$route.query.historyId)
    api.fetch(api.uri.getExamQuestions, {examid: this.id}).then(data => {
      if (data.status === 1) {
        this.data = data.result
        this.questionCount = this.data.questions.length
        this.currentQuestion = this.data.questions[this.currentQuestionNo - 1]
        console.log(this.currentQuestion)
        // 开始计时 http://jsfiddle.net/wr1ua0db/17/
        var timerSec = this.data.leftSeconds // * 60
        var minutes
        var seconds
        var self = this
        this.timerTask = setInterval(function () {
          minutes = parseInt(timerSec / 60, 10)
          seconds = parseInt(timerSec % 60, 10)
          minutes = minutes < 10 ? '0' + minutes : minutes
          seconds = seconds < 10 ? '0' + seconds : seconds
          self.countDown = minutes + ':' + seconds
          if (--timerSec <= 0) {
            self.popupType = 2
            self.showModal = true
            clearInterval(self.timerTask)

            self.timerTask = setInterval(function () {
              self.autoSubmitCountDown = self.autoSubmitCountDown - 1
              if (self.autoSubmitCountDown <= 0) {
                clearInterval(self.timerTask)
                self.showModal = false
                // TODO: 自动交卷API
                self.submitExam()
              }
            }, 1000)
            // timer = this.data.time_limit
          }
        }, 1000)
      }
    }).catch(error => {
      // TODO:
      console.log(error.message)
    })
  },
  filters: {
    formatImage: function (uri) {
      return axios.defaults.imageServer + uri
    }
  },
  methods: {
    handleCurrentChange: function (val) {
      var _self = this

      _self.updateQuestion(function (err) {
        if (!err) {
          console.log(_self.currentQuestion)
          _self.currentQuestion = _self.data.questions[val - 1]
          _self.currentQuestionNo = val
        } else {
          console.log(err.message)
        }
      })
    },
    updateQuestion: function (cb) {
      // var self = this
      var answerSelectList = []
      for (var i = 0; i < this.currentQuestion.answers.length; i++) {
        if (this.currentQuestion.answers[i].selected) {
          answerSelectList.push(this.currentQuestion.answers[i].answer_id)
        }
      }
      api.post(api.uri.saveQuestionAnwser, {
        historyid: this.historyId,
        questionid: this.currentQuestion.question_id,
        answeridList: answerSelectList,
        questionStatus: this.currentQuestion.lastStatus ? this.currentQuestion.lastStatus : 'pending'
      }).then(data => {
        if (data.status === 1) {
          cb()
        } else {
          // TODO: 统一处理
          console.log('cuole')
        }
      }).catch(error => {
        cb(error)
      })
    },
    goPre: function () {
      var self = this
      this.updateQuestion(function (err) {
        if (!err) {
          self.currentQuestionNo = self.currentQuestionNo - 1
        }
      })
    },
    goNext: function () {
      var self = this
      this.updateQuestion(function (err) {
        if (!err) {
          self.currentQuestionNo = self.currentQuestionNo + 1
        }
      })
    },
    suspect: function () {
      this.currentQuestion.lastStatus = 'indoubt'
    },
    gotoQuestion: function (index) {
      this.showModal = false
      this.currentQuestion = this.data.questions[index + 1]
      this.currentQuestionNo = index + 1
    },
    goQuestionList: function () {
      this.popupType = 0
      this.showModal = true
    },
    submitExam: function () {
      var __self = this
      this.updateQuestion(function (err) {
        if (!err) {
          api.fetch(api.uri.finishExam, {historyid: __self.historyId, examid: __self.id}).then(data => {
            __self.showModal = false
            console.log(data)
            if (data.status === 1) {
              if (data.result.status === 'pass') {
                router.push({name: 'examSuccess', query: {score: data.result.score, id: data.result.exam_id, historyId: data.result.history_id}})
              } else {
                router.push({name: 'examFailed', query: {score: data.result.score, id: data.result.exam_id, historyId: data.result.history_id}})
              }
            }
          }).catch(error => {
            console.log(error.message)
          })
        } else {
          console.log(err)
        }
      })
    },
    goSubmitModal: function () {
      this.popupType = 1
      this.showModal = true
    },
    choose: function (item) {
      // 单选
      if (this.currentQuestion.type_id === 1) {
        if (item.selected === false) {
          for (var j = 0; j < this.currentQuestion.answers.length; j++) {
            var current1 = this.currentQuestion.answers[j]
            current1.selected = false
          }
          item.selected = true
        } else {
          item.selected = false
        }
      } else if (this.currentQuestion.type_id === 2) { // 多选
        item.selected = !item.selected
      } else { // 是非
        if (item.selected === false) {
          for (var i = 0; i < this.currentQuestion.answers.length; i++) {
            var current3 = this.currentQuestion.answers[i]
            current3.selected = false
          }
          item.selected = true
        } else {
          item.selected = false
        }
      }

      this.currentQuestion.lastStatus = 'pending'
      for (var ij = 0; ij < this.currentQuestion.answers.length; ij++) {
        var currentAnswer = this.currentQuestion.answers[ij]
        if (currentAnswer.selected === true) {
          this.currentQuestion.lastStatus = 'finished'
          break
        }
      }
    }
  }
}
</script>
<style scoped="scope">
.el-pager li.active {
border-color: #20a0ff;
background-color: #20a0ff;
color: #fff;
cursor: default;
}
.el-pager li.c_g{
background: #00b553;
color: #fff;
}
.el-pager li.c_o{
background: #fc7a06;
color: #fff;
}
.el-pager li .c_grey{
background: #dedede;
color: #666;
}
.el-pagination {
padding: .02rem .05rem;
}
.el-pagination .btn-prev {
border-radius: 0.02rem 0 0 0.02rem;
}
.el-pagination button {
padding: 0 6px;
}
.el-pagination button, .el-pagination span {
font-size: .24rem;
min-width: 0.55rem;
height: 0.55rem;
line-height:0.55rem;

}
.el-pager li {
padding: 0 0.02rem;
font-size:0.24rem;
min-width: 0.55rem;
height: 0.55rem;
line-height: 0.55rem;
}
.el-pager li.btn-quicknext, .el-pager li.btn-quickprev {
line-height: 0.55rem;
}
.course_tit li{
  margin-right:0rem;
}
</style>

