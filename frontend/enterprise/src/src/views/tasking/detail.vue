<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="">学习任务管理</span> > <span class="f_blue">学习任务详情</span></span>
        </nav>
        <div class="con_tab">
          <ul class="tasking_ul">
            <li>
              <span class="tasking_margin">
                <span class="tasking_span">员工姓名 ：</span>
                <span>{{CurrentUser.full_name}}</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">部门 ：</span>
                  <span>{{CurrentUser.department}}</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">区域 ：</span>
                  <span>{{CurrentUser.area}}</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">状态 ：</span>
                  <span>{{LearnPercentage}}</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">完成进度 ：</span>
                  <span>{{LearnStatus}}</span>
              </span>
            </li>
            <li>
              <span class="tasking_span">任务名称 ：</span>
              <span class="tasking_spanCon">{{CurrentTask.task_title}}</span>
            </li>
            <li>
              <span class="tasking_span">任务描述 ：</span>
              <span>{{CurrentTask.task_description}}</span>
            </li>
            <li>
              <span class="tasking_span">目标员工范围 ：</span>
              <span>{{CurrentTask.task_scope}}</span>
            </li>
          </ul>
          <hr class="hr_line" style="margin-top: 30px;">
          <div class="tasking_a">
            <span class="tit">课程 :</span>
            <div class="con">
              <el-table border class="mt20 " style="width: 100%" :data="CourseList">
                <el-table-column align="center" prop="0" label="课程名称" width="">
                </el-table-column>
                <el-table-column align="center" prop="1" label="状态" width=""></el-table-column>
              </el-table>
            </div>
          </div>
          <hr class="hr_line" style="margin-top: 30px;">
          <div class="tasking_a">
            <span class="tit">考试 :</span>
            <div class="con">
              <el-table border class="mt20 " style="width: 100%" :data="ExamList">
                <el-table-column align="center" prop="0" label="考试名称" width="">
                </el-table-column>
                <el-table-column align="center" prop="1" label="状态" width=""></el-table-column>
              </el-table>
            </div>
            <hr class="hr_line" style="margin-top: 30px;">
            <div class="tasking_a">
              <span class="tit">报名 :</span>
              <div class="con">
                <el-table border class="mt20 " style="width: 100%" :data="EnrollmentPeriodList">
                  <el-table-column align="center" prop="0" label="报名培训" width="">
                  </el-table-column>
                  <el-table-column align="center" prop="1" label="状态" width=""></el-table-column>
                </el-table>
              </div>
            </div>
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
  //  import router from '../../router'
  //  import moment from 'moment'
  export default {
    data: function () {
      return {
        LearnStatus: '1',
        LearnPercentage: '',
        CurrentUser: {},
        CurrentTask: {},
        CourseList: [],
        ExamList: [],
        EnrollmentPeriodList: []
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.GetUserTaskStatus, {
        taskid: this.$route.query.id
      }).then(data => {
        console.log(data)
        if (data.status === 1) {
          this.CurrentUser = data.result.CurrentUser
          this.CurrentTask = data.result.CurrentTask
          this.LearnPercentage = data.result.LearnPercentage
          this.LearnStatus = data.result.LearnStatus
          this.CourseList = data.result.CourseList
          this.ExamList = data.result.ExamList
          this.EnrollmentPeriodList = data.result.EnrollmentPeriodList
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    methods: {}
  }
</script>

<style scoped="scope">

</style>
