<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">题库信息管理</span> > <span class="f_blue">编辑试题</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="课程类别">
              <el-select v-model="formInline.options" placeholder="请选择课程类别">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="试题题目" style="width: 500px;">
              <el-input v-model="formInline.name"></el-input>
            </el-form-item>
          </el-form>
          <div>
            <p class="fl ml10">答案选项</p>
            <div class="exap_tit">
              <ul>
                <li>
                  <span class="cir_choose">A</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="1">正确答案</el-radio>
                </li>
                <li>
                  <span class="cir_choose">B</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="2">正确答案</el-radio>
                </li>
                <li>
                  <span class="cir_choose">C</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="3">正确答案</el-radio>
                </li>
                <li>
                  <span class="cir_choose">D</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="4">正确答案</el-radio>
                  <button class="el-icon-plus add_ti"></button>
                </li>
              </ul>
              <ul>
                <li>
                  <span class="cir_choose">A</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="checked">备选项</el-checkbox>
                </li>
                <li>
                  <span class="cir_choose">B</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="checked">备选项</el-checkbox>
                </li>
                <li>
                  <span class="cir_choose">C</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="checked">备选项</el-checkbox>
                </li>
                <li>
                  <span class="cir_choose">D</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-checkbox class="ml10" v-model="checked">备选项</el-checkbox>
                </li>
              </ul>
              <ul>
                <li>
                  <span class="cir_choose">A</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="1">正确答案</el-radio>
                </li>
                <li>
                  <span class="cir_choose">B</span>
                  <el-input v-model="input" placeholder="请输入内容" class="input_con"></el-input>
                  <el-radio class="radio ml10" v-model="radio" label="2">正确答案</el-radio>
                </li>

              </ul>
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
  import api from '../../services/api'
  export default {
    data: function () {
      return {
        radio: '1',
        company: {},
        formInline: {
          name: '',
          region: ''
        },
        options: [{
          value: '选项1',
          label: '单选'
        }, {
          value: '选项2',
          label: '多选'
        }, {
          value: '选项3',
          label: '是非'
        }],
        value: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.company = data
        }
      })
    },
    methods: {}
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
