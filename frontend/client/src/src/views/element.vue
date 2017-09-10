<template>
  <div>
    <el-button>默认按钮</el-button>
    <el-button type="primary">主要按钮</el-button>
    <el-button type="text">文字按钮</el-button>
     <el-input-number style="width: 500px" v-model="num1" @change="handleChange" :min="1" :max="10"></el-input-number>
  </div>
</template>

<script>
// import { Button, InputNumber } from 'element-ui'

export default {
  data () {
    return {
      num1: 1
    }
  },
  methods: {
    handleChange (value) {
      console.log(value)
    }
  }
}
</script>
