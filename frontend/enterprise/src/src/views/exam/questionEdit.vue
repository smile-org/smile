<template>
  <div>
    <common-header type="exam"></common-header>
    <div class="con_main">
      <navigator module="exam" menu="question"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">题库信息管理</span> > <span class="f_blue">添加试题</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :rules="formRules" :model="currentQuestion.QuestionToBeEidt" label-width="80px">
            <el-form-item label="课程类别">
              <el-select disabled v-model="currentQuestion.QuestionToBeEidt.type_id" placeholder="请选择试题类型">
                <el-option v-for="item in form.options" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="试题题目" prop="title" style="width: 500px;">
              <el-input v-model="currentQuestion.QuestionToBeEidt.title" placeholder="请输入试题题目"></el-input>
            </el-form-item>
          </el-form>
          <div>
            <p class="fl ml10">答案选项</p>
            <div class="exap_tit">
              <ul v-if="currentQuestion.QuestionToBeEidt.type_id===1">
                <li v-for="item in anwserList" :key="item.answer_num">
                  <span class="cir_choose">{{item.answer_option}}</span>
                  <el-input v-model="item.answer_content" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="form.singleValue" :label="item.answer_num">正确答案</el-radio>
                  <button class="el-icon-plus add_ti" v-show="item.answer_num===anwserList.length" v-on:click="addAnwser"></button>
                </li>
              </ul>
              <ul v-if="currentQuestion.QuestionToBeEidt.type_id===2">
                <li v-for="item in anwserList" :key="item.answer_num">
                  <span class="cir_choose">{{item.answer_option}}</span>
                  <el-input v-model="item.answer_content" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="form.multipleValue" :label="item.answer_num">正确答案</el-checkbox>
                  <button class="el-icon-plus add_ti" v-show="item.answer_num===anwserList.length" v-on:click="addAnwser"></button>
                </li>
              </ul>
              <ul v-if="currentQuestion.QuestionToBeEidt.type_id===3">
                <li v-for="item in anwserList" :key="item.answer_num">
                  <span class="cir_choose">{{item.answer_option}}</span>
                  <el-input v-model="item.answer_content" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="form.tfValue" :label="item.answer_num">正确答案</el-radio>
                </li>
              </ul>
              <button type="primary" class="inf_btn mt30 mb20" v-on:click="save">保 存
              </button>
              <button type="button" class="inf_btn mt30 mb20 ml20" v-on:click="resetAnwser">重 置
              </button>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import _ from 'lodash'
  import api from '../../services/api'
  import router from '../../router'
  export default {
    data: function () {
      return {
        form: {
          options: [{
            value: 1,
            label: '单选'
          }, {
            value: 2,
            label: '多选'
          }, {
            value: 3,
            label: '是非'
          }],
          tfValue: 1,
          singleValue: 1,
          multipleValue: []
        },
        formRules: {
          title: [
            { required: true, message: '请输入试题题目', trigger: 'blur' }
          ]
        },
        anwserList: [],
        optionList: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'],
        id: 0,
        currentQuestion: {}
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.id = parseInt(this.$route.query.id)
      this.getQuestion()
    },
    methods: {
      getQuestion: function () {
        api.fetch(api.uri.getQuestion, {questionid: this.id}).then(data => {
          if (data.status === 1) {
            this.currentQuestion = data.result
            this.anwserList = this.currentQuestion.AnswerList
            var target
            if (this.currentQuestion.QuestionToBeEidt.type_id === 1) {
              target = _.find(this.anwserList, function (item) {
                return item.is_right === true
              })
              this.form.singleValue = target.answer_num
            } else if (this.currentQuestion.QuestionToBeEidt.type_id === 2) {
              target = _.filter(this.anwserList, function (item) {
                return item.is_right === true
              })
              for (var i = 0; i < target.length; i++) {
                this.form.multipleValue.push(target[i].answer_num)
              }
            } else {
              target = _.find(this.anwserList, function (item) {
                return item.is_right === true
              })
              this.form.tfValue = target.answer_num
            }
          }
        })
      },
      addAnwser: function () {
        if (this.currentQuestion.QuestionToBeEidt.type_id === 1 || this.currentQuestion.QuestionToBeEidt.type_id === 2) {
          if (this.anwserList.length < 10) {
            var obj = {}
            obj.answer_num = this.anwserList.length + 1
            obj.answer_option = this.optionList[this.anwserList.length]
            obj.answer_content = ''
            obj.is_right = false
            obj.answer_id = 0
            this.anwserList.push(obj)
          }
          return
        } else {
          return
        }
      },
      save: function () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.attachIsRight()
            console.log(this.anwserList)
            var submitObj = {
              questionid: this.id,
              answerList: []
            }
            submitObj.typeid = this.currentQuestion.QuestionToBeEidt.type_id
            submitObj.title = this.currentQuestion.QuestionToBeEidt.title
            for (var i = 0; i < this.anwserList.length; i++) {
              submitObj.answerList.push({
                answer_id: this.anwserList[i].answer_id,
                answer_num: this.anwserList[i].answer_num,
                answer_option: this.anwserList[i].answer_option,
                answer_content: this.anwserList[i].answer_content,
                is_right: this.anwserList[i].is_right
              })
            }
            api.post(api.uri.editQuestion, submitObj).then(data => {
              if (data.status === 1) {
                router.push({name: 'examQuestionList'})
              }
            }).catch(error => {
              this.$message(error.message)
            })
          } else {
            return false
          }
        })
      },
      attachIsRight: function () {
        var _this = this
        var target
        for (var ij = 0; ij < this.anwserList.length; ij++) {
          this.anwserList[ij].is_right = false
        }
        if (this.currentQuestion.QuestionToBeEidt.type_id === 1) {
          target = _.find(this.anwserList, function (item) {
            return item.answer_num === _this.form.singleValue
          })
          target.is_right = true
        } else if (this.currentQuestion.QuestionToBeEidt.type_id === 2) {
          target = _.filter(this.anwserList, function (item) {
            return _this.form.multipleValue.indexOf(item.answer_num) >= 0
          })
          for (var i = 0; i < target.length; i++) {
            target[i].is_right = true
          }
        } else {
          this.anwserList[this.form.tfValue - 1].is_right = true
        }
      },
      // changeType: function () {
      //   this.resetForm()
      //   // this.resetAnwser()
      // },
      resetAnwser: function () {
        this.getQuestion()
        // this.form.singleValue = 1
        // this.form.tfValue = 1
        // this.form.multipleValue = []
        // if (this.form.type === 1) {
        //   this.anwserList = [{
        //     answer_num: 1,
        //     answer_content: '',
        //     answer_option: 'A',
        //     is_right: false
        //   }, {
        //     answer_num: 2,
        //     answer_content: '',
        //     answer_option: 'B',
        //     is_right: false
        //   }, {
        //     answer_num: 3,
        //     answer_content: '',
        //     answer_option: 'C',
        //     is_right: false
        //   }, {
        //     answer_num: 4,
        //     answer_content: '',
        //     answer_option: 'D',
        //     is_right: false
        //   }]
        // } else if (this.form.type === 2) {
        //   this.anwserList = [{
        //     answer_num: 1,
        //     answer_content: '',
        //     answer_option: 'A',
        //     is_right: false
        //   }, {
        //     answer_num: 2,
        //     answer_content: '',
        //     answer_option: 'B',
        //     is_right: false
        //   }, {
        //     answer_num: 3,
        //     answer_content: '',
        //     answer_option: 'C',
        //     is_right: false
        //   }, {
        //     answer_num: 4,
        //     answer_content: '',
        //     option: 'D',
        //     is_right: false
        //   }, {
        //     answer_num: 5,
        //     answer_content: '',
        //     answer_option: 'E',
        //     is_right: false
        //   }]
        // } else {
        //   this.anwserList = [{
        //     answer_num: 1,
        //     answer_content: '对',
        //     answer_option: 'A',
        //     is_right: false
        //   }, {
        //     answer_num: 2,
        //     answer_content: '错',
        //     answer_option: 'B',
        //     is_right: false
        //   }]
        // }
      }
    }
  }
</script>

<style scoped="scope">
  .exap_tit {
    margin-left: 80px;
  }

  .exap_tit ul li {
    margin-bottom: 20px;
  }

  .cir_choose {
    width: 13px;
    height: 13px;
    display: inline-block;
    line-height: 12px;
    text-align: center;
    padding: 5px;
    border: 1px solid #bfcbd9;
    vertical-align: middle;
    margin-right: 10px;

    border-radius: 50%;
    color: #bfcbd9;
  }

  .input_con {
    width: 400px;
  }

  .add_ti {
    border: 1px solid #bfcbd9;
    padding: 5px;
    border-radius: 50%;
    font-size: 13px;
    color: #bfcbd9;
    background: none;
    margin-left:10px;
    cursor: pointer;
  }

  .add_ti:hover, .add_ti:active, .add_ti:focus {
    border: 1px solid #01b554;
    color: #fff;
    background: #01b554;
    outline: none;
  }
</style>
