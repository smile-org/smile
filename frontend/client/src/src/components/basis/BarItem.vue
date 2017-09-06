<template>
  <router-link v-bind:to="{path: path}">
    <span class="icon" :class="iconClass"></span>
    <span class="tab-label" v-text="label"></span>
  </router-link>
  <!--<a class="tab-item" v-link="{path: path, activeClass: 'active', replace: true}">

  </a>-->
</template>

<script>
export default {
  props: {
    path: '',
    icon: '',
    label: ''
  }
}
</script>
