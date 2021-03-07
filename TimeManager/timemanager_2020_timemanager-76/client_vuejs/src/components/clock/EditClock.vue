<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
  <v-row>
    <v-card-text>
      <b-col md="6">
        <b-card-body title="Change team's name" style="color: orange">
          <b-form-input class="input_signup" type="text" placeholder="Time" v-model="clock.time"></b-form-input>
          <b-form-checkbox-group id="checkbox-group-2" v-model="clock.status" name="flavour-2">
            <b-form-checkbox value="true">Enter</b-form-checkbox>
            <b-form-checkbox value="false">Exit</b-form-checkbox>
          </b-form-checkbox-group>
        </b-card-body>
        <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="edit()">Edit</b-button>
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
      clock: null,
    }
  },
  mounted () {
    axios.all([
      axios.get('http://localhost:4001/api/manager/clocks/' + this.$router.currentRoute.params.clock_id, config),
    ]).then(axios.spread((clock) => {
      this.clock = clock.data.data
    }));
  },

  methods: {
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    edit() {
      axios.put('http://localhost:4001/api/manager/clocks/' + this.clock.id, {
        "clock": {
          time: this.clock.time,
          status: this.clock.status[0],
        }
      }, config)
      this.$router.push('/clocksUser/' + this.clock.user_id)
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