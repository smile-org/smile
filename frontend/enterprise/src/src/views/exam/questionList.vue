<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="exam"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">考试管理</span> > <span class="f_blue">题库信息管理</span></span>
        </nav>
        <div class="con_tab">
          <div>
            <button type="button" v-on:click="routeByName('userCreate')" class="inf_btn mr15">添加试题</button>
            <button type="button" @click="dialogTableVisibles = true" class="inf_btn mr15">试题导入</button>
            <el-dialog title="试题导入" :visible.sync="dialogTableVisibles">
              <div class="tc">
                <!--<p class="exal">电子表格文件生成成功</p>-->

              </div>
              <div class="tc">
                <a href="#" download="w3logo" class="inf_btn download" style="display: inline-block;">下  载</a>
                <button v-on:click="dialogTableVisibles = false" type="button" class="qx_btn ml20">取 消</button>
              </div>
            </el-dialog>
            <button type="button" class="inf_btn mr15">下载导入模板</button>
            <el-button type="button" v-on:click="click" :loading="showloading" @click="dialogTableVisible = true" class="inf_btn  export_bor">导  出</el-button>
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
          </div>
          <el-form :inline="true" :model="formInLine" class="demo-form-inline mt20">
            <!--<el-row>-->
            <el-form-item label="题目">
              <el-input v-model="formInLine.user" placeholder="题目"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-select v-model="value" placeholder="请选择">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-col>
                <el-date-picker type="date" placeholder="选择日期" v-model="formInLine.date" style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <button type="button" class="inf_btn mr15">查  询</button>
            </el-form-item>
            <!--</el-row>-->
          </el-form>
          <el-table  :data="tableData" border style="width: 100%">
            <el-table-column prop="examCount" label="试题题目" width="">
            </el-table-column>
            <el-table-column prop="examScore" label="试题类型" width="">
            </el-table-column>
            <el-table-column prop="examTime" label="创建时间" width="">
            </el-table-column>
            <el-table-column label="操作" class="tc" width="">
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
        dialogTableVisibles: false,
        formInLine: {
          department: '',
          area: '',
          name: '',
          mobile: '',
          date: ''
        },
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value: ''
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
//        this.dialogTableVisible = !this.dialogTableVisible,
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
  .el-select .el-input__inner:hover,.el-select .el-input__inner:active,.el-select .el-input__inner:focus {
    border-color: #01b554;
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
