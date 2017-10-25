<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="registration"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 :<span class="">报名管理</span> > <span class=""> 培训报名管理</span> > <span class="f_blue">添加报名</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden" label-width="80px">
            <el-col :span="8">
              <el-form-item label="课程名称">
                <el-input v-model="form.name" placeholder="课程名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="讲师">
                <el-input v-model="form.department" placeholder="讲师"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="人数限制">
                <el-input v-model="form.department" placeholder="人数限制"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开始时间">
                <el-date-picker prop="date" type="date" placeholder="选择日期" v-model="form.date"
                                style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="结束时间">
                <el-date-picker prop="date1" type="date" placeholder="选择日期" v-model="form.date1"
                                style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>

          </el-form>
          <table class="page_m mt30" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                课程图标
              </td>
              <td class="page_m_b">
                <img :src="bannerSrc | formatImage" width="20%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(1)">使用默认</a>
                <a v-on:click="toggleShow(1)">上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                课程主图
              </td>
              <td class="page_m_b">
                <img :src="bannerSrc | formatImage" width="100%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(2)">使用默认</a>
                <a v-on:click="toggleShow(2)">上传</a>
              </td>
            </tr>
          </table>
          <div class="mt30">
            <p>培训内容</p>
            <template class="hidden">
              <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
                <!--<el-col :span="12">-->
                <el-col :span="12">
                  <el-form-item label="序号">
                    <el-input v-model="formInline.mum" placeholder="序号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="起止时间">
                    <el-date-picker v-model="value3" type="datetimerange" placeholder="选择时间范围"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="主题">
                    <el-input v-model="formInline.topic" placeholder="主题" style="width:400px;"></el-input>
                    <el-button :plain="true" type="success" class="ml20" size="primary">添加主题</el-button>
                  </el-form-item>
                </el-col>
              </el-form>
            </template>
            <template>
              <el-table :data="tableData" border style="width: 100%">
                <el-table-column prop="number" label="序号" width="100"></el-table-column>
                <el-table-column prop="topic" label="主题" width=""></el-table-column>
                <el-table-column prop="timeAll" label="起止时间"></el-table-column>
                <el-table-column prop="" label="操作" width="100">
                  <template scope="scope" >
                    <el-button @click="open2" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </div>
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " @click="submitForm('ruleForm')" v-on:click="routeByName('')">保  存
            </button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import api from '../../services/api'
  export default {
    data: function () {
      return {
        checked: true,
        formInline: {
          user: '',
          section: '',
          topic: '',
          mum: ''
        },
        company: {},
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        value3: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
        tableData: [{
          number: '1',
          topic: '王小虎王小虎王小虎王小虎王小虎王小虎',
          timeAll: '2000-11-10 10:10:00 - 2000-11-11 10:10:00'
        }, {
          number: '1',
          topic: '王小虎王小虎王小虎王小虎王小虎王小虎',
          timeAll: '2000-11-10 10:10:00 - 2000-11-11 10:10:00'
        }]
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getCompanyInfo).then(data => {
        if (data.status === 1) {
          this.company = data
        }
      })
    },
    methods: {
      onSubmit: function () {
        console.log('submit!')
      },
      open2 () {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
//      submitForm (formName) {
//        this.$refs[formName].validate((valid) => {
//          if (valid) {
//            alert('submit!');
//          } else {
//            console.log('error submit!!');
//            return false;
//          }
//        });
//      },
//      resetForm (formName) {
//        this.$refs[formName].resetFields();
//      }
    }
  }
</script>

<style scoped="scope">
  .el-dialog__body {
    padding: 10px 20px;
  }

  .el-button--small {
    font-size: 14px;
    color: #00b553;
  }

  .el-button--small:hover, .el-button--small:active, .el-button--small:focus {
    font-size: 14px;
    color: #1DB513;
  }

  .add_topic li span {
    /*width:100%;*/
    /*padding:10px 0;*/
    /*background: #ededed;*/
    font-size: 14px;
    color: #666;
  }

  .add_topic li i {
    /*width:100%;*/
    /*padding:10px 0;*/
    /*background: #ededed;*/
    font-size: 16px;
    color: #666;
    margin-left: 10px;
    cursor: pointer;
  }

  .add_topic li i:hover {
    color: #f93d1f;
  }
</style>
