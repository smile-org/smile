<template>
  <aside>
    <div class="a_tit">
      <span :class="['h_icon','vm',navigator.icon]"></span>
      <span class="vm">{{navigator.name}}</span>
    </div>
    <ul class="tc">
      <li v-for="item in navigator.items" :key="item.routeName"
          v-bind:class="{active: item.name===menu}"
          v-on:click="routeByName(item.routeName)">
        <span class="vm">{{item.text}}</span>
      </li>
    </ul>
  </aside>
</template>

<script>
  import router from '../router'
  export default {
    props: {
      module: '',
      menu: ''
    },
    created () {
      if (!this.module) {
        this.module = 'information'
      }
      if (this.module === 'information') {
        this.navigator = {
          icon: 'aside_icon01',
          name: '公司基本信息',
          items: [{
            text: '公司基本信息',
            routeName: 'informationDetail',
            name: 'detail'
          }, {
            text: '页面定制管理',
            routeName: 'informationEdit',
            name: 'edit'
          }]
        }
      } else if (this.module === 'user') {
        this.navigator = {
          icon: 'aside_icon02',
          name: '员工管理',
          items: [{
            text: '员工信息管理',
            routeName: 'userList',
            name: 'list'
          }, {
            text: '员工登录日志',
            routeName: 'userLog',
            name: 'log'
          }]
        }
      } else if (this.module === 'course') {
        this.navigator = {
          icon: 'aside_icon03',
          name: '课程管理',
          items: [{
            text: '课程类别管理',
            routeName: 'courseCategoryList',
            name: 'categorylist'
          }, {
            text: '课程信息管理',
            routeName: 'courseList',
            name: 'list'
          }, {
            text: '员工学习记录',
            routeName: 'learningList',
            name: 'learnlist'
          }]
        }
      } else if (this.module === 'exam') {
        this.navigator = {
          icon: 'aside_icon04',
          name: '考试管理',
          items: [{
            text: '考试信息管理',
            routeName: 'examList',
            name: 'list'
          }, {
            text: '题库信息管理',
            routeName: 'examQuestionList',
            name: 'question'
          }, {
            text: '员工考试记录',
            routeName: 'examUserList',
            name: 'user'
          }]
        }
      } else if (this.module === 'registration') {
        this.navigator = {
          icon: 'aside_icon05',
          name: '培训报名管理',
          items: [{
            text: '培训报名管理',
            routeName: 'registrationTrainlist',
            name: 'list'
          }, {
            text: '员工报名管理',
            routeName: 'registrationEnrollmentRecord',
            name: 'record'
          }]
        }
      } else if (this.module === 'booking') {
        this.navigator = {
          icon: 'aside_icon06',
          name: '约课管理',
          items: [{
            text: '约课信息管理',
            routeName: 'bookingList',
            name: 'list'
          }]
        }
      } else if (this.module === 'tasking') {
        this.navigator = {
          icon: 'aside_icon10',
          name: '学习任务',
          items: [{
            text: '学习任务管理',
            routeName: 'taskingList',
            name: 'list'
          }, {
            text: '员工学习任务',
            routeName: 'taskingUserList',
            name: 'userList'
          }
          ]
        }
      } else if (this.module === 'membering') {
        this.navigator = {
          icon: 'aside_icon11',
          name: '会员升级',
          items: [{
            text: '会员服务介绍',
            routeName: 'memberList',
            name: 'list'
          }, {
            text: '会员升级咨询',
            routeName: 'memberContact',
            name: 'contact'
          }]
        }
      } else {
        this.navigator = {
          icon: 'aside_icon07',
          name: '系统管理',
          items: [{
            text: '公司管理员',
            routeName: 'adminList',
            name: 'list'
          }, {
            text: '修改密码',
            routeName: 'adminChangePWD',
            name: 'admin'
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
        router.push({name: name})
      }
    }
  }
</script>

<style>

</style>
