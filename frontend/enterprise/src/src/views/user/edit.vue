<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="user"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">员工管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="ruleForm.mobile"></el-input>
            </el-form-item>
            <el-form-item label="工号" prop="employeeNo">
              <el-input v-model="ruleForm.employeeNo"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="部门" prop="department">
              <el-input v-model="ruleForm.department"></el-input>
            </el-form-item>
            <el-form-item label="区域" prop="area">
              <el-input v-model="ruleForm.area"></el-input>
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
  import api from '../../services/api'
  import router from '../../router'
  export default {
    data: function () {
      return {
        id: 0,
        ruleForm: {
          name: '',
          mobile: '',
          employeeNo: '',
          email: '',
          department: '',
          area: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号', trigger: 'change' },
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
          email: [
            { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
          ]
        }
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.id = this.$route.query.id
      api.fetch(api.uri.getUser, {id: this.id}).then(data => {
        if (data.status === 1) {
          this.ruleForm = data
        }
      }).catch(error => {
        alert(error.message)
      })
    },
    methods: {
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.post(api.uri.createUser, {
              name: this.ruleForm.name,
              mobile: this.ruleForm.mobile,
              employeeNo: this.ruleForm.employeeNo,
              email: this.ruleForm.email,
              department: this.ruleForm.department,
              area: this.ruleForm.area
            }).then(data => {
              alert('保存成功')
              router.push({name: 'userList'})
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

<style scoped>
  .demo-ruleForm{
    width:600px;
    margin-top:20px;
  }
  .el-input__inner:focus{
    border-color: #01b554;
  }
</style>
