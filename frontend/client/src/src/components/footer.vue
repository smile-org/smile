<template>
  <footer class="footer_all">
    <div class="index_footer">
      <router-link v-if="currentTag==1" v-bind:to="{name: 'homepage'}" class="footer_li">
        <img src="./../assets/img/f_home3.png" />
        <!--<span class="menu_img"></span>-->
        <span class="footer_font active">首页</span>
      </router-link>
      <router-link v-else v-bind:to="{name: 'homepage'}" class="footer_li">
        <img src="./../assets/img/f_home.png" />
        <!--<span class="menu_img"></span>-->
        <span class="footer_font">首页</span>
      </router-link>

      <router-link v-if="currentTag==2" v-bind:to="{name: 'discover'}" class="footer_li">
        <img src="./../assets/img/discover2.png" />
        <span class="footer_font active">发现</span>
      </router-link>
      <router-link v-else v-bind:to="{name: 'discover'}" class="footer_li">
        <img src="./../assets/img/discover.png" />
        <span class="footer_font">发现</span>
      </router-link>

      <router-link v-if="currentTag==3" v-bind:to="{name: 'getBookingList'}" class="footer_li">
        <img src="./../assets/img/book_fed.png" />
        <span class="footer_font active">约课</span>
      </router-link>
      <router-link v-else v-bind:to="{name: 'getBookingList'}" class="footer_li">
        <img src="./../assets/img/book_f.png" />
        <span class="footer_font">约课</span>
      </router-link>

      <router-link v-if="currentTag==4" v-bind:to="{name: 'me'}" class="footer_li">
        <img src="./../assets/img/me2.png" />
        <span class="footer_font active">我</span>
      </router-link>
      <router-link v-else v-bind:to="{name: 'me'}" class="footer_li">
        <img src="./../assets/img/me.png" />
        <span class="footer_font">我</span>
      </router-link>

    </div>
  </footer>
</template>
<script>
  export default {
    props: ['currentTag'],
    data: function () {
      return {

      }
    },
    watch: {
      currentTag () {
//          alert(this.currentTag)
      }
    },
    created () {

    },
    methods: {}
  }
</script>
<style scoped>
</style>
