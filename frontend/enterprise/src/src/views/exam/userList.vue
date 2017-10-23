<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="f_blue">员工学习记录</span></span>
        </nav>
        <div class="con_tab">

          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="考试编号">
              <el-input v-model="formInline.user" placeholder="考试编号"></el-input>
            </el-form-item>
            <el-form-item label="考试名称">
              <el-input v-model="formInline.user" placeholder="考试名称"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="formInline.address" placeholder="员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date1" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="fr">
              <button type="button" class="line-btn ml20">查  询</button>
              <el-button type="button" v-on:click="click" :loading="showloading" @click="dialogTableVisible = true" class="inf_btn ml20 export_bor">导  出</el-button>
              <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                <div class="tc">
                  <!--<p class="exal">电子表格文件生成成功</p>-->
                  <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                </div>
                <div class="tc">
                  <a href="#" download="w3logo" class="inf_btn download" style="display: inline-block;">下  载</a>
                  <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">取 消</button>
                </div>
              </el-dialog>
            </el-form-item>
          </el-form>
          <el-table  :data="tableData" border style="width: 100%">
            <el-table-column prop="" label="考试名称" width="180">
            </el-table-column>
            <el-table-column prop="" label="考试人" width="180">
            </el-table-column>
            <el-table-column prop="" label="部门" width="180">
            </el-table-column>
            <el-table-column prop="" label="区域" width="180">
            </el-table-column>
            <el-table-column prop="" label="分数" width="180">
            </el-table-column>
            <el-table-column prop="" label="是否通过" width="180">
            </el-table-column>
            <el-table-column prop="" label="开始时间" width="180">
            </el-table-column>
            <el-table-column prop="" label="结束时间" width="180">
            </el-table-column>
            <el-table-column label="操作" class="tc" width="180">
              <template scope="scope">
                <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                <el-button @click="open2" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF" style="margin:10px 38%">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"  :page-size="10" layout="total, prev, pager, next" :total="total"></el-pagination>
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
        data: [],
        take: 20,
        currentPage: 0,
        total: 0,
        showloading: false,
        dialogTableVisible: false,
        formInline: {
          user: '',
          company: {},
          apartment: '',
          address: '',
          date: '',
          date1: ''
        }
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
        console.log(name)
        router.push({ name: name })
      },
      open2 () {
        this.$confirm('此课程将要被删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      click: function () {
        this.showloading = true
      }
    }
  }
</script>

<style >
  .export_bor i{
    color: #fff!important;
  }
  .export_bor{
    border:none;
    color: #fff;
  }
  .export_bor:hover, .export_bor:active,.export_bor:focus{
    color: #fff;
  }
  .el-icon-loading{
    color: #fff;
  }
  .el-icon-loading {
    animation: rotating 1s linear infinite;
    color: #fff;
  }

  /*导出样式*/
  .download {
    line-height: 38px;
    display: inline-block;
  }

  .qx_btn {
    min-width: 120px;
    height: 38px;
    text-align: center;
    color: #fff;
    background: #a4a4a4;
    border-radius: 4px;
    font-size: 16px;
    letter-spacing: 2px;
    cursor: pointer;
    padding: 0 20px;
  }

  .qx_btn:hover, .qx_btn:active, .qx_btn:focus {
    color: #fff;
    background: #c3c3c3;
    outline: none;
  }
  /*导出样式end*/
</style>
