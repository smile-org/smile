<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

</script>

<style>
@import './assets/css/common.css';
@import './assets/css/style.css';
.el-input__inner:focus,el-input:focus{
  border-color: #55b761!important;
}
.el-icon-loading {
  color: #fff;
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
</style>
