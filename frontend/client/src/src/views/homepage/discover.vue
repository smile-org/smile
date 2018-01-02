<template>
  <div>
    <common-header></common-header>
    <section>
      <ul class="new_box">
        <li v-on:click="getCourse()">
          <img class="myimg" src="../../assets/img/img_t1.png" />
          <span>我要学课</span>
        </li>
        <li v-on:click="getExam()">
          <img class="myimg" src="../../assets/img/img_t3.png" />
          <span>我要考试</span>
        </li>
        <li v-on:click="getRegistration()">
          <img class="myimg" src="../../assets/img/img_t2.png" />
          <span>我要报名</span>
        </li>
      </ul>
    </section>
    <v-footer currentTag="2"></v-footer>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import VFooter from '../../components/footer'
  import router from '../../router'
  export default {
    data: function () {
      return {
        currentPage: -1
      }
    },
    components: {
      commonHeader,
      VFooter
    },
    methods: {
      getCourse: function () {
        router.push({
          name: 'courseCategories'
        })
      },
      getExam: function () {
        router.push({
          name: 'getExamList'
        })
      },
      getRegistration: function () {
        router.push({
          name: 'getEnrollList'
        })
      }
    },
    created () {
    }
  }
</script>

