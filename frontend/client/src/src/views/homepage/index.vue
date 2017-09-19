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
      this.user = {
        firstName: 'cui',
        lastName: 'lin',
        age: 18
      }
      // api.fetch('users/getUser').then(data => {
      //   this.user = data
      // }).catch(error => {
      //   alert(error.message)
      // })
    }
  }
}
</script>
