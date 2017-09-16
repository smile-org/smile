<template>
  <div id="app">
    <bar>
      <bar-item path="/getCourseCategories" label="课程"></bar-item>
    </bar>
    <p>homepage index</p>
    {{user.firstName}}, {{user.lastName}}, {{user.age}}
  </div>
</template>

<script>
import api from '../../services/api'
import Bar from '../../components/Bar'
import BarItem from '../../components/BarItem'

export default {
  data () {
    return {
      user: {}
    }
  },
  components: {
    Bar,
    BarItem
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
