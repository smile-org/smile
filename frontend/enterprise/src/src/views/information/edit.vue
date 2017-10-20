<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
     <navigator module="information"></navigator>
      <section class="con_main_r">
        <nav>
          <img :src="logoSrc" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>
        </nav>
        <div class="con_tab">
          <!--&lt;!&ndash;此部分为上传页面&ndash;&gt;-->
          <table class="page_m" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                logo
              </td>
              <td class="page_m_b">
                <img :src="bannerSrc | formatImage" width="20%"/>
              </td>
              <td class="page_m_c">
                <!--<a v-on:click="setDefault(1)">使用默认</a>-->
                <a v-on:click="toggleShow(1)">上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                banner
              </td>
              <td class="page_m_b">
                <img v-if="bannerSrc" :src="bannerSrc" v-show="true" style="display: none;">
                <img :src="bannerSrc | formatImage" width="100%"/>
              </td>
              <td class="page_m_c">
                <!--<a v-on:click="setDefault(2)">使用默认</a>-->
                <a v-on:click="toggleShow(2)">上传</a>
              </td>
            </tr>
          </table>
          <div class="tc">
            <button class="inf_btn btn_margin" v-on:click="routeByName('informationShow')">预  览</button>
          </div>
        </div>
      </section>
    </div>
    <my-upload @input="closeMyUpload" field="img"
    @crop-success="cropSuccess"
    @crop-upload-success="cropUploadSuccess"
    @crop-upload-fail="cropUploadFail"
    :width="300"
    :height="300"
    :params="params"
    :headers="headers"
    :value.sync="show"
    :no-circle=true
    img-format="png"></my-upload>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import myUpload from 'vue-image-crop-upload'
  import api from '../../services/api'
  import axios from 'axios'

  export default {
    filter: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri
      }
    },
    data: function () {
      return {
        logoSrc: '',
        bannerSrc: '',

        show: false,
        params: {
          type: 1
        }
      }
    },
    components: {
      navigator,
      commonHeader,
      myUpload
    },
    created () {
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.logoSrc = data.logo
          this.bannerSrc = data.banner
        }
      })
    },
    methods: {
      closeMyUpload: function (value) {
        this.show = value
      },
      // 1: 上传logo; 2: 上传banner
      toggleShow: function (type) {
        this.params.type = type
        this.show = !this.show
      },
      cropSuccess (data, field) {
        console.log(data)
        this.bannerSrc = data
        // console.log('-------- crop success --------')
        // this.imgDataUrl = imgDataUrl
        // this.logoSrc = imgDataUrl
      },
      /**
      * upload success
      *
      * [param] jsonData   服务器返回数据，已进行json转码
      * [param] field
      */
      cropUploadSuccess (jsonData, field) {
        if (this.params.type === 1) {
          this.logoSrc = jsonData.imagePath
        } else {
          this.bannerSrc = jsonData.imagePath
        }
      },
      /**
      * upload fail
      *
      * [param] status    server api return error status, like 500
      * [param] field
      */
      cropUploadFail (status, field) {
        alert(status)
        // console.log('-------- upload fail --------')
        // console.log(status)
        // console.log('field: ' + field)
      }
    }
  }
</script>

<style>

</style>
