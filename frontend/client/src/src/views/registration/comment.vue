<template>
  <div>
    <!--<header>-->
      <!--<div class="logo_c">-->
        <!--<a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>-->
        <!--<a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>-->
        <!--<a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>-->
      <!--</div>-->
    <!--</header>-->
    <common-header></common-header>
    <section>
      <div class="appraise_w">
        <div class="hidden">
          <h3 class="fl">{{title}}</h3>
          <el-rate class="fr" v-model="star" :colors="['#99A9BF', '#F7BA2A', '#FF9900']"></el-rate>
        </div>

        <ul class="a_case">
          <li v-on:click="quickComments('干货满满')">
            干货满满
          </li >
          <li v-on:click="quickComments('课程生动')">
            课程生动
          </li>
          <li v-on:click="quickComments('受益匪浅')">
            受益匪浅
          </li>
        </ul>
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入内容" v-model="comments">
        </el-input>
        <p class="form_warning" v-show="showError">
          {{errorMessage}}
        </p>
        <button class="login_btn" type="button" v-on:click="comment">提交</button>
      </div>
    </section>
  </div>
</template>

<script>
import { required, between } from 'vuelidate/lib/validators'
import api from '../../services/api'
import router from '../../router'
import commonHeader from '../../components/CommonHeader'
export default {
  data: function () {
    return {
      title: '',
      comments: '',
      star: 0,
      showError: false,
      errorMessage: true,
      id: 0
    }
  },
  components: {
    commonHeader
  },
  created () {
    this.id = this.$route.query.id
    this.pid = this.$route.query.pid
    this.title = sessionStorage.getItem('enrollCourseTitle')
  },
  validations: {
    comments: {
      required
    },
    star: {
      between: between(1, 5)
    }
  },
  methods: {
    quickComments: function (comments) {
      this.comments += (' ' + comments)
    },
    comment: function () {
      if (this.$v.$invalid) {
        this.showError = true
        this.errorMessage = '请评分并评论后再提交'
        return
      }
      api.post(api.uri.addEnrollmentComment, {enrollmentid: parseInt(this.id), star: this.star, comment: this.comments}).then(data => {
        if (data.status === 1) {
          // TODO: element ui 弹出框后再跳转
          router.push({name: 'getEnroll', query: { id: this.pid }})
        } else {
          this.showError = true
          this.errorMessage = data.result
        }
      }).catch(error => {
        this.showError = true
        this.errorMessage = error.message
      })
    }

  }
}
</script>

<style scoped="scope">
  .el-rate {
    height: .4rem;
    line-height: 0.4rem;
  }
  .el-textarea textarea{
    min-height:4rem;
    outline: 0;
    -webkit-appearance: none;
    appearance: none;
  }
  .el-rate__icon {
    font-size: .18rem;
    margin-right:.08rem;
  }
</style>
