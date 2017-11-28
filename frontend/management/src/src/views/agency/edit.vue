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
            <el-form-item label="代理商名称" prop="agency_name">
              <el-input v-model="ruleForm.agency_name"></el-input>
            </el-form-item>
            <el-form-item label="法人姓名" prop="legal_person">
              <el-input v-model="ruleForm.legal_person"></el-input>
            </el-form-item>
            <el-form-item label="联系人" prop="contact_person">
              <el-input v-model="ruleForm.contact_person"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="contact_phone">
              <el-input v-model="ruleForm.contact_phone"></el-input>
            </el-form-item>
            <el-form-item label="代理区域" prop="agent_area">
              <el-input v-model="ruleForm.agent_area"></el-input>
            </el-form-item>
            <el-form-item label="代理权有效期" >
              <el-col :span="11">
                <el-form-item prop="agent_start_date">
                  <el-date-picker v-model="ruleForm.agent_start_date" prop="agent_start_date" type="date" placeholder="开始时间" style="width: 100%;" format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col class="line ml_10style" :span="2">—</el-col>
              <el-col :span="11">
                <el-form-item prop="agent_end_date">
                  <el-date-picker v-model="ruleForm.agent_end_date" prop="agent_end_date" type="date" placeholder="结束时间" style="width: 100%;" format="yyyy-MM-dd"></el-date-picker>
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
        id: 0,
        ruleForm: {
          agency_name: '',
          legal_person: '',
          contact_person: '',
          contact_phone: '',
          agent_area: '',
          agent_start_date: '',
          agent_end_date: ''
        },
        rules: {
          agency_name: [
            { required: true, message: '代理商名称', trigger: 'blur' }
          ],
          legal_person: [
            { required: true, message: '请输入法人姓名', trigger: 'blur' }
          ],
          contact_person: [
            { required: true, message: '请输入联系人姓名', trigger: 'blur' }
          ],
          contact_phone: [
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
          agent_area: [
            { required: true, message: '请输入代理区域', trigger: 'blur' }
          ],
          agent_start_date: [
            { type: 'date', required: true, message: '请输入开始时间', trigger: 'change' }
          ],
          agent_end_date: [
            { type: 'date', required: true, message: '请输入结束时间', trigger: 'change' }
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
      api.fetch(api.uri.getAgency, {
        agencyid: this.id
      }).then(data => {
        if (data.status === 1) {
          this.ruleForm.agency_name = data.result.agency_name
          this.ruleForm.legal_person = data.result.legal_person
          this.ruleForm.contact_person = data.result.contact_person
          this.ruleForm.contact_phone = data.result.contact_phone
          this.ruleForm.agent_area = data.result.agent_area
          this.ruleForm.agent_start_date = new Date(data.result.agent_start_date)
          this.ruleForm.agent_end_date = new Date(data.result.agent_end_date)
        }
      })
    },
    methods: {
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            api.post(api.uri.updateAgency, {
              agency_id: this.id,
              agency_name: this.ruleForm.agency_name,
              legal_person: this.ruleForm.legal_person,
              contact_person: this.ruleForm.contact_person,
              contact_phone: this.ruleForm.contact_phone,
              agent_area: this.ruleForm.agent_area,
              agent_start_date: this.ruleForm.agent_start_date,
              agent_end_date: this.ruleForm.agent_end_date
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
