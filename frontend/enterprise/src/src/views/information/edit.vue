<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator module="information" menu="edit"></navigator>
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
                个人端logo
              </td>
              <td class="page_m_b">
                <img :src="logoSrc" width="20%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault('logo')">使用默认</a>
                <a v-on:click="toggleShow('logo')">上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                个人端主图

              </td>
              <td class="page_m_b">
                <img :src="bannerSrc" width="100%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault('banner')">使用默认</a>
                <a v-on:click="toggleShow('banner')">上传</a>
              </td>
            </tr>
          </table>
          <div class="tc">
            <button class="inf_btn btn_margin" v-on:click="dialogFormVisible = true">预  览</button>
            <el-dialog title="预览" :visible.sync="dialogFormVisible" class="auto_width">
              <!--&lt;!&ndash;此部分为上传页面&ndash;&gt;-->
              <div class="m_show">
                <div class="m_show_header">
                  <img :src="logoSrc"/>
                </div>
                <!--<img src="img/p_header.jpg"/>-->
                <img class="m_show_banner" :src="bannerSrc" width="100%"/>
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
    <my-upload
      @input="closeIcon"
      field="file"
      :params="logoFormData"
      @crop-success="cropIconSuccess"
      @crop-upload-success="cropIconUploadSuccess"
      @crop-upload-fail="cropIconUploadFail"
      :url="uploadUrl"
      :width="95"
      :headers="headers"
      :height="72"
      :value.sync="showIcon"
      :no-circle=true
      img-format="png">
    </my-upload>

    <my-upload
      @input="closeBanner"
      field="file"
      :params="bannerFormData"
      @crop-success="cropBannerSuccess"
      @crop-upload-success="cropBannerUploadSuccess"
      @crop-upload-fail="cropBannerUploadFail"
      :url="uploadUrl"
      :width="375"
      :headers="headers"
      :height="150"
      :value.sync="showBanner"
      :no-circle=true
      img-format="png">
    </my-upload>
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

        showIcon: false,
        showBanner: false,
        params: {
          pictype: ''
        },
        headers: {},
        // uploadUrl: api.uri.uploadCompanyPic,
        uploadUrl: '',
        dialogFormVisible: false,
        logoFormData: {
          key: '',
          policy: '',
          OSSAccessKeyId: '',
          signature: '',
          expire: 0,
          success_action_status: '200'
        },
        bannerFormData: {
          key: '',
          policy: '',
          OSSAccessKeyId: '',
          signature: '',
          expire: 0,
          success_action_status: '200'
        }
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
      this.initLogoFormData()
      this.initBannerFormData()
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
      initLogoFormData () {
        this.headers = api.getUploadHeaders()
        api.fetch(api.uri.ossInfo, {businessType: 'company-logo'}).then(data => {
          // console.log('logo: ', data.result)
          if (data.status === 1) {
            this.logoFormData.OSSAccessKeyId = data.result.accessid
            this.logoFormData.key = data.result.dir
            this.logoFormData.policy = data.result.policy
            this.logoFormData.signature = data.result.signature
            this.logoFormData.expire = data.result.expire
            this.uploadUrl = data.result.host
          }
        })
      },
      initBannerFormData () {
        this.headers = api.getUploadHeaders()
        api.fetch(api.uri.ossInfo, {businessType: 'company-banner'}).then(data => {
          if (data.status === 1) {
            this.bannerFormData.OSSAccessKeyId = data.result.accessid
            this.bannerFormData.key = data.result.dir
            this.bannerFormData.policy = data.result.policy
            this.bannerFormData.signature = data.result.signature
            this.bannerFormData.expire = data.result.expire
            this.uploadUrl = data.result.host
          }
        })
      },
      closeIcon: function (value) {
        this.showIcon = value
      },
      closeBanner: function (value) {
        this.showBanner = value
      },
      // logo: 上传logo; banner: 上传banner
      toggleShow: function (type) {
        if (type === 'logo') {
          this.showIcon = !this.showIcon
        } else {
          this.showBanner = !this.showBanner
        }
        this.params.pictype = type
      },
      cropIconSuccess (data, field) {
        this.logoFormData.key = this.logoFormData.key + api.guid() + '.png'
      },
      cropIconUploadSuccess (jsonData, field) {
        this.logoSrc = this.uploadUrl + '/' + this.logoFormData.key
        api.post(api.uri.saveCompanyPic, {pictype: 'logo', url: this.logoSrc}).then(data => {
        })
      },
      cropIconUploadFail (status, field) {
        // console.log('-----------------------failed----------------------')
        // // console.error(this.logoFormData)
        // console.log('status:', status)
        // console.log('field:', field)
      },
      cropBannerSuccess (data, field) {
        this.bannerFormData.key = this.bannerFormData.key + api.guid() + '.png'
      },
      cropBannerUploadSuccess (jsonData, field) {
        this.bannerSrc = this.uploadUrl + '/' + this.bannerFormData.key
        api.post(api.uri.saveCompanyPic, {pictype: 'banner', url: this.bannerSrc}).then(data => {
        })
      },
      cropBannerUploadFail (status, field) {
      }
    }
  }
</script>

<style scoped="scope">
  .page_m {
    background: #f1f1f1;
  }
</style>
<style>
  .auto_width .el-dialog--small {
    width: auto !important;
  }
</style>
