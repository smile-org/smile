<template>
  <div>
    <common-header type="booking"></common-header>
    <div class="con_main">
      <navigator menu="agency"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">代理商管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="企业名称" prop="name">
              <el-input v-model="ruleForm.companyName"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="联系人" prop="user">
                  <el-input v-model="ruleForm.contacts"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :span="12" label="联系电话" prop="mobile">
                  <el-input v-model="ruleForm.phone"></el-input>
                </el-form-item>
              </el-col>
              <el-col class="attention_p">
                <span>注：</span>该人将作为企业管理员添加至系统中，密码将以短信形式发至该手机上。
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="省份" prop="user">
                  <el-input v-model="ruleForm.province"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :span="12" label="城市" prop="contacts">
                  <el-input v-model="ruleForm.city"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="主营行业" prop="industry">
                  <el-select class="dateTab_width"  placeholder="请选择主营行业">
                    <el-option>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代理商" prop="agent">
                  <el-select class="dateTab_width"  placeholder="请选择代理商">
                    <el-option>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="企业地址" prop="address">
              <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <el-form-item label="营业执照" prop="license">
              <el-upload
                class="upload-demo"
                :action=imgUrl
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                multiple
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList"
                :headers="headers">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
              <div class="license_width">
                <img v-bind:src="src">
              </div>
            </el-form-item>
            <div class="tc btn_margin">
              <button class="inf_btn" type="button" v-on:click="submitForm('ruleForm')">确  定</button>
              <button class="inf_btn ml20" type="button">返  回</button>
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
//  import moment from 'moment'
  export default {
    data: function () {
      return {
        imgUrl: axios.defaults.baseURL + api.uri.uploadLicense,
        headers: {},
        src: '',
        ruleForm: {
          companyName: '',
          contacts: '',
          phone: '',
          provience: '',
          city: '',
          business_id: '',
          agency_id: '',
          address: ''
        },
        rules: {
          name: [
            { required: true, message: '代理商名称', trigger: 'blur' }
          ],
          user: [
            { required: true, message: '请输入联系人', trigger: 'blur' }
          ],
//          contacts: [
//            { required: true, message: '请输入联系人电话', trigger: 'blur' }
//          ],
          mobile: [
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
          department: [
            { required: true, message: '请输入代理区域', trigger: 'blur' }
          ],
          industry: [
            { required: true, message: '请选择主营行业', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '请输入详细地址', trigger: 'blur' }
          ],
          agent: [
            { required: true, message: '请选择代理商', trigger: 'blur' }
          ]
        }
      }
    },
    components: {
//      ElRow,
      commonHeader,
      navigator,
      axios
    },
    created () {
      this.headers = api.getUploadHeaders()
    },
    methods: {
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.fetch(api.uri.settled, {
              company_name: this.ruleForm.name,
              contact_person: this.ruleForm.contacts,
              phone_number: this.ruleForm.phone,
              province_id: this.ruleForm.department,
              city: this.ruleForm.dsvalue1,
              business_id: this.ruleForm.business_id,
              agency_id: this.ruleForm.agency_id,
              address: this.ruleForm.address,
              LincenceUrl: this.src
            }).then(data => {
              router.push({name: 'agencyList'})
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
        console.log(response)
        this.src = axios.defaults.imageServer + response.result
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
    margin-top: -10px;
    margin-bottom: 20px;
    font-size: 12px;
    color: #f56d16;
  }
  .attention_p span{
    color: #f56d16;
  }

</style>
