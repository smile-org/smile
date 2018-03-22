<template>
  <div>
    <common-header type="membering"></common-header>
    <div class="con_main">
      <navigator module="membering" menu="list"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">学习任务</span> > <span class="f_blue">学习任务管理</span></span>
        </nav>
        <div class="con_tab member_bg">
            <table class="page_m info_tab member_tab" cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td class="mem_bg1">服务</td>
                <td class="mem_bg2">免费会员</td>
                <td class="mem_bg3">付费会员</td>
              </tr>
              <tr>
                <td class="">账号数量</td>
                <td class="">最高50个</td>
                <td class=""><i class="el-icon-star-off mem_star"/>根据自己需要订制</td>
              </tr>
              <tr>
                <td class="">学习材料类型</td>
                <td class="">PPT/PDF/word/图片</td>
                <td class=""><i class="el-icon-star-off mem_star"/>PPT/PDF/word/图片/视频</td>
              </tr>
              <tr>
                <td class="">单个材料限制</td>
                <td class="">不超过10M</td>
                <td class=""><i class="el-icon-star-off mem_star"/>不超过500M</td>
              </tr>
              <tr>
                <td class="">课程学习</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">考试</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">课程学习</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">培训报名</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">学习任务</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">约课</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">课件阅后即焚</td>
                <td class="">支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持</td>
              </tr>
              <tr>
                <td class="">无障碍升级</td>
                <td class="">不支持</td>
                <td class=""><i class="el-icon-star-off mem_star"/>支持（免费升级最新功能）</td>
              </tr>
              <tr>
                <td class="">服务期限</td>
                <td class="">1年</td>
                <td class=""><i class="el-icon-star-off mem_star"/>根据需求订制</td>
              </tr>
            </table>
            <img src="../../assets/img/member_plane.png" class="member_plane">
        </div>
      </section>
    </div>

  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
//  import api from '../../services/api'
//  import moment from 'moment'
//  import router from '../../router'
//  import axios from 'axios'
  export default {
    components: {
      commonHeader,
      navigator
    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
