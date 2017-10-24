<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="">员工考试记录</span> > <span class="f_blue">考试详情</span></span>
        </nav>
        <div class="con_tab">
          <div class="detail_tit">
            <span>销售经理必须具备的技能考试</span>
            <span>2017-08-20 12:00:00     —   2017-08-20 13:00:00</span>
            <span>80分</span>
            <span>张三</span>
            <button class="inf_btn fr" v-on:click="routeByName('informationEdit')">返  回</button>
          </div>
          <ul>

            <li class="single_title">
              <li>1/15 (单选题)</li>
              <li class="tit_con">
                <p>公司企业文化的需求是什么 <span class="false_n"> 正确答案：AC</span></p>
                <ul>
                  <li>
                    <el-radio class="radio" v-model="radio" label="1">A.选项A在这里可以选择</el-radio>
                  </li>
                  <li>
                    <el-radio class="radio" v-model="radio" label="2">A.选项A在这里可以选择</el-radio>
                  </li>
                  <li>
                    <el-radio class="radio" v-model="radio" label="3">A.选项A在这里可以选择</el-radio>
                  </li>
                  <li>
                    <el-radio class="radio" v-model="radio" label="4">A.选项A在这里可以选择</el-radio>
                  </li>
                </ul>
              </li>
            </li>
            <li class="single_title">
            <li>1/15 (单选题)</li>
            <li class="tit_con">
              <p>公司企业文化的需求是什么</p>
              <ul>
                <li>
                  <el-radio class="radio" v-model="radio" label="1">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="2">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="3">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="4">A.选项A在这里可以选择</el-radio>
                </li>
              </ul>
            </li>
            </li>
            <li class="single_title">
            <li>1/15 (单选题)</li>
            <li class="tit_con">
              <p>公司企业文化的需求是什么</p>
              <ul>
                <li>
                  <el-radio class="radio" v-model="radio" label="1">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="2">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="3">A.选项A在这里可以选择</el-radio>
                </li>
                <li>
                  <el-radio class="radio" v-model="radio" label="4">A.选项A在这里可以选择</el-radio>
                </li>
              </ul>
            </li>
            </li>
          </ul>
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
  .el-radio__label{
    margin-left:15px;
  }
  .false_n{
    color: rgb(245, 90, 16);
  }

</style>
