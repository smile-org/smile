<template>
  <div>
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <ul class=" list_border course_line reg_nohover">
        <li class="course_list  line_only">
          <a>
            <div class="hidden">
              <img class="person_header fl" src="../../assets/img/img01.jpg">
              <div class="bm_con">
                <div class="hidden bm_font">
                  <h3 class="fl ">{{data.appointmentTitle}}</h3>
                  <p>主讲：{{data.sponsorName}}
                    <span class="ml2">{{data.sponsorDate | formatDate}}</span>
                  </p>

                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{data.followerCount}}</span>
                    </li>
                  </ul>
                  <span class="see_student">
                    <router-link v-bind:to="{name: 'getBookingFollowers', query: {id: id}}">
                    查看同学
                    </router-link>
                  </span>
                </div>
              </div>
            </div>
            <ul class="keword_num">
              <li v-for="(item, index) in keywords" :key="index">{{keyword}}</li>
            </ul>
          </a>
        </li>
      </ul>
      <div class="demand_list">
        <h3 class="list_need mt3 mb2">需求列表</h3>
        <ul class="list_border course_con mb3hafe">
          <li class="course_list  line_only" v-for="(item, index) in data.itemList" :key="item.itemId">
            <div class="demand_font">
              <p>{{index}}.{{item.content}}</p>
              <el-row class="demand_emo">
                <el-col :span="12" class="fl">
                  <p class="small_f">{{item.sponsorName}}
                    <span class="ml2">{{item.sponsorDate | formatDate}}</span>
                  </p>
                </el-col>
                <el-col :span="12" class="fr">
                  <ul class="small_icon fr">
                    <li class="fl">
                      <span class="icon icon1"></span>
                      <span class="green00b">{{followCount}}</span>
                    </li>
                    <li class="fl">
                      <span id="c_save" @click="like(item)" class="icon icon7"></span>
                    </li>
                  </ul>
                </el-col>
              </el-row>
            </div>
          </li>

        </ul>
      </div>
    </section>
    <footer>
      <button class="login_btn btn_position" @click="open" type="button">添加需求</button>
    </footer>

    <div class="booking_add" v-show="showModal">
      <h3>需求描述</h3>
      <div class="booking_input">
        <input v-model="newRequirement"  type="text">
        <span class="add_img"></span>
        <div class="el-row dio_btn yk">
          <div class="el-col el-col-12">
            <button type="button" class="c_g btn fr mr2" v-on:click="newRequirement">确定</button>
          </div>
          <div class="el-col el-col-12">
            <button type="button" class="c_o  btn fl" v-on:click="cancel">取消</button>
          </div>
        </div>
      </div>
    </div>
    <div class="c-modal" v-show="showModal">
    </div>
  </div>
</template>

<script>
import api from '../../services/api'
import router from '../../router'
export default {
  data: function () {
    return {
      showModal: false,
      data: {},
      id: 0,
      keywords: [],
      newRequirement: ''
    }
  },
  filters: {
    tImage: function (uri) {
      return axios.defaults.baseURL + uri
    },
    formatDate (time) {
      var date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd')
    }
  },
  created () {
    this.id = this.$route.query.id
    api.fetch(api.uri.getBooking, {id: this.id}).then(data => {
      if (data.status === 1) {
        this.data = data.result
        this.keywords = data.result.keyword.split(',')
      } else {
        // TODO: 统一处理
      }
    }).catch(error => {
      // TODO: 统一处理
    })
  },
  methods: {
    like: function (item) {
      if (item.isFollow) {
        // TODO: 统一弹框， 已经点过赞了
      } else {
        api.fetch(api.uri.likeBooking, {appointmentId: this.id, itemId: item.itemId}).then(data => {
          if (data.status === 1) {
            item.isFollow = 1
            this.data.followerCount ++
          } else {
            // TODO: 统一处理
          }
        })
      }
      // TODO
      // api.fetch(api.uri.likeBooking, {appointmentId: this.id, itemId: item.itemId})
    },
    newRequirement: function () {
      api.post(api.uri.newRequirement, {appointmentId: this.id, content: this.newRequirement}).then(data => {
        if (data.status === 1) {
          data.itemList.push({
            content: this.newRequirement,
            itemId: data.result.itemId,
            sponsorDate: data.result.sponsorDate,
            followCount: 0,
            isFollow: 0,
            sponsorName: data.result.sponsorName})
          this.showModal = false
        } else {
          // TODO:
        }
      }).catch(error => {
        // TODO:
      })
    },
    cancel: function () {
      this.showModal = false
    },
    open: function () {
      this.showModal = true
    }
  }
}
</script>

<style>
.el-tabs__active-bar {
  width: 0!important;
  transform: translateX(0rem)!important;
}

.el-tabs__item.is-active:before {
  content: "";
  position: absolute;
  bottom: 0;
  left: .7rem;
  right: .7rem;
  height: 3px;
  background: #00b553;
  z-index: 2;
}

.el-tabs__item {
  padding: 0 .5rem;
  height: .7rem;
  box-sizing: border-box;
  line-height: .5rem;
  display: inline-block;
  list-style: none;
  font-size: .28rem;
  color: #666;
  position: relative;
  width: 50%;
}

.el-tabs__item.is-active {
  color: #00b553;
}

.el-tabs__nav {
  width: 100%;
  text-align: center;
  margin-top: -.3rem;
}

.el-tabs__header {
  border-bottom: 0;
  margin: 0;
}

.el-tabs__nav .el-tabs__item:first-child {
  border-right: 1px solid #ededed;
}

.bm_font p {
  margin-top: .45rem;
}

.course_bg li:after,
.course_con li:before {
  content: "";
  height: 0;
  display: block;
  border-bottom: 0;
  position: absolute;
  left: 0rem;
  right: 0;
  bottom: 0rem;
}

.bm_font .small_icon {
  top: .3rem;
}
</style>
