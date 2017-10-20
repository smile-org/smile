<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="">课程类别管理</span> > <span class="f_blue">编辑课程类别</span></span>
        </nav>
        <div class="con_tab">
          <div class="el-form-item el-form--label-left mt20">
            <label class="el-form-item__label" style="width: 100px;">课程类别</label>
            <input type="" class="el-input__inner">
          </div>
          <div class="">
            <button type="button" class="inf_btn ">保  存</button>
          </div>

        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../../components/CommonHeader'
  import navigator from '../../../components/Navigator'
  import api from '../../../services/api'
  export default {
    data: function () {
      return {
        company: {}
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
    methods: {}
  }
</script>

<style scoped="scope">
  .approve_tab {
    width: 100%;
    text-align: left;
    margin-top: 30px;
    border-right: 1px solid #dcdcdc;
    border-bottom: 1px solid #dcdcdc;
  }

  .approve_tab tr td {
    border-left: 1px solid #dcdcdc;
    border-top: 1px solid #dcdcdc;
    padding: 20px;
  }

  .approve_tab tr {
    text-align: center;
  }

  .approve_tab tr th {
    border-left: 1px solid #dcdcdc;
    border-top: 1px solid #dcdcdc;
    background: #f1efee;
    padding: 20px;
  }

  .approve_sfz .s_sfz img {
    width: 270px;
    height: 170px;
    max-width: 270px;
    max-height: 170px;
    border: 1px solid #dcdcdc;
    border-radius: 4px;
    padding: 5px;
    margin-top: 10px;
  }

  .yj_p {
    margin-left: 70px;
  }

  .el-button--small {
    font-size: 14px;
    color: #00b553;
  }

  .el-input__inner{
    width:50%;
    min-width: 200px;
    max-width:500px;
  }
  .el-input__inner:focus{
    border-color: #00b553;
  }
</style>
