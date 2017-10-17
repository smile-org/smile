<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">

        </div>
      </section>
    </div>

  </div>
</template>

<script>
  import router from '../../router'
  export default {
    data: function () {
      return {
        formInline: {
          user: '',
          region: ''
        },
        tableData: [{
          name: '王小虎',
          number: '12323243222',
          workNum: '12345644',
          email: '378999999999@qq.com',
          department: '378999999999@qq.com',
          address: '上海市普陀区金沙江路 1518 弄',
          timeStart: '12345644',
          timeEnd: '12323243222',
          workNumssss: '12345644'
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
    methods: {
      onSubmit: function () {
        console.log('submit!')
      },
      routeByName: function (name) {
        console.log(name)
        router.push({ name: name })
      }
    }
  }
</script>

<style scoped>
  .el-input {
    width: 150px;
  }

  .el-button--primary {
    color: #fff;
    background-color: #00b553;
    border-color: #00b553;
    width: 80px;
  }

  .el-button--primary:hover, .el-button--primary:active {
    background: rgba(0, 181, 83, 0.75);
  }
</style>
