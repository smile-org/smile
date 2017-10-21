<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="registration"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">报名管理</span> > <span class="f_blue">员工报名记录</span> </span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="form.name" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="讲师" prop="region">
              <el-input v-model="form.user" placeholder="讲师"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date"
                                style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date1"
                                style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn ml20">查  询</button>
            </el-form-item>
          </el-form>
          <!--添加报名导出表格-->
          <div class="fr hidden mb20">
            <button type="button" class="inf_btn mr20" v-on:click="routeByName('registrationEdit')">添加报名</button>
            <button type="button" @click="dialogTableVisible = true" class="inf_btn">导  出</button>
            <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">

              <div class="tc">
                <!--<p class="exal">电子表格文件生成成功</p>-->
                <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
              </div>
              <div class="tc">
                <a href="#" download="w3logo" class="inf_btn download" style="display: inline-block;">下  载</a>
                <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消</button>
              </div>

            </el-dialog>
          </div>
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="name" label="课程名称" width="">
            </el-table-column>
            <el-table-column prop="lecturer" label="讲师" width="100">
            </el-table-column>
            <el-table-column prop="timeStart" label="开始时间" width="">
            </el-table-column>
            <el-table-column prop="timeEnd" label="结束时间" width="">
            </el-table-column>
            <el-table-column prop="appraise" label="查看评价" width="180">
              <template scope="scope">
                <el-button v-on:click="routeByName('registrationComment')" type="text" size="small">查看课程评价</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="appraise" label="再开一期" width="100">
              <template scope="scope">
                <el-button v-on:click="routeByName('')" type="text" size="small">再开一期</el-button>
              </template>
            </el-table-column>

            <el-table-column label="操作" class="tc" width="180">
              <template scope="scope">
                <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                <el-button @click="checkFail(scope.row.id)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
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
        close: '',
        form: {
          name: '',
          user: '',
          company: {},
          department: '',
          area: '',
          date: '',
          date1: ''
        },
        dialogTableVisible: false,
        tableData: [{
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日'
        }, {
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日'
        }, {
          name: '成功过心理学',
          lecturer: '望天有',
          timeStart: '2017年10月12日',
          timeEnd: '2017年10月12日'
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
      routeByName: function (name) {
        router.push({name: name})
      },
      click: function () {
        this.dialogTableVisible = !this.dialogTableVisible
      },
      open4 () {
        const h = this.$createElement
        this.$msgbox({
          title: '消息',
          message: h('p', null, [
            h('span', null, '内容可以是 '),
            h('i', {style: 'color: teal'}, 'VNode')
          ]),
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true
              instance.confirmButtonText = '执行中...'
              setTimeout(() => {
                done()
                setTimeout(() => {
                  instance.confirmButtonLoading = false
                }, 300)
              }, 3000)
            } else {
              done()
            }
          }
        }).then(action => {
          this.$message({
            type: 'info',
            message: 'action: ' + action
          })
        })
      }
    }
  }
</script>

<style scoped="scope">
  .download {
    line-height: 38px;
    display: inline-block;
  }

  .qx_btn {
    min-width: 120px;
    height: 38px;
    text-align: center;
    color: #fff;
    background: #a4a4a4;
    border-radius: 4px;
    font-size: 16px;
    letter-spacing: 2px;
    cursor: pointer;
    padding: 0 20px;
  }

  .qx_btn:hover, .qx_btn:active, .qx_btn:focus {
    color: #fff;
    background: #c3c3c3;
    outline: none;
  }
</style>
