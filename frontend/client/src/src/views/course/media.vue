<template>
  <div>
    <common-header></common-header>
    <iframe class="aaaa" v-show="showIframe" :src="link" style="width: 100%; height: 100vh; border: none;"></iframe>
    <img v-show="showImage" :src="link" width="100%" height="100%"/>
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
        this.showIframe = false
      } else if (this.type === 'pdf' || this.type === 'doc' || this.type === 'docx' || this.type === 'ppt' || this.type === 'pptx') {
        // http://ow365.cn/?i=15062&del=1&furl=http://files.jxueyuan.com/company-34/course/abcd.pdf
        this.link = this.link.replace('http://dev-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
          .replace('http://prod-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
        this.link = 'http://ow365.cn/?i=15062&del=1&furl=' + this.link
        // comments
        this.showIframe = true
        this.showImage = false
      } else {
        this.showIframe = false
        this.showImage = false
      }
    },
    methods: {}
  }
</script>
<style scoped="scope">
  */
</style>
<style>
</style>

