<template>

  <div id="app">

    <img src="./assets/logo.png">
    <bar>
      <bar-item path="home" label="首页"></bar-item>
      <bar-item path="me" label="我的"></bar-item>
    </bar>
    <router-view></router-view>
  </div>
</template>

<script>
import Bar from './components/basis/Bar'
import BarItem from './components/basis/BarItem'
export default {
  name: 'app',
  components: {
    Bar,
    BarItem
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
