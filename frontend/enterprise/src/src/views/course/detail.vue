<template>
  <div>
    <header>
      <div class="logo">
        <img class="vm" src="../../assets/img/small_logo.png"/>
        <span class="vm">企业管理后台</span>
      </div>
      <ul class="fr">
        <li>
          <span class="h_icon h_icon01"></span>
          <p>基本信息</p>
        </li>
        <li>
          <span class="h_icon h_icon02"></span>
          <p>员工管理</p>
        </li>
        <li>
          <span class="h_icon h_icon03"></span>
          <p>课程类别</p>
        </li>
        <li>
          <span class="h_icon h_icon04"></span>
          <p>考试管理</p>
        </li>
        <li>
          <span class="h_icon h_icon05"></span>
          <p>报名管理</p>
        </li>
        <li>
          <span class="h_icon h_icon06"></span>
          <p>约课管理</p>
        </li>
        <li>
          <span class="h_icon h_icon07"></span>
          <p class="name_e">公司管理员</p>
        </li>
        <li>
          <span class="h_icon h_icon08"></span>
          <p>退出</p>
        </li>
      </ul>
    </header>
    <div class="con_main">
      <aside>
        <div class="a_tit">
          <span class="h_icon aside_icon03 vm"></span>
          <span class="vm">课程管理</span>
        </div>
        <ul class="tc">
          <li>
            <span class="vm">课程类别管理</span>
          </li>
          <li>
            <span class="vm">课程信息管理</span>
          </li>
          <li>
            <span class="vm">员工学习记录</span>
          </li>
        </ul>
      </aside>
      <section class="con_main_r">
        <nav>
          <img src="../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="f_blue">基本信息</span></span>

        </nav>
        <div class="con_tab">
          <table class="page_m" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td class="page_m_a">
                logo
              </td>
              <td class="page_m_b">
                <img src="../../assets/img/logo1.png" width="20%"/>
              </td>
              <td class="page_m_c">
                <a>使用默认</a>
                <a>上传</a>
              </td>
            </tr>
            <tr>
              <td class="page_m_a">
                banner
              </td>
              <td class="page_m_b">
                <img src="../../assets/img/banner_05.png" width="100%"/>
              </td>
              <td class="page_m_c">
                <a>使用默认</a>
                <a>上传</a>
              </td>
            </tr>
          </table>
          <div class="tc">
            <button class="inf_btn btn_margin" v-on:click="routeByName('informationShow')">预  览</button>
          </div>
        </div>
      </section>
    </div>

  </div>
</template>

<script>
  export default {

  }
</script>

<style>

</style>
