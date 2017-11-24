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
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="联系人" prop="user">
                  <el-input v-model="ruleForm.user"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :span="12" label="联系电话" prop="mobile">
                  <el-input v-model="ruleForm.employeeNo"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="省份" prop="user">
                  <el-input v-model="ruleForm.user"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :span="12" label="城市" prop="contacts">
                  <el-input v-model="ruleForm.employeeNo"></el-input>
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
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="营业执照" prop="license">
              <el-button size="small" type="primary">点击上传</el-button>
              <div class="license_width">
                <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511510506103&di=4d32cfe044a5c9a5c9c0d2221b0ef111&imgtype=0&src=http%3A%2F%2Fphoto.img.chem960.com%2Fpl2014%2Fpm4%2F4%2F2r14qa10h734109.jpg">
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
//  import moment from 'moment'
  export default {
    data: function () {
      return {
        ruleForm: {
          name: '',
          user: '',
          mobile: '',
          contacts: '',
          department: '',
          address: '',
          industry: '',
          license: '',
          agent: ''
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
      commonHeader,
      navigator
    },
    created () {
    },
    methods: {
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.fetch(api.uri.createUser, {
              fullname: this.ruleForm.name,
              cellphone: this.ruleForm.user,
              jobnumber: this.ruleForm.mobile,
              department: this.ruleForm.department,
              dsvalue1: this.ruleForm.dsvalue1,
              dsvalue2: this.ruleForm.dsvalue2
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
</style>
