<template>
  <div>
    <common-header type="booking"></common-header>
    <div class="con_main">
      <navigator menu="booking"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">约课信息管理</span> > <span class="f_blue">约课详情</span></span>
        </nav>
        <div class="con_tab">
          <ul class="booking_detail">
            <li>
              <span class="tit">企业名称 :</span>
              <div class="con">
                <span>北京玖點致新科技有限公司</span>
              </div>
            </li>
            <li>
              <span class="tit">主营行业 :</span>
              <div class="con">
                <span>IT/互联网/通信/电子</span>
              </div>
            </li>
            <li>
              <span class="tit">约课主题 :</span>
              <div class="con">
                <span>{{appointmentTitle}}</span>
              </div>
            </li>
            <li>
              <span class="tit">关键字 :</span>
              <div class="con">
                <span>{{appointmentTitle}}</span>
              </div>
            </li>
            <li>
              <span class="tit">发起人 :</span>
              <div class="con">
                <span>{{sponsorName}}</span>
              </div>
            </li>
            <li>
              <span class="tit">发起人联系方式 :</span>
              <div class="con">
                <span>13800001111</span>
              </div>
            </li>
            <li>
              <span class="tit">企业联系人 :</span>
              <div class="con">
                <span>张小娴</span>
              </div>
            </li>
            <li>
              <span class="tit">企业联系方式 :</span>
              <div class="con">
                <span>13800001111</span>
              </div>
            </li>
            <li>
              <span class="tit">发起时间 :</span>
              <div class="con">
                <span>{{sponsorDate | formatDate}}</span>
              </div>
            </li>
            <li>
              <span class="tit">参与人数 :</span>
              <div class="con">
                <span>88</span>
              </div>
            </li>
            <li>
              <span class="tit">需求列表 :</span>

              <div class="con">
                <el-table :data="itemList" border class="mt20 " style="width: 100%">
                  <el-table-column type="index" width="50">
                  </el-table-column>
                  <el-table-column align="center" prop="itemTitle" label="约课需求" width=""></el-table-column>
                  <el-table-column  align="center" prop="sponsorName" label="需求提出者" width=""></el-table-column>
                  <el-table-column   align="center" prop="sponsorDate" label="提出时间" width="">
                    <template scope="scope">
                      {{scope.row.sponsorDate | formatDate}}
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </li>
          </ul>
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " @click="closeAppointment()">返  回</button>
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
  export default {
    data: function () {
      return {
        tableData: [],
        id: '',
        appointmentTitle: '',
        keywords: [],
        sponsorName: '',
        sponsorDate: '',
        itemList: [],
        followerCount: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.id = parseInt(this.$route.query.id)
      console.log(this.id)
      api.fetch(api.uri.backAppointment, {appointmentId: this.id}).then(data => {
        if (data.status === 1) {
          this.appointmentTitle = data.result.appointmentTitle
          this.keywords = data.result.keyWord.split(',')
          this.sponsorName = data.result.sponsorName
          this.sponsorDate = data.result.sponsorDate
          this.itemList = data.result.itemList
        }
      })
      api.fetch(api.uri.backAppointmentFollowers, {appointmentId: this.id}).then(data => {
        if (data.status === 1) {
          this.tableData = data.result
          this.followerCount = data.result.length
        }
      })
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    methods: {
      closeAppointment () {
        console.log(this.id)
        this.$confirm('此操作将关闭该约课需求，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.closeAppointment, {appointmentId: this.id}).then(data => {
            if (data.status === 1) {
              this.$message({
                type: 'success',
                message: '关闭成功!'
              })
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消关闭'
          })
        })
      }
    }
  }
</script>

<style scoped="scope">
  .booking_detail li {
    margin-bottom: 20px;
    font-size: 14px;
    overflow: hidden;
  }

  .booking_detail li .tit {
    width: 140px;
    text-align: left;
    font-weight: bold;
    float: left;
  }

  .booking_detail li .con {
    margin-left: 140px;
    /*float: left;*/
  }

  .booking_detail li .con span {
    margin-right: 20px;
  }
</style>
