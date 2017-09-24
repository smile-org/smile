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
          <h3 class="fl">{{courseTitle}}</h3>
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
        <button class="login_btn" type="button" :disabled="$v.comments.$invalid || $v.star.$invalid" v-on:click='submit'>提交</button>
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
  data () {
    return {
      courseTitle: '',
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
    this.courseTitle = sessionStorage.getItem('courseTitle')
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
      this.comments += comments
    },
    submit: function () {
      if (this.$v.$invalid) {
        this.showError = true
        this.errorMessage = '请评分并评论后再提交'
        return
      }
      api.post(api.uri.submitCourseComments, {courseid: this.id, star: this.star, comment: this.comments}).then(data => {
        if (data.status === 1) {
          // TODO: element ui 弹出框后再跳转
          router.push({name: 'courseDetails', query: { id: this.id }})
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

