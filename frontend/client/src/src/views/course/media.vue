<template>
  <div>
    <common-header></common-header>
    <iframe v-show="showIframe" :src="link" width="100%" height="100%"></iframe>
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
      this.showIframe = true
      this.showImage = false
    } else {
      this.showIframe = false
      this.showImage = false
    }
  },
  methods: {

  }
}
</script>
<style >

</style>

