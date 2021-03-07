<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
    <b-form style="width: 500px">
      <h1>Create Clocks</h1>
      <b-form-group>
        <b-form-input class="input_signup" type="text" placeholder="Time" v-model="time"></b-form-input>
        <b-form-checkbox-group id="checkbox-group-2" v-model="status" name="flavour-2">
          <b-form-checkbox value="true">Enter</b-form-checkbox>
          <b-form-checkbox value="false">Exit</b-form-checkbox>
        </b-form-checkbox-group>
      </b-form-group>
      <b-button-group>
        <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
          <b-icon icon="x-circle"></b-icon>
          Cancel
        </b-button>
        <router-link :to="{ name: 'clocksUser', params: {id: this.$router.currentRoute.params.user_id} }">
          <b-button  squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="createClock()">
            <b-icon icon="plus-square-fill"></b-icon>
            Create
          </b-button>
        </router-link>
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
  name: "clocksUser",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      time: "",
      status: "",
      token_id:"",
      token_decode:"",
      token:""
    }
  },
  methods: {
    go_home(){
      this.token =VueCookies.get('token');
      this.token_decode= VueJwtDecode.decode(this.token);
      this.token_id = this.token_decode.sub;
      this.$router.push('/user/'+this.token_id);
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    createClock() {
      axios.post('http://localhost:4001/api/manager/clocks', {
        "clock": {
          time: this.time,
          status: this.status[0],
          user_id:  this.$router.currentRoute.params.user_id
        }
      }, config)
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