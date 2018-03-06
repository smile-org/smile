<template>
  <div>
    <common-header></common-header>
    <iframe class="aaaa" v-show="showIframe" :src="link" style="width: 100%; height: 100vh; border: none;"></iframe>
    <img v-show="showImage" :src="link" width="100%" height="100%"/>
    <video :src="link" controls="controls" preload="true" type='video/mp4' webkit-playsinline="true">
      您的浏览器不支持 video 标签。
    </video>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  export default {
    data () {
      return {
        link: '',
        showIframe: false,
        showImage: false,
        showVideo: false,
        type: ''
      }
    },
    components: {
      commonHeader
    },
    created () {
      this.link = this.$route.query.link
      this.type = this.$route.query.type.toLowerCase()
      if (this.type === 'png' || this.type === 'jpg') {
        this.showImage = true
        // this.showIframe = false
      } else if (this.type === 'pdf' || this.type === 'doc' || this.type === 'docx' || this.type === 'ppt' || this.type === 'pptx') {
        // http://ow365.cn/?i=15062&del=1&n=3&furl=http://files.jxueyuan.com/company-34/course/abcd.pdf
        this.link = this.link.replace('http://dev-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
          .replace('http://prod-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
        this.link = 'http://ow365.cn/?i=15062&del=1&n=3&furl=' + this.link
        // comments
        this.showIframe = true
        // this.showImage = false
      } else if (this.type === 'mp4') {
        this.showVideo = true
      } else {
        this.showIframe = false
        this.showImage = false
        this.showVideo = false
      }
    },
    methods: {}
  }
</script>
<style scoped="scope">
  /*header {*/
    /*position: fixed !important;*/
    /*width: 100%!important;*/
  /*}*/
</style>
<style>
</style>

