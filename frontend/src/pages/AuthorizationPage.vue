<template>
  <div class="wrapper">
    <form-style action="" class="form" id="form" @submit.prevent="auth">
      <h1>Authorisation</h1>
      <input type="text" name="name" placeholder="Name"
             v-model="form.name"/>
      <p
          v-if="validateError(v$.form.name)===1" class="error"
      >{{ this.errors["empty"] }}</p>
      <p
          v-if="validateError(v$.form.name)===5" class="error"
      >{{ this.errors["more_then_one_symbols"] }}</p>
      <div class="wrapper__input">
        <input type="text" name="surname" placeholder="surname"
               v-model="form.surname"/>
        <p
            v-if="validateError(v$.form.surname)===1" class="error"
        > {{ this.errors["empty"] }}</p>
        <p
            v-if="validateError(v$.form.surname)===5" class="error"
        > {{ this.errors["more_then_one_symbols"] }}</p>
      </div>
      <div class="wrapper__input">
        <input type="text" name="email" placeholder="sidny@gmail.com"
               v-model="form.email"/>
        <p
            v-if="validateError(v$.form.email)===1" class="error"
        > {{ this.errors["empty"] }}</p>
        <p
            v-if="validateError(v$.form.email)===4" class="error"
        > {{ this.errors["emailIncorrect"] }}</p>
        <p
            v-if="this.emailExist===true && this.form.email.includes('@')" class="error"
        > {{ this.errors["email_exist"] }}</p>


      </div>
      <div class="wrapper__input">
        <input type="password" name="password" placeholder="password"
               v-model="form.password"/>
        <p
            v-if="validateError(v$.form.password)===1" class="error"
        > {{ this.errors["empty"] }}</p>
        <p
            v-if="validateError(v$.form.password)===3" class="error"
        > {{ this.errors["min_8_symbols"] }}</p>


        <div class="wrapper__input">
          <input id="phone" type="text" name="phone"
                 data-mask="phone"
                 v-mask="'38(0##)###-##-##'"
                 placeholder="+38(0__)___--"
                 v-model="form.phone"/>

        </div>
      </div>
      <div class="wrapper__input">
        <input type="text" name="description" placeholder="some about you"
               v-model="form.description"/>

      </div>
      <br>
      <button type="submit" class="form-button">authorisation</button>
      <br>
      <br>
      <button @click="$router.push('/')" class="form-button">to login</button>

    </form-style>
  </div>

</template>

<script>
import FormStyle from "@/components/UI/Form-style.vue";
import {mask} from "vue-the-mask";
import useVuelidate from "@vuelidate/core";
import {alpha, email, minLength, required} from "@vuelidate/validators";
import UserService from "@/services/UserService.js"
import handleError from "@/errors/handleError.js";

export default {
  setup() {
    return {v$: useVuelidate()}
  },
  name: "Authorization",
  components: {
    FormStyle
  },
  directives: {
    mask
  },
  validations() {
    return {
      form: {
        name: {required, minLength: minLength(2)},
        surname: {required, minLength: minLength(2)},
        email: {email, required},
        password: {required, minLength: minLength(8)}
      }
    }
  },
  data() {
    return {
      form: {
        name: "",
        surname: "",
        email: "",
        password: "",
        phone: "",
        description: ""
      },
      emailExist: false,
      user: "",
      users: [],
      errors: {
        empty: "The field should not be empty",
        emailIncorrect: "Email should be correct!",
        min_8_symbols: "Should be minimum 8 symbols",
        more_then_one_symbols: "Should contain more then 1 symbol",
        email_exist: "Such email already exist",
      }
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
      } else if (paramInvalid === this.v$.form.surname || paramInvalid === this.v$.form.name) {
        if (paramInvalid.$dirty && (paramInvalid.minLength.$invalid || paramInvalid.minLength.$invalid)) return 5// Should contain more then 1 symbol
      }
      return 0;
    },

    auth() {
      if (this.formValidation()) {
        this.insertUser(this.form)
        this.$router.push('/infopage');
        this.setUserRegistered(true);

      }
    },
    setUserRegistered(value) {
      UserService.setUserRegistered(value).then((response) => {
        console.log(response)
      }).catch(error => {
        handleError(error, this.$router)
      })
    },

    formValidation() {
      if (this.ifEmailAlreadyExists() || this.v$.$invalid) {
        this.v$.$touch();
        return false;
      }
      return true;
    },

    ifEmailAlreadyExists() {
      if (this.searchUserByEmail(this.form.email)) return this.emailExist = true;
      return false;
    },

    insertUser(user) {
      console.log(user)
      UserService.insertUser(user).then((response) => {
        console.log(response);
        this.getUsers();
      }).catch(error => {
        handleError(error, this.$router)
      })
    },

    getUsers() {
      UserService.getUsers().then((response) => {
        console.log(response);
        this.users = response.data;
      }).catch(error => {
        handleError(error, this.$router)
      })
    },

    searchUserByEmail(email) {
      return this.users.some(user => user.email === email);
    }
    ,
  }
  ,
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