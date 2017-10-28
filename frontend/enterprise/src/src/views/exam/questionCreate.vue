<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">题库信息管理</span> > <span class="f_blue">添加试题</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :rules="formRules" :model="form" label-width="80px">
            <el-form-item label="课程类别">
              <el-select v-model="form.type" placeholder="请选择试题类型" @change="changeType">
                <el-option v-for="item in form.options" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="试题题目" prop="title" style="width: 500px;">
              <el-input v-model="form.title" placeholder="请输入试题题目"></el-input>
            </el-form-item>
          </el-form>
          <div>
            <p class="fl ml10">答案选项</p>
            <div class="exap_tit">
              <ul v-if="form.type===1">
                <li v-for="item in anwserList" :key="item.num">
                  <span class="cir_choose">{{item.option}}</span>
                  <el-input v-model="item.content" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="form.singleValue" :label="item.num">正确答案</el-radio>
                  <button class="el-icon-plus add_ti" v-show="item.num===anwserList.length" v-on:click="addAnwser"></button>
                </li>
              </ul>
              <ul v-if="form.type===2">
                <li v-for="item in anwserList" :key="item.num">
                  <span class="cir_choose">{{item.option}}</span>
                  <el-input v-model="item.content" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="form.multipleValue" :label="item.num">正确答案</el-checkbox>
                  <button class="el-icon-plus add_ti" v-show="item.num===anwserList.length" v-on:click="addAnwser"></button>
                </li>
              </ul>
              <ul v-if="form.type===3">
                <li v-for="item in anwserList" :key="item.num">
                  <span class="cir_choose">{{item.option}}</span>
                  <el-input v-model="item.content" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="form.tfValue" :label="item.num">正确答案</el-radio>
                </li>
              </ul>
              <button type="primary" class="inf_btn mt30 mb20" v-on:click="save">保 存
              </button>
              <button type="button" class="inf_btn mt30 mb20" v-on:click="resetAnwser">重 置
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
          title: '',
          // 单选： 1； 多选：2； 是非： 3
          type: 1,
          tfValue: 1,
          singleValue: 1,
          multipleValue: []
        },
        formRules: {
          title: [
            { required: true, message: '请输入试题题目', trigger: 'blur' }
          ]
        },
        // num, option, content, isRight
        anwserList: [],
        optionList: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.changeType()
    },
    methods: {
      addAnwser: function () {
        if (this.form.type === 1 || this.form.type === 2) {
          if (this.anwserList.length < 10) {
            var obj = {}
            obj.num = this.anwserList.length + 1
            obj.option = this.optionList[this.anwserList.length]
            obj.content = ''
            obj.isRight = false
            this.anwserList.push(obj)
          }
          return
        } else {
          return
        }
      },
      resetForm: function () {
        this.form.singleValue = 1
        this.form.tfValue = 1
        this.form.multipleValue = []
        this.form.title = ''
      },
      save: function () {
        // var _this = this
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.attachIsRight()
            var submitObj = {
              answerList: []
            }
            submitObj.typeid = this.form.type
            submitObj.title = this.form.title
            for (var i = 0; i < this.anwserList.length; i++) {
              submitObj.answerList.push({
                answer_num: this.anwserList[i].num,
                answer_option: this.anwserList[i].option,
                answer_content: this.anwserList[i].content,
                is_right: this.anwserList[i].isRight
              })
            }
            api.post(api.uri.addQuestion, submitObj).then(data => {
              if (data.status === 1) {
                this.$message('添加成功')
                this.changeType()
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
        if (this.form.type === 1) {
          target = _.find(this.anwserList, function (item) {
            return item.num === _this.form.singleValue
          })
          target.isRight = true
          console.log(this.anwserList)
        } else if (this.form.type === 2) {
          target = _.filter(this.anwserList, function (item) {
            return _this.form.multipleValue.indexOf(item.num) >= 0
          })
          for (var i = 0; i < target.length; i++) {
            target[i].isRight = true
          }
          console.log(target)
          console.log(this.anwserList)
        } else {
          this.anwserList[this.form.tfValue - 1].isRight = true
        }
      },
      changeType: function () {
        this.resetForm()
        this.resetAnwser()
      },
      resetAnwser: function () {
        this.form.singleValue = 1
        this.form.tfValue = 1
        this.form.multipleValue = []
        if (this.form.type === 1) {
          this.anwserList = [{
            num: 1,
            content: '',
            option: 'A',
            isRight: false
          }, {
            num: 2,
            content: '',
            option: 'B',
            isRight: false
          }, {
            num: 3,
            content: '',
            option: 'C',
            isRight: false
          }, {
            num: 4,
            content: '',
            option: 'D',
            isRight: false
          }]
        } else if (this.form.type === 2) {
          this.anwserList = [{
            num: 1,
            content: '',
            option: 'A',
            isRight: false
          }, {
            num: 2,
            content: '',
            option: 'B',
            isRight: false
          }, {
            num: 3,
            content: '',
            option: 'C',
            isRight: false
          }, {
            num: 4,
            content: '',
            option: 'D',
            isRight: false
          }, {
            num: 5,
            content: '',
            option: 'E',
            isRight: false
          }]
        } else {
          this.anwserList = [{
            num: 1,
            content: '对',
            option: 'A',
            isRight: false
          }, {
            num: 2,
            content: '错',
            option: 'B',
            isRight: false
          }]
        }
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
