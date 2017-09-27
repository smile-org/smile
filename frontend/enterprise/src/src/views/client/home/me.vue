<template>
  <div>
    {{user.firstName}}, {{user.lastName}}, {{user.age}}
    <span v-on:click="follow">follow</span> {{number}} followers

    <mt-popup
      v-model="popupVisible"
      popup-transition="popup-fade"
      position="top"
      >
      更新成功
    </mt-popup>
  </div>
</template>

<script>
import api from '../../services/api'

export default {
  data () {
    return {
      user: {},
      number: 0,
      popupVisible: false
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
      this.popupVisible = true
      this.$cookie.set('smile', 'lalala', 1)
      setTimeout(() => {
        this.popupVisible = false
      }, 2000)
      // api.post('/users/favorite', {userId: 123}).then(data => {
      //   this.number = data.number
      //   this.popupVisible = true
      // }).catch(error => {
      //   console.log(error.message)
      // })
    }
  }

}
</script>

