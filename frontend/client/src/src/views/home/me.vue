<template>
  <div>
    {{user.firstName}}, {{user.lastName}}, {{user.age}}
    <span v-on:click="follow">follow</span> {{number}} followers
  </div>
</template>

<script>
import api from '../../services/api'
// import { Message } from 'element-ui'
export default {
  data () {
    return {
      user: {},
      number: 0
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      api.fetch('/users/getUser/1').then(data => {
        this.user = data
      }).catch(error => {
        console.log(error.message)
      })
    },
    follow () {
      api.post('/users/favorite', {userId: 123}).then(data => {
        this.number = data.number
        this.$message('收藏成功!')
      }).catch(error => {
        console.log(error.message)
      })
    }
  }

}
</script>
