<template>
    <div>
        <common-header type="user"></common-header>
        <div class="con_main">
            <navigator module="user"  menu="list"></navigator>
            <section class="con_main_r">
                <nav>
                    <img src="../../assets/img/house.png" class="vm">
                    <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

                </nav>
                <div class="con_tab">

                    <el-form :inline="true" :model="formInLine" class="demo-form-inline ">
                        <!--<el-row>-->
                        <el-form-item label="姓名">
                            <el-input v-model="formInLine.name" placeholder="姓名"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号">
                            <el-input v-model="formInLine.mobile" placeholder="手机号"></el-input>
                        </el-form-item>
                        <!--</el-row>-->
                        <!--<el-row>-->
                        <el-form-item label="部门">
                            <el-input v-model="formInLine.department" placeholder="部门"></el-input>
                        </el-form-item>
                        <el-form-item label="区域">
                            <el-input v-model="formInLine.area" placeholder="区域"></el-input>
                        </el-form-item>
                        <!--<el-form-item class="">-->

                        <!--</el-form-item>-->
                        <!--</el-row>-->
                    </el-form>
                  <div class="fr mb20 dc_width">
                    <button  class="line-btn vm mr15" @click="onSubmit">查  询</button>
                    <button v-on:click="routeByName('userCreate')" class="inf_btn mr15 vm">添加员工</button>
                    <button class="inf_btn mr15 vm dis_in_block" v-on:click="showUploadDialog ()">批量导入</button>
                    <a v-bind:href="excelUrl" class="inf_btn mr15 vm dis_in_block">下载导入模板</a>
                    <button type="button" v-on:click="exportUserList" :loading="showloading" class="inf_btn export_bor vm">导  出
                    </button>
                    <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                      <div class="tc">
                        <!--<p class="exal">电子表格文件生成成功</p>-->
                        <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                      </div>
                      <div class="tc">
                        <a v-bind:href="exportExcelUrl" v-on:click="dialogTableVisible = false" class="inf_btn download" style="display: inline-block;">下  载</a>
                        <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取  消
                        </button>
                      </div>
                    </el-dialog>
                    <el-dialog title="员工导入" :visible.sync="dialogUploadVisible">
                      <el-upload class="upload-demo"
                                 ref="uploadContent"
                                 :action="uploadContentAction"
                                 :on-success="onContentSuccess"
                                 :before-upload="beforeContentUpload"
                                 :auto-upload="true"
                                 :headers="headers">
                        <button slot="trigger" size="small" class="inf_btn2 vm" type="primary">点击上传
                        </button>
                        <div slot="tip" class="el-upload__tip">支持类型xlsx，大小不超过100M</div>
                      </el-upload>
                    </el-dialog>
                  </div>
                    <hr class="hr_line">
                    <template>
                        <el-table :data="data" border style="width: 100%">
                            <el-table-column prop="full_name" align="center" label="姓名" min-width="100">
                            </el-table-column>
                            <el-table-column prop="cell_phone" align="center" label="手机" min-width="110">
                            </el-table-column>
                            <el-table-column prop="job_number" align="center" label="工号" min-width="140">
                            </el-table-column>
                            <el-table-column prop="email" align="center" label="邮箱" min-width="160">
                            </el-table-column>
                            <el-table-column prop="department" align="center" label="部门" width="120">
                            </el-table-column>
                            <el-table-column prop="area" label="区域" align="center" min-width="100">
                            </el-table-column>
                            <!--<el-table-column prop="created_at" align="center" label="创建时间" min-width="180">-->
                                <!--<template scope="scope">-->
                                    <!--{{scope.row.created_at | formatDate}}-->

                                <!--</template>-->
                            <!--</el-table-column>-->
                            <!--<el-table-column prop="updated_at" align="center" label="最后修改时间" min-width="180">-->
                                <!--<template scope="scope">-->
                                    <!--{{scope.row.updated_at | formatDate}}-->

                                <!--</template>-->
                            <!--</el-table-column>-->
                            <el-table-column label="操作" align="center" class="tc" width="120">
                                <template scope="scope">
                                    <el-button @click="editUser(scope.row.user_id)" type="text" size="small">编辑
                                    </el-button>
                                    <el-button @click="delUser(scope.row.user_id)" class="red_font" type="text"
                                               size="small">删除
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                    <div class="ds_oq_pageF">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                                       layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import router from '../../router'
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        formInLine: {
          department: '',
          area: '',
          name: '',
          // 去掉， 还要写验证， 麻烦
          mobile: ''
        },
        data: [],
        take: 10,
        currentPage: 0,
        total: 0,
        excelUrl: axios.defaults.fileServer + '/import/template/User.xlsx',
        dialogUploadVisible: false,
        uploadContentAction: api.uri.uploadEmployeeExcel,
        headers: {},
        showloading: false,
        exportExcelUrl: '',
        dialogTableVisible: false
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.headers = api.getUploadHeaders()
      console.log(this.formInLine)
      this.loadUser()
    },
    filters: {
      formatDate: function (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    methods: {
      loadUser: function () {
        api.fetch(api.uri.searchUserList, {
          fullname: this.formInLine.name,
          cellphone: this.formInLine.mobile,
          department: this.formInLine.department,
          area: this.formInLine.area,
          skip: 0,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          console.error(error.message)
        })
      },
      editUser: function (id) {
        router.push({name: 'userEdit', query: {id: id}})
      },
      delUser: function (id) {
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteUser, {userid: id}).then(data => {
            this.onSubmit()
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      handleCurrentChange: function (value) {
        this.currentPage = value
        api.fetch(api.uri.searchUserList, {
          fullname: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          cellphone: this.formInLine.mobile,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          alert(error.message)
        })
      },
      onSubmit: function () {
        api.fetch(api.uri.searchUserList, {
          fullname: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          cellphone: this.formInLine.mobile,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.data = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      routeByName: function (name) {
        router.push({name: name})
      },
      showUploadDialog () {
        this.dialogUploadVisible = true
      },
      onContentSuccess (response, file) {
        this.dialogUploadVisible = false
        if (response.status === 1) {
          this.loadUser()
        } else {
          this.$message({
            type: 'error',
            message: response.result
          })
        }
      },
      beforeContentUpload (file) {
        if (file.name.indexOf('.xlsx') < 0) {
          this.$message({
            type: 'info',
            message: '请上传excel文件！'
          })
          return false
        }
        if (file.size > 100 * 1024 * 1024) {
          this.$message({
            type: 'info',
            message: '附件不能大于100M'
          })
          return false
        }
      },
      exportUserList () {
        this.showloading = true
        api.fetch(api.uri.exportUserList, {
          fullname: this.formInLine.name,
          area: this.formInLine.area,
          department: this.formInLine.department,
          cellphone: this.formInLine.mobile
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.exportExcelUrl = axios.defaults.fileServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      }
    }
  }
</script>
