<template>
  <div>
    <!--<common-header></common-header>-->
    <!--<section>-->
      <!--<div v-if="type===0">-->
      <video-bg :sources="source">
      </video-bg>
      <!--<iframe frameborder="0"  width="100%" height="100%" :src="url">
        </iframe>-->

    <!--</section>-->
    <!--<a href="http://192.168.1.103:3000/a.mp4"> aaaaa </a>-->
  </div>
</template>

<script>
import commonHeader from '../../components/CommonHeader'
import VideoBg from 'vue-videobg'
export default {
  data () {
    return {
      type: 0,
      source: ['http://192.168.1.103:3000/a.mp4'],
      url: 'http://123.207.154.226:4001/company-1/course/html5/88-airflow%E5%AE%89%E8%A3%85%E6%96%87%E6%A1%A3-20170912.html'
    }
  },
  components: {
    commonHeader,
    VideoBg
  },
  created () {

  },
  methods: {

  }
}
</script>
<style >

</style>

