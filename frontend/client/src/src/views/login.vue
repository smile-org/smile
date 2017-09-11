<template>
    <div id="login">
		<div>

        <label>username</label><input v-model="username" type="text" @input="$v.username.$touch()">
        <span v-if="!$v.username.required">Field is required.</span>
        <span v-if="!$v.username.email">please use email.</span>
        <span v-if="!$v.username.required">Field is required</span>
        <!--<span v-if="!$v.username.minLength">Name must have at least {{$v.username.$params.minLength.min}} letters.</span>-->
        <label>password</label><input v-model="password" type="password">
        <span v-if="!$v.password.required">password, password, password!!!</span>
        {{password}}
        <div>
          <input v-model.trim="age" @input="$v.age.$touch()">
          <span v-if="!$v.age.between">Must be between {{$v.age.$params.between.min}} and {{$v.age.$params.between.max}}</span>
        </div>

		</div>
		<div>
			<button :disabled="$v.username.$invalid || $v.password.$invalid" v-on:click='login'>登录</button>
		</div>
  </div>

</template>

<script>
import {required, between} from 'vuelidate/lib/validators'
import api from '../services/api'
import router from '../router/index'
export default {
  data: function () {
    return {
      // isDisabled: false,
      username: '',
      password: '',
      age: 0
    }
  },
  validations: {
    username: {
      required
      // email,
      // minLength: minLength(4)
    },
    age: {
      between: between(20, 30)
    },
    password: {
      required
    }
  },
  methods: {
    login: function () {
      var uri = api.uri.login
      api.post(uri, {username: this.username, password: this.password}).then(data => {
        if (data.status) {
          sessionStorage.setItem('smile', 'lalalal')
          router.push({name: 'home'})
        }
      }).catch(error => {
        alert(error.message)
      })
    }
  }

}
</script>

<style>

</style>

