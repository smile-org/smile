<template>
  <div>
    <common-header type="course"></common-header>
    <div class="con_main">
      <navigator module="course" menu="learnlist"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="f_blue">员工学习记录</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="课程名称">
              <el-input v-model="formInline.title" placeholder="课程名称"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="formInline.user" placeholder="员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="部门">
              <el-input v-model="formInline.department" placeholder="部门"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="区域"></el-input>
            </el-form-item>
            <el-form-item>
              <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
              <button type="button" v-on:click="exportList()" :loading="showloading"
                         class="inf_btn ml20 export_bor">导  出
                        </button>
              <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                <div class="tc">
                  <!--<p class="exal">电子表格文件生成成功</p>-->
                  <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                </div>
                <div class="tc">
                  <a v-bind:href="exportExcelUrl" class="inf_btn download"
                     style="display: inline-block;">下  载</a>
                  <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消
                                </button>
                </div>
              </el-dialog>
            </el-form-item>
          </el-form>
          <hr class="hr_line">
          <el-table :data="tableData"  border style="width: 100%">
            <el-table-column prop="full_name" align="center" label="员工姓名" width="120">
            </el-table-column>
            <el-table-column prop="title" align="center" label="课程名称">
            </el-table-column>
            <el-table-column prop="department" align="center" label="部门" width="120">
            </el-table-column>
            <el-table-column prop="area" align="center" label="区域" width="120">
            </el-table-column>
            <el-table-column prop="content" align="center" label="课程内容">
            </el-table-column>
            <el-table-column align="center" label="学习时间">
              <template scope="scope">
                {{scope.row.learn_at|formatDate}}
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="take" layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        currentPage: 1,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          title: '',
          department: '',
          area: ''
        },
        tableData: [],
        showloading: false,
        dialogTableVisible: false,
        exportExcelUrl: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    filters: {
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    created () {
      this.search()
    },
    methods: {
      exportList () {
        this.showloading = true
        api.fetch(api.uri.exportUserLearnProgressList, {
          title: this.formInline.title,
          fullname: this.formInline.name,
          department: this.formInline.department,
          area: this.formInline.area
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.exportExcelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      search () {
        api.fetch(api.uri.getCourseLearningRecords, {
          title: this.formInline.title,
          fullname: this.formInline.name,
          department: this.formInline.department,
          area: this.formInline.area,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      handleCurrentChange (val) {
        this.currentPage = val
        this.search()
      }
    }
  }
</script>

<style scoped="scope">

</style>

