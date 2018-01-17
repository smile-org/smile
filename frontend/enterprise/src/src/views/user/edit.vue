<template>
  <div>
    <common-header type="user"></common-header>
    <div class="con_main">
      <navigator module="user"  menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">员工管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="姓名" prop="full_name">
              <el-input v-model="ruleForm.full_name"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="cell_phone">
              <el-input v-model="ruleForm.cell_phone"></el-input>
            </el-form-item>
            <el-form-item label="工号" prop="job_number">
              <el-input v-model="ruleForm.job_number"></el-input>
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
          full_name: '',
          cell_phone: '',
          job_number: '',
          email: '',
          department: '',
          area: ''
        },
        rules: {
          full_name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
          ],
          cell_phone: [
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
      this.id = parseInt(this.$route.query.id)
      console.log(this.id)
      api.fetch(api.uri.getUser, {userid: this.id}).then(data => {
        if (data.status === 1) {
          this.ruleForm = data.result
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    methods: {
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.fetch(api.uri.editUser, {
              userid: this.id,
              fullname: this.ruleForm.full_name,
              cellphone: this.ruleForm.cell_phone,
              jobnumber: this.ruleForm.job_number,
              email: this.ruleForm.email,
              department: this.ruleForm.department,
              area: this.ruleForm.area
            }).then(data => {
              if (data.status === 1) {
                router.push({name: 'userList'})
              }
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
</style>
