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
                <span>{{data.company_name}}</span>
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
                <span>{{data.appointment_title}}</span>
              </div>
            </li>
            <li>
              <span class="tit">关键字 :</span>
              <div class="con">
                <span>{{data.key_word}}</span>
              </div>
            </li>
            <li>
              <span class="tit">发起人 :</span>
              <div class="con">
                <span>{{data.sponsor_idfull_name}}</span>
              </div>
            </li>
            <li>
              <span class="tit">发起人联系方式 :</span>
              <div class="con">
                <span>{{data.sponsor_idcell_phone}}</span>
              </div>
            </li>
            <li>
              <span class="tit">企业联系人 :</span>
              <div class="con">
                <span>{{data.contact_person}}</span>
              </div>
            </li>
            <li>
              <span class="tit">企业联系方式 :</span>
              <div class="con">
                <span>{{data.phone_number}}</span>
              </div>
            </li>
            <li>
              <span class="tit">发起时间 :</span>
              <div class="con">
                <span>{{data.sponsorDate | formatDate}}</span>
              </div>
            </li>
            <li>
              <span class="tit">参与人数 :</span>
              <div class="con">
                <span>{{data.follow_count}}</span>
              </div>
            </li>
            <li>
              <span class="tit">需求列表 :</span>
              <div class="con">
                <el-table :data="data.itemList" border class="mt20 " style="width: 100%">
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
            <button type="button" class="inf_btn  " @click="goBack">返  回</button>
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
  import router from '../../router'
  export default {
    data: function () {
      return {
        data: '',
        id: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      this.id = parseInt(this.$route.query.id)
      api.fetch(api.uri.getAppointment, {
        appointmentid: this.id
      }).then(data => {
        if (data.status === 1) {
          this.data = data.result
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
      goBack: function () {
        router.push({name: 'bookingList'})
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
