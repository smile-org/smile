<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="">课程信息管理</span> > <span class="f_blue">编辑课程</span></span>
        </nav>
        <div class="con_tab">
          <el-form ref="form" :inline="true" :model="form" class="demo-form-inline mt20 hidden" label-width="80px">
            <el-col :span="8">
              <el-form-item label="课程名称">
                <el-input v-model="form.name" placeholder="课程名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程类别">
                <el-select v-model="form.region" placeholder="课程类别">
                  <el-option label="课程类别1" value="课程类别1"></el-option>
                  <el-option label="课程类别2" value="课程类别1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="部门">
                <el-input v-model="form.department" placeholder="部门"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="责任人">
                <el-input v-model="form.name" placeholder="责任人"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="有效期">
                <el-date-picker prop="date1" type="date" placeholder="选择日期" v-model="form.date1"
                                style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="简介">
                <el-input v-model="form.department" placeholder="简介"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
          <table class="page_m mt30" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                课程图标
              </td>
              <td class="page_m_b">
                <img :src="bannerSrc | formatImage" width="20%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(1)">使用默认</a>
                <a v-on:click="toggleShow(1)">上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                课程主图
              </td>
              <td class="page_m_b">
                <img :src="bannerSrc | formatImage" width="100%"/>
              </td>
              <td class="page_m_c">
                <a v-on:click="setDefault(2)">使用默认</a>
                <a v-on:click="toggleShow(2)">上传</a>
              </td>
            </tr>
          </table>

          <div class="mt30">
            <p>课程内容
              <el-button type="text" @click="dialogFormVisible = true">添加课程内容</el-button>
            </p>
            <template>
              <el-dialog title="添加/编辑课程内容" :visible.sync="dialogFormVisible">
                <el-form ref="form" :inline="true" :model="formInline" class="demo-form-inline mt20" label-width="80px">
                  <el-form-item label="序号">
                    <el-input v-model="formInline.user" placeholder="序号"></el-input>
                  </el-form-item>
                  <el-form-item label="章节">
                    <el-input v-model="formInline.user" placeholder="章节"></el-input>
                  </el-form-item>
                  <el-form-item label="标题">
                    <el-input v-model="formInline.address" placeholder="标题"></el-input>
                  </el-form-item>
                </el-form>
                <!--<div slot="footer" class="dialog-footer">-->
                <!--<el-button @click="dialogFormVisible = false">取 消</el-button>-->
                <!--<el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>-->
                <!--</div>-->
                <div class="tc btn_margin">
                  <button type="button" class="inf_btn  " v-on:click="routeByName('')">上传课件</button>
                  <button type="button" class="inf_btn  ml20" v-on:click="routeByName('')">保  存</button>
                </div>
              </el-dialog>

            </template>
          </div>
          <div class="mt20">
            <template>
              <el-table :data="tableData" border class="mt20" style="width: 100%">
                <el-table-column prop="num" label="序号" width="100">
                </el-table-column>
                <el-table-column prop="section" label="章节">
                </el-table-column>
                <el-table-column prop="headline" label="标题">
                </el-table-column>
                <el-table-column prop="courseware" label="课件">
                </el-table-column>
                <el-table-column label="操作" class="tc" width="">
                  <template scope="scope">
                    <el-button @click="checkPass(scope.row.id)" type="text" size="small">编辑</el-button>
                    <el-button @click="checkFail(scope.row.id)" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>

          </div>
          <div class="mt30">
            <p><template><el-checkbox v-model="checked">备选项</el-checkbox></template>白名单
              <el-button type="text" @click="dialogTableVisible = true">导入白名单</el-button>
            </p>
            <el-dialog title="导入白名单" :visible.sync="dialogTableVisible">
              <el-form :inline="true" :model="formInline" class="demo-form-inline mt20">
                <el-form-item label="姓名">
                  <el-input v-model="formInline.user" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                  <el-input v-model="formInline.department" placeholder="部门"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
              </el-form>
              <el-table :data="gridData" border>
                <el-table-column property="" label="" width="100">
                  <template scope="scope">
                    <el-checkbox v-model="checked"></el-checkbox>
                  </template>
                </el-table-column>
                <el-table-column property="date" label="姓名" width=""></el-table-column>
                <el-table-column property="name" label="手机" width=""></el-table-column>
                <el-table-column property="address" label="部门"></el-table-column>
              </el-table>
              <div class="tc">
                <button type="button" class="inf_btn mt30 mb20" v-on:click="routeByName('informationEdit')">保  存
                </button>
              </div>
            </el-dialog>
            <div class="baiming_list">
              <el-tag v-for="tag in tags" :key="tag.name" :closable="true" :type="tag.type">{{tag.name}}</el-tag>

            </div>
          </div>
          <div class="tc btn_margin">
            <button type="button" class="inf_btn  " v-on:click="routeByName('')">保  存</button>
            <button type="button" class="inf_btn  ml20" v-on:click="routeByName('')">发布/隐藏</button>
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
  export default {
    data: function () {
      return {
        checked: true,
        formInline: {
          user: '',
          region: ''
        },
//        白名单标签
        tags: [
          {name: '张望的', type: ''},
          {name: '好世界', type: 'gray'},
          {name: '理解', type: 'primary'},
          {name: '你的号', type: 'success'},
          {name: '电话', type: 'warning'},
          {name: '接口净', type: 'danger'}
        ],
        //        白名单标签end
        company: {},
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        gridData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
        dialogTableVisible: false,
        dialogFormVisible: false,
        tableData: [{
          num: '01',
          section: '第一章',
          headline: '论成功如何成功',
          courseware: '第一张.MP4'
        }, {
          num: '01',
          section: '第一章',
          headline: '论成功如何成功',
          courseware: '第一张.MP4'
        }, {
          num: '01',
          section: '第一章',
          headline: '论成功如何成功',
          courseware: '第一张.MP4'
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
      onSubmit: function () {
        console.log('submit!')
      }
    }
  }
</script>

<style>
  .el-dialog__body {
    padding: 10px 20px;
  }

  .el-button--small {
    font-size: 14px;
    color: #00b553;
  }

  .el-button--small:hover, .el-button--small:active, .el-button--small:focus {
    font-size: 14px;
    color: #1DB513;
  }
  .baiming_list{
    width:100%;
    min-height:30px;
    padding:20px;
    border: 1px solid #dcdcdc;
    margin-top:20px;
  }
  .baiming_list span{
    margin-right:20px;
    height: 34px;
    line-height: 32px;
    padding: 0 5px 0 15px;
    font-size: 14px;
  }
  .baiming_list span i{
    margin-left:10px;
  }
</style>
