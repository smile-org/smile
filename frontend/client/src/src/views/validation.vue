
<template>
<div>
  <div class="form-group" v-bind:class="{ 'form-group--error': $v.flatA.$error }">
    <label class="form__label">Flat A</label>
    <input class="form__input" v-model.trim="flatA" @input="$v.flatA.$touch()">
  </div><span class="form-group__message" v-if="!$v.flatA.required">Field is required.</span>
  <div class="form-group" v-bind:class="{ 'form-group--error': $v.flatB.$error }">
    <label class="form__label">Flat B</label>
    <input class="form__input" v-model.trim="flatB" @input="$v.flatB.$touch()">
  </div><span class="form-group__message" v-if="!$v.flatB.required">Field is required.</span>
  <div class="form-group" v-bind:class="{ 'form-group--error': $v.forGroup.nested.$error }">
    <label class="form__label">Nested field</label>
    <input class="form__input" v-model.trim="forGroup.nested" @input="$v.forGroup.nested.$touch()">
  </div><span class="form-group__message" v-if="!$v.forGroup.nested.required">Field is required.</span>
  <div class="form-group" v-bind:class="{ 'form-group--error': $v.validationGroup.$error }"></div><span class="form-group__message" v-if="$v.validationGroup.$error">Group is invalid.</span>
  <pre>validationGroup: {{ $v.validationGroup }}</pre>
</div>
</template>
<<script>
import { required } from 'vuelidate/lib/validators'

export default {
  data () {
    return {
      flatA: '',
      flatB: '',
      forGroup: {
        nested: ''
      }
    }
  },
  validations: {
    flatA: { required },
    flatB: { required },
    forGroup: {
      nested: { required }
    },
    validationGroup: ['flatA', 'flatB', 'forGroup.nested']
  }
}
</script>



    // <div id="login">
		// <div>

    //     <label>username</label><input v-model="username" type="text" @input="$v.username.$touch()">
    //     <span v-if="!$v.username.required">Field is required.</span>
    //     <span v-if="!$v.username.email">please use email.</span>
    //     <span v-if="!$v.username.required">Field is required</span>
    //     <span v-if="!$v.username.minLength">Name must have at least {{$v.username.$params.minLength.min}} letters.</span>
    //     <label>password</label><input v-model="password" type="password">
    //     <span v-if="!$v.password.required">password, password, password!!!</span>
    //     {{password}}
    //     <div>
    //       <input v-model.trim="age" @input="$v.age.$touch()">
    //       <span v-if="!$v.age.between">Must be between {{$v.age.$params.between.min}} and {{$v.age.$params.between.max}}</span>
    //     </div>

		// </div>
		// <div>
		// 	<button :disabled="$v.username.$invalid || $v.password.$invalid" v-on:click='login'>登录</button>
		// </div>

