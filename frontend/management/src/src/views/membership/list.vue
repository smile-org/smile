<template>
  <div>
    <common-header type="course"></common-header>
    <div class="con_main">
      <navigator menu="membership"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">企业会员管理</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline" label-width="80px">
            <el-form-item label="企业名称">
              <el-input v-model="formInline.name" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="省份">
              <el-select class="dateTab_width" v-model="formInline.categoryId" placeholder="请选择省份">
                <el-option
                  v-for="item in categoryList"
                  :key="item.category_id"
                  :label="item.category_name"
                  :value="item.category_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="主营行业">
              <el-select class="dateTab_width" v-model="formInline.categoryId" placeholder="请选择主营行业">

              </el-select>
            </el-form-item>
            <el-form-item label="代理商">
              <el-input v-model="formInline.user" placeholder="代理商"></el-input>
            </el-form-item>
            <el-form-item label="入驻时间">
              <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date" style="width: 100%;"></el-date-picker>
              </el-col>
              <el-col class="line ml_10style" :span="2">—</el-col>
              <el-col :span="11">
                <el-time-picker type="fixed-time" placeholder="选择时间" v-model="formInline.time" style="width: 100%;"></el-time-picker>
              </el-col>
            </el-form-item>
            <el-form-item class="fr hidden mb20 dc_width">
              <button type="button" class="line-btn ml20">查  询</button>
              <button type="button" v-on:click="membershipCreate"  class="inf_btn ml20 export_bor">企业入驻</button>
            </el-form-item>
          </el-form>
          <hr class="hr_line">
          <el-table :data="tableData" border style="width: 100%" >
            <el-table-column prop="title" align="center" label="企业名称" min-width="180">
            </el-table-column>
            <el-table-column prop="category_name" align="center" label="联系人" min-width="140">
            </el-table-column>
            <el-table-column prop="principal_user_idName" align="center" label="联系电话" width="120">
            </el-table-column>
            <el-table-column prop="department" label="省份" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="expiration_date" label="主营行业" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="type_name" label="代理商" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="publish_date" label="入驻时间" align="center" width="120">
            </el-table-column>
            <el-table-column prop="ispublished" label="状态" align="center" min-width="100">
              <template scope="scope">
                {{scope.row.ispublished ? "有效" : "禁用"}}
              </template>
            </el-table-column>
            <el-table-column label="操作" class="tc" width="140" align="center">
              <template scope="scope">
                <el-button @click="edit(scope.row.course_id)" type="text" size="small">编辑</el-button>
                <el-button @click="updateState(scope.row)"  class="red_font"  type="text" size="small">{{scope.row.ispublished ? '禁用' : '有效'}}</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="ds_oq_pageF">
            <el-pagination @current-change="handleCurrentChange" :current-page="currentPage"
                           :page-size="take" layout="total, prev, pager, next"
                           :total="total"></el-pagination>
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
  import axios from 'axios'
  export default {
    data: function () {
      return {
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
        tableData: [],
        categoryList: [{
          category_id: -1,
          category_name: '请选择'
        }],
        showloading: false,
        exportExcelUrl: '',
        dialogTableVisible: false
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.searchCourse, {
        title: this.formInline.name,
        priName: this.formInline.user,
        categoryid: this.formInline.categoryId ? parseInt(this.formInline.categoryId) : 0,
        // pubdate: this.formInline.date,
        start: this.formInline.start,
        end: this.formInline.end,
        skip: 0,
        take: this.take
      }).then(data => {
        if (data.status === 1) {
          this.tableData = data.result
          this.total = data.total
          api.fetch(api.uri.getCategory, {skip: 0, take: this.take}).then(data1 => {
            if (data1.status === 1) {
              this.categoryList = this.categoryList.concat(data1.result)
            }
          }).catch(err => {
            this.$message(err.message)
          })
        }
      }).catch(error => {
        this.$message(error.message)
      })
    },
    methods: {
      membershipCreate: function () {
        router.push({name: 'membershipCreate'})
      },
      addCourse: function () {
        router.push({name: 'courseCreate'})
      },
      handleCurrentChange: function (val) {
        this.currentPage = val
        this.search()
      },
//      getComments: function (id) {
//        router.push({name: 'courseComment', query: {id: id}})
//      },
      edit: function (id) {
        router.push({name: 'courseEdit', query: {id: id}})
      },
      del: function (id) {
        this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteCourse, {courseid: id}).then(data => {
            this.search()
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      search: function () {
        var start = ''
        var end = ''
        if (this.formInline.start) {
          start = moment(this.formInline.start).format('YYYY-MM-DD')
        }
        if (this.formInline.end) {
          end = moment(this.formInline.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.searchCourse, {
          title: this.formInline.name,
          priName: this.formInline.user,
          categoryid: (this.formInline.categoryId && this.formInline.categoryId !== -1) ? parseInt(this.formInline.categoryId) : 0,
          // pubdate: pubDate,
          start: start,
          end: end,
          skip: this.take * (this.currentPage - 1),
          take: this.take
        }).then(data => {
          if (data.status === 1) {
            this.tableData = data.result
            this.total = data.total
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      updateState (current) {
        // 隐藏传0， 发布传1
        var _type = 0
        if (current.ispublished === true) {
          _type = 0
        } else {
          _type = 1
        }
        api.fetch(api.uri.publishCourse, {
          courseid: current.course_id,
          publish: _type
        }).then(data => {
          if (data.status === 1) {
            this.$message('操作成功')
            current.ispublished = !current.ispublished
            if (current.ispublished) {
              console.log(moment())
              current.publish_date = moment().format('YYYY-MM-DD')
            } else {
              current.publish_date = ''
            }
          }
        }).catch(error => {
          this.$message(error.message)
        })
      },
      exportQuestionList () {
        this.showloading = true
        // var pubDate = ''
        // if (this.formInline.date) {
        //   pubDate = moment(this.formInline.date).format('YYYY-MM-DD')
        // }
        var start = ''
        var end = ''
        if (this.formInline.start) {
          start = moment(this.formInline.start).format('YYYY-MM-DD')
        }
        if (this.formInline.end) {
          end = moment(this.formInline.end).format('YYYY-MM-DD')
        }
        api.fetch(api.uri.exportCourseList, {
          title: this.formInline.name,
          priName: this.formInline.user,
          categoryid: (this.formInline.categoryId && this.formInline.categoryId !== -1) ? parseInt(this.formInline.categoryId) : 0,
          // pubdate: pubDate
          start: start,
          end: end
        }).then(data => {
          if (data.status === 1) {
            console.log(data.result)
            this.exportExcelUrl = axios.defaults.imageServer + data.result
            this.showloading = false
            this.dialogTableVisible = true
          }
        })
      }
    }
  }
</script>

<style scoped="scope">
  .el-input {
    width: 100%;
  }
</style>
