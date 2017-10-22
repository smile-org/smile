<template>
  <div>
    <common-header></common-header>
    <div class="con_main">
      <navigator module="course"></navigator>
      <section class="con_main_r">
        <nav>
          <img src="../../../assets/img/house.png" class="vm">
          <span class="vm">您的当前位置 : <span class="">课程管理</span> > <span class="f_blue">课程类别管理</span></span>
        </nav>
        <div class="con_tab">
          <button class="inf_btn " v-on:click="routeByName('courseCategoryCreate')">添加课程类别</button>
          <table class="approve_tab" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <th>
                <span class="bold">课程类别</span><span></span>
              </th>
              <th>
                <span class="bold">操作</span><span></span>
              </th>
            </tr>
            <tr v-for="item in data" :key="item.category_id">
              <td class="">
                <span>{{item.category_name}}</span>
              </td>
              <td>
                <div class="cell">
                  <button type="button" class="el-button el-button--text el-button--small mr20" v-on:click="edit(item.category_id)">编辑</button>
                  <button type="button" class="el-button el-button--text el-button--small" v-on:click="del(item.category_id)">删除</button>
                </div>
              </td>
            </tr>

          </table>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
  import commonHeader from '../../../components/CommonHeader'
  import navigator from '../../../components/Navigator'
  import api from '../../../services/api'
  import router from '../../../router'
  export default {
    data: function () {
      return {
        data: []
      }
    },
    components: {
      commonHeader,
      navigator
    },
    created () {
      api.fetch(api.uri.getCategory, {skip: 0, take: 10000}).then(data => {
        if (data.status === 1) {
          this.data = data.result
        }
      }).catch(error => {
        alert(error.message)
      })
    },
    methods: {
      routeByName (name) {
        router.push({name: name})
      },
      edit (id) {
        router.push({name: 'courseCategoryEdit', query: {id: id}})
      },
      del (id) {
        this.$confirm('此操作将永久删除该课程分类, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.fetch(api.uri.deleteCategory, {cateid: id}).then(data => {
            api.fetch(api.uri.getCategory, {skip: 0, take: 10000}).then(data1 => {
              if (data1.status === 1) {
                this.data = data1.result
              }
            }).catch(error => {
              alert(error.message)
            })
          }).catch(error => {
            alert(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }

    }
  }
</script>

<style scoped="scope">
  .approve_tab {
    width: 100%;
    text-align: left;
    margin-top: 30px;
    border-right: 1px solid #dcdcdc;
    border-bottom: 1px solid #dcdcdc;
  }

  .approve_tab tr td {
    border-left: 1px solid #dcdcdc;
    border-top: 1px solid #dcdcdc;
    padding: 20px;
  }

  .approve_tab tr {
    text-align: center;
  }

  .approve_tab tr th {
    border-left: 1px solid #dcdcdc;
    border-top: 1px solid #dcdcdc;
    background: #f1efee;
    padding: 20px;
  }

  .approve_sfz .s_sfz img {
    width: 270px;
    height: 170px;
    max-width: 270px;
    max-height: 170px;
    border: 1px solid #dcdcdc;
    border-radius: 4px;
    padding: 5px;
    margin-top: 10px;
  }

  .yj_p {
    margin-left: 70px;
  }

  .el-button--small {
    font-size: 14px;
    color: #00b553;
  }
</style>
