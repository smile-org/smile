<template>
  <div>
    <common-header type="booking"></common-header>
    <div class="con_main">
      <navigator menu="membership"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">企业会员管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="企业名称" prop="companyName">
              <el-input v-model="ruleForm.companyName"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="联系人" prop="contacts">
                  <el-input v-model="ruleForm.contacts"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :span="12" label="联系电话" prop="phone">
                  <el-input v-model="ruleForm.phone"></el-input>
                </el-form-item>
              </el-col>
              <el-col class="attention_p">
                <span>注：</span>该人将作为企业管理员添加至系统中，密码将以短信形式发至该手机上。
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="授权用户" prop="userCount">
                  <el-input-number v-model="ruleForm.userCount" :min="1" label="授权用户"></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="服务截止" prop="dateEnd">
                  <el-col>
                    <el-date-picker
                      format="yyyy-MM-dd"
                      class="dateTab_width"
                      type="date"
                      placeholder="选择服务截止日期"
                      v-model="ruleForm.dateEnd"
                      style="width: 100%;"></el-date-picker>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="省份" prop="province">
                  <el-select class="dateTab_width" v-model="ruleForm.province" @change="getCity"  placeholder="请选择省份">
                    <el-option v-for="item in provinceList"
                      :key="item.province_id"
                      :label="item.province_name"
                      :value="item.province_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="城市" prop="city" >
                  <el-select class="dateTab_width" v-model="ruleForm.city"  placeholder="请选择城市">
                    <el-option v-for="item in cityList"
                        :key="item.city_id"
                        :label="item.city_name"
                        :value="item.city_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="主营行业" prop="business_id">
                  <el-select class="dateTab_width" v-model="ruleForm.business_id"  placeholder="请选择主营行业">
                    <el-option v-for="item in businessList"
                      :key="item.business_id"
                      :label="item.business_name"
                      :value="item.business_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代理商" prop="agency_id">
                  <el-select class="dateTab_width" v-model="ruleForm.agency_id" placeholder="请选择代理商">
                    <el-option v-for="item in agencyList"
                      :key="item.agency_id"
                      :label="item.agency_name"
                      :value="item.agency_id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="企业地址" prop="address">
              <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <el-form-item label="营业执照" prop="src">
              <el-upload
                class="upload-demo"
                :action=imgUrl
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
                :after-upload="handleSuccess"
                :limit="1"
                :on-change="changeUpload"
                :on-exceed="handleExceed"
                :file-list="fileList"
                :headers="headers"
                :data="uploadFormData">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
              </el-upload>
              <div class="license_width">
                <img v-show="ruleForm.src !== ''" v-bind:src="ruleForm.src">
              </div>
            </el-form-item>
            <div class="tc btn_margin">
              <button class="inf_btn" type="button" v-on:click="submitForm('ruleForm')">确  定</button>
              <button class="inf_btn ml20" type="button" v-on:click="goBack()">返  回</button>
            </div>
          </el-form>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import router from '../../router'
  import api from '../../services/api'
  import axios from 'axios'
