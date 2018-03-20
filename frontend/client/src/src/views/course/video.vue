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
      <!-- <li class="con_list">
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
      </li> -->
    </ul>

  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import api from '../../services/api'
  import router from '../../router'
  export default {
    data () {
      return {
        link: '',
        courseContent: [],
        showVideo: false,
        type: '',
        id: 0
      }
    },
    components: {
      commonHeader
    },
    created () {
      this.link = this.$route.query.link
      this.type = this.$route.query.type.toLowerCase()
      this.showVideo = true
      this.id = this.$route.query.id
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
        } else {
          alert(data.result)
        }
      }).catch(error => {
        alert(error.message)
      })
    },
    methods: {
      start: function (contentId, contentLink, orignalPath, type) {
        sessionStorage.setItem('current_course_id', this.id)
        api.fetch(api.uri.startCourse, {
          courseid: this.id,
          contentid: contentId
        }).then(data => {
          if (data.status === 1) {
            if (type === 'mp4') {
              router.push({name: 'courseVideo', query: {link: orignalPath, type: type, id: this.id}})
            } else {
              router.push({name: 'courseMedia', query: {link: orignalPath, type: type, id: this.id}})
            }
          }
        })
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

