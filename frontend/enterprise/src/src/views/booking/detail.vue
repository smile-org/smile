<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="booking"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">约课管理</span> > <span class="">约课信息管理</span> > <span class="f_blue">约课详情</span></span>
        </nav>
        <div class="con_tab">
          <ul class="booking_detail">
            <li>
              <span class="tit">约课主题 :</span>
              <div class="con">
                <span>销售经理必须具备的技能培训</span>
              </div>
            </li>
            <li>
              <span class="tit">关键字 :</span>
              <div class="con">
                <span>销售经理</span>
                <span>销售技能</span>
                <span>技能培训</span>
              </div>
            </li>
            <li>
              <span class="tit">发起人 :</span>
              <div class="con">
                <span>张三</span>
              </div>
            </li>
            <li>
              <span class="tit">发起时间 :</span>
              <div class="con">
                <span>2017-08-20 12:00:00</span>
              </div>
            </li>
            <li>
              <span class="tit">需求列表 :</span>
              <ul class="con">
                <li>
                  1.<span>销售经理必须具备的技能培训</span>
                  <span>张三</span>
                  <span>2017-08-20 12:00:00</span>
                </li>
                <li>
                  1.<span>销售经理必须具备的技能培训</span>
                  <span>张三</span>
                  <span>2017-08-20 12:00:00</span>
                </li>
                <li>
                  1.<span>销售经理必须具备的技能培训</span>
                  <span>张三</span>
                  <span>2017-08-20 12:00:00</span>
                </li>
              </ul>
            </li>
            <li>
              <span class="tit">参与者 :</span>
              <div class="con">
                <span>100人</span>
                <el-table :data="tableData" border class="mt20" style="width: 100%">
                  <el-table-column prop="user" label="姓名" width="">
                  </el-table-column>
                  <el-table-column prop="role" label="角色" width="">
                  </el-table-column>
                  <el-table-column prop="department" label="部门" width="">
                  </el-table-column>
                  <el-table-column prop="address" label="区域" width="">
                  </el-table-column>
                </el-table>
              </div>
            </li>
          </ul>
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " v-on:click="routeByName('')">关闭约课</button>
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
  export default {
    data: function () {
      return {
        company: {},
        tableData: [{
          user: '张三',
          role: '发起者',
          department: '技术部',
          address: '天安门'
        }, {
          user: '张三',
          role: '发起者',
          department: '技术部',
          address: '天安门'
        }, {
          user: '张三',
          role: '发起者',
          department: '技术部',
          address: '天安门'
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
      }
    }
  }
</script>

<style scoped="scope">
  .booking_detail li {
    margin-bottom: 20px;
    font-size: 14px;
  }

  .booking_detail li .tit {
    width: 100px;
    text-align: left;
    font-weight: bold;
    float: left;
  }

  .booking_detail li .con {
    margin-left: 100px;
    /*float: left;*/
  }

  .booking_detail li .con span {
    margin-right: 20px;
  }
</style>
