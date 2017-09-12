
<template>
  <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
    <div v-for="item in data" :key="item.name">
    {{ item.name }}
    </div>
  </div>
</template>

<script>

var counter = 0
export default {
  data: function () {
    return {
      data: [],
      busy: false
    }
  },
  methods: {
    loadMore: function () {
      this.busy = true

      setTimeout(() => {
        for (var i = 0, j = 50; i < j; i++) {
          this.data.push({ name: counter++ })
        }
        this.busy = false
      }, 1000)
    }
  }
}
</script>

<style>

</style>

