<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="">学习任务管理</span> > <span class="f_blue">添加学习任务</span></span>
        </nav>
        <div class="con_tab">
          <el-form :rules="formRules" ref="form" :inline="true" :model="form"
                   class="demo-form-inline mt20 hidden add_width" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="任务名称" prop="title">
                  <el-input v-model="form.title" placeholder="任务名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="截止日期" prop="date">
                  <el-date-picker v-model="form.date" type="date" placeholder="选择日期" class="dateTab_width"
                                  style="width: 100%;" :picker-options="pickerOptions0">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col>
              <el-form-item label="任务描述" prop="intro" class="input_textarea">
                <el-input type="textarea" v-model="form.intro" placeholder="任务描述" style="min-width: 545px;"></el-input>
              </el-form-item>
              <el-form-item label="目标学员范围" prop="intro" class="input_textarea">
                <el-input type="textarea" v-model="form.scope" placeholder="目标学员范围"
                          style="min-width: 545px;"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
          <hr class="hr_line">
          <!--添加课程-->
          <div class="mt30">
            <p>课程
              <el-button type="text" class="ml15" @click="addContent">添加课程</el-button>
            </p>
            <template>
              <el-dialog title="添加课程" :visible.sync="dialogFormVisible">
                <el-form ref="formInline" :inline="true" :model="formInline"
                         class="demo-form-inline">
                  <el-form-item label="课程名称" prop="num">
                    <el-input v-model.number="formInline.num" placeholder="课程名称"></el-input>
                  </el-form-item>
                  <el-form-item label="开始时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期"
                                      v-model="formInline.start_date" style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item label="结束时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.end_date"
                                      style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item>
                    <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change=""
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选课程</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%">
                  <el-table-column prop="name" align="center" label="课程名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="remove(scope.row.id)" type="text" class="red_font" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </div>
          </div>
          <!--添加考试-->
          <div class="mt30">
            <p>考试
              <el-button type="text" class="ml15" @click="addExam">添加考试</el-button>
            </p>
            <template>
              <el-dialog title="添加考试" :visible.sync="dialogFormVisible1">
                <el-form ref="formInline" :inline="true" :model="formInline"
                         class="demo-form-inline">
                  <el-form-item label="考试名称" prop="num">
                    <el-input v-model.number="formInline.num" placeholder="课程名称"></el-input>
                  </el-form-item>
                  <el-form-item label="开始时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期"
                                      v-model="formInline.start_date" style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item label="结束时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.end_date"
                                      style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item>
                    <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="考试名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change=""
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选考试</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="考试名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%">
                  <el-table-column prop="name" align="center" label="考试名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="remove(scope.row.id)" type="text" class="red_font" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </div>
          </div>
          <!--添加报名-->
          <div class="mt30">
            <p>报名
              <el-button type="text" class="ml15" @click="addApply">添加报名</el-button>
            </p>
            <template>
              <el-dialog title="添加报名" :visible.sync="dialogFormVisible2">
                <el-form ref="formInline" :inline="true" :model="formInline"
                         class="demo-form-inline">
                  <el-form-item label="报名名称" prop="num">
                    <el-input v-model.number="formInline.num" placeholder="报名名称"></el-input>
                  </el-form-item>
                  <el-form-item label="开始时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期"
                                      v-model="formInline.start_date" style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item label="结束时间">
                    <el-col>
                      <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.end_date"
                                      style="width: 100%;"></el-date-picker>
                    </el-col>
                  </el-form-item>
                  <el-form-item>
                    <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change=""
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选课程</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="报名名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%">
                  <el-table-column prop="name" align="center" label="课程名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="remove(scope.row.id)" type="text" class="red_font" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </div>
          </div>
          <!--添加员工-->
          <div class="mt30">
            <p>员工
              <el-button type="text" class="ml15" @click="addEmployee">添加员工</el-button>
            </p>
            <template>
              <el-dialog title="添加员工" :visible.sync="dialogFormVisible3">
                <el-form ref="formInline" :inline="true" :model="formInline"
                         class="demo-form-inline" label-width="80px">
                  <el-form-item label="课程名称" prop="num">
                    <el-input v-model.number="formInline.num" placeholder="姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="部门" prop="title">
                    <el-input v-model="formInline.title" placeholder="部门"></el-input>
                  </el-form-item>
                  <el-form-item label="区域" prop="content">
                    <el-input v-model="formInline.content" placeholder="区域" style=""></el-input>
                  </el-form-item>
                  <el-form-item>
                    <button type="button" class="line-btn ml20" v-on:click="search">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable">
                    <el-table-column property="user_id" align="center" width="100" type="selection"
                                     @selection-change="changeFun">
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="姓名" width=""></el-table-column>
                    <el-table-column property="full_name" align="center" label="部门" width=""></el-table-column>
                    <el-table-column property="full_name" align="center" label="区域" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="submitUploadContent">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%">
                  <el-table-column prop="name" align="center" label="员工姓名">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button v-on:click="del()" type="text" class="red_font" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </div>
          </div>
          <div class="tc">
            <button type="button" class="inf_btn mt30 mb20" v-on:click="saveExam">保  存
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
  //  import ElRow from "element-ui/packages/row/src/row";
  import api from '../../services/api'
  //  import moment from 'moment'
  //  import router from '../../router'
  export default {
    data: function () {
      return {
        pickerOptions0: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        form: {
          title: '',
          date: '',
          scope: '',
          intro: ''
        },
        formInline: {
          id: '',
          num: '',
          title: '',
          content: '',
          name: '',
          start_date: '',
          end_date: '',
          url: ''
        },
        dialogFormVisible: false,
        dialogFormVisible1: false,
        dialogFormVisible2: false,
        dialogFormVisible3: false,
        dialogTableVisible: false,
        take: 10,
        currentPage: 1,
        total: 0,
        formRules: {
          title: [
            {required: true, message: '请输入任务名称', trigger: 'blur'}
          ],
          date: [
            {type: 'date', required: true, message: '请选择有效时间', trigger: 'change'}
          ],
          intro: [
            {required: true, message: '请输入任务描述', trigger: 'blur'}
          ],
          scope: [
            {required: true, message: '请输入目标学员范围', trigger: 'blur'}
          ]
        }
      }
    },
    components: {
      commonHeader,
      navigator
    },
    methods: {
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
      addContent () {
        if (!this.addContentInProgress) {
//          this.resetFormInline()
          this.addContentInProgress = true
        }
        this.dialogFormVisible = true
      },
      addExam () {
//        if (!this.addExamInProgress) {
//          this.addExamInProgress = true
//        }
        this.dialogFormVisible1 = true
      },
      addApply () {
//        if (!this.addExamInProgress) {
//          this.addExamInProgress = true
//        }
        this.dialogFormVisible2 = true
      },
      addEmployee () {
//        if (!this.addExamInProgress) {
//          this.addExamInProgress = true
//        }
        this.dialogFormVisible3 = true
      },

      submitUploadContent () {
      },
      del (id) {
        this.$confirm('此操作将永久删除该课程分类, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteCategory, {cateid: id}).then(data => {
            api.fetch(api.uri.getCategory, {skip: 0, take: 10000}).then(data1 => {
              if (data1.status === 1) {
                this.data = data1.result
              }
            }).catch(error => {
              alert(error.message)
            })
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }

    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
