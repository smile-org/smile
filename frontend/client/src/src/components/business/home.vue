<template>
  <div>
    {{user.firstName}}, {{user.lastName}}, {{user.age}}
  </div>
</template>

<script>
import api from '../../services/api'

export default {
  data () {
    return {
      user: {}
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      api.fetch('users/getUser').then(data => {
        this.user = data
      }).catch(error => {
        alert(error.message)
      })
    }
  }
}
</script>
