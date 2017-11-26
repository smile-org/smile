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
                <div id="myChart" style="height: 300px;"></div>
              </div>
            </el-col>
            <el-col :span="2"></el-col>
            <el-col :span="11" class="fr">
              <div class="aa">
                <div class="bb"> 企业行业分布统计</div>
                <div id="myChart2" style="height:270px;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="aa">
                <div class="bb">当前员工数量：<span>333</span></div>
                <div id="myChart3" style="height: 300px;"></div>
              </div>
            </el-col>
            <el-col :span="2"></el-col>
            <el-col :span="11" class="fr">
              <div class="aa">
                <div class="bb">当前企业数量：<span>22</span></div>
                <div id="myChart4" style="height:300px;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="aa">
                <div class="bb">企业地区分布统计</div>
                <div id="myChart5" style="height: 300px;"></div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="aa">
                <div class="bb">代理商统计</div>
                <div id="myChart6" style="height: 300px;"></div>
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
        company: {}

      }
    },
    mounted () {
      this.drawLine()
      this.drawLine2()
      this.drawLine3()
      this.drawLine4()
      this.drawLine5()
      this.drawLine6()
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
            for (var i = 0; i < resourceData.result.length; i++) {
              var item = resourceData.result[i]
              arr.push(item.count)
            }
            this.drawLine2(arr)
          }
        })
//      api.fetch(api.uri.getEmployee)
//        .then((resourceData) => {
//          if (resourceData.status === 1) {
//            this.drawLine3(resourceData.result)
//          }
//        })
//      api.fetch(api.uri.getIncresing)
//        .then((resourceData) => {
//          if (resourceData.status === 1) {
//            this.drawLine4(resourceData.result)
//          }
//        })
//      api.fetch(api.uri.getGetCompanyArea)
//        .then((resourceData) => {
//          if (resourceData.status === 1) {
//            this.drawLine5(resourceData.result)
//          }
//        })
//      api.fetch(api.uri.getGetAgency)
//        .then((resourceData) => {
//          if (resourceData.status === 1) {
//            this.drawLine4(resourceData.result)
//          }
//        })
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
              type: 'bar',
              itemStyle: {
                normal: {
                  color: 'blue',
                  barBorderRadius: 0
                }
              },
              data: data === undefined ? [] : data
            }
          ]
        })
      },
      drawLine2 (data) {
        let myChart2 = echarts.init(document.getElementById('myChart2'))
        myChart2.setOption({
          title: {
            text: '行业统计是',
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
            y2: 100
          },
          xAxis: {
            data: ['IT互联网/通信/电子', '金融/会计/银行/保险', '房地产/建筑业', '咨询/翻译/中介', '教育/培训', '广告/媒体', '消费品/贸易/批发/零售', '加工制造/仪器仪表', '交通/物流/运输', '制药/医疗', '能源/采掘/化工/环保', '政府/农林牧渔', '其他'],
            axisLabel: {
              interval: 0,
              rotate: 50
            }
          },
          yAxis: {},
          series: [
            {
              name: '课程',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: 'blue',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine3 (data) {
        let myChart3 = echarts.init(document.getElementById('myChart3'))
        // 绘制图表
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
          tooltip: {},
          xAxis: {
            data: ['201701', '201702', '201703', '201704', '201705', '201706', '201707', '201708', '201709', '201710', '201711', '201712'],
            axisLabel: {
//              interval: 0,
//              rotate: 40
              interval: 0
            }
          },
          yAxis: {},
          series: [
            {
              name: '课程',
              type: 'line',
              itemStyle: {
                normal: {
                  color: 'blue',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine4 (data) {
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
          tooltip: {},
          xAxis: {
            data: ['201701', '201702', '201703', '201704'],
            axisLabel: {
//              interval: 0,
//              rotate: 40
            }
          },
          yAxis: {},
          series: [
            {
              name: '课程',
              type: 'line',
              itemStyle: {
                normal: {
                  color: 'blue',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine5 (data) {
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
          tooltip: {},
          xAxis: {
            data: ['201701', '201702', '201703', '201704'],
            axisLabel: {
//              interval: 0,
//              rotate: 40
            }
          },
          yAxis: {},
          series: [
            {
              name: '代理商统计',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: 'blue',
                  barBorderRadius: 0
                }
              },
              data: data
            }
          ]
        })
      },
      drawLine6 (data) {
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
          tooltip: {},
          xAxis: {
            data: ['201701', '201702', '201703', '201704'],
            axisLabel: {
//              interval: 0,
//              rotate: 40
            }
          },
          yAxis: {},
          series: [
            {
              name: '课程',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: 'blue',
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