//  import ElRow from "element-ui/packages/row/src/row";
  import moment from 'moment'
  export default {
    data: function () {
      return {
        fileList: [],
        // imgUrl: axios.defaults.baseURL + api.uri.uploadLicense,
        imgUrl: '',
        tempImgUrl: '',
        headers: {},
        // src: '',
        businessList: [],
        agencyList: [],
        provinceList: [],
        cityList: [],
        uploadFormData: {
          key: '',
          policy: '',
          OSSAccessKeyId: '',
          signature: '',
          expire: 0,
          success_action_status: '200'
        },
        ruleForm: {
          companyName: '',
          contacts: '',
          phone: '',
          province: '',
          city: '',
          business_id: '',
          agency_id: '',
          address: '',
          src: '',
          userCount: 0,
          dateEnd: ''
        },
        rules: {
          companyName: [
            { required: true, message: '代理商名称', trigger: 'blur' }
          ],
          contacts: [
            { required: true, message: '请输入联系人', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请输入联系人电话', trigger: 'change' },
            { len: 11, message: '请输入正确格式的手机号码', trigger: 'blur' },
            { validator: (rule, value, callback) => {
              if (/^1[34578]\d{9}$/.test(value) === false) {
                callback(new Error('请输入正确格式的手机号码'))
              } else {
                callback()
              }
            },
              trigger: 'blur'}
          ],
          province: [
            { type: 'number', required: true, message: '请选择省份', trigger: 'change' }
          ],
          city: [
            { type: 'number', required: true, message: '请选择城市', trigger: 'change' }
          ],
          business_id: [
            { type: 'number', required: true, message: '请选择主营行业', trigger: 'change' }
          ],
          agency_id: [
            { type: 'number', required: true, message: '请选择代理商', trigger: 'change' }
          ],
          address: [
            { required: true, message: '请输入地址', trigger: 'trigger' }
          ],
          src: [
            { required: true, message: '请上传企业执照', trigger: 'trigger' }
          ],
          dateEnd: [
            { type: 'date', required: true, message: '请输入服务截止日期', trigger: 'change' }
          ],
          userCount: [
            { type: 'number', required: true, message: '请输入授权用户数', trigger: 'change' }
          ]
        }
      }
    },
    components: {
      commonHeader,
      navigator,
      axios
    },
    filters: {
      formatImage: function (url) {
        return axios.defaults.imageServer + url
      }
    },
    created () {
      this.headers = api.getUploadHeaders()
      this.initUploadData()
      api.fetch(api.uri.getProvinceAndBusiness, {companyid: 0}).then(data => {
        if (data.status === 1) {
          this.businessList = data.result.BusinessList
          this.provinceList = data.result.ProvinceList
          this.agencyList = data.result.AgencyList
        }
      })
    },
    methods: {
      initUploadData () {
        this.headers = api.getUploadHeaders()
        api.fetch(api.uri.ossInfo, {businessType: 'business-licences'}).then(data => {
          if (data.status === 1) {
            this.uploadFormData.OSSAccessKeyId = data.result.accessid
            this.uploadFormData.key = data.result.dir
            this.uploadFormData.policy = data.result.policy
            this.uploadFormData.signature = data.result.signature
            this.uploadFormData.expire = data.result.expire
            this.imgUrl = data.result.host
          }
        })
      },
      changeUpload (file, fileList) {
        // 保证页面显示一个附件
        if (fileList.length > 0) {
          this.fileList = [file]
        }
      },
      getCity () {
        api.fetch(api.uri.getCityList, {provinceid: this.ruleForm.province}).then(data => {
          if (data.status === 1) {
            this.cityList = data.result
            this.ruleForm.city = ''
          }
        })
      },
      goBack () {
        router.push({name: 'membershipList'})
      },
      beforeUpload (file) {
        // 判断大小与格式
        if (file.name.indexOf('.') !== -1) {
          var arrLen = file.name.split('.').length - 1
          var extension = file.name.split('.')[arrLen].toUpperCase()
          if (['JPG', 'PNG'].indexOf(extension) === -1) {
            this.$message({
              type: 'info',
              message: '不支持的上传文件格式'
            })
            this.fileList = []
            return false
          }
        } else {
          this.$message({
            type: 'info',
            message: '不支持的上传文件格式'
          })
          this.fileList = []
          return false
        }
        if (file.size > 2 * 1024 * 1024) {
          this.$message({
            type: 'info',
            message: '上传文件不能大于2MB'
          })
          this.fileList = []
          return false
        }
        this.initUploadData()
        this.uploadFormData.key = this.uploadFormData.key + api.guid() + '.' + api.getFileExt(file.name)
        console.log(this.uploadFormData.key)
        this.tempImgUrl = this.uploadFormData.key
      },
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.post(api.uri.createCompany, {
              company_name: this.ruleForm.companyName,
              contact_person: this.ruleForm.contacts,
              phone_number: this.ruleForm.phone,
              province_id: this.ruleForm.province,
              city_id: this.ruleForm.city,
              business_id: this.ruleForm.business_id,
              agency_id: this.ruleForm.agency_id,
              address: this.ruleForm.address,
              LincenceUrl: this.ruleForm.src,
              user_limit: this.ruleForm.userCount,
              expiration_date: moment(this.ruleForm.dateEnd).format('YYYY-MM-DD')
            }).then(data => {
              if (data.status === 1) {
                router.push({name: 'membershipList'})
              }
            })
          } else {
            return false
          }
        })
      },
      resetForm (formName) {
        this.$refs[formName].resetFields()
      },
      // 上传成功后赋给src
      handleSuccess (response, file, fileList) {
        this.ruleForm.src = this.imgUrl + '/' + this.tempImgUrl
      },
      handleRemove (file, fileList) {
        console.log(file, fileList)
      },
      handlePreview (file) {
        console.log(file)
      },
      handleExceed (files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
      }
    }
  }
</script>

<style scoped="scope">
  .demo-ruleForm{
    width:700px;
    margin-top:20px;
  }
  .el-input__inner:focus{
    border-color: #01b554;
  }
  .el-select {
    width: 100%;
  }
  .license_width img{
    margin-top:20px;
    max-width:580px;
    padding:10px;
    border:1px solid #dcdcdc;
  }
  .attention_p{
    margin-left: 100px;
    margin-top: -5px;
    margin-bottom: 20px;
    font-size: 12px;
    color: #f56d16;
  }
  .attention_p span{
    color: #f56d16;
  }

</style>
