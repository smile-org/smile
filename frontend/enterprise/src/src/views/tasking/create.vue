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
                <el-form-item label="任务名称" prop="task_title">
                  <el-input v-model="form.task_title" placeholder="任务名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="截止日期" prop="expiration_date">
                  <el-date-picker v-model="form.expiration_date" type="date" placeholder="选择日期" class="dateTab_width"
                                  style="width: 100%;" :picker-options="pickerOptions0">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col>
              <el-form-item label="任务描述" prop="task_description" class="input_textarea">
                <el-input type="textarea" v-model="form.task_description" placeholder="任务描述" style="min-width: 545px;"></el-input>
              </el-form-item>
              <el-form-item label="目标学员范围" prop="task_scope" class="input_textarea">
                <el-input type="textarea" v-model="form.task_scope" placeholder="目标学员范围"
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
                  <el-form-item label="课程名称" prop="name">
                    <el-input v-model.number="formInline.name" placeholder="课程名称"></el-input>
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
                    <button type="button" class="line-btn ml20" v-on:click="courseSearch">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <!-- 查询课程列表 -->
                  <el-table border ref="multipleTable" :data="courseData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small" @click="addCourse(scope.row,scope.$index)">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="title" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change="handleCurrentChange"
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选课程</h3>
                  <!-- 已选课程列表 -->
                  <el-table border ref="multipleTable" :data="selCourseData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small" @click="delCourse(scope.$index)">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="title" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="saveCourse">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%" :data="selCourseData">
                  <el-table-column prop="title" align="center" label="课程名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="delCourse(scope.$index)" type="text" class="red_font" size="small">删除</el-button>
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
                  <el-form-item label="考试名称" prop="name">
                    <el-input v-model.number="formInline.name" placeholder="课程名称"></el-input>
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
                    <button type="button" class="line-btn ml20" v-on:click="examSearch">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <!-- 查询考试列表 -->
                  <el-table border ref="multipleTable" :data="examData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small" @click="pushExam(scope.row,scope.$index)">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="exam_title" align="center" label="考试名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change="handleCurrentChange1"
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选考试</h3>
                  <el-table border ref="multipleTable" :data="selExamData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small" @click="delExam(scope.$index)">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="exam_title" align="center" label="考试名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="saveExam">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%" :data="selExamData">
                  <el-table-column prop="exam_title" align="center" label="考试名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="delExam(scope.$index)" type="text" class="red_font" size="small">删除</el-button>
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
                  <el-form-item label="报名名称" prop="title">
                    <el-input v-model.number="formInline.name" placeholder="报名名称"></el-input>
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
                    <button type="button" class="line-btn ml20" v-on:click="enrollmentSearch">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable" :data="enrollmentData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" size="small" @click="pushEnrollment(scope.row,scope.$index)">加入任务</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="title" align="center" label="课程名称" width=""></el-table-column>
                  </el-table>
                  <el-pagination class="tc mt20" small layout="total, prev, pager, next" @current-change="handleCurrentChange2"
                                 :current-page="currentPage" :page-size="take" :total="total"></el-pagination>
                </el-row>
                <el-row>
                  <h3 class="tasking_h3 mt20">已选课程</h3>
                  <el-table border ref="multipleTable" :data="selEnrollmentData">
                    <el-table-column label="操作" align="center">
                      <template scope="scope">
                        <el-button type="text" class="red_font" size="small" @click="delEnrollment(scope.$index)">取消</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column property="title" align="center" label="报名名称" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="saveEnrollment">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%" :data="selEnrollmentData">
                  <el-table-column prop="title" align="center" label="课程名称">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button @click="delEnrollment(scope.$index)" type="text" class="red_font" size="small">删除</el-button>
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
                  <el-form-item label="课程名称" prop="name">
                    <el-input v-model.number="formInline.name" placeholder="姓名"></el-input>
                  </el-form-item>
                  <el-form-item label="部门" prop="area">
                    <el-input v-model="formInline.area" placeholder="部门"></el-input>
                  </el-form-item>
                  <el-form-item label="区域" prop="department">
                    <el-input v-model="formInline.department" placeholder="区域" style=""></el-input>
                  </el-form-item>
                  <el-form-item>
                    <button type="button" class="line-btn ml20" @click="userSearch">查  询</button>
                  </el-form-item>
                </el-form>
                <hr class="hr_line" style="margin-bottom: 15px;">
                <el-row>
                  <h3 class="tasking_h3">查询结果</h3>
                  <el-table border ref="multipleTable" :data="userData" @selection-change="changeFun">
                    <el-table-column property="user_id" align="center" width="100" type="selection" @selection-change="changeFun">
                    </el-table-column>
                    <el-table-column property="full_name" align="center" label="姓名" width=""></el-table-column>
                    <el-table-column property="department" align="center" label="部门" width=""></el-table-column>
                    <el-table-column property="area" align="center" label="区域" width=""></el-table-column>
                  </el-table>
                </el-row>
                <div class="tc btn_margin">
                  <el-button type="success" class="inf_btn  ml20" @click="saveUser">保 存</el-button>
                </div>
              </el-dialog>
            </template>
            <div class="mt20">
              <template>
                <el-table border class="mt20 mb30" style="width: 100%" :data="selUserData">
                  <el-table-column prop="full_name" align="center" label="员工姓名">
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template scope="scope">
                      <el-button v-on:click="delUser(scope.$index)" type="text" class="red_font" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </div>
          </div>
          <div class="tc">
            <button type="button" class="inf_btn mt30 mb20" v-on:click="saveTask">保  存
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
  import moment from 'moment'
  //  import router from '../../router'
  export default {
    data: function () {
      return {
        // 查询课程----------------
        courseData: [],
        // 选中课程
        selCourseData: [],
        // 课程id
        courseids: [],
        // 查询考试----------------
        examData: [],
        // 选中考试
        selExamData: [],
        // 考试id
        examids: [],
        // 查询报名----------------
        enrollmentData: [],
        // 选中报名
        selEnrollmentData: [],
        // 报名id
        enrollmentids: [],
        // 查询员工----------------
        userData: [],
        // 选中员工
        selUserData: [],
        selUserData2: [],
        // 员工id
        userids: [],
        pickerOptions0: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        form: {
          task_title: '',
          task_description: '',
          task_scope: '',
          expiration_date: ''
        },
        formInline: {
          department: '',
          area: '',
          mobile: '',
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
          task_title: [
            {required: true, message: '请输入任务名称', trigger: 'blur'}
          ],
          expiration_date: [
            {type: 'date', required: true, message: '请选择有效时间', trigger: 'change'}
          ],
          task_description: [
            {required: true, message: '请输入任务描述', trigger: 'blur'}
          ],
          task_scope: [
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
      // ********** 课程 ***********
      courseSearch: function () {
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.formInline.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.formInline.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.searchCourse, {
          title: this.formInline.name,
          priName: this.formInline.user,
          categoryid: this.formInline.categoryId ? parseInt(this.formInline.categoryId) : 0,
          // pubdate: this.formInline.date,
          start: date1,
          end: date2,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
            this.courseData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      addCourse: function (title, index) {
        this.selCourseData.push(title)
        console.log(this.selCourseData)
      },
      saveCourse: function () {
        this.courseids.splice(0, this.courseids.length)
        for (var i = 0; i < this.selCourseData.length; i++) {
          this.courseids.push(this.selCourseData[i].course_id)
        }
        this.dialogFormVisible = false
        console.log(this.courseids)
      },
      delCourse: function (index) {
        this.$confirm('此操作将删除该复习资料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.selCourseData.splice(index, 1)
          this.courseids.splice(0, this.courseids.length)
          for (var i = 0; i < this.selCourseData.length; i++) {
            this.courseids.push(this.selCourseData[i].course_id)
          }
          console.log(this.selCourseData)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      // ********** 考试 ***********
      examSearch: function () {
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.formInline.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.formInline.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getExamList, {
          num: this.formInline.exam_num,
          title: this.formInline.name,
          manager: this.formInline.manager_idName,
          start: date1,
          end: date2,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
            this.examData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      pushExam: function (title) {
        this.selExamData.push(title)
        console.log(this.selExamData)
      },
      saveExam: function () {
        this.examids.splice(0, this.examids.length)
        for (var i = 0; i < this.selExamData.length; i++) {
          this.examids.push(this.selExamData[i].exam_id)
        }
        this.dialogFormVisible1 = false
        console.log(this.examids)
      },
      delExam: function (index) {
        this.$confirm('此操作将删除该复习资料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.selExamData.splice(index, 1)
          for (var i = 0; i < this.selExamData.length; i++) {
            this.examids.push(this.selExamData[i].exam_id)
          }
          console.log(this.selCourseData)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      // ********** 报名 ***********
      enrollmentSearch: function () {
        var date1 = ''
        if (this.form.startDate) {
          date1 = moment(this.formInline.startDate).format('YYYY-MM-DD')
        }
        var date2 = ''
        if (this.form.endDate) {
          date2 = moment(this.formInline.endDate).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.getEnrollmentList, {
          title: this.formInline.name,
          teacher: this.formInline.teacher,
          start: date1,
          end: date2,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
            this.enrollmentData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      pushEnrollment: function (title, index) {
        console.log(title)
        this.selEnrollmentData.push(title)
        console.log(this.selEnrollmentData)
      },
      saveEnrollment: function () {
        this.enrollmentids.splice(0, this.enrollmentids.length)
        for (var i = 0; i < this.selEnrollmentData.length; i++) {
          this.enrollmentids.push(this.selEnrollmentData[i].enrollment_id)
        }
        this.dialogFormVisible2 = false
        console.log(this.enrollmentids)
      },
      delEnrollment: function (index) {
        this.$confirm('此操作将删除该复习资料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.selEnrollmentData.splice(index, 1)
          this.enrollmentids.splice(0, this.enrollmentids.length)
          for (var i = 0; i < this.selEnrollmentData.length; i++) {
            this.enrollmentids.push(this.selEnrollmentData[i].enrollment_id)
          }
          console.log(this.selEnrollmentData)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      // ********** 员工 ***********
      userSearch: function () {
        api.fetch(api.uri.searchUserList, {
          fullname: this.formInline.name,
          area: this.formInline.area,
          department: this.formInline.department,
          cellphone: this.formInline.mobile,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
            this.userData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      pushUser: function (title, index) {
        console.log(title)
        this.selUserData.push(title)
        console.log(this.selUserData)
      },
      saveUser: function () {
        this.selUserData = this.selUserData2
        this.userids.splice(0, this.userids.length)
        for (var i = 0; i < this.selUserData.length; i++) {
          this.userids.push(this.selUserData[i].user_id)
        }
        this.dialogFormVisible3 = false
      },
      delUser: function (index) {
        this.$confirm('此操作将删除该复习资料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.selUserData.splice(index, 1)
          this.userids.splice(0, this.userids.length)
          for (var i = 0; i < this.selUserData.length; i++) {
            this.userids.push(this.selUserData[i].user_id)
          }
          console.log(this.selUserData)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      saveTask: function () {
        api.post(api.uri.AddTask, {
          task_title: this.form.task_title,
          task_description: this.form.task_description,
          task_scope: this.form.task_scope,
          expiration_date: this.form.expiration_date,
          courseids: this.courseids,
          examids: this.examids,
          enrollmentperiodids: this.enrollmentids,
          userids: this.userids
        }).then(data => {
          console.log(data)
          if (data.status === 1) {
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      changeFun: function (val) {
        this.selUserData2 = val
      },
      search: function () {
        console.log('search')
      },
      handleCurrentChange: function (val) {
        console.log(val)
        this.currentPage = val
        this.courseSearch()
      },
      handleCurrentChange1: function (val) {
        console.log(val)
        this.currentPage = val
        this.examSearch()
      },
      handleCurrentChange2: function (val) {
        console.log(val)
        this.currentPage = val
        this.EnrollmentSearch()
      },
      handleCurrentChange3: function (val) {
        console.log(val)
        this.currentPage = val
        this.userSearch()
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
