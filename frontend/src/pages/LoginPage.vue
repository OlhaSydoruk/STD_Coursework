<template>
  <div class="wrapper">
    <form-style action="" class="form" id="form" @submit.prevent="formValidation">
      <h1>Login</h1>

      <input type="text" name="email" placeholder="sidny@gmail.com"
             v-model="form.email"/>
      <p
          v-if="validateError(v$.form.email)===1" class="error"
      >{{ this.errors["empty"] }}</p>
      <p
          v-if="validateError(v$.form.email)===4" class="error"
      >{{ this.errors["emailIncorrect"] }}</p>
      <p
          v-if="this.form.login===false && this.form.email.includes('@') " class="error"
      >{{ this.errors["notExist"] }}</p>

      <div class="wrapper__input">
        <input type="password" name="password" placeholder="password"
               v-model="form.password"/>
        <p
            v-if="validateError(v$.form.password)===1" class="error"
        > {{ this.errors["empty"] }}</p>
        <p
            v-if="validateError(v$.form.password)===3" class="error"
        > {{ this.errors["min_8_symbols"] }}</p>
        <p
            v-if="this.form.login===false && this.form.password.length>=8 " class="error"
        > {{ this.errors["notExist"] }}</p>

      </div>
      <br>
      <button type="submit" class="form-button"> login</button>
      <br>
      <br>
      <button @click="$router.push('/authorization')" class="form-button">authorization</button>

    </form-style>
  </div>

</template>

<script>
import vSelect from "vue-select";
import FormStyle from "@/Components/UI/Form-style.vue";
import UserService from "@/services/UserService.js"

import useVuelidate from "@vuelidate/core";
import {email, minLength, required} from "@vuelidate/validators";
import handleError from "@/errors/handleError.js";

export default {
  name: "LoginPage",
  components: {
    vSelect, FormStyle
  },
  setup() {
    return {v$: useVuelidate()}
  },
  validations() {

    return {
      form: {
        email: {email, required},
        password: {required, minLength: minLength(8)}
      }
    }
  },
  data() {
    return {
      form: {
        login:true,
        email: "",
        password: ""
      },
      errors: {
        empty: "The field should not be empty",
        emailIncorrect: "Email should be correct!",
        min_8_symbols: "Should be minimum 8 symbols",
        notExist: "Incorrect email or password"
      },
      user: "",
      users: []
    }
  },

  methods: {
    validateError(paramInvalid) {

      if (paramInvalid.$dirty && paramInvalid.required.$invalid) {
        return 1;//The field should not be empty
      } else if (paramInvalid === this.v$.form.email) {
        if (paramInvalid.$dirty && paramInvalid.email.$invalid) return 4;//Email should be correct!
      } else if (paramInvalid === this.v$.form.password) {
        if (paramInvalid.$dirty && paramInvalid.minLength.$invalid) return 3//password should have minimum 8 symbols
      }
      return 0;
    },
    formValidation() {
      if (this.v$.$invalid && !this.userExist()) this.v$.$touch()
          if(!this.userExist()) this.form.login = false;
      else {
        this.searchUser();
        this.$router.push('/infopage');
        this.setUserRegistered(true);
      }
    },

    setUserRegistered(value){
      UserService.setUserRegistered(value).then((response)=>{
        console.log(response)
      }).catch(error => {
        handleError(error, this.$router)
      })
    },

    userExist() {
      return this.users.some(user => user.email === this.form.email && user.password === this.form.password)
    },
    searchUser() {
      let foundUser = {}
      foundUser = this.users.find(user => user.email === this.form.email && user.password === this.form.password);
      if (foundUser) {
        this.user = foundUser;
        this.errors = {};
      } else {
        this.errors = {
          password: "Incorrect email or password",
          email: "Incorrect email or password"
        };
      }
    },


    getUsers() {
      UserService.getUsers().then((response) => {
        console.log(response);
        this.users = response.data;
      }).catch(error => {
        handleError(error, this.$router)
      })
    },
    getUser(id) {

      UserService.getUser(id).then((response) => {
        console.log(response);
        this.user = response.data;
        this.errors = {};
      }).catch(error => {
        handleError(error, this.$router)
      })
    }
  },
  created() {
    this.getUsers();
  }
}
</script>

<style scoped>

.error {
  color: red;
}

.form-registration h1 {

  font-size: 45px;
  font-weight: 900;
  text-shadow: -1px -2px #dcb109, 0 1px 0 #f5de4b;
  font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif, serif;
  margin: 0 0 30px;
  padding: 0;
}

.wrapper {
  align-content: center;
  width: 100%;
}

h1 {
  color: navajowhite;
}

input, v-select {
  color: navajowhite;
}

input::placeholder {
  color: navajowhite;
}

.wrapper__input input, button {
  width: 100%;
  height: 48px;
  border: none;
  border-bottom: 1px solid #fff; /*линия снизу */
  font-family: 'Caveat', sans-serif;
  outline: none;
  text-align: center;
}


.wrapper__input input, select, button:focus {
  outline: none;
  background-color: rgba(0, 0, 0, 0);
  transition: .5s;
  padding: 2px;
}

.form-button {
  width: 100%;
  height: 48px;
  border: none;
  text-align: center;
  color: navajowhite;
  background-color: rgba(0, 0, 0, 0.2);
}
</style>