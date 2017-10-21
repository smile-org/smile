<template>
  <aside>
    <div class="a_tit">
      <span class="h_icon aside_icon01 vm"></span>
      <span class="vm">{{navigator.name}}</span>
    </div>
    <ul class="tc">
      <li v-for="item in navigator.items" :key="item.routeName" v-on:click="routeByName(item.routeName)">
        <span class="vm">{{item.text}}</span>
      </li>
    </ul>
  </aside>
</template>

<script>
import router from '../router'
export default {
  props: {
    module: ''
  },
  created () {
    if (!this.module) {
      this.module = 'information'
    }
    if (this.module === 'information') {
      this.navigator = {
        name: '公司基本信息',
        items: [{
          text: '公司基本信息',
          routeName: 'informationDetail'
        }, {
          text: '页面定制管理',
          routeName: 'informationEdit'
        }]
      }
    } else if (this.module === 'user') {
      this.navigator = {
        name: '员工管理',
        items: [{
          text: '员工信息管理',
          routeName: 'userList'
        }, {
          text: '员工登录日志',
          routeName: 'userLog'
        }]
      }
    } else if (this.module === 'course') {
      this.navigator = {
        name: '课程管理',
        items: [{
          text: '课程类别管理',
          routeName: 'courseCategoryList'
        }, {
          text: '课程信息管理',
          routeName: 'courseDetail'
        }, {
          text: '员工学习记录',
          routeName: 'courseList'
        }]
      }
    } else if (this.module === 'exam') {
      this.navigator = {
        name: '考试管理',
        items: [{
          text: '考试信息管理',
          routeName: ''
        }, {
          text: '题库信息管理',
          routeName: ''
        }, {
          text: '员工考试记录',
          routeName: ''
        }]
      }
    } else if (this.module === 'registration') {
      this.navigator = {
        name: '培训报名管理',
        items: [{
          text: '培训报名管理',
          routeName: 'registrationDetail'
        }, {
          text: '员工报名管理',
          routeName: 'registrationList'
        }]
      }
    } else if (this.module === 'booking') {
      this.navigator = {
        name: '约课管理',
        items: [{
          text: '约课信息管理',
          routeName: 'bookingList'
        }]
      }
    } else {
      this.navigator = {
        name: '系统管理',
        items: [{
          text: '公司管理员',
          routeName: 'adminList'
        }, {
          text: '修改密码',
          routeName: 'adminChangePWD'
        }]
      }
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
