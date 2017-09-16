<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Bar from './components/Bar'
import BarItem from './components/BarItem'
export default {
  components: {
    Bar,
    BarItem
  }
}
</script>

<style>
@import './assets/css/common.css';
@import './assets/css/style.css';
</style>
