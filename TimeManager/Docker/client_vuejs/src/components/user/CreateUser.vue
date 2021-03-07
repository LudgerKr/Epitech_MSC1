<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <v-row>
        <v-card-text>
          <b-col md="6">
            <b-card-body title="Create a new user" style="color: orange">
              <b-form-input class="input_signup" type="text" placeholder="Firstname" v-model="firstname"></b-form-input>
              <b-form-input class="input_signup" type="text" placeholder="Lastname" v-model="lastname" ></b-form-input>
              <b-form-input class="input_signup" type="text" placeholder="Username" v-model="username" ></b-form-input>
              <b-form-input class="input_signup" type="text" placeholder="Mail" v-model="email"></b-form-input>
              <b-form-input class="input_signup" type="text" placeholder="Password" v-model="u_pwd"></b-form-input>
              <b-form-input class="input_signup" type="text" placeholder="Confirm password" v-model="u_pwd_conf"></b-form-input>
            </b-card-body>
            <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="createUser()">Create</b-button>
          </b-col>
        </v-card-text>
      </v-row>
    </b-container>
</template>

<script>

import axios from "axios";
import VueCookies from "vue-cookies";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "User",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      firstname: "",
      lastname: "",
      username: "",
      email: "",
      u_pwd: "",
      u_pwd_conf: "",
      u_role_id: "",
    }
  },
  methods: {
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    createUser() {
      axios.post('http://localhost:400diapo1/api/admin/users', {
        "user": {
          firstname: this.firstname,
          lastname: this.lastname,
          email: this.email,
          username: this.username,
          password: this.u_pwd,
          password_confirmation: this.u_pwd_conf,
        }
      }, config)
      this.$router.push('/user/' + this.user.id)
    }
  }
}

</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}

</style>