<template>
  <div id="app">
    <!--<header>-->
    <!--<div class="logo_c">-->
    <!--<a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>-->
    <!--<a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>-->
    <!--<a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>-->
    <!--</div>-->
    <!--</header>-->
    <common-header></common-header>
    <section>
      <p class="tasking_member">
        <img src="../../assets/img/progress.png" class="vm mr1" />您打败了<span>30%</span>的同学
      </p>
      <ul class="course_con list_border ">
        <li class="tasking_memberFont">
          <img class="vm mr1" src="../../assets/img/img01.jpg"/>
          <span class="vm mr1">王晓乐 </span>
          <span class="vm">当前完成进度：30%</span>
        </li>
        <li class="tasking_memberFont">
          <img class="vm mr1" src="../../assets/img/img01.jpg"/>
          <span class="vm mr1">王晓乐 </span>
          <span class="vm">当前完成进度：30%</span>
        </li>
        <li class="tasking_memberFont">
          <img class="vm mr1" src="../../assets/img/img01.jpg"/>
          <span class="vm mr1">王晓乐 </span>
          <span class="vm">当前完成进度：30%</span>
        </li>
        <li class="tasking_memberFont">
          <img class="vm mr1" src="../../assets/img/img01.jpg"/>
          <span class="vm mr1">wangdash </span>
          <span class="vm">当前完成进度：30%</span>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
  import api from '../../services/api'
  import axios from 'axios'
  import commonHeader from '../../components/CommonHeader'
  export default {
    data: function () {
      return {}
    },
    components: {
      commonHeader
    },
    filters: {
      formatImage: function (uri) {
        return axios.defaults.imageServer + uri
      }
    },
    methods: {
      // handleClick: function (tab, event) {
      //   this.loadMore()
      // },
      handleStar: function (data) {
        for (var i = 0; i < data.length; i++) {
          var current = data[i]
          if (current && current.star) {
            current.star = current.star.toFixed(1)
          }
        }
      },
      loadMore: function () {
        this.busy = true
        this.currentPage_inProgress = this.currentPage_inProgress + 1
        api.fetch(api.uri.getMyTaskListInProgress, {
          take: this.take,
          skip: this.currentPage_inProgress * this.take
        }).then(data => {
          if (data.status === 1) {
            this.dataInProgress = this.dataInProgress.concat(data.result)
            this.handleStar(this.dataInProgress)
            if (data.result.length === this.take) {
              this.isBusy_inProgress = false
            }
          } else {
            // todo:
          }
        }).catch(error => {
          console.log(error.message)
        })
      }
    }
  }
</script>

<style scoped="scope">

</style>
