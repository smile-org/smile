<template>
  <div>
    <common-header type="tasking"></common-header>
    <div class="con_main">
      <navigator module="tasking" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">约课管理</span> > <span class="">约课信息管理</span> > <span class="f_blue">约课详情</span></span>
        </nav>
        <div class="con_tab">
          <ul class="tasking_ul">
            <li>
              <span class="tasking_margin">
                <span class="tasking_span">员工姓名 ：</span>
                <span>来说谁</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">部门 ：</span>
                  <span>技术部</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">区域 ：</span>
                  <span>和必胜</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">状态 ：</span>
                  <span>未发布</span>
              </span>
              <span class="tasking_margin">
                <span class="tasking_span">完成进度 ：</span>
                  <span>40%</span>
              </span>
            </li>
            <li>
              <span class="tasking_span">任务名称 ：</span>
              <span class="tasking_spanCon">看就看福建省的看法几点睡呢福建省电话费几点睡呢副教授等你房间卡萨电话费那就是的你放假是否合适京东方你说的你付款几点睡呢</span>
            </li>
            <li>
              <span class="tasking_span">任务描述 ：</span>
              <span>看就看福建省的看法几点睡呢福建省电话费几点睡呢副教授等你房间卡萨电话费那就是的你放假是否合适京东方你说的你付款几点睡呢</span>
            </li>
            <li>
              <span class="tasking_span">目标员工范围 ：</span>
              <span>看就看福建省的看法几点睡呢福建省电话费几点睡呢副教授等你房间卡萨电话费那就是的你放假是否合适京东方你说的你付款几点睡呢</span>
            </li>
          </ul>
          <hr class="hr_line" style="margin-top: 30px;">
          <div class="tasking_a">
            <span class="tit">课程 :</span>
            <div class="con">
              <el-table border class="mt20 " style="width: 100%">
                <el-table-column align="center" prop="itemTitle" label="课程名称" width="">
                </el-table-column>
                <el-table-column align="center" prop="sponsorName" label="状态" width=""></el-table-column>
              </el-table>
            </div>
          </div>
          <hr class="hr_line" style="margin-top: 30px;">
          <div class="tasking_a">
            <span class="tit">考试 :</span>
            <div class="con">
              <el-table border class="mt20 " style="width: 100%">
                <el-table-column align="center" prop="itemTitle" label="考试名称" width="">
                </el-table-column>
                <el-table-column align="center" prop="sponsorName" label="状态" width=""></el-table-column>
              </el-table>
            </div>
            <hr class="hr_line" style="margin-top: 30px;">
            <div class="tasking_a">
              <span class="tit">报名 :</span>
              <div class="con">
                <el-table border class="mt20 " style="width: 100%">
                  <el-table-column align="center" prop="itemTitle" label="报名培训" width="">
                  </el-table-column>
                  <el-table-column align="center" prop="sponsorName" label="状态" width=""></el-table-column>
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
  //  import api from '../../services/api'
  //  import router from '../../router'
  //  import moment from 'moment'
  export default {
    data: function () {
      return {}
    },
    components: {
      commonHeader,
      navigator
    },
    methods: {}
  }
</script>

<style scoped="scope">

</style>
