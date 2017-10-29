<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="exam"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">员工考试记录</span> > <span
                            class="f_blue">考试详情</span></span>
                </nav>
                <div class="con_tab">
                    <div class="detail_tit">
                        <span>{{examTitle}}</span>
                        <span>{{startDate}} — {{endDate}}</span>
                        <span>{{score}}分</span>
                        <span>{{fullname}}</span>
                        <button class="inf_btn fr" v-on:click="back()">返  回</button>
                    </div>
                    <ul>
                        <li class="single_title" v-for="item in tableData" :key="item.question_id">
                            <ul>
                                <li>{{item.question_num}}/{{total}}({{item.question_type}})</li>
                                <li class="tit_con" v-if="item.type_id === 2">
                                    <p>{{item.title}}
                                        <span class="false_n ml20" v-if="item.is_right === true">
                                            <i class="el-icon-check" />
                                            ( 正确答案：{{item.right_answers}} )
                                        </span>
                                        <span class="ml20" v-else>
                                            <i class="el-icon-close" />
                                            ( 正确答案：{{item.right_answers}} )
                                        </span>
                                    </p>
                                    <ul>
                                        <el-checkbox-group v-model="item.selected_answers">
                                            <li v-for="answer in item.answers">
                                                <el-checkbox class="radio" v-model="item.selected_answers" :label="answer.answer_id">
                                                    {{answer.answer_option}}. {{answer.answer_content}} {{item.selected_answer}}
                                                </el-checkbox>
                                            </li>
                                        </el-checkbox-group>
                                    </ul>
                                </li>
                                <li class="tit_con" v-else>
                                    <p>{{item.title}}
                                        <span class="false_n ml20" v-if="item.is_right === true">
                                            <i class="el-icon-check" />
                                            ( 正确答案：{{item.right_answers}} )
                                        </span>
                                        <span class="ml20" v-else>
                                            <i class="el-icon-close" />
                                            ( 正确答案：{{item.right_answers}} )
                                        </span>
                                    </p>
                                    <ul>
                                        <el-radio-group v-model="item.selected_answer">
                                            <li v-for="answer in item.answers">
                                                <el-radio class="radio" v-model="item.selected_answer" :label="answer.answer_id">
                                                    {{answer.answer_option}}. {{answer.answer_content}} {{item.selected_answer}}
                                                </el-radio>
                                            </li>
                                        </el-radio-group>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <div class="tc mt20">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                                       :page-size="take"
                                       layout="total, prev, pager, next" :total="total"></el-pagination>
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import api from '../../services/api'
  import moment from 'moment'
  import router from '../../router'
  export default {
    data: function () {
      return {
        historyId: '',
        examTitle: '',
        startDate: '',
        endDate: '',
        score: '',
        fullname: '',
        tableData: [],
        total: 0,
        take: 5,
        currentPage: 1
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.historyId = parseInt(this.$route.query.history_id)
      api.fetch(api.uri.getExamHistoryDetail, {historyid: this.historyId}).then(data => {
        if (data.status === 1) {
          this.examTitle = data.result.exam_title
          this.startDate = moment(new Date(data.result.start_date)).format('YYYY-MM-DD HH:mm:ss')
          this.endDate = moment(new Date(data.result.end_date)).format('YYYY-MM-DD HH:mm:ss')
          this.score = data.result.score
          this.fullname = data.result.full_name
        }
      })
      this.getQuestionList()
    },
    methods: {
      getQuestionList: function () {
        this.tableData = []
        api.fetch(api.uri.getExamDetailQuestionList, {
          historyid: this.historyId,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            for (var i in data.result) {
              var questionType = ''
              if (data.result[i].type_id === 1) {
                questionType = '单选题'
              } else if (data.result[i].type_id === 2) {
                questionType = '多选题'
              } else {
                questionType = '是非题'
              }
              var answers = []
              var rightAnswers = ''
              var selectedAnswer = ''
              var selectedAnswers = []
              for (var j in data.result[i].answers) {
                var answer = {
                  answer_id: data.result[i].answers[j].answer_id,
                  answer_num: data.result[i].answers[j].answer_num,
                  answer_option: data.result[i].answers[j].answer_option,
                  answer_content: data.result[i].answers[j].answer_content
                }
                answers.push(answer)
                if (data.result[i].answers[j].is_right === true) {
                  rightAnswers += data.result[i].answers[j].answer_option
                }
                if (data.result[i].type_id === 2) {
                  if (data.result[i].answers[j].selected === true) {
                    console.log(data.result[i].answers[j].answer_id)
                    selectedAnswers.push(data.result[i].answers[j].answer_id)
                  }
                } else {
                  if (data.result[i].answers[j].selected === true) {
                    selectedAnswer = data.result[i].answers[j].answer_id
                  }
                }
              }
              var item = {
                question_id: data.result[i].question_id,
                question_num: data.result[i].question_num,
                title: data.result[i].title,
                type_id: data.result[i].type_id,
                question_type: questionType,
                is_right: data.result[i].is_Right,
                right_answers: rightAnswers,
                selected_answer: selectedAnswer,
                selected_answers: selectedAnswers,
                answers: answers
              }
              this.tableData.push(item)
            }
            this.total = data.total
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.getQuestionList()
      },
      back: function () {
        router.push({name: 'examUserList'})
      }
    }
  }
</script>

<style scoped="scope">
    .detail_tit {
        font-size: 14px;
    }

    .detail_tit span {
        margin-top: 5px;
        margin-right: 20px;
    }

    .single_title {
        margin-top: 20px;
    }

    .tit_con {
        margin-left: 20px;

    }

    .tit_con p {
        margin-top: 15px;
        margin-bottom: 10px;
        font-szie: 16px;
        /*font-weight: bold;*/
    }

    .tit_con li {
        margin-bottom: 10px;
    }

    .tit_con .el-radio__original {
        border-color: #01b554;
        background: #01b554 !important;
        margin-right: 10px;
    }

    .el-radio__label {
        margin-left: 15px;
    }

    .false_n {
        color: rgb(245, 90, 16);
    }

</style>
