<template>
  <div id="app">
    <header>
      <div class="logo_c">
        <a class="tl" href="##"><img src="../../assets/img/back.png" alt="返回" /></a>
        <a class="tc" href="##"><img src="../../assets/img/logo.png" alt="smile" class="logo1" /></a>
        <a class="tr" href="##"><img src="../../assets/img/home.png" alt="更多" /></a>
      </div>
    </header>
    <section>
      <div class="add_list">
        <div class="add_bg">
          <h3 class="list_need">约课主题</h3>
          <hr>
          <div class="w_p3">
            <img class="add_input" src="../../assets/img/icon9.png" />
            <input class="w_yueke" type="text" v-model="title" placeholder="在此输入列表需求" maxlength=200 minlength=1>
          </div>
        </div>
        <div class="add_bg">
          <h3 class="list_need">关键字</h3>
          <hr>
          <div class="w_p4">
            <el-tag :key="item" v-for="item in keywords" :closable="true" :close-transition="false" @close="handleClose(item)">
              {{item}}
            </el-tag>
            <el-input class="input-new-tag" v-if="inputVisible" v-model="keyword" ref="saveTagInput" size="mini" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm" maxlength="20" minlength="1">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新增关键字</el-button>
          </div>
        </div>
        <div class="add_bg">
          <h3 class="list_need">需求列表</h3>
          <hr>
          <div class="w_p3 mb3hafe">
            <img class="add_input" src="../../assets/img/icon10.png" />
            <img class="add_input2" src="../../assets/img/icon12.png" v-on:click="addRequirement" />

            <input class="w_yueke" type="text" v-model="requirement" placeholder="请在此输入需求" maxlength=200 minlength=1>
            <p v-for="(item, index) in requirements" :key="index">
              {{index+1}}. {{item}}
              <img class="delate_add" src="../../assets/img/icon11.png" v-on:click="removeRequirement(item)" />
            </p>
          </div>
        </div>
      </div>
    </section>
    <footer>
      <el-row class="exam_b">
        <el-col class="btn g_f line_add" :span="12">
          <button class="btn g_f line_add" v-on:click="submit">确认</button>
        </el-col>
        <el-col class="btn o_f" :span="12">
          <button class="btn g_f line_add" v-on:click="cancel">取消</button>
        </el-col>
      </el-row>
    </footer>
  </div>
</template>

<script>
import api from '../../services/api'
import router from '../../router'
// import { required, minLength, maxLength } from 'vuelidate/lib/validators'

export default {
  data: function () {
    return {
      title: '',
      keywords: [],
      requirements: [],
      keyword: '',
      requirement: '',
      inputVisible: false
    }
  },
  // validations: {
  //   title: {
  //     required,
  //     minLength: minLength(1),
  //     maxLength: maxLength(200)
  //   },
  //   keyword: {
  //     required,
  //     minLength: minLength(1),
  //     maxLength: maxLength(20)
  //   },
  //   requirement: {
  //     required,
  //     minLength: minLength(1),
  //     maxLength: maxLength(200)
  //   }
  // },
  methods: {
    submit: function () {
      api.post(api.uri.submitBooking, {
        appointmentTitle: this.title,
        keywords: this.keywords,
        items: this.requirements
      }).then(data => {
        if (data.status === 1) {
          router.push({ name: 'getBookingList' })
        } else {
          // TODO:
        }
      }).catch(error => {
        // TODO:
        console.log(error.message)
      })
    },
    cancel: function () {
      // TODO: 弹出是否确认， 确认回到列表页面
      router.push({ name: 'getBookingList' })
    },

    handleClose (item) {
      this.keywords.splice(this.keywords.indexOf(item), 1)
    },

    removeRequirement (item) {
      this.requirements.splice(this.requirements.indexOf(item), 1)
    },

    addRequirement () {
      if (this.requirement) {
        this.requirements.push(this.requirement)
        this.requirement = ''
      }
    },

    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm () {
      let keyword = this.keyword.trim()
      if (keyword) {
        this.keywords.push(keyword)
      }
      this.inputVisible = false
      this.keyword = ''
    }
  }
}
</script>
<style>
.input-new-tag {
  /*width:1rem;*/
  max-width: 3rem;
  min-width: 1rem;
  height: 1rem;
  line-height: .7rem;
  margin-top: -.8rem!important;
}

.el-button--small {
  padding: .07rem .09rem;
  font-size: .22rem;
  border-radius: .1rem;
}

.el-tag {
  background-color: #dcdcdc;
  padding: 0.05rem .05rem;
  height: .5rem;
  line-height: .4rem;
  font-size: .22rem;
  border-radius: .1rem;
  margin-right: .15rem;
}

.el-tag .el-icon-close {
  color: #999;
}

.el-button--small {
  padding: 0.05rem .1rem;
  height: .5rem;
  line-height: .35rem;
  font-size: .22rem;
}
</style>
