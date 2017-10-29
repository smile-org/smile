<template>
    <div>
        <common-header></common-header>
        <div class="con_main">
            <navigator module="information"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
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
                                <img :src="logoSrc | formatImage" width="20%"/>
                            </td>
                            <td class="page_m_c">
                                <a v-on:click="setDefault('logo')">使用默认</a>
                                <a v-on:click="toggleShow('logo')">上传</a>
                            </td>
                        </tr>
                        <tr>
                            <td class="page_m_a">
                                banner

                            </td>
                            <td class="page_m_b">
                                <img :src="bannerSrc | formatImage" width="100%"/>
                            </td>
                            <td class="page_m_c">
                                <a v-on:click="setDefault('banner')">使用默认</a>
                                <a v-on:click="toggleShow('banner')">上传</a>
                            </td>
                        </tr>
                    </table>
                    <div class="tc">
                        <button class="inf_btn btn_margin" v-on:click="dialogFormVisible = true">预  览</button>
                        <el-dialog title="预览" :visible.sync="dialogFormVisible">
                            <!--&lt;!&ndash;此部分为上传页面&ndash;&gt;-->
                            <div class="m_show">
                                <div class="m_show_header">
                                    <img :src="logoSrc | formatImage"/>
                                </div>
                                <!--<img src="img/p_header.jpg"/>-->
                                <img class="m_show_banner" :src="bannerSrc | formatImage" width="100%"/>
                            </div>
                            <div class="tc btn_margin">
                                <el-button type="success" class="inf_btn  ml20" @click="dialogFormVisible = false">关 闭
                                </el-button>
                            </div>
                        </el-dialog>
                    </div>
                </div>
            </section>
        </div>
        <my-upload @input="closeMyUpload" field="file"
                   @crop-success="cropSuccess"
                   @crop-upload-success="cropUploadSuccess"
                   @crop-upload-fail="cropUploadFail"
                   :width="300"
                   :height="300"
                   :params="params"
                   :headers="headers"
                   :value.sync="show"
                   :no-circle=true
                   :url="uploadUrl"
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
    filters: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri + '?t=' + Math.random()
      }
    },
    data: function () {
      return {
        logoSrc: '',
        bannerSrc: '',

        show: false,
        params: {
          pictype: ''
        },
        headers: {},
        uploadUrl: api.uri.uploadCompanyPic,
        dialogFormVisible: false
      }
    },
    components: {
      navigator,
      commonHeader,
      myUpload
    },
    created () {
      this.headers = api.getUploadHeaders()
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.logoSrc = data.result.logo
          this.bannerSrc = data.result.banner
        }
      })
    },
    methods: {
      setDefault: function (type) {
        api.fetch(api.uri.setDefaultImage, {
          pictype: type
        }).then(data => {
          if (data.status === 1) {
            if (type === 'logo') {
              this.logoSrc = data.result
            } else {
              this.bannerSrc = data.result
            }
          }
        })
      },
      closeMyUpload: function (value) {
        this.show = value
      },
      // 1: 上传logo; 2: 上传banner
      toggleShow: function (type) {
        this.params.pictype = type
        this.show = !this.show
      },
      cropSuccess (data, field) {
        // if (this.params.pictype === 'logo') {
        //   this.iconSrc = data
        // } else {
        //   this.bannerSrc = data
        // }
      },
      /**
       * upload success
       *
       * [param] jsonData   服务器返回数据，已进行json转码
       * [param] field
       */
      cropUploadSuccess (jsonData, field) {
        if (this.params.pictype === 'logo') {
          this.logoSrc = jsonData.result
        } else {
          this.bannerSrc = jsonData.result
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
      }
    }
  }
</script>

<style>

</style>
