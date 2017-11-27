<template>
  <div>
    <common-header type="information"></common-header>
    <div class="con_main">
      <navigator menu="repository"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">管理驾驶舱</span></span>
        </nav>
        <div class="con_tab">
          <!--<div id="myChart" style="width: 400px;height: 400px;"></div>-->
          <!--<el-row :gutter="20">-->
          <!--<el-col :span="12">-->
          <!--<div id="myChart" style="width: 400px;height: 400px;"></div>-->
          <!--</el-col>-->
          <!--<el-col :span="12"> <div ></div></el-col>-->

          <!--</el-row>-->
          <el-row>
            <el-col :span="11">
              <div class="aa">
                <div class="bb">企业资源统计</div>
                <div id="myChart" style="height: 300px;width: 100%;"></div>
              </div>
            </el-col>
            <el-col :span="2"></el-col>
            <el-col :span="11" class="fr">
              <div class="aa">
                <div class="bb"> 企业行业分布统计</div>
                <div id="myChart2" style="height:270px;width: 100%;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="aa">
                <div class="bb">当前员工数量：<span>{{count}}</span></div>
                <div id="myChart3" style="height: 300px;width: 100%;"></div>
              </div>
            </el-col>
            <el-col :span="2"></el-col>
            <el-col :span="11" class="fr">
              <div class="aa">
                <div class="bb">当前企业数量：<span>{{company_count}}</span></div>
                <div id="myChart4" style="height:300px;width: 100%;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="aa">
                <div class="bb">企业地区分布统计</div>
                <div id="myChart5" style="height: 300px;width: 100%;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="aa">
                <div class="bb">代理商统计</div>
                <div id="myChart6" style="height: 300px;width: 100%;"></div>
              </div>
            </el-col>
          </el-row>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../components/CommonHeader'
  import navigator from '../../components/Navigator'
  //  import axios from 'axios'
  import api from '../../services/api'
  // 引入基本模板
  let echarts = require('../../../node_modules/echarts/lib/echarts')
  // 引入柱状图折线图组件
  require('../../../node_modules/echarts/lib/chart/bar')
  require('../../../node_modules/echarts/lib/chart/line')
  // 引入提示框和title组件
  require('../../../node_modules/echarts/lib/component/tooltip')
  require('../../../node_modules/echarts/lib/component/title')
  // import api from '../../servi_withTaskces/api'
  export default {
    data: function () {
      return {
        company: {},
        count: 0,
        company_count: 0
      }
    },
    mounted () {
      this.drawLine()
      this.drawLine2()
      this.drawLine3()
      this.drawLine4()
      this.drawLine5()
      this.drawLine6()
//      let self = this
//      window.setTimeout(function () {
//        self.count = 11
//      }, 2000)
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
//      axios.all([
//        api.fetch(api.uri.getRepository)
//        api.fetch(api.uri.getHomepageCourse),
//        api.fetch(api.uri.getHomepageCourse),
//        api.fetch(api.uri.getHomepageCourse)
//      ]).then(axios.spread((resourceData, industryData, employeeData, incresingData) => {
//      ]).then(axios.spread((resourceData) => {
//        if (resourceData.status === 1) {
//          var resourceArray = []
//          resourceArray.push(resourceData.result.course_count)
//          resourceArray.push(resourceData.result.exam_count)
//          resourceArray.push(resourceData.result.enrollment_coun)
//          resourceArray.push(resourceData.result.appointment_count)
//          this.drawLine(resourceArray)
//        }
//      }))
      api.fetch(api.uri.getRepository)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var resourceArray = []
            resourceArray.push(resourceData.result.course_count)
            resourceArray.push(resourceData.result.exam_count)
            resourceArray.push(resourceData.result.enrollment_coun)
            resourceArray.push(resourceData.result.appointment_count)
            this.drawLine(resourceArray)
          }
        })
      api.fetch(api.uri.getIndustry)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var arr = []
            var arrName = []
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
              arrName.push(item.name)
            }
            this.drawLine2(arr, arrName)
          }
        })
      api.fetch(api.uri.getEmployee)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var arr = []
            var arrMonth = []
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
              arrMonth.push(item.month)
              if (resourceData.result.length - 1 === i) {
                this.count = item.count
              }
            }
            this.drawLine3(arr, arrMonth)
          }
        })
      api.fetch(api.uri.getIncreasing)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var arr = []
            var arrMonth = []
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
              arrMonth.push(item.month)
              if (resourceData.result.length - 1 === i) {
                this.company_count = item.count
              }
            }
            this.drawLine4(arr, arrMonth)
          }
        })
      api.fetch(api.uri.getGetCompanyArea)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var arr = []
            var arrName = []
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
              arrName.push(item.name)
            }
            this.drawLine5(arr, arrName)
          }
        })
      api.fetch(api.uri.getGetAgency)
        .then((resourceData) => {
          if (resourceData.status === 1) {
            var arr = []
            var arrName = []
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
              arrName.push(item.name)
            }
            this.drawLine6(arr, arrName)
          }
        })
    },
    methods: {
      drawLine (data) {
        let myChart = echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          title: {
            text: '资源统计',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          tooltip: {},
          xAxis: {
            data: ['课程', '考试', '报名', '约课'],
//            axisLabel: {
//              interval: 0,
//              rotate: 40
//            },
            axisTick: {
              length: 2
            }
          },
          yAxis: {},
          series: [
            {
              name: '课程',
              barMaxWidth: '30',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data === undefined ? [] : data
            }
          ]
        })
      },
      drawLine2 (data, data2) {
        let myChart2 = echarts.init(document.getElementById('myChart2'))
        myChart2.setOption({
          title: {
            text: '行业统计',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          tooltip: {},
          grid: {
            x: 80,
            y2: 110
          },
          xAxis: {
            type: 'category',
            data: data2,
            axisLabel: {
              interval: 0,
              rotate: 50
            }
          },
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '课程',
              barMaxWidth: '30',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine3 (data, data3) {
        let myChart3 = echarts.init(document.getElementById('myChart3'))
        myChart3.setOption({
          title: {
            text: '员工增速曲线图',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          grid: {
            x: 40,
            y2: 80
          },
          tooltip: {},
          xAxis: {
            type: 'category',
            data: data3,
            axisLabel: {
              interval: 0,
              rotate: 40
            }
          },
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '课程',
              type: 'line',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine4 (data, data4) {
        let myChart4 = echarts.init(document.getElementById('myChart4'))
        // 绘制图表
        myChart4.setOption({
          title: {
            text: ' 企业增速曲线图',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          grid: {
            x: 40,
            y2: 80
          },
          tooltip: {},
          xAxis: {
            type: 'category',
            data: data4,
            axisLabel: {
              interval: 0,
              rotate: 40
            }
          },
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '课程',
              type: 'line',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine5 (data, data5) {
        let myChart5 = echarts.init(document.getElementById('myChart5'))
        // 绘制图表
        myChart5.setOption({
          title: {
            text: '企业地区分布统计',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          grid: {
            x: 80,
            y2: 110
          },
          tooltip: {},
          xAxis: {
            type: 'category',
            data: data5,
            axisLabel: {
              interval: 0,
              rotate: 50
            }
          },
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '代理商统计',
              type: 'bar',
              barMaxWidth: '40',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine6 (data, data6) {
        let myChart6 = echarts.init(document.getElementById('myChart6'))
        // 绘制图表
        myChart6.setOption({
          title: {
            text: ' 企业增速曲线图',
            x: 'center',
            textStyle: {
              color: '#55b761',
              fontStyle: 'normal',
              fontWeight: '100',
              fontSize: 18
            }
          },
          grid: {
            x: 80,
            y2: 110
          },
          tooltip: {},
          xAxis: {
            type: 'category',
            data: data6,
            axisLabel: {
              interval: 0,
              rotate: 50
            }
          },
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '课程',
              type: 'bar',
              barMaxWidth: '50',
              itemStyle: {
                normal: {
                  color: '#55b761',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
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

  .aa {
    height: 340px;
    border: 1px solid #dcdcdc;
    position: relative;
    margin-top: 30px;
    overflow: hidden;
  }

  .bb {
    width: 100%;
    height: 40px;
    line-height: 40px;
    padding-left: 10px;
    background: #dcdcdc;
    margin-bottom: 20px;
  }
</style>
