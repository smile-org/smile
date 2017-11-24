<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="booking"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">约课信息管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="企业名称">
              <el-input v-model="formInline.name" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="主营行业">
              <el-select class="dateTab_width" v-model="formInline.categoryId" placeholder="请选择主营行业">

              </el-select>
            </el-form-item>
            <el-form-item label="约课主题">
              <el-input v-model="formInline.user" placeholder="约课主题"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-date-picker class="dateTab_width" type="date" placeholder="开始时间"
                              v-model="formInline.start" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-date-picker class="dateTab_width" type="date" placeholder="结束时间"
                              v-model="formInline.end" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <button type="button" class="line-btn ml20" >查  询</button>
            </el-form-item>
          </el-form>
          <template>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="date" label="约课主题"  align="center" min-width="180"></el-table-column>
              <el-table-column prop="name" label="企业名称"  align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="主营行业"  align="center" width="180"></el-table-column>
              <el-table-column prop="date" label="发起者"  align="center" width="180"></el-table-column>
              <el-table-column prop="name" label="发起人联系方式"  align="center" width="180"></el-table-column>
              <el-table-column prop="address" label="企业联系人"  align="center" width="180"></el-table-column>
              <el-table-column prop="date" label="发起时间"  align="center" width="180"></el-table-column>
              <el-table-column label="操作" class="tc" width="100" align="center">
                <template scope="scope">
                  <el-button  v-on:click="bookingDetail" type="text" size="small">查看</el-button>
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
  //  import api from '../../servi_withTaskces/api'
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
      bookingDetail: function () {
        router.push({name: 'bookingDetail'})
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

</style>
