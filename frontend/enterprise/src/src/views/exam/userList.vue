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
                            <el-input v-model="formInline.exam_num" placeholder="考试编号"></el-input>
                        </el-form-item>
                        <el-form-item label="考试名称">
                            <el-input v-model="formInline.exam_title" placeholder="考试名称"></el-input>
                        </el-form-item>
                        <el-form-item label="员工姓名">
                            <el-input v-model="formInline.exam_fullname" placeholder="员工姓名"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.start_date"
                                                style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-col>
                                <el-date-picker class="dateTab_width" type="date" placeholder="选择日期" v-model="formInline.end_date"
                                                style="width: 100%;"></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item class="fr">
                            <button type="button" @click="queryExamHistoryList" class="line-btn ml20">查  询</button>
                            <el-button type="button" v-on:click="exportExamHistoryList" :loading="showloading"
                                       class="inf_btn ml20 export_bor">导  出

                            </el-button>
                            <el-dialog title="电子表格文件生成成功" :visible.sync="dialogTableVisible">
                                <div class="tc">
                                    <!--<p class="exal">电子表格文件生成成功</p>-->
                                    <img src="../../assets/img/face_img1.png" class="mb20" style="width: 100px;"/>
                                </div>
                                <div class="tc">
                                    <a class="inf_btn download"
                                       v-bind:href="excelUrl" v-on:click="dialogTableVisible = false"
                                       style="display: inline-block;">下  载</a>
                                    <button v-on:click="dialogTableVisible = false" type="button" class="qx_btn ml20">
                                        取 消

                                    </button>
                                </div>
                            </el-dialog>
                        </el-form-item>
                    </el-form>
                   <hr class="hr_line">
                    <el-table :data="tableData" border style="width: 100%">
                        <el-table-column align="center" prop="exam_num" label="考试编号" width="180">
                        </el-table-column>
                        <el-table-column align="center" prop="exam_title" label="考试编号" width="180">
                        </el-table-column>
                        <el-table-column align="center" prop="full_name" label="考试人" width="180">
                        </el-table-column>
                        <el-table-column align="center" prop="department" label="部门" width="180">
                        </el-table-column>
                        <el-table-column align="center" prop="area" label="区域" width="180">
                        </el-table-column>
                        <el-table-column align="center" prop="score" label="分数" width="100">
                        </el-table-column>
                        <el-table-column align="center" prop="status" label="是否通过" width="120">
                            <template scope="scope">
                                <span v-if="scope.row.status === 'pass'">通过</span>
                                <span v-else>未通过</span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="start_date" label="开始时间" width="180">
                            <template scope="scope">
                                <span>{{scope.row.start_date | formatDate}} </span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="end_date" label="结束时间" width="180">
                            <template scope="scope">
                                <span>{{scope.row.end_date | formatDate}} </span>
                            </template>
                        </el-table-column>
                        <el-table-column  align="center" label="操作" class="tc" width="100">
                            <template scope="scope">
                                <el-button v-on:click="viewDetail(scope.row.history_id)" type="text" size="small">查看
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="tc mt20">
                        <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                                       :page-size="take"
                                       layout="total, prev, pager, next" :total="total"></el-pagination>
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
  import moment from 'moment'
  import axios from 'axios'
  export default {
    data: function () {
      return {
        formInline: {
          exam_num: '',
          exam_title: '',
          exam_fullname: '',
          start_date: '',
          end_date: ''
        },
        tableData: [],
        take: 10,
        currentPage: 1,
        total: 0,
        showloading: false,
        dialogTableVisible: false,
        excelUrl: ''
      }
    },
    components: {
      commonHeader,
      navigator
    },
    filters: {
      formatDate (time) {
        var date = new Date(time)
        return moment(date).format('YYYY-MM-DD')
      }
    },
    created () {
      this.queryExamHistoryList()
    },
    methods: {
      queryExamHistoryList: function () {
        var date1 = ''
        if (this.formInline.start_date) {
          date1 = moment(this.formInline.start_date).format('YYYY-MM-DD')
        }

        var date2 = ''
        if (this.formInline.end_date) {
          date2 = moment(this.formInline.end_date).format('YYYY-MM-DD')
        }

        api.fetch(api.uri.getExamHistoryList, {
          num: this.formInline.exam_num,
          title: this.formInline.exam_title,
          fullname: this.formInline.exam_fullname,
          start: date1,
          end: date2,
          skip: (this.currentPage - 1) * this.take,
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
            console.log(this.tableData)
          }
        })
      },
      handleCurrentChange (pageNum) {
        this.currentPage = pageNum
        this.queryExamHistoryList()
      },
      exportExamHistoryList: function () {
        this.showloading = true
        var date1 = ''
        if (this.formInline.start_date) {
          date1 = moment(this.formInline.start_date).format('YYYY-MM-DD')
        }

        var date2 = ''
        if (this.formInline.end_date) {
          date2 = moment(this.formInline.end_date).format('YYYY-MM-DD')
        }

        api.fetch(api.uri.exportExamHistoryList, {
          num: this.formInline.exam_num,
          title: this.formInline.exam_title,
          fullname: this.formInline.exam_fullname,
          start: date1,
          end: date2
        }).then(data => {
          if (data.status === 1) {
            this.excelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      },
      viewDetail: function (historyId) {
        router.push({name: 'examDetail', query: {history_id: historyId}})
      }
    }
  }
</script>

<style scoped="scope">
    .export_bor i {
        color: #fff !important;
    }

    .export_bor {
        border: none;
        color: #fff;
    }

    .export_bor:hover, .export_bor:active, .export_bor:focus {
        color: #fff;
    }

    .el-icon-loading {
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
