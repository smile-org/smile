<template>
  <div>
    <common-header></common-header>
    <!--<iframe class="aaaa" v-show="showIframe" :src="link" style="width: 100%; height: 100vh; border: none;"></iframe>-->
    <!--<img v-show="showImage" :src="link" width="100%" height="100%"/>-->
    <div style="height: 4.5rem; overflow: hidden;background: #dcdcdc;">
      <video :src="link" id="my-video" controls="controls" preload="true" type='video/mp4' webkit-playsinline="true"
             style="width: 100%; height: 100%;">
        您的浏览器不支持 video 标签。
      </video>
    </div>
    <ul style="overflow: hidden" class="list_border course_con a_v  mt3">
      <ul style="overflow: hidden" class="list_border course_con a_v">
        <li class="con_list" v-for="item in courseContent" :key="item.content_id">
                <span v-on:click='start(item.content_id, item.content_link, item.orignal_path, item.content_type)'>
                  <span v-bind:class="item.typeImageClass" class="media_img media_img1"></span>
                  <span class="c_list_font">{{item.sequnce_title}} {{item.content}}</span>
                </span>
        </li>
      </ul>
      <li class="con_list">
          <span>
            <span class="media_img media_img1"></span>
            <span class="c_list_font">diyijie sdss</span>
          </span>
      </li>
      <li class="con_list">
          <span>
            <span class="media_img media_img1"></span>
            <span class="c_list_font">diyijie sdss</span>
          </span>
      </li>
    </ul>

  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import api from '../../services/api'
  export default {
    data () {
      return {
        link: '',
//        showIframe: false,
//        showImage: false,
        courseContent: [],
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
      this.showVideo = true
//      if (this.type === 'png' || this.type === 'jpg') {
//        this.showImage = true
//        // this.showIframe = false
//      } else if (this.type === 'pdf' || this.type === 'doc' || this.type === 'docx' || this.type === 'ppt' || this.type === 'pptx') {
//        // http://ow365.cn/?i=15062&del=1&n=3&furl=http://files.jxueyuan.com/company-34/course/abcd.pdf
//        this.link = this.link.replace('http://dev-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
//          .replace('http://prod-env.oss-cn-beijing.aliyuncs.com/', 'http://files.jxueyuan.com/')
//        this.link = 'http://ow365.cn/?i=15062&del=1&n=3&furl=' + this.link
//        // comments
//        this.showIframe = true
//        // this.showImage = false
//      } else if (this.type === 'mp4') {
//        this.showVideo = true
//      } else {
//        this.showIframe = false
//        this.showImage = false
//        this.showVideo = false
//      }
    },
    methods: {
      handleClick: function (tab, event) {
        if (tab.name === 'second') {
          api.fetch(api.uri.getCourseContent, {courseid: this.id}).then(data => {
            if (data.status === 1) {
              for (var i = 0; i < data.result.length; i++) {
                var item = data.result[i]
                if (item.content_type.toUpperCase() === 'DOC' || item.content_type.toUpperCase() === 'DOCX') {
                  item.typeImageClass = 'media_img media_img3'
                } else if (item.content_type.toUpperCase() === 'PPTX' || item.content_type.toUpperCase() === 'PPT') {
                  item.typeImageClass = 'media_img media_img2'
                } else if (item.content_type.toUpperCase() === 'MP4' || item.content_type.toUpperCase() === 'MOV') {
                  item.typeImageClass = 'media_img media_img1'
                } else if (item.content_type.toUpperCase() === 'JPG' || item.content_type.toUpperCase() === 'JPEG' || item.content_type.toUpperCase() === 'PNG') {
                  item.typeImageClass = 'media_img media_img4'
                } else if (item.content_type.toUpperCase() === 'PDF') {
                  item.typeImageClass = 'media_img media_img5'
                }
              }
              this.courseContent = data.result
//              this.handleStar(this.courseContent)
            } else {
              alert(data.result)
            }
          }).catch(error => {
            alert(error.message)
          })
        } else if (tab.name === 'third') {
          api.fetch(api.uri.getCourseComment, {courseid: this.id}).then(data => {
            if (data.status === 1) {
              this.courseComment = data.result
            } else {
              alert(data.result)
            }
          }).catch(error => {
            alert(error.message)
          })
        }
      }
    }
  }
</script>
<style>
  #my-video {
    object-fit: cover;
    object-position: center center;
  }
</style>

