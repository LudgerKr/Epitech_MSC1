<!-- Fichier contenant SignIn/SignUp-->

<template>
  <div class="hello">
    <b-card no-body class="overflow-hidden" style="max-width: 540px; background-color: black" v-if="show">
      <b-row>
        <b-col md="6" no-gutters>
          <!--<b-img style="max-width: 420px; max-height: 420px" src="http://www.garotasgeeks.com/wp-content/uploads/2012/01/batman.png" alt="Image" class="rounded-0"></b-img>-->
        </b-col>
        <b-col md="6">
          <b-card-body title="Welcome" style="color: orange">
            <b-form-input id="input_email" type="text" placeholder="Email" v-model="u_mail"></b-form-input>
            <b-form-input id="input_pwd" type="password" placeholder="Password" v-model="u_pwd"></b-form-input>

            <b-form-group>
              <b-form-checkbox-group id="checkbox-group-2" v-model="u_role_id" name="flavour-2">
                <b-form-checkbox value="1">Administrator</b-form-checkbox>
                <b-form-checkbox value="2">Manager</b-form-checkbox>
                <b-form-checkbox value="3">Employee</b-form-checkbox>
              </b-form-checkbox-group>
            </b-form-group>

          </b-card-body>
            <b-button style="margin: 0 auto; display: block; background-color: orange;" id="btn_signIn" @click="signIn()">Sign In</b-button>
          <b-form-text>
            Member of the Justice League?<br>
            <a @click="setShow()">Tap Here to Sign Up</a>
          </b-form-text>
        </b-col>
      </b-row>
    </b-card>
    <b-card no-body class="overflow-hidden" style="max-width: 540px; background-color: black" v-if="!show">
      <b-row>
        <b-col md="6" no-gutters>
          <!--<b-img style="max-width: 420px; max-height: 420px" src="http://www.garotasgeeks.com/wp-content/uploads/2012/01/batman.png" alt="Image" class="rounded-0"></b-img>-->
        </b-col>
        <b-col md="6">
          <b-card-body title="Inscription" style="color: orange">
            <b-form-input class="input_signup" type="text" placeholder="Firstname" v-model="user.firstname"></b-form-input>
            <b-form-input class="input_signup" type="text" placeholder="Lastname" v-model="user.lastname" ></b-form-input>
            <b-form-input class="input_signup" type="text" placeholder="Username" v-model="user.username" ></b-form-input>
            <b-form-input class="input_signup" type="text" placeholder="Mail" v-model="user.email"></b-form-input>
            <b-form-input class="input_signup" type="text" placeholder="Password" v-model="u_pwd"></b-form-input>
            <b-form-input class="input_signup" type="text" placeholder="Confirm password" v-model="u_pwd_conf"></b-form-input>
          </b-card-body>
          <b-button style="margin: 0 auto; display: block; background-color: orange;" @click="createUser()">Sign Up</b-button>

          <b-button style="margin: 0 auto; display: block; background-color: orange;" v-on:click="onLogin($event)" @click="createUser(token_id)">Sign Up</b-button>

          <b-form-text>
            Do you have an account?<br>
            <a @click="setShow()">Tap Here to Sign In</a>
          </b-form-text>
        </b-col>
      </b-row>
    </b-card>
  </div>

</template>

<script>
import axios from "axios";
import VueJwtDecode from 'vue-jwt-decode';
import VueCookies from 'vue-cookies';

export default {
  name: 'Home',
  props: ['user'],
  data() {
    return {
      show: true,
      u_mail:"",
      u_pwd: "",
      u_pwd_conf: "",
      u_role_id: "",
      //user
      token_id:"",
      token_decode:""
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
    onLogin : function(){
      this.$emit('child-props', this.user);
    },
    setShow() {
      this.show = !this.show
    },
    signIn() {
      console.log(this.u_role_id[0])
      axios.post('http://localhost:4001/api/sign_in', {
        email: this.u_mail,
        password: this.u_pwd,
        role_id: this.u_role_id[0],
      }).then(response => {
        VueCookies.set('token', response.data.jwt); // j'initilise mon 'token' avec le jwt
        this.token_decode = VueJwtDecode.decode(response.data.jwt); // je décode le token jwt dans token_decode
        this.user.role_id = this.token_decode.role;
        VueCookies.set('role', this.user.role_id);
        this.token_id = this.token_decode.sub; // je prend l'id du token avec token_decode.sub dans token_id
        this.$router.push('/user/' + this.token_id)
      })
    },

    createUser() {
      axios.post('http://localhost:4001/api/sign_up', {
        "user": {
          firstname: this.user.firstname,
          lastname: this.user.lastname,
          email: this.user.email,
          username: this.user.username,
          password: this.u_pwd,
          password_confirmation: this.u_pwd_conf,
        }
      }).then(response => {
        VueCookies.set('token',response.data.jwt); // j'initilise mon 'token' avec le jwt
        this.token_decode = VueJwtDecode.decode(response.data.jwt); // je décode le token jwt dans token_decode
        console.log(VueJwtDecode.decode(response.data.jwt));
        this.user.role_id = this.token_decode.role;
        VueCookies.set('role', this.user.role_id);
        this.token_id = this.token_decode.sub; // je prend l'id du token avec token_decode.sub dans token_id
        this.$router.push({name:'userHome', params:{id:this.token_id}});
      });
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* class */
.input_signup{
  margin-bottom: 20px;
}
.hello{
  align-content: center;
}
.card {
  margin: 10% auto;
  float: none;
  margin-bottom: 10px;
  border: solid orange;
  border-radius: 10px;
}
/* id */
#input_username{
  margin-top: 40px;
  margin-bottom: 40px;
}
#input_pwd{
  margin-top: 40px;
  margin-bottom: 40px;
}

</style>
