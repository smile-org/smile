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
            <el-form-item label="代理商名称" prop="name">
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="法人姓名" prop="user">
              <el-input v-model="ruleForm.user"></el-input>
            </el-form-item>
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="ruleForm.employeeNo"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="mobile">
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="代理区域" prop="department">
              <el-input v-model="ruleForm.department"></el-input>
            </el-form-item>
            <el-form-item label="审核时间" >
              <el-col :span="11">
                <el-form-item prop="dsvalue1">
                  <el-date-picker v-model="ruleForm.dsvalue1" prop="dsvalue1" type="date" placeholder="审核开始时间" style="width: 100%;" format="yyyy-MM-dd" @change="getNormalTime1_com"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col class="line ml_10style" :span="2">—</el-col>
              <el-col :span="11">
                <el-form-item prop="dsvalue2">
                  <el-date-picker v-model="ruleForm.dsvalue2" prop="dsvalue2" type="date" placeholder="审核结束时间" style="width: 100%;" format="yyyy-MM-dd" @change="getNormalTime2_com"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-form-item>
            <div class="tc">
              <button class="inf_btn btn_margin" type="button" v-on:click="submitForm('ruleForm')">保  存</button>
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
          dsvalue1: '',
          dsvalue2: ''
        },
        rules: {
          name: [
            { required: true, message: '代理商名称', trigger: 'blur' }
          ],
          user: [
            { required: true, message: '请输入法人姓名', trigger: 'blur' }
          ],
          contacts: [
            { required: true, message: '请输入联系人姓名', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号', trigger: 'change' },
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
          dsvalue1: [
            { required: true, message: '请输入开始时间', trigger: 'blur' }
          ],
          dsvalue2: [
            { required: true, message: '请输入结束时间', trigger: 'blur' }
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
    width:600px;
    margin-top:20px;
  }
  .el-input__inner:focus{
    border-color: #01b554;
  }
</style>
