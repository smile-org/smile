<template>
  <aside>
    <!--<div class="a_tit">-->
      <!--<span class="h_icon aside_icon01 vm"></span>-->
      <!--<span class="vm">{{navigator.name}}</span>-->
    <!--</div>-->
    <ul class="tc">
      <!--<li v-for="item in navigator.items" :key="item.routeName" v-bind:class="{active: item.name===menu}" v-on:click="routeByName(item.routeName)">-->
        <!--<span class="vm">{{item.text}}</span>-->
      <!--</li>-->
      <li  v-on:click="routeByName('repositoryList')" v-bind:class="{active: menu==='repository'}">
        <span class="vm">管理驾驶舱</span>
      </li>
      <li  v-on:click="routeByName('membershipList')" v-bind:class="{active: menu==='membership'}">
        <span class="vm">企业会员管理</span>
      </li>
      <li v-on:click="routeByName('bookingList')" v-bind:class="{active: menu==='booking'}">
        <span class="vm">约课信息管理</span>
      </li>
      <li v-on:click="routeByName('agencyList')" v-bind:class="{active: menu==='agency'}"  >
        <span class="vm">代理商管理</span>
      </li>
      <li v-on:click="routeByName('maintainenceList')" v-bind:class="{active: menu==='4'}"  >
        <span class="vm">运营人员管理</span>
      </li>
    </ul>
  </aside>
</template>

<script>
import router from '../router/index'
export default {
  props: {
    menu: ''
  },
  created () {
    if (!this.menu) {
      this.module = 'repository'
    }
  },
  data: function () {
    return {
      navigator: null
    }
  },
  methods: {
    routeByName: function (name) {
      router.push({ name: name })
    }
  }
}
</script>

<style>

</style>
