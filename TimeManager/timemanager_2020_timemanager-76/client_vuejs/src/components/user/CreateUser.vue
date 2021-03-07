<template>
    <b-container fluid >
      <b-form>
        <h1> Create a new user </h1>
        <b-form style="width: 500px">
          <b-form-group label="Firstname" label-for="input_1">
            <b-form-input class="input_1" type="text" placeholder="Firstname" v-model="firstname"></b-form-input>
          </b-form-group>
          <b-form-group label="Lastname" label-for="input_2">
            <b-form-input class="input_2" type="text" placeholder="Lastname" v-model="lastname" ></b-form-input>
          </b-form-group>
          <b-form-group label="Username" label-for="input_3">
            <b-form-input class="input_3" type="text" placeholder="Username" v-model="username" ></b-form-input>
          </b-form-group>
          <b-form-group label="Email" label-for="input_3">
            <b-form-input class="input_signup" type="text" placeholder="Password" v-model="email"></b-form-input>
          </b-form-group>
          <b-form-group label="Password" label-for="input_3">
            <b-form-input class="input_3" type="password" placeholder="Mail" v-model="u_pwd"></b-form-input>
          </b-form-group>
          <b-form-group label="Confirm New Password" label-for="input_4">
            <b-form-input class="input_4" type="password" placeholder="Confirm password" v-model="u_pwd_conf"></b-form-input>
          </b-form-group>
        </b-form>
        <b-button-group >
          <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
            <b-icon icon="x-circle"></b-icon>
            Cancel
          </b-button>
          <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="createUser()">
            <b-icon icon="plus-square-fill"></b-icon>
            Create
          </b-button>
        </b-button-group>
      </b-form>
    </b-container>
</template>

<script>

import axios from "axios";
import VueCookies from "vue-cookies";
import VueJwtDecode from "vue-jwt-decode";

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
      token:"",
      token_decode:"",
      token_id:"",
    }
  },
  methods: {
    go_home(){
      this.token =VueCookies.get('token');
      this.token_decode= VueJwtDecode.decode(this.token);
      this.token_id = this.token_decode.sub;
      this.$router.push('/user/'+this.user_id);
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    createUser() {
      axios.post('http://localhost:4001/api/admin/users', {
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
  color: #e85d04;
}

</style>