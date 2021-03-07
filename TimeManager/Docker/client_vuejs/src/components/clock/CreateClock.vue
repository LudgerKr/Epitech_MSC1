<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
  <v-row>
    <v-card-text>
      <b-col md="6">
        <b-card-body title="Change team's name" style="color: orange">
          <b-form-input class="input_signup" type="text" placeholder="Time" v-model="time"></b-form-input>
          <b-form-checkbox-group id="checkbox-group-2" v-model="status" name="flavour-2">
            <b-form-checkbox value="true">Enter</b-form-checkbox>
            <b-form-checkbox value="false">Exit</b-form-checkbox>
          </b-form-checkbox-group>
        </b-card-body>
        <router-link :to="{ name: 'clocksUser', params: {id: this.$router.currentRoute.params.user_id} }">
          <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="createClock()">Create</b-button>
        </router-link>
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
  name: "clocksUser",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      time: "",
      status: "",
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
    createClock() {
      axios.post('http://13.68.159.231:4001/api/manager/clocks', {
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
}

</style>