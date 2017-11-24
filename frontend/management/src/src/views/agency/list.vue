<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="agency"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">代理商管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="120px">
            <el-form-item label="代理商名称">
              <el-input v-model="formInline.name" placeholder="代理商名称"></el-input>
            </el-form-item>
            <el-form-item label="代理区域">
              <el-input v-model="formInline.user" placeholder="代理区域"></el-input>
            </el-form-item>
            <el-form-item label="代理权截至日期">
              <el-date-picker class="dateTab_width" type="date" placeholder="代理权截至日期"
                              v-model="formInline.start" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item class="fr hidden mb20 dc_width">
              <button type="button" class="line-btn ml20">查  询</button>
              <button type="button" v-on:click="agencyCreate"  class="inf_btn ml20 export_bor">代理商入驻</button>
            </el-form-item>
          </el-form>
          <hr class="hr_line">
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="date" label="代理商名称" align="center" min-width="180"></el-table-column>
              <el-table-column prop="name" label="法人" align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="联系人" align="center" width="180"></el-table-column>
              <el-table-column prop="date" label="联系方式" align="center" width="180"></el-table-column>
              <el-table-column prop="name" label="代理区域" align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="代理权开始日期" sortable align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="代理权截止日期" sortable align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="服务企业数量" sortable align="center" width="180"></el-table-column>
              <el-table-column label="操作" class="tc" width="100" align="center">
                <template scope="scope">
                  <el-button v-on:click="agencyEdit" type="text" size="small">编辑</el-button>
                  <el-button class="red_font" type="text" size="small">禁止</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="ds_oq_pageF">
              <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                             :page-size="take" layout="total, prev, pager, next"
                             :total="total"></el-pagination>
            </div>
          </template>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  import router from '../../router'
  //  import api from '../../services/api'
  export default {
    data: function () {
      return {
        company: {},
        currentPage: 0,
        take: 10,
        total: 0,
        formInline: {
          name: '',
          user: '',
          // date: '',
          categoryId: '',
          start: '',
          end: ''
        },
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {

    },
    methods: {
      agencyCreate: function () {
        router.push({name: 'agencyCreate'})
      },
      agencyEdit: function () {
        router.push({name: 'agencyEdit'})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      }
    }
  }
</script>

<style scoped="scope">
  .info_tab {
    /*padding: 5px;*/
  }

  .info_tab tr td {
    padding: 10px;
    font-size: 14px;
  }

  .info_tab tr td:first-child {
    /*font-weight:600;*/
    background: #f2f7f1;
  }

  .el-form--inline .el-form-item__label {
    text-align: left !important;
  }

</style>
